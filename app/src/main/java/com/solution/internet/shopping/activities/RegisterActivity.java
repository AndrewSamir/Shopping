package com.solution.internet.shopping.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelLoginRequest.ModelLoginRequest;
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
    //endregion

    //region views
    @NotEmpty(messageResId = R.id.required)
    @BindView(R.id.edtRegisterFirstName)
    EditText edtRegisterFirstName;

    @NotEmpty(messageResId = R.id.required)
    @BindView(R.id.edtRegisterLastName)
    EditText edtRegisterLastName;

    @NotEmpty(messageResId = R.id.required)
    @BindView(R.id.edtRegisterPostalCode)
    EditText edtRegisterPostalCode;

    @Email(messageResId = R.id.correct_mail)
    @BindView(R.id.edtRegisterMail)
    EditText edtRegisterMail;

    @Password(messageResId = R.id.required)
    @BindView(R.id.edtRegisterPassword)
    EditText edtRegisterPassword;

    @ConfirmPassword(messageResId = R.id.correct_password)
    @BindView(R.id.edtRegisterConfirmPassword)
    EditText edtRegisterConfirmPassword;

    @BindView(R.id.chRegisterMale)
    CheckBox chRegisterMale;

    @BindView(R.id.chRegisterFemale)
    CheckBox chRegisterFemale;

    @NotEmpty(messageResId = R.id.required)
    @BindView(R.id.edtRegisterDescription)
    EditText edtRegisterDescription;
    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        // TODO submit data to server...
    }

    @OnClick(R.id.tvRegisterLogin)
    public void onClicktvRegisterLogin() {
        // TODO submit data to server...
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
    private void callSignup() {
        ModelSignUpRequest modelSignUpRequest = new ModelSignUpRequest();
        modelSignUpRequest.setEmail(edtRegisterMail.getText().toString().trim());
        modelSignUpRequest.setFullname(edtRegisterFirstName.getText().toString().trim() + " " + edtRegisterLastName.getText().toString().trim());
        modelSignUpRequest.setMobile(edtRegisterPostalCode.getText().toString().trim());
        modelSignUpRequest.setPassword(edtRegisterPassword.getText().toString().trim());
        modelSignUpRequest.setUserType("user");

        Call call = HandleCalls.restShopping.getClientService().callSignup(modelSignUpRequest);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callSignup.name(), true);
    }

    //endregion

    //region validation


    @Override
    public void onValidationSucceeded() {
        callSignup();
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
