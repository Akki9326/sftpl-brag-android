package com.ragtagger.brag.ui.toolbar;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.databinding.ToolbarBaseBinding;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.utils.Utility;

/**
 * Created by alpesh.rathod on 4/2/2018.
 */

public abstract class ToolbarActivity<CA extends ToolbarActivity, VB extends ViewDataBinding, VM extends ToolbarViewModel> extends CoreActivity<CA, VB, VM> implements ToolbarNavigator, CoreActivity.OnToolbarSetupListener {

    ToolbarBaseBinding mToolbarBinding;
    ToolbarViewModel mToolbarViewModel;


    @Override
    public void beforeLayoutSet() {

    }

    @Override
    public void afterLayoutSet() {
        mToolbarViewModel = mViewModel;
        View toolbarView = findViewById(R.id.included_toolbar);
        mToolbarBinding = DataBindingUtil.findBinding(toolbarView);
        mToolbarBinding.setVariable(getBindingVariable(), mToolbarViewModel);
        mToolbarBinding.executePendingBindings();
        if (bActivity != null) {
            bActivity.setUpToolbar();
        }
    }

    public void setUpToolbar() {
        setSupportActionBar(mToolbarBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            Utility.applyTypeFace(getApplicationContext(), mToolbarBinding.toolbar);
        }
    }

    @Override
    public void onBackClick() {
        performBackClick();
    }

    @Override
    public void onCartClick() {
        performCartClick();
    }

    public void enableBackButton(boolean isEnable) {
        mToolbarBinding.imageViewBack.setEnabled(isEnable);
    }

    public void showToolbar(boolean displayBack, boolean displayLogo, boolean displayCart, String title) {
        mToolbarViewModel.setDisplayBack(displayBack);
        mToolbarViewModel.setDisplayLogo(displayLogo);
        mToolbarViewModel.setDisplayTitle(!displayLogo);
        mToolbarViewModel.setDisplayCart(displayCart);
        if (!displayLogo && title != null) {
            mToolbarViewModel.updateToolbarTitle(title);
            mToolbarViewModel.setDisplayTitle(true);
        }
    }

    public void showToolbar(boolean displayBack, boolean displayLogo, boolean displayCart) {
        showToolbar(displayBack, displayLogo, displayCart, null);
    }

    public void performCartClick() {

    }

    public void performBackClick() {

    }

    public void setBadgeCount(int count) {
        if (count == 0) {
            mToolbarViewModel.setShowBadge(false);
        } else {
            mToolbarViewModel.setShowBadge(true);
            mToolbarViewModel.setBadgeCount(Utility.getBadgeNumber(count));
        }
    }

    public String getNotificationLabel() {
        if (BragApp.NotificationNumber > 0) {
            return getString(R.string.toolbar_label_notification) + " (" + Utility.getBadgeNumber(BragApp.NotificationNumber) + ")";
        } else {
            return getString(R.string.toolbar_label_notification);
        }
    }


}
