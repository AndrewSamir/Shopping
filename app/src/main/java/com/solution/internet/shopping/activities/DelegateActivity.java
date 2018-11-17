package com.solution.internet.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.solution.internet.shopping.MainActivity;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.adapters.AdapterItems;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.models.ModelCallDelivery.ModelCallDelivery;
import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class DelegateActivity extends AppCompatActivity implements HandleRetrofitResp {


    //region fields
    List<Items> itemsList;
    AdapterItems adapterItems;
    ModelCallDelivery modelCallDelivery;
    //endregion

    //region views

    @BindView(R.id.imgDelegate)
    CircleImageView imgDelegate;
    @BindView(R.id.tvDelegateName)
    TextView tvDelegateName;
    @BindView(R.id.rvDelegate)
    RecyclerView rvDelegate;

    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        itemsList = new ArrayList<>();
        adapterItems = new AdapterItems(itemsList, this);
        rvDelegate.setLayoutManager(new LinearLayoutManager(this));
        rvDelegate.setAdapter(adapterItems);

        HandleCalls.getInstance(this).setonRespnseSucess(this);

        callDelivery();
    }

    //endregion

    //region clicks

    @OnClick(R.id.tvDelegateSpecialRequest)
    public void onClick() {
        // TODO submit data to server...
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

        if (flag.equals(DataEnum.callDelivery.name())) {
            modelCallDelivery = gson.fromJson(jsonObject, ModelCallDelivery.class);
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

    //region calls
    private void callDelivery() {

        Call call = HandleCalls.restShopping.getClientService().callDelivery(1);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callDelivery.name(), true);
    }

    //endregion

    //region functions

    private void adjustView() {

        tvDelegateName.setText(modelCallDelivery.getInfo().getFullname());
        adapterItems.addAll(modelCallDelivery.getItems());
    }
    //endregion

}
