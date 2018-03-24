package com.ragtagger.brag.ui.home.product.list;

import com.ragtagger.brag.data.model.datas.DataFilter;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public interface ProductListNavigator extends CoreNavigator {

    void swipeRefresh();

    void search(String query);

    void onNoData();

    void setData(DataProductList data);

    void setProductList(int prodcutSize, List<DataProductList.Products> dataList);

    void setFilter(DataFilter.Filter filter);

    void openFragmentDetails(int position);

    void openAddProductDialog(int position);

    void openSortingDialog();

    void openFilterDialog();
}