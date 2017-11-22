package com.pulse.brag.fragments;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.pulse.brag.BuildConfig;
import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.activities.ChangePasswordActivity;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.adapters.MoreListAdapter;
import com.pulse.brag.enums.MoreList;
import com.pulse.brag.helper.ApiClient;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.GeneralRespone;
import com.pulse.brag.pojo.datas.MoreListData;
import com.pulse.brag.pojo.datas.UserData;
import com.pulse.brag.pojo.respones.ChangePasswordRespone;
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
    TextView mTxtVersion;
    TextView mTxtName;

    UserData mUserData;
    MoreListAdapter moreListAdapter;

    Dialog alertDialog;

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
        mTxtVersion = (TextView) mView.findViewById(R.id.textview_version);
        mTxtName = (TextView) mView.findViewById(R.id.textview_name);

        mUserData = PreferencesManager.getInstance().getUserData();
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
                int viewId = (int) moreListAdapter.getItemId(position);

                switch (viewId) {
                    case 1:
                        Toast.makeText(getActivity(), "My Order", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Privacy", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Terms", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                        intent.putExtra(Constants.BUNDLE_MOBILE,mUserData.getMobileNumber());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                        ((BaseActivity) getActivity()).pushFragments(
//                                ChangePasswordFragment.newInstance(mUserData.getMobileNumber())
//                                , true, true);
                        break;
                    case 5:
                        showAlertMessageLogOut(getActivity(), getString(R.string.msg_logout));
                        break;
                }
            }
        });
    }

    @Override
    public void showData() {

        mTxtName.setText(mUserData.getFullName());

        List<MoreListData> moreListData = new ArrayList<>();
        moreListData.add(new MoreListData(0, getResources().getDrawable(R.drawable.ic_cart), ""));
        moreListData.add(new MoreListData(MoreList.MY_ORDER.getNumericType(), getResources().getDrawable(R.drawable.ic_cart), getString(R.string.label_my_order)));
        moreListData.add(new MoreListData(0, getResources().getDrawable(R.drawable.ic_cart), ""));
        moreListData.add(new MoreListData(MoreList.PRIVACY_POLICY.getNumericType(), getResources().getDrawable(R.drawable.ic_cart), getString(R.string.label_pri_policy)));
        moreListData.add(new MoreListData(MoreList.TERMS_AND.getNumericType(), getResources().getDrawable(R.drawable.ic_cart), getString(R.string.label_terms_and)));
        moreListData.add(new MoreListData(0, getResources().getDrawable(R.drawable.ic_cart), ""));
        moreListData.add(new MoreListData(MoreList.CHANGE_PASS.getNumericType(), getResources().getDrawable(R.drawable.ic_change_pass), getString(R.string.label_change_pass)));
        moreListData.add(new MoreListData(MoreList.LOGOUT.getNumericType(), getResources().getDrawable(R.drawable.ic_logout), getString(R.string.label_logout)));

        moreListAdapter = new MoreListAdapter(getContext(), moreListData);
        mListView.setAdapter(moreListAdapter);

        mTxtVersion.setText(getString(R.string.label_version) + " " + BuildConfig.VERSION_NAME);

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
                    if (Utility.isConnection(getActivity())) {
                        LogOutAPI();
                    } else {
                        Utility.showAlertMessage(getActivity(), 0);
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
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/");
        Call<GeneralRespone> responeCall = ApiClient.getInstance(getActivity()).getApiResp().logout();
        responeCall.enqueue(new Callback<GeneralRespone>() {
            @Override
            public void onResponse(Call<GeneralRespone> call, Response<GeneralRespone> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralRespone respone = response.body();
                    if (respone.isStatus()) {
                        alertDialog.dismiss();
                        PreferencesManager.getInstance().logout();
                        Intent intent = new Intent(getActivity(), SplashActivty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    alertDialog.dismiss();
                    Utility.showAlertMessage(getActivity(), 1);
                }
            }

            @Override
            public void onFailure(Call<GeneralRespone> call, Throwable t) {
                hideProgressDialog();
                alertDialog.dismiss();
                Utility.showAlertMessage(getActivity(), t);
            }
        });
    }
}
