package com.ragtagger.brag.di.builder;

import com.ragtagger.brag.ui.authentication.profile.addeditaddress.AddEditAddresstProvider;
import com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog.StateDialogProvider;
import com.ragtagger.brag.ui.collection.CollectionProvider;
import com.ragtagger.brag.ui.home.category.CategoryFragmentProvider;
import com.ragtagger.brag.ui.cart.CartFragmentProvider;
import com.ragtagger.brag.ui.cart.placeorder.PlaceOrderFragmentProvider;
import com.ragtagger.brag.ui.cart.editquantity.EditQtytDialogProvider;
import com.ragtagger.brag.ui.home.HomeFragmentProvider;
import com.ragtagger.brag.ui.home.product.details.ProductDetailProvider;
import com.ragtagger.brag.ui.home.product.list.ProductListProvider;
import com.ragtagger.brag.ui.home.product.list.filter.ProductFilterDialogProvider;
import com.ragtagger.brag.ui.home.product.list.sorting.ProductSortingDialogProvider;
import com.ragtagger.brag.ui.home.product.quickadd.AddProductDialogProvider;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.contactus.ContactUsProvider;
import com.ragtagger.brag.ui.authentication.createnewpassord.CreateNewPasswordProvider;
import com.ragtagger.brag.ui.more.MoreProvider;
import com.ragtagger.brag.ui.notification.NotificationListProvider;
import com.ragtagger.brag.ui.notification.handler.NotificationHandlerActivity;
import com.ragtagger.brag.ui.notification.handler.NotificationHandlerModule;
import com.ragtagger.brag.ui.order.MyOrderProvider;
import com.ragtagger.brag.ui.order.orderdetail.OrderDetailFragmentProvider;
import com.ragtagger.brag.ui.authentication.profile.UserProfileModule;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.ui.authentication.profile.changemobile.ChangeMobNumberProvider;
import com.ragtagger.brag.ui.authentication.profile.changepassword.ChangePassProvider;
import com.ragtagger.brag.ui.authentication.forgotpassword.ForgotPasswordProvider;
import com.ragtagger.brag.ui.authentication.login.LoginFragmentProvider;
import com.ragtagger.brag.ui.authentication.otp.OTPFragmentProvider;
import com.ragtagger.brag.ui.authentication.profile.updateprofile.UpdateProfileProvider;
import com.ragtagger.brag.ui.authentication.signup.SignUpFragmentProvider;
import com.ragtagger.brag.ui.authentication.signup.complete.SignUpCompleteProvider;
import com.ragtagger.brag.ui.main.MainActivityModule;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.ui.splash.SplashActivityModule;
import com.ragtagger.brag.ui.home.subcategory.SubCategoryFragmentProvider;
import com.ragtagger.brag.views.webview.WebviewDailogProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {SplashActivityModule.class, LoginFragmentProvider.class, ForgotPasswordProvider.class, OTPFragmentProvider.class, SignUpFragmentProvider.class, SignUpCompleteProvider.class, CreateNewPasswordProvider.class, ContactUsProvider.class, ProductSortingDialogProvider.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class, HomeFragmentProvider.class
            , CategoryFragmentProvider.class, SubCategoryFragmentProvider.class
            , CartFragmentProvider.class, EditQtytDialogProvider.class
            , ProductListProvider.class, ProductDetailProvider.class, AddProductDialogProvider.class
            , ProductSortingDialogProvider.class, ProductFilterDialogProvider.class
            , OrderDetailFragmentProvider.class
            , PlaceOrderFragmentProvider.class
            , MyOrderProvider.class, MoreProvider.class,
            NotificationListProvider.class, CollectionProvider.class,
            WebviewDailogProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {UserProfileModule.class, ChangePassProvider.class, ForgotPasswordProvider.class,
            UpdateProfileProvider.class, ChangeMobNumberProvider.class,
            OTPFragmentProvider.class
            , AddEditAddresstProvider.class, StateDialogProvider.class})
    abstract UserProfileActivity bindChangePasswordOrMobileActivity();

    @ContributesAndroidInjector(modules = {NotificationHandlerModule.class})
    abstract NotificationHandlerActivity bindNotificationHandlerActivity();
}
