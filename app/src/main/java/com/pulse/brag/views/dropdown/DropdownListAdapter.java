package com.pulse.brag.views.dropdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;

public class DropdownListAdapter extends BaseAdapter {
    Context context;
    private ViewHolder holder;
    private LayoutInflater mInflater;
    private ArrayList<DropdownItem> mListItems;
    ArrayList<String> msCategoryDeaileenitiy2;
    View mselectbutton;
    PopupWindow pw;

    private class ViewHolder {
        TextView tv;

        private ViewHolder() {
        }
    }

    public DropdownListAdapter(Context context, View mselectbutton, ArrayList<DropdownItem> mListItems, PopupWindow pw) {
        this.context = context;
        this.mListItems = mListItems;
        this.mselectbutton = mselectbutton;
        this.pw = pw;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.mListItems.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.drop_down_list_row, null);

            this.holder = new ViewHolder();
            this.holder.tv = (TextView) convertView.findViewById(R.id.tvDropdownText);
            convertView.setTag(this.holder);
        } else {
            this.holder = (ViewHolder) convertView.getTag();
        }
        Utility.applyTypeFace(context, (LinearLayout) convertView.findViewById(R.id.base_layout));
        this.holder.tv.setText(((DropdownItem) this.mListItems.get(position)).getValue());
        this.holder.tv.setTag(Integer.valueOf(((DropdownItem) this.mListItems.get(position)).getId()));
        return convertView;
    }
}
