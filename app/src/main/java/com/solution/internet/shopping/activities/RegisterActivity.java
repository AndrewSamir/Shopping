package com.solution.internet.shopping.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelGetCities.ModelGetCities;
import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;
import com.solution.internet.shopping.models.ModelRefreshTokenRequest.ModelRefreshTokenRequest;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class RegisterActivity extends Activity implements HandleRetrofitResp, Validator.ValidationListener, CompoundButton.OnCheckedChangeListener {

    //region fields
    Validator validator;
    String type;
    int selectedCityId = 0;
    //endregion

    //region views
    @Length(min = 7, messageResId = R.string.full_name_validation)
    @BindView(R.id.edtRegisterFullName)
    EditText edtRegisterFullName;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtRegisterMobile)
    EditText edtRegisterMobile;

    @Email(messageResId = R.string.required)
    @BindView(R.id.edtRegisterMail)
    EditText edtRegisterMail;
    @BindView(R.id.edtRegisterNameShow)
    EditText edtRegisterNameShow;
    @BindView(R.id.edtRegisterLink)
    EditText edtRegisterLink;
    @BindView(R.id.edtRegisterNationalId)
    EditText edtRegisterNationalId;

    @Password(messageResId = R.string.required)
    @BindView(R.id.edtRegisterPassword)
    EditText edtRegisterPassword;

    @ConfirmPassword(messageResId = R.string.correct_password)
    @BindView(R.id.edtRegisterConfirmPassword)
    EditText edtRegisterConfirmPassword;

    @BindView(R.id.chRegisterPolicy)
    CheckBox chRegisterPolicy;

    @BindView(R.id.btnRegisterRegister)
    Button btnRegisterRegister;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtRegisterCity)
    EditText edtRegisterCity;
    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.buyer_register);

        Intent intent = getIntent();
        type = intent.getStringExtra(DataEnum.intentRegisterType.name());
        ButterKnife.bind(this);
        edtRegisterCity.setFocusable(false);
        edtRegisterCity.setClickable(true);
        adjustView();
        HandleCalls.getInstance(this).setonRespnseSucess(this);
//        callCities();
        chRegisterPolicy.setOnCheckedChangeListener(this);
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

    @OnClick(R.id.btnRegisterRegister)
    public void onClickbtnRegisterRegister() {
        validator.validate();
    }

/*
    @OnClick(R.id.tvRegisterLogin)
    public void onClicktvRegisterLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
*/

    @OnClick(R.id.edtRegisterCity)
    public void onClickedtRegisterCity() {

        edtRegisterCity.setError(null);
        final CharSequence[] items = new CharSequence[SplashActivity.citiesName.size()];

        for (int i = 0; i < SplashActivity.citiesName.size(); i++) {
            items[i] = SplashActivity.citiesName.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("أختر مدينة");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                edtRegisterCity.setText(SplashActivity.citiesName.get(item));
                selectedCityId = SplashActivity.modelGetCitiesList.get(item).getCityId();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {

        Gson gson = new Gson();

        if (flag.equals(DataEnum.callSignup.name())) {
            JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
            ModelLoginResponse modelLoginResponse = gson.fromJson(jsonObject, ModelLoginResponse.class);
            SharedPrefHelper.getInstance(this).setUser(modelLoginResponse);
          /*  startActivity(new Intent(RegisterActivity.this, MainActivity.class)
                    .putExtra("mobile", edtRegisterMobile.getText().toString()));*/
            if (FirebaseInstanceId.getInstance().getToken() != null)
                callRefreshToken();
            else {
                if (SharedPrefHelper.getInstance(this).getUserType().equals("user"))
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class).putExtra("mobile", edtRegisterMobile.getText().toString()));
                else
                    startActivity(new Intent(RegisterActivity.this, DeliveryMainActivity.class).putExtra("mobile", edtRegisterMobile.getText().toString()));
                finish();
            }
        }

    }

    @Override
    public void onNoContent(String flag, int code) {
        if (SharedPrefHelper.getInstance(this).getUserType().equals("user"))
            startActivity(new Intent(RegisterActivity.this, MainActivity.class).putExtra("mobile", edtRegisterMobile.getText().toString()));
        else
            startActivity(new Intent(RegisterActivity.this, DeliveryMainActivity.class).putExtra("mobile", edtRegisterMobile.getText().toString()));
        finish();
    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }
    //endregion

    //region calls
    private void callSignUp() {
        Call call;
        ModelSignUpRequest modelSignUpRequest = new ModelSignUpRequest();
        modelSignUpRequest.setEmail(edtRegisterMail.getText().toString().trim());
        modelSignUpRequest.setFullname(edtRegisterFullName.getText().toString().trim());
        modelSignUpRequest.setMobile(edtRegisterMobile.getText().toString().trim());
        modelSignUpRequest.setPassword(edtRegisterPassword.getText().toString().trim());
        if (type.equals("delivery")) {
            if (selectedCityId != 0)
                modelSignUpRequest.setCity_id(selectedCityId + "");
            if (edtRegisterLink.getText().toString().length() > 0)
                modelSignUpRequest.setMaarof_link(edtRegisterLink.getText().toString());
            else
                modelSignUpRequest.setMaarof_link(" ");
            if (edtRegisterNationalId.getText().toString().length() > 0)
                modelSignUpRequest.setNational_id(edtRegisterNationalId.getText().toString());
            else
                modelSignUpRequest.setNational_id(" ");

            modelSignUpRequest.setLat("24.56565645");
            modelSignUpRequest.setLng("36.34343434");

            call = HandleCalls.restShopping.getClientService().callSignupDelivery(modelSignUpRequest);
            HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callSignup.name(), true);
        } else {
            call = HandleCalls.restShopping.getClientService().callSignup(modelSignUpRequest);
            HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callSignup.name(), true);
        }
    }

   /* private void callCities() {

        Call call = HandleCalls.restShopping.getClientService().callCities();
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callCities.name(), true);
    }*/

    private void callRefreshToken() {
        ModelRefreshTokenRequest modelRefreshTokenRequest = new ModelRefreshTokenRequest();
        modelRefreshTokenRequest.setDeviceToken(FirebaseInstanceId.getInstance().getToken());

        Call call = HandleCalls.restShopping.getClientService().callRefreshToken(modelRefreshTokenRequest);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callRefreshToken.name(), true);
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

    //region functions
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            btnRegisterRegister.setEnabled(true);
            btnRegisterRegister.setAlpha(1f);
        } else {

            btnRegisterRegister.setEnabled(false);
            btnRegisterRegister.setAlpha(0.5f);
        }
    }

    private void adjustView() {

        if (type.equals("user")) {
            edtRegisterLink.setVisibility(View.GONE);
            edtRegisterNameShow.setVisibility(View.GONE);
            edtRegisterCity.setVisibility(View.GONE);
        }
    }
    //endregion

}
