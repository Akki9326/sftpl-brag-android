package com.pulse.brag.views.erecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pulse.brag.views.erecyclerview.loadmore.LoadMoreFooterView;
import com.pulse.brag.views.erecyclerview.loadmore.LoadMoreViewStateListener;
import com.pulse.brag.views.erecyclerview.loadmore.OnLoadMoreListener;
import com.pulse.brag.views.erecyclerview.pulltorefresh.OnRefreshListener;
import com.pulse.brag.views.erecyclerview.pulltorefresh.RefreshViewStateListener;
import com.pulse.brag.views.erecyclerview.pulltorefresh.SwipeRefreshHeaderView;
import com.pulse.brag.views.erecyclerview.quickreturn.QuickReturnRecyclerViewOnScrollListener;
import com.pulse.brag.views.erecyclerview.quickreturn.QuickReturnViewType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Multi purpose recycler view.
 * Created by Emre Eran on 11/12/15.
 */
public class ERecyclerView extends RecyclerView {

    protected int PAGE_SIZE = 10;
    protected SwipeRefreshHeaderView mPullToRefreshView;
    protected LoadMoreFooterView mLoadMoreView;
    protected ArrayList<View> mHeaderViewList = new ArrayList<>();
    protected ArrayList<View> mFooterViewList = new ArrayList<>();
    protected ArrayList<View> mInjectedViewList = new ArrayList<>();
    protected ArrayList<Integer> mInjectedViewPositions = new ArrayList<>();
    private int mCurrentInjectedView = 0;

    protected WrapAdapter mWrapAdapter;
    protected boolean isLoadingData = false;

    private int mPrevItemCount = 0;
    private float mLastY = -1;
    private static final float DRAG_RATE = 3;
    private boolean isDataAvailable = true;
    private boolean isPullToRefresh = false;
    private boolean isLoadingMore = false;
    private boolean hasQuickReturnHeader = false;
    private boolean hasQuickReturnFooter = false;
    private boolean isSnapable = false;
    private int mQuickReturnHeaderHeight;
    private int mQuickReturnFooterHeight;
    private QuickReturnRecyclerViewOnScrollListener mOnScrollListener;
    private View mQuickReturnHeaderView;
    private View mQuickReturnFooterView;

    private static final int ITEM_REFRESH_HEADER = 9999991;
    private static final int ITEM_HEADER = 9999992;
    private static final int ITEM_FOOTER = 9999993;
    private static final int ITEM_LOAD_MORE = 9999994;
    private static final int ITEM_PLACEHOLDER_HEADER = 9999995;
    private static final int ITEM_INJECTED = 9999996;

    public ERecyclerView(Context context) {
        this(context, null);
    }

    public ERecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ERecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPageSize(int PAGE_SIZE) {
        this.PAGE_SIZE = PAGE_SIZE;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (hasQuickReturnHeader) {
            if (hasQuickReturnFooter) {
                mOnScrollListener = new QuickReturnRecyclerViewOnScrollListener
                        .Builder(QuickReturnViewType.BOTH)
                        .header(mQuickReturnHeaderView)
                        .minHeaderTranslation(-mQuickReturnHeaderHeight)
                        .footer(mQuickReturnFooterView)
                        .minFooterTranslation(mQuickReturnFooterHeight)
                        .isSnappable(isSnapable)
                        .build();
            } else {
                mOnScrollListener = new QuickReturnRecyclerViewOnScrollListener
                        .Builder(QuickReturnViewType.HEADER)
                        .header(mQuickReturnHeaderView)
                        .minHeaderTranslation(-mQuickReturnHeaderHeight)
                        .isSnappable(isSnapable)
                        .build();
            }
            addOnScrollListener(mOnScrollListener);
        } else if (hasQuickReturnFooter) {
            QuickReturnRecyclerViewOnScrollListener onScrollListener = new QuickReturnRecyclerViewOnScrollListener
                    .Builder(QuickReturnViewType.FOOTER)
                    .footer(mQuickReturnFooterView)
                    .minFooterTranslation(mQuickReturnFooterHeight)
                    .isSnappable(isSnapable)
                    .build();
            addOnScrollListener(onScrollListener);
        }

        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mPrevItemCount = adapter.getItemCount();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (isOnTop() && isPullToRefresh) {
                    mPullToRefreshView.onSwipe(deltaY / DRAG_RATE);
                    if (mPullToRefreshView.getVisibleHeight() > 0 && mPullToRefreshView.getState() < RefreshViewStateListener.STATE_REFRESHING) {
                        return false;
                    }
                }
                break;
            default:
                mLastY = -1;
                if (isOnTop() && isPullToRefresh) {
                    mPullToRefreshView.onRelease();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE && !isLoadingData && isLoadingMore) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }

            if (lastVisibleItemPosition >= PAGE_SIZE) {
                if (layoutManager.getChildCount() > 0 &&
                        lastVisibleItemPosition >= layoutManager.getItemCount() - 1 &&
                        layoutManager.getItemCount() >= layoutManager.getChildCount()) {

                    if (isDataAvailable &&
                            !(mPullToRefreshView != null && mPullToRefreshView.getState() >= RefreshViewStateListener.STATE_REFRESHING)) {
                        mLoadMoreView.onLoad();
                        isLoadingData = true;
                    }
                }
            }
        }
    }

    public void removeListeners() {
        removeOnScrollListener(mOnScrollListener);
    }


    // ---- QuickReturn begin

    public void setQuickReturnHeader(View view, int viewHeight) {
        mQuickReturnHeaderHeight = viewHeight;
        hasQuickReturnHeader = true;
        mQuickReturnHeaderView = view;
    }

    public void setQuickReturnFooter(View view, int viewHeight) {
        mQuickReturnFooterHeight = viewHeight;
        hasQuickReturnFooter = true;
        mQuickReturnFooterView = view;
    }

    public void setQuickReturnHeaderHeight(int height) {
        mQuickReturnHeaderHeight = height;
        mWrapAdapter.changeQuickReturnPlaceholderHeight(height);
    }

    public void setQuickReturnViewsSnapable(boolean isSnapable) {
        this.isSnapable = isSnapable;
    }

    // ---- QuickReturn end

    public void setPullToRefreshView(int resourceId, @Nullable RefreshViewStateListener listener) {
        SwipeRefreshHeaderView view = new SwipeRefreshHeaderView(getContext());
        mPullToRefreshView = view.createView(resourceId, listener);
        isPullToRefresh = true;
    }

    public void setLoadMoreView(int resourceId, @Nullable LoadMoreViewStateListener listener) {
        LoadMoreFooterView view = new LoadMoreFooterView(getContext());
        mLoadMoreView = view.createView(resourceId, listener);
        isLoadingMore = true;
    }

    public void addHeaderView(View view) {
        mHeaderViewList.add(view);
        if (mWrapAdapter != null) {
            mWrapAdapter.setItemPositions();
        }
    }

    public void addFooterView(View view) {
        mFooterViewList.add(view);
        if (mWrapAdapter != null) {
            mWrapAdapter.setItemPositions();
        }
    }

    public void injectView(View view, int position) {
        mInjectedViewList.add(view);
        mInjectedViewPositions.add(position);
        if (mWrapAdapter != null) {
            mWrapAdapter.setItemPositions();
        }
    }

    public void updateInjectedViewPositions(int change) {
        mWrapAdapter.updateInjectedViewPositions(change);
    }

    public void refreshComplete() {
        mPullToRefreshView.onComplete();
        mWrapAdapter.setItemPositions();
    }

    public void loadMoreComplete(boolean noMoreDataAvailable) {
        try {
            isLoadingData = false;
            mLoadMoreView.onComplete();
            mWrapAdapter.setItemPositions();
            int itemCount = getAdapter().getItemCount();
            if (noMoreDataAvailable) {
                isDataAvailable = false;
                mWrapAdapter.removeLoadMoreView();
            }
            mPrevItemCount = itemCount;
        } catch (Exception e) {

        }
    }

    protected int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    protected boolean isOnTop() {
        return getChildCount() == 0 || getChildAt(0).getTop() == 0;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreView.setOnLoadMoreListener(listener);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        if (mPullToRefreshView != null) {
            mPullToRefreshView.setOnRefreshListener(listener);
        }
    }

    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    public void setIsLoadingMore(boolean b) {
        isLoadingMore = b;
        isDataAvailable = b;
    }

    private class WrapAdapter extends Adapter<ViewHolder> {
        private Adapter adapter;

        private HashMap<Integer, View> mViewMap;
        private int mFooterStartPosition;
        private int mListItemsStartPosition;
        private int mRefreshHeaderPosition;
        private int mCurrentHeaderPosition;
        private int mCurrentFooterPosition;
        private int mLoadMorePosition;

        public WrapAdapter(Adapter adapter) {
            this.adapter = adapter;
            setItemPositions();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);

                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return mViewMap.containsKey(position) ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && mViewMap.containsKey(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case ITEM_PLACEHOLDER_HEADER:
                    return new SimpleViewHolder(mViewMap.get(0));
                case ITEM_REFRESH_HEADER:
                    return new SimpleViewHolder(mViewMap.get(mRefreshHeaderPosition));
                case ITEM_HEADER:
                    View header = mViewMap.get(mCurrentHeaderPosition);
                    mCurrentHeaderPosition++;
                    return new SimpleViewHolder(header);
                case ITEM_FOOTER:
                    View footer = mViewMap.get(mCurrentFooterPosition);
                    mCurrentFooterPosition++;
                    return new SimpleViewHolder(footer);
                case ITEM_LOAD_MORE:
                    View loadMore = mViewMap.get(mLoadMorePosition);
                    return new SimpleViewHolder(loadMore);
                case ITEM_INJECTED:
                    int injectedViewPosition = mInjectedViewPositions.get(mCurrentInjectedView);
                    mCurrentInjectedView++;
                    if (mCurrentInjectedView > mInjectedViewPositions.size()) {
                        mCurrentInjectedView = 0;
                    }
                    return new SimpleViewHolder(mViewMap.get(injectedViewPosition));
                default:
                    return adapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mViewMap.containsKey(position)) {
                return;
            }

            if (adapter != null && adapter.getItemCount() > 0) {
                int adapterPosition = position - mListItemsStartPosition;
                adapterPosition -= getInjectedViewCountUntilPosition(position);
                adapter.onBindViewHolder(holder, adapterPosition);
            }
        }

        @Override
        public int getItemCount() {
            int count = 0;

            if (hasQuickReturnHeader) {
                count++;
            }

            if (isPullToRefresh) {
                count++;
            }

            if (isLoadingMore) {
                count++;
            }

            count += mHeaderViewList.size();
            count += mFooterViewList.size();
            count += mInjectedViewList.size();

            if (adapter != null) {
                count += adapter.getItemCount();
            }

//            WildcraftLog.e("==== count : ", "" + count);
            return count;
        }

        @Override
        public int getItemViewType(int position) {
            if (mViewMap.containsKey(position)) {
                if (position >= mFooterStartPosition) {
                    if (mViewMap.get(position) instanceof LoadMoreFooterView) {
                        return ITEM_LOAD_MORE;
                    } else {
                        return ITEM_FOOTER;
                    }
                } else if (position < mListItemsStartPosition) {
                    if (hasQuickReturnHeader && position == 0) {
                        return ITEM_PLACEHOLDER_HEADER;
                    }
                    if (mViewMap.get(position) instanceof SwipeRefreshHeaderView) {
                        return ITEM_REFRESH_HEADER;
                    } else {
                        return ITEM_HEADER;
                    }
                } else if (mInjectedViewList.size() > 0) {
                    return ITEM_INJECTED;
                }
            }

            if (adapter != null && adapter.getItemCount() > 0) {
                int adapterPosition = position - mListItemsStartPosition;
                adapterPosition -= getInjectedViewCountUntilPosition(position);
                return adapter.getItemViewType(adapterPosition);
            }

            return 0;
        }

        @Override
        public long getItemId(int position) {
            if (mViewMap.containsKey(position)) {
                return -1;
            }
            int adapterPosition = position - mListItemsStartPosition;
            adapterPosition -= mInjectedViewList.size();
            return adapter.getItemId(adapterPosition);
        }

        public void changeQuickReturnPlaceholderHeight(int height) {
            if (hasQuickReturnHeader) {
                View view = mViewMap.get(0);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = height;
                view.setLayoutParams(params);

            }
        }

        public void setItemPositions() {
            mViewMap = new HashMap<>();
            mFooterStartPosition = 0;
            mListItemsStartPosition = 0;
            mRefreshHeaderPosition = 0;
            mCurrentHeaderPosition = 0;
            mCurrentFooterPosition = 0;
            mLoadMorePosition = 0;

            if (isPullToRefresh) {
                mListItemsStartPosition++;
                mCurrentHeaderPosition++;
                if (hasQuickReturnHeader) {
                    mRefreshHeaderPosition++;
                    mListItemsStartPosition++;
                    mCurrentHeaderPosition++;

                    View quickReturnHeaderPlaceholder = new View(getContext());
                    ViewGroup.LayoutParams layoutParams =
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mQuickReturnHeaderHeight);
                    quickReturnHeaderPlaceholder.setLayoutParams(layoutParams);

                    mViewMap.put(0, quickReturnHeaderPlaceholder);
                    mViewMap.put(1, mPullToRefreshView);
                    for (int i = 0; i < mHeaderViewList.size(); i++) {
                        mViewMap.put(i + 2, mHeaderViewList.get(i));
                        mListItemsStartPosition++;
                    }
                } else {
                    mViewMap.put(0, mPullToRefreshView);
                    for (int i = 0; i < mHeaderViewList.size(); i++) {
                        mViewMap.put(i + 1, mHeaderViewList.get(i));
                        mListItemsStartPosition++;
                    }
                }
            } else {
                if (hasQuickReturnHeader) {
                    mListItemsStartPosition++;
                    mCurrentHeaderPosition++;

                    View quickReturnHeaderPlaceholder = new View(getContext());
                    ViewGroup.LayoutParams layoutParams =
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mQuickReturnHeaderHeight);
                    quickReturnHeaderPlaceholder.setLayoutParams(layoutParams);

                    mViewMap.put(0, quickReturnHeaderPlaceholder);
                    for (int i = 0; i < mHeaderViewList.size(); i++) {
                        mViewMap.put(i + 1, mHeaderViewList.get(i));
                        mListItemsStartPosition++;
                    }
                } else {
                    for (int i = 0; i < mHeaderViewList.size(); i++) {
                        mViewMap.put(i, mHeaderViewList.get(i));
                        mListItemsStartPosition++;
                    }
                }
            }

            if (mInjectedViewList.size() > 0) {
                for (int i = 0; i < mInjectedViewList.size(); i++) {
                    mViewMap.put(mInjectedViewPositions.get(i), mInjectedViewList.get(i));
                    mFooterStartPosition++;
                }
            }

            mFooterStartPosition += mListItemsStartPosition;
            mFooterStartPosition += adapter.getItemCount();
            mCurrentFooterPosition = mFooterStartPosition;
            mLoadMorePosition = mFooterStartPosition;

            for (int i = 0; i < mFooterViewList.size(); i++) {
                mViewMap.put(mFooterStartPosition + i, mFooterViewList.get(i));
                mLoadMorePosition++;
            }

            if (isLoadingMore) {
                mViewMap.put(mLoadMorePosition, mLoadMoreView);
            }
        }

        public void updateInjectedViewPositions(int change) {
            for (int i = 0; i < mInjectedViewPositions.size(); i++) {
                int position = mInjectedViewPositions.get(i);
                position += change;
                mInjectedViewPositions.set(i, position);
            }
        }

        public void removeLoadMoreView() {
            isLoadingMore = false;
            notifyItemRemoved(mLoadMorePosition);
        }

        private int getInjectedViewCountUntilPosition(int position) {
            int count = 0;

            for (int i = 0; i < mInjectedViewPositions.size(); i++) {
                if (mInjectedViewPositions.get(i) < position) {
                    count++;
                }
            }

            return count;
        }

        private class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
