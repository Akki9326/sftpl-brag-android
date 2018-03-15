package com.pulse.brag.ui.more.adapter;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.data.model.datas.DataMoreList;

import java.util.List;

/**
 * Created by nikhil.vadoliya on 07-11-2017.
 */


public class MoreListAdapter extends BaseAdapter {

    Context context;
    List<DataMoreList> moreListData;
    LayoutInflater inflter;

    public MoreListAdapter(Context context, List<DataMoreList> moreListData) {
        this.context = context;
        this.moreListData = moreListData;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return moreListData.size();
    }

    @Override
    public Object getItem(int position) {
        return moreListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return moreListData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.item_list_more, null);
        TextView textLabel = (TextView) convertView.findViewById(R.id.textview_label);
        ImageView imageIcon = (ImageView) convertView.findViewById(R.id.imageview_icon);
        Utility.applyTypeFace(context, (LinearLayout) convertView.findViewById(R.id.base_layout));

        if (moreListData.get(position).getLabel().isEmpty()) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            textLabel.setVisibility(View.INVISIBLE);
            imageIcon.setVisibility(View.INVISIBLE);
        } else {
            textLabel.setText(moreListData.get(position).getLabel());
            imageIcon.setImageDrawable(moreListData.get(position).getDrawable());
        }



        return convertView;
    }
}
