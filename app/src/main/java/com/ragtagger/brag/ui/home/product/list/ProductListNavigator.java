package com.ragtagger.brag.ui.home.product.list;

import android.view.View;

import com.ragtagger.brag.data.model.datas.DataFilter;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public interface ProductListNavigator extends CoreNavigator {

    void performSwipeRefresh();

    void performSearch(String query);

    void performClickSort(View view);

    void openSortingDialog();

    void performClickFilter(View view);

    void openFilterDialog();

    void onNoData();

    void setData(DataProductList data);

    void setProductList(int prodcutSize, List<DataProductList.Products> dataList);

    void setFilter(DataFilter.Filter filter);

    void openFragmentDetails(int position);

    void openAddProductDialog(int position);
}
