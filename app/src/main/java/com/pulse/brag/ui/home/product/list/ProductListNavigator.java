package com.pulse.brag.ui.home.product.list;

import com.pulse.brag.data.model.DummeyDataRespone;
import com.pulse.brag.data.model.datas.DataProductList;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public interface ProductListNavigator extends CoreNavigator {

    void swipeRefresh();

    void onNoData();

    void setProductList(int prodcutSize, List<DataProductList.Products> dataList);

    void setFilter(DataProductList.Filter filter);

    void openSortDialog();

    void openFilter();
}
