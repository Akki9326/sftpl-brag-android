package com.pulse.brag.ui.home.product.list.filter;

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataFilter;
import com.pulse.brag.data.model.requests.QGetFilter;
import com.pulse.brag.data.model.response.RFilter;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ProductFilterDialogViewModel extends CoreViewModel<ProductFilterDialogNavigator> {

    private final ObservableField<String> title = new ObservableField<>();
    private final ObservableField<Boolean> inProgress = new ObservableField<>();

    public ProductFilterDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<Boolean> getInProgress() {
        return inProgress;
    }

    public void updateInProgress(boolean inProgress) {
        this.inProgress.set(inProgress);
    }

    public View.OnClickListener onDismissClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().dismissFragment();
            }
        };
    }

    public void updateTitle(String title) {
        this.title.set(title);
    }

    public View.OnClickListener onApplyClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().applyFilter();
            }
        };
    }

    public View.OnClickListener onResetClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().resetFilter();
            }
        };
    }

    public View.OnClickListener overrideClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
            }
        };
    }

    public void getFilter(String category, String subCategory, String seasonCode, final DataFilter.Filter appliedFilter) {
        final QGetFilter filter = new QGetFilter();
        filter.setCategory(category);
        filter.setSubCategory(subCategory);
        filter.setSeasonCode(seasonCode);
        Call<RFilter> call = getDataManager().getFilter(filter);
        call.enqueue(new ApiResponse<RFilter>() {
            @Override
            public void onSuccess(RFilter rFilter, Headers headers) {
                if (rFilter.isStatus()) {
                    getNavigator().onApiSuccess();
                    if (rFilter.getData() != null && rFilter.getData().getFilter() != null) {
                        getNavigator().onSetData();
                        if (rFilter.getData().getFilter().getColorCodes() != null && rFilter.getData().getFilter().getColorCodes().size() > 0) {

                            List<DataFilter.ColorCode> colorCodeList = new ArrayList<>();
                            HashMap<String, DataFilter.ColorCode> mMapSelectedColor = new HashMap<>();

                            if (appliedFilter != null) {
                                if (appliedFilter.getColorCodes() != null && appliedFilter.getColorCodes().size() > 0)
                                    for (DataFilter.ColorCode item : appliedFilter.getColorCodes())
                                        mMapSelectedColor.put(item.getKey(), item);

                                for (DataFilter.ColorCode color : rFilter.getData().getFilter().getColorCodes()) {
                                    if (!Common.isNotNullOrEmpty(color.getHash()))
                                        color.setHash("#000000");

                                    if (mMapSelectedColor.containsKey(color.getKey()))
                                        color.setSelected(true);
                                    colorCodeList.add(color);
                                }
                            } else {
                                colorCodeList = rFilter.getData().getFilter().getColorCodes();
                            }

                            getNavigator().setColorFilter(colorCodeList, mMapSelectedColor);
                        } else {
                            getNavigator().noColorFilter();
                        }

                        if (rFilter.getData().getFilter().getSizeCodes() != null && rFilter.getData().getFilter().getSizeCodes().size() > 0) {
                            List<DataFilter.SizeCode> sizeModels = new ArrayList<>();
                            HashMap<String, DataFilter.SizeCode> mMapSelectedSize = new HashMap<>();
                            if (appliedFilter != null) {

                                if (appliedFilter.getSizeCodes() != null && appliedFilter.getSizeCodes().size() > 0)
                                    for (String item : appliedFilter.getSizeCodes())
                                        mMapSelectedSize.put(item, new DataFilter.SizeCode(item, true));

                                for (String size : rFilter.getData().getFilter().getSizeCodes()) {
                                    if (Common.isNotNullOrEmpty(size)) {
                                        sizeModels.add(new DataFilter.SizeCode(size, mMapSelectedSize.containsKey(size)));
                                    }
                                }

                            } else {
                                for (String size : rFilter.getData().getFilter().getSizeCodes()) {
                                    if (Common.isNotNullOrEmpty(size))
                                        sizeModels.add(new DataFilter.SizeCode(size, false));
                                }
                            }
                            getNavigator().setSizeList(sizeModels, mMapSelectedSize);
                        } else {
                            getNavigator().noSizeFilter();
                        }

                    } else {
                        getNavigator().noData();
                    }
                } else {
                    getNavigator().onApiError(new ApiError(rFilter.getErrorCode(), rFilter.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });


    }

}
