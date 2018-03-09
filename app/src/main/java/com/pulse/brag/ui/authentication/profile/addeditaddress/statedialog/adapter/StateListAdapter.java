package com.pulse.brag.ui.authentication.profile.addeditaddress.statedialog.adapter;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.pulse.brag.R;
import com.pulse.brag.data.model.datas.StateData;
import com.pulse.brag.databinding.ItemListStateBinding;
import com.pulse.brag.ui.authentication.profile.addeditaddress.statedialog.StateItemViewModel;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 07-03-2018.
 */


public class StateListAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    List<StateData> mList = Collections.emptyList();
    LayoutInflater inflater;
    List<StateData> mFilterList;
    ValueFilter valueFilter;

    public StateListAdapter(Context context, List<StateData> mList) {
        this.mList = mList;
        mContext = context;
        mFilterList = this.mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      /*  if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.item_list_state, parent, false);
        }
*/

        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        ItemListStateBinding binding = DataBindingUtil.getBinding(convertView);
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.item_list_state, parent, false);
        }
        binding.setViewModel(new StateItemViewModel(mContext, mList.get(position)));
        binding.executePendingBindings();

        Utility.applyTypeFace(mContext, binding.baseLayout);
        return binding.getRoot();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<StateData> filterList = new ArrayList<>();
                for (int i = 0; i < mFilterList.size(); i++) {
                    if ((mFilterList.get(i).getText().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        StateData data = new StateData(mFilterList.get(i)
                                .getId(), mFilterList.get(i)
                                .getText());
                        filterList.add(data);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mList = (ArrayList<StateData>) results.values;
            notifyDataSetChanged();
        }

    }
}
