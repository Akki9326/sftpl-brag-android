package com.ragtagger.brag.ui.core;

import com.ragtagger.brag.data.model.ApiError;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

public interface CoreNavigator {

    void onApiSuccess();
    void onApiError(ApiError error);
}
