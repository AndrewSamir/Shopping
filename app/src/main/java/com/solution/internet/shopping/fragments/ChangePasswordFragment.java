package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelChangePasswordRequest.ModelChangePasswordRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ChangePasswordFragment extends BaseFragment implements HandleRetrofitResp, Validator.ValidationListener {

    //region fields
    Validator validator;

    //endregion

    //region views
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtChangePasswordOld)
    EditText edtChangePasswordOld;

    @Password(messageResId = R.string.required)
    @BindView(R.id.edtChangePasswordNew)
    EditText edtChangePasswordNew;

    @ConfirmPassword(messageResId = R.string.required_confirm_password)
    @BindView(R.id.edtChangePasswordConfirmNew)
    EditText edtChangePasswordConfirmNew;
    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.change_password_fragment, container, false);

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
//               appHeader.setRight(0, 0, 0);
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
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion

    //region clicks

    @OnClick(R.id.btnChangePassword)
    public void onClickbtnChangePassword() {
        validator.validate();
    }

    //endregion

    //region calls

    private void callChangePassword() {

        ModelChangePasswordRequest modelChangePasswordRequest = new ModelChangePasswordRequest();
        modelChangePasswordRequest.setPassword(edtChangePasswordOld.getText().toString().trim());
        modelChangePasswordRequest.setNewpassword(edtChangePasswordNew.getText().toString().trim());

        Call call = HandleCalls.restShopping.getClientService().callChangePassword(modelChangePasswordRequest);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callChangePassword.name(), true);

    }
    //endregion

    //region functions

    public static ChangePasswordFragment init() {
        return new ChangePasswordFragment();
    }
    //endregion

    //region validation


    @Override
    public void onValidationSucceeded() {
        callChangePassword();
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
