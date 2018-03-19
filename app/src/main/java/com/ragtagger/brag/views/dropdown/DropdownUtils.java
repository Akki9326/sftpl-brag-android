package com.ragtagger.brag.views.dropdown;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.utils.Common;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;

public class DropdownUtils {

    public static void DropDownSpinner(Context context, final ArrayList<DropdownItem> drowupdowlist, final View view, final IOnDropDownItemClick iOnDropDownItemClick) {
        Common.hideKeyboard(context, view);
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.drop_down_view, null);
        Utility.applyTypeFace(context, layout);
        final PopupWindow pw = new PopupWindow(layout, -1, -2, true);
        pw.setWidth(view.getWidth());
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);
        pw.setOutsideTouchable(true);
        pw.setHeight(-2);
        pw.setTouchInterceptor(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent event) {
                if (event.getAction() != 4) {
                    return false;
                }
                pw.dismiss();
                return true;
            }
        });
        pw.setContentView(layout);

        //pw.showAsDropDown(view);
        Rect location = Common.locateView(view);
        pw.showAtLocation(view, Gravity.TOP | Gravity.LEFT, location.left, location.bottom);

        ListView list = (ListView) layout.findViewById(R.id.dropDownList);
        list.setAdapter(new DropdownListAdapter(context, view, drowupdowlist, pw));
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                ((TextView) view).setText(((DropdownItem) drowupdowlist.get(arg2)).getValue());
                ((TextView) view).setTag(Integer.valueOf(((DropdownItem) drowupdowlist.get(arg2)).getId()));
                iOnDropDownItemClick.onItemClick(((DropdownItem) drowupdowlist.get(arg2)).getValue(), ((DropdownItem) drowupdowlist.get(arg2)).getId());
                pw.dismiss();
            }
        });
    }
}
