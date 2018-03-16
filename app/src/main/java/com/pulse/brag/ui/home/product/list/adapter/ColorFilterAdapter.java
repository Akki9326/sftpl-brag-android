package com.pulse.brag.ui.home.product.list.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pulse.brag.R;
import com.pulse.brag.data.model.datas.DataFilter;
import com.pulse.brag.databinding.ItemListColorFilterBinding;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.ui.home.product.list.filter.listener.IFilterSelectedListener;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ColorFilterAdapter extends RecyclerView.Adapter<ColorFilterAdapter.ItemViewHolder> {

    Activity mActivity;
    List<DataFilter.ColorCode> mList;
    IFilterSelectedListener colorSelectListener;

    ItemViewHolder mViewHolder;

    public ColorFilterAdapter(Activity mActivity, List<DataFilter.ColorCode> mList, IFilterSelectedListener colorSelectListener) {
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

    public void resetList(List<DataFilter.ColorCode> list) {
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
            Utility.applyTypeFace(mActivity, itemBinding.baseLayout);
        }

        void bindColorData(int pos, DataFilter.ColorCode colorModel) {
            this.pos = pos;
            itemBinding.setItemModel(new ItemColorFilterViewModel(itemView.getContext(), pos, colorModel, this));
            itemBinding.executePendingBindings();
        }

        @Override
        public void onBind(int position) {

        }

        @Override
        public void OnColorClick(DataFilter.ColorCode model, int pos) {
            mList.get(pos).setSelected(!model.isSelected());
            colorSelectListener.onSelectedColor(model.isSelected(), model);
            notifyItemChanged(pos);
        }
    }
}
