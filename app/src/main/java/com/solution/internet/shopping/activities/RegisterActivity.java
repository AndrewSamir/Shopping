package com.solution.internet.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity implements HandleRetrofitResp, Validator.ValidationListener {

    //region fields
    Validator validator;
    String type;
    //endregion

    //region views
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtRegisterFullName)
    EditText edtRegisterFullName;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtRegisterMobile)
    EditText edtRegisterMobile;

    @BindView(R.id.edtRegisterMail)
    EditText edtRegisterMail;
    @BindView(R.id.edtRegisterNationalId)
    EditText edtRegisterNationalId;
    @BindView(R.id.edtRegisterLink)
    EditText edtRegisterLink;

    @Password(messageResId = R.string.required)
    @BindView(R.id.edtRegisterPassword)
    EditText edtRegisterPassword;

    @ConfirmPassword(messageResId = R.string.correct_password)
    @BindView(R.id.edtRegisterConfirmPassword)
    EditText edtRegisterConfirmPassword;


    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        type = intent.getStringExtra(DataEnum.intentRegisterType.name());
        ButterKnife.bind(this);
        HandleCalls.getInstance(this).setonRespnseSucess(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    //endregion

    //region clicks

    @OnClick(R.id.btnRegisterRegister)
    public void onClickbtnRegisterRegister() {
        validator.validate();
    }

    @OnClick(R.id.tvRegisterLogin)
    public void onClicktvRegisterLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {

    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }
    //endregion

    //region calls
    private void callSignUp() {
        ModelSignUpRequest modelSignUpRequest = new ModelSignUpRequest();
        modelSignUpRequest.setEmail(edtRegisterMail.getText().toString().trim());
        modelSignUpRequest.setFullname(edtRegisterFullName.getText().toString().trim());
        modelSignUpRequest.setMobile(edtRegisterMobile.getText().toString().trim());
        modelSignUpRequest.setPassword(edtRegisterPassword.getText().toString().trim());
        modelSignUpRequest.setUserType(type);

        Call call = HandleCalls.restShopping.getClientService().callSignup(modelSignUpRequest);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callSignup.name(), true);
    }

    //endregion

    //region validation


    @Override
    public void onValidationSucceeded() {
        callSignUp();
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
}
