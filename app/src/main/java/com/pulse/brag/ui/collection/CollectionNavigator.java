package com.pulse.brag.ui.collection;

import com.pulse.brag.data.model.datas.CategoryListResponseData;
import com.pulse.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

public interface CollectionNavigator extends CoreNavigator{

    void swipeRefresh();

    void onNoData();

    void setCategoryList(List<CategoryListResponseData.Category> list);

    void setBanner(List<CategoryListResponseData.Banners> list);
}
