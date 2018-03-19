package com.pulse.brag.ui.home.product.list.filter;

import com.pulse.brag.data.model.datas.DataFilter;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public interface ProductFilterDialogNavigator extends CoreNavigator {

    void dismissFragment();

    void noData();

    void onSetData();

    void setColorFilter(List<DataFilter.ColorCode> colorCodeList, HashMap<String, DataFilter.ColorCode> selectedMap);

    void noColorFilter();

    void setSizeList(List<DataFilter.SizeCode> sizeList,HashMap<String, DataFilter.SizeCode> selectedMap);

    void noSizeFilter();

    void applyFilter();

    void resetFilter();
}
