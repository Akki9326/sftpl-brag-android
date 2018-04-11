package com.ragtagger.brag.ui.order.orderdetail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.datas.DataOrderStatus;
import com.ragtagger.brag.views.verticalstepper.MainItemView;
import com.ragtagger.brag.views.verticalstepper.VerticalStepperAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusStepperAdapter extends VerticalStepperAdapter {
    Context context;
    List<DataOrderStatus> states;

    public OrderStatusStepperAdapter(Context context, List<DataOrderStatus> orderStatuses) {
        super(context, orderStatuses);
        this.context = context;
        states = new ArrayList<>();
        states = orderStatuses;

    }


    @NonNull
    @Override
    public CharSequence getTitle(int position) {
        return states.get(position).getStatusLabel(context);
    }

    @Nullable
    @Override
    public CharSequence getSummary(int position) {
        return states.get(position).getStatusDate();
    }

    @Override
    public int getConnectionLineStatus(int position) {
        return states.get(position).getOrderStatusStepper();
    }

    @Override
    public int getCount() {
        return states.size();
    }

    @Override
    public Void getItem(int position) {
        return null;
    }

    @NonNull
    @Override
    public View onCreateContentView(Context context, int position) {
        View content = new MainItemView(context);
        return content;
    }
}
