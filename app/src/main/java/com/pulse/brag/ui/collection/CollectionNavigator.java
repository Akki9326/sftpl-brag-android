package com.pulse.brag.ui.collection;

import com.pulse.brag.data.model.datas.DataCategoryList;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

public interface CollectionNavigator extends CoreNavigator{

    void swipeRefresh();

    void onNoData();

    void setCategoryList(List<DataCategoryList.Category> list);

    void setBanner(List<DataCategoryList.Banners> list);
}
