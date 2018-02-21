package com.pulse.brag.ui.home.product.list;

import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public interface ProductListNavigator extends CoreNavigator {

    void sort();
    void filter();
    void showList(int prodcutSize,List<DummeyDataRespone> dataList);
    void swipeRefresh();
}
