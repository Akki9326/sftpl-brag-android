package com.ragtagger.brag.ui.collection;

import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

public interface CollectionNavigator extends CoreNavigator {

    void performSwipeRefresh();

    void setCategoryList(List<DataCategoryList.Category> list);

    void setBanner(List<DataCategoryList.Banners> list);

    void onNoData();
}
