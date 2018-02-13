package com.pulse.brag.adapters;


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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.pojo.datas.PriceListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 05-12-2017.
 */


public class PriceListAdapter extends BaseAdapter {


    List<PriceListData> mListData;
    Context mContext;
    LayoutInflater inflter;

    public PriceListAdapter(Context mContext, List<PriceListData> mListData) {
        this.mListData = new ArrayList<>();
        this.mListData = mListData;
        this.mContext = mContext;
        inflter = (LayoutInflater.from(this.mContext));
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.item_list_price, null);
        TextView txtProduct = (TextView) convertView.findViewById(R.id.textview_product_name);
        TextView txtMultiple = (TextView) convertView.findViewById(R.id.textview_multi);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.textview_price);

        Utility.applyTypeFace(mContext, (LinearLayout) convertView.findViewById(R.id.base_layout));

        txtProduct.setText(mListData.get(position).getProduct());
        txtMultiple.setText("" + mListData.get(position).getMuliplecation());
        txtPrice.setText("" + mListData.get(position).getPrice());


        return convertView;
    }
}
