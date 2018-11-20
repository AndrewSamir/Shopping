package com.solution.internet.shopping.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelGetProfile.ModelGetProfile;
import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class DelegateDetailsActivity extends AppCompatActivity implements HandleRetrofitResp {

    //region fields
    //endregion

    //region views

    @BindView(R.id.tvDelegateName)
    TextView tvDelegateName;

    @BindView(R.id.tvDelegateMobile)
    TextView tvDelegateMobile;

    @BindView(R.id.tvDelegateMail)
    TextView tvDelegateMail;

    @BindView(R.id.imgDelegateDetails)
    CircleImageView imgDelegateDetails;
    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        callGetProfile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HandleCalls.getInstance(this).setonRespnseSucess(this);
    }

    //endregion

    //region clicks


    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

        if (flag.equals(DataEnum.callGetProfile.name())) {
            ModelGetProfile modelGetProfile = gson.fromJson(jsonObject, ModelGetProfile.class);
            adjustView(modelGetProfile);
        }
    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }
    //endregion

    //region calls

    private void callGetProfile() {

        Call call = HandleCalls.restShopping.getClientService().callGetProfile();
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callGetProfile.name(), true);
    }
    //endregion

    //region functions

    private void adjustView(ModelGetProfile modelGetProfile) {
        tvDelegateName.setText(modelGetProfile.getFullname());
        tvDelegateMail.setText(modelGetProfile.getEmail());
        tvDelegateMobile.setText(modelGetProfile.getMobile());

        Picasso.with(this)
                .load(modelGetProfile.getAvatar())
                .into(imgDelegateDetails);
    }
    //endregion

}
