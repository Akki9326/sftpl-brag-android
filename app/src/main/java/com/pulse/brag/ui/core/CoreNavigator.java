package com.pulse.brag.ui.core;

import com.pulse.brag.data.model.ApiError;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

public interface CoreNavigator {

    void onApiSuccess();
    void onApiError(ApiError error);
}
