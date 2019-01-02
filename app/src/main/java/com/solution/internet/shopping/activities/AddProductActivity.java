package com.solution.internet.shopping.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelAddProductRequest.ModelAddProductRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;


public class AddProductActivity extends AppCompatActivity implements HandleRetrofitResp {

    //region fields

    //endregion

    //region views

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.tvAddProductTitle)
    EditText tvAddProductTitle;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.tvAddProductPrice)
    EditText tvAddProductPrice;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.tvAddProductContent)
    EditText tvAddProductContent;

    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        HandleCalls.getInstance(this).setonRespnseSucess(this);
    }

    //endregion

    //region clicks
    @OnClick(R.id.btnAddProduct)
    public void onClickbtnAddProduct() {
        callProductsAdd();
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

    private void callProductsAdd() {

        ModelAddProductRequest modelAddProductRequest = new ModelAddProductRequest();
        modelAddProductRequest.setCategory_id("1");
        modelAddProductRequest.setContent(tvAddProductContent.getText().toString().trim());
        modelAddProductRequest.setPrice(tvAddProductPrice.getText().toString().trim());
        modelAddProductRequest.setTitle(tvAddProductTitle.getText().toString().trim());

        Call call = HandleCalls.restShopping.getClientService().callProductsAdd(modelAddProductRequest);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callProductsAdd.name(), true);
    }

    //endregion

    //region functions

    private void adjustView() {

    }
    //endregion
}
