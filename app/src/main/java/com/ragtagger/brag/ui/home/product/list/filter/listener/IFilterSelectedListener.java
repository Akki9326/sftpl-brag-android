package com.ragtagger.brag.ui.home.product.list.filter.listener;

import com.ragtagger.brag.data.model.datas.DataFilter;

/**
 * Created by alpesh.rathod on 3/12/2018.
 */

public interface IFilterSelectedListener {
    void onSelectedColor(boolean isSelected, DataFilter.ColorCode item);
    void onSelectedSize(boolean isSelected, DataFilter.SizeCode item);
}
