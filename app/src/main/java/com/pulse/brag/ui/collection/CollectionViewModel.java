package com.pulse.brag.ui.collection;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.remote.ApiClient;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.AlertUtils;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

public class CollectionViewModel extends CoreViewModel<CollectionNavigator> {
    public CollectionViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void getCollectionList() {
        // TODO: 2/28/2018 change url
        Call<CollectionListResponse> getCategoryList = getDataManager().getCollectionProduct("home/get/2");
        getCategoryList.enqueue(new ApiResponse<CollectionListResponse>() {
            @Override
            public void onSuccess(CollectionListResponse collectionListResponse, Headers headers) {
                if (collectionListResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().collectionResponse(collectionListResponse.getData());
                } else {
                    getNavigator().onApiError(new ApiError(collectionListResponse.getErrorCode(), collectionListResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });

        /*ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<CollectionListResponse> setCategoryList = ApiClient.getInstance(getContext()).getApiResp().getCollectionProduct("home/get/2");
        setCategoryList.enqueue(new Callback<CollectionListResponse>() {
            @Override
            public void onResponse(Call<CollectionListResponse> call, Response<CollectionListResponse> response) {
                if (response.isSuccessful()) {
                    CollectionListResponse data = response.body();
                    if (data.isStatus()) {

                        mCollectionList.addAll(data.getData());
//                        Collections.sort(mCollectionList);
                        showData();
                    } else {
                        //Utility.showAlertMessage(getContext(), data.getErrorCode(), data.getMessage());
                        AlertUtils.showAlertMessage(getActivity(), data.getErrorCode(), data.getMessage());
                    }
                } else {
                    //Utility.showAlertMessage(getContext(), 1, null);
                    AlertUtils.showAlertMessage(getActivity(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<CollectionListResponse> call, Throwable t) {
                //Utility.showAlertMessage(getContext(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });*/
    }
}
