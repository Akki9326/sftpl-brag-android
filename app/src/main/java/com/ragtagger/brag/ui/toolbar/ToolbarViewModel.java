package com.ragtagger.brag.ui.toolbar;

import android.databinding.ObservableField;
import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 4/2/2018.
 */

public class ToolbarViewModel<TN extends ToolbarNavigator> extends CoreViewModel<TN> {
    public ToolbarViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    ObservableField<Boolean> displayBack = new ObservableField<>();
    ObservableField<Boolean> displayLogo = new ObservableField<>();
    ObservableField<Boolean> displayTitle = new ObservableField<>();
    ObservableField<String> toolbarTitle = new ObservableField<>();
    ObservableField<Boolean> displayCart = new ObservableField<>();
    ObservableField<Boolean> showBadge = new ObservableField<>();
    ObservableField<String> badgeCount = new ObservableField<>();

    public ObservableField<Boolean> getDisplayBack() {
        return displayBack;
    }

    public void setDisplayBack(boolean isDisplayBack) {
        this.displayBack.set(isDisplayBack);
    }

    public ObservableField<Boolean> getDisplayLogo() {
        return displayLogo;
    }

    public void setDisplayLogo(boolean isDisplayLogo) {
        this.displayLogo.set(isDisplayLogo);
    }

    public ObservableField<Boolean> getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(boolean isDisplayTitle){
        this.displayTitle.set(isDisplayTitle);
    }

    public ObservableField<Boolean> getDisplayCart() {
        return displayCart;
    }

    public void setDisplayCart(boolean isDisplayCart) {
        this.displayCart.set(isDisplayCart);
    }

    public ObservableField<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public void updateToolbarTitle(String title) {
        toolbarTitle.set(title);
    }

    public ObservableField<Boolean> getShowBadge() {
        return showBadge;
    }

    public ObservableField<String> getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(String count) {
        this.badgeCount.set(count);
    }

    public void setShowBadge(boolean showBadge) {
        this.showBadge.set(showBadge);
    }

    public View.OnClickListener onBackButtonClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onBackClick();
            }
        };
    }

    public View.OnClickListener onCartButtonClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onCartClick();
            }
        };
    }

}
