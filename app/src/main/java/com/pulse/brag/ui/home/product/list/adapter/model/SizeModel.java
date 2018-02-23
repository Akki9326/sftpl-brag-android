package com.pulse.brag.ui.home.product.list.adapter.model;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class SizeModel {
    String size;
    boolean selected;

    public SizeModel(String size, boolean selected) {
        this.size = size;
        this.selected = selected;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
