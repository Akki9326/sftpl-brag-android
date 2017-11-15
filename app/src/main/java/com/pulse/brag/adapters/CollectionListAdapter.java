package com.pulse.brag.adapters;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.respones.CategoryListRespone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 29-09-2017.
 */


public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.ViewHolder> {


    List<CategoryListRespone> listRespones;
    Activity mActivity;
    OnItemClickListener mItemClickListener;

    public CollectionListAdapter(Activity mActivity, List<CategoryListRespone> listRespones, OnItemClickListener mItemClickListener) {
        this.mActivity = mActivity;
        this.listRespones = new ArrayList<>();
        this.listRespones = listRespones;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public CollectionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mActivity).inflate(R.layout.item_collection_grid, null);
        ViewHolder viewHolder1 = new ViewHolder(view1);
        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(CollectionListAdapter.ViewHolder holder, final int position) {

        runEnterAnimation(holder.mView);

        Utility.imageSet(mActivity, listRespones.get(position).getUrl(), holder.mImgBackground);
        holder.mText.setText(listRespones.get(position).getName());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRespones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgBackground;
        TextView mText;
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            Utility.applyTypeFace(mActivity, (FrameLayout) itemView.findViewById(R.id.base_layout));
            mImgBackground = (ImageView) itemView.findViewById(R.id.imageView_collection_background);
            mText = (TextView) itemView.findViewById(R.id.textview_collection_label);
        }
    }

    private void runEnterAnimation(View view) {
        view.setTranslationY(Utility.getScreenHeight(mActivity));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(10.f))
                .setDuration(1000)
                .start();
    }
}
