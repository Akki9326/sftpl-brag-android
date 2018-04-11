package com.ragtagger.brag.ui.home.product.list.sorting;

import android.database.Observable;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.RadioGroup;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

public class ProductSortingDialogViewModel extends CoreViewModel<ProductSortingDialogNavigator> {

  ObservableField<Boolean> rbPriceLowToHigh = new ObservableField<>();
  ObservableField<Boolean> rbPriceHighToLow = new ObservableField<>();
  ObservableField<Boolean> rbDateAsc = new ObservableField<>();
  ObservableField<Boolean> rbDateDesc = new ObservableField<>();

    public ProductSortingDialogViewModel(IDataManager dataManager) {
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

    public void onSortingChanged(RadioGroup radioGroup, int id) {
        getNavigator().updateSorting(radioGroup, id);
        switch (id) {
            case R.id.rb_price_low_to_high:
                setRbDateAsc(false);
                setRbDateDesc(false);
                setRbPriceHighToLow(false);
                setRbPriceLowToHigh(true);
                break;
            case R.id.rb_price_high_to_low:
                setRbDateAsc(false);
                setRbDateDesc(false);
                setRbPriceHighToLow(true);
                setRbPriceLowToHigh(false);
                break;
            case R.id.rb_date_asc:
                setRbDateAsc(true);
                setRbDateDesc(false);
                setRbPriceHighToLow(false);
                setRbPriceLowToHigh(false);
                break;
            case R.id.rb_date_desc:
                setRbDateAsc(false);
                setRbDateDesc(true);
                setRbPriceHighToLow(false);
                setRbPriceLowToHigh(false);
                break;
        }

    }

    public ObservableField<Boolean> getRbPriceLowToHigh() {
        return rbPriceLowToHigh;
    }

    public void setRbPriceLowToHigh(boolean rbPriceLowToHigh) {
        this.rbPriceLowToHigh.set(rbPriceLowToHigh);
    }

    public ObservableField<Boolean> getRbPriceHighToLow() {
        return rbPriceHighToLow;
    }

    public void setRbPriceHighToLow(boolean rbPriceHighToLow) {
        this.rbPriceHighToLow.set(rbPriceHighToLow);
    }

    public ObservableField<Boolean> getRbDateAsc() {
        return rbDateAsc;
    }

    public void setRbDateAsc(boolean rbDateAsc) {
        this.rbDateAsc.set(rbDateAsc);
    }

    public ObservableField<Boolean> getRbDateDesc() {
        return rbDateDesc;
    }

    public void setRbDateDesc(boolean rbDateDesc) {
        this.rbDateDesc.set(rbDateDesc);
    }
}
