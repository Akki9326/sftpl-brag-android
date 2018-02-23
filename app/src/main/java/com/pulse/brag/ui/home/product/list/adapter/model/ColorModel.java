package com.pulse.brag.ui.home.product.list.adapter.model;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ColorModel {
    String color;
    boolean selected;

    public ColorModel(String color, boolean selected) {
        this.color = color;
        this.selected = selected;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
