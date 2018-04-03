package com.ragtagger.brag.ui.order.orderstatushistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.datas.DataOrderStatus;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.verticalstepper.VerticalStepperAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainStepperAdapter extends VerticalStepperAdapter {
    Context context;
    List<DataOrderStatus> states;

    public MainStepperAdapter(Context context, List<DataOrderStatus> orderStatuses) {
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
    public boolean isEditable(int position) {
        return false;
    }

    @Override
    public int getCompleteTextColor(int position) {
        return states.get(position).getOrderStatesColor();
    }

    @Override
    public int getConnectionLineColor(int position) {
        return states.get(position).getOrderStatusStepper() == Constants.OrderStatusStepper.COMPLETE.ordinal() ?
                R.color.pink : R.color.gray;
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
        /*Button actionContinue = content.findViewById( R.id.action_continue );
        actionContinue.setEnabled( position < getCount() - 1 );
        actionContinue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                next();
            }
        } );

        Button actionBack = content.findViewById( R.id.action_back );
        actionBack.setEnabled( position > 0 );
        actionBack.findViewById( R.id.action_back ).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    previous();
                }
            } );*/

        return content;
    }
}
