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
import com.solution.internet.shopping.activities.DelegateActivity;
import com.solution.internet.shopping.activities.LoginActivity;
import com.solution.internet.shopping.activities.MapsActivity;
import com.solution.internet.shopping.activities.RegisterActivity;
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

public class MainActivity extends AppCompatActivity {

    //region fields

    //endregion

    //region views


    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //endregion

    //region clicks
    @OnClick(R.id.btnLoginEnter)
    public void onClickbtnLoginEnter() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.btnSignUp)
    public void onClickbtnSignUp() {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    @OnClick(R.id.btnDelegate)
    public void onClickbtnDelegate() {
        startActivity(new Intent(MainActivity.this, DelegateActivity.class));
    }
    //endregion
}
