package com.solution.internet.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
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

public class ChangePasswordActivity extends AppCompatActivity implements HandleRetrofitResp, Validator.ValidationListener {

    //region fields
    Validator validator;

    //endregion

    //region views
    @Password
    @BindView(R.id.edtChangePasswordOld)
    EditText edtChangePasswordOld;

    @Password
    @BindView(R.id.edtChangePasswordNew)
    EditText edtChangePasswordNew;

    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HandleCalls.getInstance(this).setonRespnseSucess(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    //endregion

    //region clicks

    @OnClick(R.id.btnChangePassword)
    public void onClickbtnChangePassword() {
        validator.validate();
    }
    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {
        onBackPressed();
    }

    @Override
    public void onNoContent(String flag, int code) {
        onBackPressed();
    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }
    //endregion

    //region calls

    private void callChangePassword() {

        ModelChangePasswordRequest modelChangePasswordRequest = new ModelChangePasswordRequest();
        modelChangePasswordRequest.setPassword(edtChangePasswordOld.getText().toString().trim());
        modelChangePasswordRequest.setNewpassword(edtChangePasswordNew.getText().toString().trim());

        Call call = HandleCalls.restShopping.getClientService().callChangePassword(modelChangePasswordRequest);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callChangePassword.name(), true);

    }
    //endregion

    //region functions


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
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
//                showMessage(message);
            }
        }
    }
    //endregion

    @OnClick(R.id.tvNavBarMore)
    public void onClicktvNavBarMore() {
        startActivity(new Intent(this, DelegateDetailsActivity.class));
    }

    @OnClick(R.id.tvNavBarProducts)
    public void onClicktvNavBarProducts() {
        startActivity(new Intent(this, SearchActivity.class));
    }

}
