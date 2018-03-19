package com.ragtagger.brag.views.dropdown;

public class DropdownItem {
    int id;
    String value;

    public DropdownItem(String value, int id) {
        this.value = value;
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
