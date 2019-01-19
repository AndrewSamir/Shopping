package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelGetProfile.ModelGetProfile;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class UserProfileFragment extends BaseFragment implements HandleRetrofitResp {
    //region fields
    ModelGetProfile modelGetProfile;
    //endregion

    //region views

    @BindView(R.id.tvUserProfileName)
    TextView tvUserProfileName;

    @BindView(R.id.tvUserProfileMobile)
    TextView tvUserProfileMobile;

    @BindView(R.id.tvUserProfileMail)
    TextView tvUserProfileMail;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.user_profile_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        callGetProfile();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader() {
        return false;
    }

    @Override
    protected boolean canShowBottomBar() {
        return false;
    }

    @Override
    protected boolean canShowBackArrow() {
        return false;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    public int getSelectedMenuId() {
        return R.id.tvNavBarMore;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

        if (flag.equals(DataEnum.callGetProfile.name())) {
            modelGetProfile = gson.fromJson(jsonObject, ModelGetProfile.class);
            adjustView();
        }
    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion

    //region clicks
    @OnClick(R.id.tvUserProfileEditData)
    void onClicktvUserProfileEditData(View view) {
        addFragment(UserEditProfileFragment.init(modelGetProfile), true);
    }

    @OnClick(R.id.tvUserProfileChangePassword)
    void onClicktvUserProfileChangePassword(View view) {
        addFragment(ChangePasswordFragment.init(), true);
    }
    //endregion

    //region calls

    private void callGetProfile() {
        Call call = HandleCalls.restShopping.getClientService().callGetProfile();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callGetProfile.name(), true);
    }
    //endregion

    //region functions

    public static UserProfileFragment init() {
        return new UserProfileFragment();
    }

    private void adjustView() {
        tvUserProfileName.setText(modelGetProfile.getFullname());
        tvUserProfileMobile.setText(modelGetProfile.getMobile());
        tvUserProfileMail.setText(modelGetProfile.getEmail());

    }
    //endregion

}
