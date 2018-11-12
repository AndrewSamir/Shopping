package com.solution.internet.shopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.solution.internet.shopping.activities.MapsActivity;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelLoginRequest.ModelLoginRequest;
import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements HandleRetrofitResp, Validator.ValidationListener
{

    //region fields
    Validator validator;
    //endregion

    //region views
    @NotEmpty
    @BindView(R.id.edtLoginPhone)
    EditText edtLoginPhone;

    @Password
    @BindView(R.id.edtLoginPassword)
    EditText edtLoginPassword;

    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        HandleCalls.getInstance(this).setonRespnseSucess(this);

        edtLoginPhone.setText("501231233");
        edtLoginPassword.setText("123123");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    //endregion

    //region clicks
    @OnClick(R.id.btnLoginEnter)
    public void onClickbtnLoginEnter()
    {
        validator.validate();
    }

    //endregion

    //region call response
    @Override
    public void onResponseSuccess(String flag, Object o)
    {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

        if (flag.equals(DataEnum.callLogin.name()))
        {
            ModelLoginResponse modelLoginResponse = gson.fromJson(jsonObject, ModelLoginResponse.class);
            SharedPrefHelper.getInstance(this).setUser(modelLoginResponse);
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }
    }

    @Override
    public void onNoContent(String flag, int code)
    {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position)
    {

    }

    //endregion

    //region calls
    private void callLogin()
    {
        ModelLoginRequest modelLoginRequest = new ModelLoginRequest();
        modelLoginRequest.setMobile(edtLoginPhone.getText().toString().trim());
        modelLoginRequest.setPassword(edtLoginPassword.getText().toString().trim());

        Call call = HandleCalls.restShopping.getClientService().callLogin(modelLoginRequest);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callLogin.name(), true);
    }

    //endregion

    //region validation


    @Override
    public void onValidationSucceeded()
    {
        callLogin();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            } else
            {
//                showMessage(message);
            }
        }
    }
    //endregion
}
