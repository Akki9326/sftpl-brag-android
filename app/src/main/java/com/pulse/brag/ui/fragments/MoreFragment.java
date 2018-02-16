package com.pulse.brag.ui.fragments;


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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pulse.brag.BuildConfig;
import com.pulse.brag.R;
import com.pulse.brag.enums.ProfileIsFrom;
import com.pulse.brag.ui.activities.BaseActivity;
import com.pulse.brag.ui.profile.UserProfileActivity;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.adapters.MoreListAdapter;
import com.pulse.brag.enums.MoreList;
import com.pulse.brag.data.remote.ApiClient;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.PreferencesManager;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.datas.MoreListData;
import com.pulse.brag.pojo.datas.UserData;
import com.pulse.brag.views.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikhil.vadoliya on 07-11-2017.
 */


public class MoreFragment extends BaseFragment implements BaseInterface {

    View mView;
    ImageView mImgProfile;
    ListView mListView;
    TextView mTxtName;

    UserData mUserData;
    MoreListAdapter moreListAdapter;
    List<MoreListData> moreListData;

    Dialog alertDialog;

    private long mLastClickTime = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_more, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mUpdateNotification,
                new IntentFilter(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION));
    }

    @Override
    public void setToolbar() {
//        ((BaseActivity) getActivity()).showToolbar(false, false, false, getString(R.string.toolbar_label_more));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void initializeData() {
        mImgProfile = (ImageView) mView.findViewById(R.id.imageview_profile);
        mListView = (ListView) mView.findViewById(R.id.listview);
        mTxtName = (TextView) mView.findViewById(R.id.textview_name);

        mImgProfile.setVisibility(View.GONE);

        mUserData = PreferencesManager.getInstance().getUserData();

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));

        //footer (version name)
        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_more_list, null, false);
        ((TextView) footerView.findViewById(R.id.textview_version)).setText(getString(R.string.label_version) + " " + BuildConfig.VERSION_NAME);
        Utility.applyTypeFace(getActivity(), (LinearLayout) footerView.findViewById(R.id.base_layout));
        mListView.addFooterView(footerView, null, false);
    }

    @Override
    public void setListeners() {

        mImgProfile.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                Bundle args = new Bundle();
                args.putString(Constants.BUNDLE_IMAGE_URL, "http://cdn.shopify.com/s/files/1/1629/9535/t/2/assets/feature1.jpg?9636438770338163967");
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DialogFragment mDialogFragmentImage = new FullScreenImageDialogFragment();
                mDialogFragmentImage.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                mDialogFragmentImage.setArguments(args);
                mDialogFragmentImage.show(fm, "");
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    case 1:
                        ((BaseActivity) getActivity()).pushFragments(new MyOrderFragment()
                                , true, true);
                        break;
                    case 2:

                        bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_TITLE, "Privacy");
                        bundle.putString(Constants.BUNDLE_SUBTITLE, "https://bragstore.com/pages/privacy-policy");
                        dialogFragment = new WebviewDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getChildFragmentManager(), "");
                        break;
                    case 3:
                        bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_TITLE, "Terms and Condition");
                        bundle.putString(Constants.BUNDLE_SUBTITLE, "https://bragstore.com/pages/terms-and-condition");
                        dialogFragment = new WebviewDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getChildFragmentManager(), "");
                        break;
                    case 4:
                        intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra(Constants.BUNDLE_MOBILE, mUserData.getMobileNumber());
                        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, ProfileIsFrom.CHANGE_PASS.ordinal());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                        ((BaseActivity) getActivity()).pushFragments(
//                                ChangePassFragment.newInstance(mUserData.getMobileNumber())
//                                , true, true);
                        break;
                    case 5:
                        showAlertMessageLogOut(getActivity(), getString(R.string.msg_logout));
                        break;

                    case 6:
                        intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra(Constants.BUNDLE_MOBILE, mUserData.getMobileNumber());
                        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, ProfileIsFrom.CHANGE_MOBILE.ordinal());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        break;
                    case 7:
                        ((BaseActivity) getActivity()).pushFragments(new NotificationListFragment(), true, true);
                        break;
                    case 8:
                        intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra(Constants.BUNDLE_MOBILE, mUserData.getMobileNumber());
                        intent.putExtra(Constants.BUNDLE_PROFILE_IS_FROM, ProfileIsFrom.UPDATE_PROFILE.ordinal());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        break;
                }
            }
        });
    }

    @Override
    public void showData() {

        mTxtName.setText(mUserData.getFullName());

        moreListData = new ArrayList<>();
        moreListData.add(new MoreListData(0, getResources().getDrawable(R.drawable.ic_cart),
                ""));
        moreListData.add(new MoreListData(MoreList.MY_ORDER.getNumericType(), getResources().getDrawable(R.drawable.ic_order),
                getString(R.string.label_my_order)));
        moreListData.add(new MoreListData(MoreList.NOTIFICATION.getNumericType(), getResources().getDrawable(R.drawable.ic_notification),
                ((BaseActivity) getActivity()).getNotificationlabel()));
        moreListData.add(new MoreListData(0, getResources().getDrawable(R.drawable.ic_cart),
                ""));
        moreListData.add(new MoreListData(MoreList.PRIVACY_POLICY.getNumericType(), getResources().getDrawable(R.drawable.ic_cart),
                getString(R.string.label_pri_policy)));
        moreListData.add(new MoreListData(MoreList.TERMS_AND.getNumericType(), getResources().getDrawable(R.drawable.ic_cart),
                getString(R.string.label_terms_and)));
        moreListData.add(new MoreListData(0, getResources().getDrawable(R.drawable.ic_cart),
                ""));
        moreListData.add(new MoreListData(MoreList.CHANGE_PASS.getNumericType(), getResources().getDrawable(R.drawable.ic_change_pass),
                getString(R.string.label_change_pass)));
        moreListData.add(new MoreListData(MoreList.CHANGE_MOBILE.getNumericType(), getResources().getDrawable(R.drawable.ic_cart),
                getString(R.string.label_change_mobile_num)));
        moreListData.add(new MoreListData(MoreList.USER_PROFILE.getNumericType(), getResources().getDrawable(R.drawable.ic_cart),
                getString(R.string.label_update_profile)));
        moreListData.add(new MoreListData(MoreList.LOGOUT.getNumericType(), getResources().getDrawable(R.drawable.ic_logout),
                getString(R.string.label_logout)));

        moreListAdapter = new MoreListAdapter(getContext(), moreListData);
        mListView.setAdapter(moreListAdapter);

        Utility.setListViewHeightBasedOnChildren(mListView);

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
                        LogOutAPI();
                    } else {
                        //Utility.showAlertMessage(getActivity(), 0, null);
                        AlertUtils.showAlertMessage(getActivity(), 0, null);
                    }
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LogOutAPI() {
        showProgressDialog();
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<GeneralResponse> responeCall = ApiClient.getInstance(getActivity()).getApiResp().logoutCall();
        responeCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralResponse respone = response.body();
                    if (respone.isStatus()) {
                        alertDialog.dismiss();
                        PreferencesManager.getInstance().logout();
                        Intent intent = new Intent(getActivity(), SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    alertDialog.dismiss();
                    //Utility.showAlertMessage(getActivity(), 1, null);
                    AlertUtils.showAlertMessage(getActivity(), 1, null);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                hideProgressDialog();
                alertDialog.dismiss();
                //Utility.showAlertMessage(getActivity(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });
    }

    private BroadcastReceiver mUpdateNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            moreListData.set(2, new MoreListData(MoreList.NOTIFICATION.getNumericType(), getResources().getDrawable(R.drawable.ic_notification), ((BaseActivity) getActivity()).getNotificationlabel()));
            moreListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateNotification);

    }
}
