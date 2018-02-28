package com.pulse.brag.ui.home.product.list.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pulse.brag.R;
import com.pulse.brag.databinding.ItemListColorFilterBinding;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.ui.home.product.list.adapter.model.ColorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ColorFilterAdapter extends RecyclerView.Adapter<ColorFilterAdapter.ItemViewHolder> {

    Activity mActivity;
    List<ColorModel> mList;
    IOnProductColorSelectListener colorSelectListener;

    ItemViewHolder mViewHolder;

    public ColorFilterAdapter(Activity mActivity, List<ColorModel> mList, IOnProductColorSelectListener colorSelectListener) {
        this.mActivity = mActivity;
        this.mList = new ArrayList<>();
        this.mList = mList;
        this.colorSelectListener = colorSelectListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListColorFilterBinding itemListColorFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_color_filter, parent, false);
        return new ColorFilterAdapter.ItemViewHolder(itemListColorFilterBinding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.bindColorData(position, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void resetList(List<ColorModel> list) {
        if (mList != null && mList.size() > 0)
            mList.clear();

        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends CoreViewHolder implements ItemColorFilterViewModel.ItemColorViewModelListener {

        ItemListColorFilterBinding itemBinding;
        int pos;

        public ItemViewHolder(ItemListColorFilterBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }

        void bindColorData(int pos, ColorModel colorModel) {
            this.pos = pos;
            itemBinding.setItemModel(new ItemColorFilterViewModel(itemView.getContext(), pos, colorModel, this));

        }

        @Override
        public void onBind(int position) {

        }

        @Override
        public void OnColorClick(ColorModel model, int pos) {
            mList.get(pos).setSelected(!model.isSelected());
            notifyItemChanged(pos);
        }
    }
}
