package com.pulse.brag.ui.authentication.profile.addeditaddress.statedialog.adapter;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.data.model.datas.StateListRespone;
import com.pulse.brag.utils.Utility;

import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 07-03-2018.
 */


public class StateListAdapter extends BaseAdapter {

    Context mContext;
    List<StateListRespone> mList = Collections.emptyList();

    public StateListAdapter(Context context, List<StateListRespone> mList) {
        this.mList = mList;
        mContext=context;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.item_list_state, parent, false);
        }

        TextView textViewItemName = convertView.findViewById(R.id.textview_state);


        textViewItemName.setText(mList.get(position).getText());

        Utility.applyTypeFace(mContext,(RelativeLayout)convertView.findViewById(R.id.base_layout));
        return convertView;
    }
}
