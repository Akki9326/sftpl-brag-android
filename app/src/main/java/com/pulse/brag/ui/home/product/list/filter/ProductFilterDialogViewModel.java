package com.pulse.brag.ui.home.product.list.filter;

import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.ui.home.product.list.adapter.model.ColorModel;
import com.pulse.brag.ui.home.product.list.adapter.model.SizeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ProductFilterDialogViewModel extends CoreViewModel<ProductFilterDialogNavigator> {
    public ProductFilterDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onDismissClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().dismissFragment();
            }
        };
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

    public List<ColorModel> buildColorList() {
        List<ColorModel> list = new ArrayList<>();
        list.add(new ColorModel("#F44336", false));
        list.add(new ColorModel("#E91E63", false));
        list.add(new ColorModel("#9C27B0", false));
        list.add(new ColorModel("#2196F3", false));
        list.add(new ColorModel("#FF9800", false));
        list.add(new ColorModel("#FF5722", false));
        list.add(new ColorModel("#3F51B5", false));
        list.add(new ColorModel("#B5C1B5", false));
        list.add(new ColorModel("#BF5DD5", false));
        list.add(new ColorModel("#DD126F", false));
        return list;
    }

    public List<SizeModel> buildSizeList(){
        List<SizeModel> list = new ArrayList<>();

        list.add(new SizeModel("S", false));
        list.add(new SizeModel("M", false));
        list.add(new SizeModel("X", false));
        list.add(new SizeModel("XL", false));
        list.add(new SizeModel("XXL", false));
        list.add(new SizeModel("XXXL", false));
        return list;
    }
}
