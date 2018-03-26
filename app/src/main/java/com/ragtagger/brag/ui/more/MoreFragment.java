package com.ragtagger.brag.ui.more;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ragtagger.brag.BR;
import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.BuildConfig;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.FragmentMoreBinding;
import com.ragtagger.brag.data.model.datas.DataMoreList;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.HomeFragment;
import com.ragtagger.brag.views.FullScreenImageDialogFragment;
import com.ragtagger.brag.ui.notification.NotificationListFragment;
import com.ragtagger.brag.views.webview.WebviewDialogFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.more.adapter.MoreListAdapter;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.ui.order.MyOrderListFragment;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 07-11-2017.
 */


public class MoreFragment extends CoreFragment<FragmentMoreBinding, MoreViewModel> implements MoreNavigator {

    @Inject
    MoreViewModel mMoreViewModel;

    FragmentMoreBinding mFragmentMoreBinding;


    DataUser mUserData;
    MoreListAdapter moreListAdapter;
    List<DataMoreList> moreListData;

    Dialog alertDialog;

    private long mLastClickTime = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoreViewModel.setNavigator(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mUpdateNotification,
                new IntentFilter(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mUpdateProfile, new IntentFilter(Constants.LOCALBROADCAST_UPDATE_PROFILE));
    }

    @Override
    public void beforeViewCreated() {
        mUserData = mMoreViewModel.getDataManager().getUserData();
    }


    @Override
    public void afterViewCreated() {
        mFragmentMoreBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentMoreBinding.baseLayout);

        mFragmentMoreBinding.imageviewProfile.setVisibility(View.GONE);

        //footer (version name)
        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_more_list, null, false);
        ((TextView) footerView.findViewById(R.id.textview_version)).setText(getString(R.string.label_version) + " " + BuildConfig.VERSION_NAME);
        Utility.applyTypeFace(getActivity(), (LinearLayout) footerView.findViewById(R.id.base_layout));
        mFragmentMoreBinding.listview.addFooterView(footerView, null, false);

        mFragmentMoreBinding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                int viewId = (int) moreListAdapter.getItemId(position);
                Intent intent;
                Bundle bundle;
                WebviewDialogFragment dialogFragment;
                switch (viewId) {
                    case 1://order list
                        ((MainActivity) getActivity()).pushFragments(new MyOrderListFragment()
                                , true, true);
                        break;
                    case 2://privacy policy
                        bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_TITLE, "Privacy");
                        bundle.putString(Constants.BUNDLE_SUBTITLE, "https://bragstore.com/pages/privacy-policy");
                        dialogFragment = new WebviewDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getChildFragmentManager(), "");
                        break;
                    case 3://terms & conditions
                        bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_TITLE, "Terms and Condition");
                        bundle.putString(Constants.BUNDLE_SUBTITLE, "https://bragstore.com/pages/terms-and-condition");
                        dialogFragment = new WebviewDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getChildFragmentManager(), "");
                        break;
                    case 4://change password
                        intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra(Constants.BUNDLE_MOBILE, mUserData.getMobileNumber());
                        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, Constants.ProfileIsFrom.CHANGE_PASS.ordinal());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        break;
                    case 5://logout
                        showAlertMessageLogOut(getActivity(), getString(R.string.msg_logout));
                        break;

                    case 6://change mobile number
                        intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra(Constants.BUNDLE_MOBILE, mUserData.getMobileNumber());
                        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, Constants.ProfileIsFrom.CHANGE_MOBILE.ordinal());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        break;
                    case 7://notification list
                        ((MainActivity) getActivity()).pushFragments(new NotificationListFragment(), true, true);
                        break;
                    case 8://update profile
                        intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra(Constants.BUNDLE_MOBILE, mUserData.getMobileNumber());
                        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, Constants.ProfileIsFrom.UPDATE_PROFILE.ordinal());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        break;

                    case 9://add and edit address
                        intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, Constants.ProfileIsFrom.ADD_EDIT_ADDRESS.ordinal());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        break;

                    case 10://faqs
                        bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_TITLE, "FAQs");
                        bundle.putString(Constants.BUNDLE_SUBTITLE, "https://bragstore.com/pages/refund-cancellations");
                        dialogFragment = new WebviewDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getChildFragmentManager(), "");
                        break;
                    case 11://about us
                        bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_TITLE, "About us");
                        bundle.putString(Constants.BUNDLE_SUBTITLE, "https://bragstore.com/pages/about-us");
                        dialogFragment = new WebviewDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getChildFragmentManager(), "");
                        break;
                }
            }
        });

        mMoreViewModel.setFullName(mUserData.getFullName());
        mMoreViewModel.setFullAddress(mUserData.getFullAddress());
        moreListData = new ArrayList<>();
        moreListData.add(new DataMoreList(0, getResources().getDrawable(R.drawable.ic_cart),
                ""));
        moreListData.add(new DataMoreList(Constants.MoreList.MY_ORDER.getNumericType(), getResources().getDrawable(R.drawable.ic_order),
                getString(R.string.label_my_order)));
        moreListData.add(new DataMoreList(Constants.MoreList.NOTIFICATION.getNumericType(), getResources().getDrawable(R.drawable.ic_notification_more),
                Utility.getNotificationlabel(getActivity())));
        moreListData.add(new DataMoreList(0, getResources().getDrawable(R.drawable.ic_cart),
                ""));
        moreListData.add(new DataMoreList(Constants.MoreList.PRIVACY_POLICY.getNumericType(), getResources().getDrawable(R.drawable.ic_privacy_policy),
                getString(R.string.label_pri_policy)));
        moreListData.add(new DataMoreList(Constants.MoreList.TERMS_AND.getNumericType(), getResources().getDrawable(R.drawable.ic_terms_conditions),
                getString(R.string.label_terms_and)));
        moreListData.add(new DataMoreList(Constants.MoreList.FAQ.getNumericType(), getResources().getDrawable(R.drawable.ic_faq),
                getString(R.string.label_faq)));
        moreListData.add(new DataMoreList(Constants.MoreList.ABOUT_US.getNumericType(), getResources().getDrawable(R.drawable.ic_about_us),
                getString(R.string.label_about_us)));
        moreListData.add(new DataMoreList(0, getResources().getDrawable(R.drawable.ic_cart),
                ""));
        moreListData.add(new DataMoreList(Constants.MoreList.CHANGE_ADDRESS.getNumericType(), getResources().getDrawable(R.drawable.ic_change_address),
                getString(R.string.label_add_update_address)));
        moreListData.add(new DataMoreList(Constants.MoreList.CHANGE_PASS.getNumericType(), getResources().getDrawable(R.drawable.ic_change_pass),
                getString(R.string.label_change_pass)));
        moreListData.add(new DataMoreList(Constants.MoreList.CHANGE_MOBILE.getNumericType(), getResources().getDrawable(R.drawable.ic_change_mob_num),
                getString(R.string.label_change_mobile_num)));
        moreListData.add(new DataMoreList(Constants.MoreList.USER_PROFILE.getNumericType(), getResources().getDrawable(R.drawable.ic_update_profile),
                getString(R.string.label_update_profile)));
        moreListData.add(new DataMoreList(Constants.MoreList.LOGOUT.getNumericType(), getResources().getDrawable(R.drawable.ic_logout),
                getString(R.string.label_logout)));

        moreListAdapter = new MoreListAdapter(getContext(), moreListData);
        mFragmentMoreBinding.listview.setAdapter(moreListAdapter);

        Utility.setListViewHeightBasedOnChildren(mFragmentMoreBinding.listview);

    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(false, false, false, getString(R.string.toolbar_label_more));
    }

    @Override
    public MoreViewModel getViewModel() {
        return mMoreViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }


    private void showAlertMessageLogOut(final Context mContext, String s) {


        try {
            alertDialog = new Dialog(mContext);

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.dialog_two_button);
            Utility.applyTypeFace(mContext, (LinearLayout) alertDialog.findViewById(R.id.base_layout));
            alertDialog.setCancelable(false);

            TextView txt = (TextView) alertDialog.findViewById(R.id.txt_alert_tv);
            txt.setText(s);

            Button dialogYesBtn = (Button) alertDialog.findViewById(R.id.button_yes_alert_btn);
            Button dialogNoBtn = (Button) alertDialog.findViewById(R.id.button_no_alert_btn);
            dialogNoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            dialogYesBtn.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    alertDialog.dismiss();
                    if (Utility.isConnection(getActivity())) {
                        showProgress();
                        mMoreViewModel.logout();
                        //LogOutAPI();
                    } else {
                        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
                    }
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver mUpdateNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            moreListData.set(2, new DataMoreList(Constants.MoreList.NOTIFICATION.getNumericType(), getResources().getDrawable(R.drawable.ic_notification_more), ((CoreActivity) getActivity()).getNotificationlabel()));
            moreListAdapter.notifyDataSetChanged();
            //update in badge number
            ((HomeFragment) getParentFragment()).setNotificationBadge();
        }
    };

    private BroadcastReceiver mUpdateProfile = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.BUNDLE_IS_ADDRESS_UPDATE)) {
                if (intent.getBooleanExtra(Constants.BUNDLE_IS_ADDRESS_UPDATE, true)) {
                    mMoreViewModel.setFullAddress(mMoreViewModel.getDataManager().getUserData().getFullAddress());
                } else if (intent.hasExtra(Constants.BUNDLE_KEY_MOBILE_NUM)) {
                    //if successfully change moblie  num than update userprofile prefrence
                    DataUser dataUser = mMoreViewModel.getDataManager().getUserData();
                    dataUser.setMobileNumber(intent.getStringExtra(Constants.BUNDLE_KEY_MOBILE_NUM));
                    mMoreViewModel.getDataManager().setUserData(new Gson().toJson(dataUser));
                    mUserData = mMoreViewModel.getDataManager().getUserData();
                } else {
                    mMoreViewModel.setFullName(mMoreViewModel.getDataManager().getUserData().getFullName());
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateNotification);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateProfile);

    }

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void profile() {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_IMAGE_URL, "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature1.jpg?9636438770338163967");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DialogFragment mDialogFragmentImage = new FullScreenImageDialogFragment();
        mDialogFragmentImage.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        mDialogFragmentImage.setArguments(args);
        mDialogFragmentImage.show(fm, "");
    }

    @Override
    public void logout() {
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }


}
