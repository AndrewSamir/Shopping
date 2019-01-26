package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelConfirmationCodeRequest.ModelConfirmationCodeRequest;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ConfirmationCodeFragment extends BaseFragment implements HandleRetrofitResp, Validator.ValidationListener {

    //region fields
    Validator validator;
    static String mobile;
    //endregion

    //region views

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtConfirmationCode)
    EditText edtConfirmationCode;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.confirmation_code, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
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
        return 0;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("user"))
            addFragment(HomeFragment.init(), false);
        else
            addFragment(DeliveryHomeFragment.init(), false);
    }

    @Override
    public void onNoContent(String flag, int code) {
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("user"))
            addFragment(HomeFragment.init(), false);
        else
            addFragment(DeliveryHomeFragment.init(), false);

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion

    //region clicks

    @OnClick(R.id.btnConfirmationCodeSend)
    public void onClickbtnConfirmationCodeSend() {
        validator.validate();
    }
    //endregion

    //region calls
    private void callActivePhone() {

        ModelConfirmationCodeRequest modelConfirmationCodeRequest = new ModelConfirmationCodeRequest();
        modelConfirmationCodeRequest.setResetcode(edtConfirmationCode.getText().toString());
        modelConfirmationCodeRequest.setMobile(mobile);

        Call call = HandleCalls.restShopping.getClientService().callActivePhone(modelConfirmationCodeRequest);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callActivePhone.name(), true);
    }
    //endregion

    //region functions

    public static ConfirmationCodeFragment init(String mobile) {
        setMobile(mobile);
        return new ConfirmationCodeFragment();
    }

    public static void setMobile(String mobile) {
        ConfirmationCodeFragment.mobile = mobile;
    }

    //endregion

    //region validation


    @Override
    public void onValidationSucceeded() {
        callActivePhone();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getBaseActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
//                showMessage(message);
            }
        }
    }


    //endregion
}
