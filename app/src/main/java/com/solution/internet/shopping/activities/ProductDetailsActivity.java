package com.solution.internet.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsActivity extends AppCompatActivity implements HandleRetrofitResp {

    //region fields
    //endregion

    //region views
    @BindView(R.id.tvProductDetailsName)
    TextView tvProductDetailsName;
    @BindView(R.id.tvProductDetailsTitle)
    TextView tvProductDetailsTitle;
    @BindView(R.id.tvProductDetailsContent)
    TextView tvProductDetailsContent;
    @BindView(R.id.tvProductDetailsPrice)
    TextView tvProductDetailsPrice;
    @BindView(R.id.imgProductDetails)
    ImageView imgProductDetails;

    //endregion

    //region life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/

        ButterKnife.bind(this);
        Intent intent = getIntent();
        adjustView((Items) intent.getSerializableExtra("test"));

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

    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }
    //endregion

    //region calls

    //endregion

    //region functions

    private void adjustView(Items items) {
        tvProductDetailsName.setText(items.getCategoryname());
        tvProductDetailsTitle.setText(items.getTitle());
        tvProductDetailsContent.setText(items.getCityname());
        tvProductDetailsPrice.setText(items.getPrice() + "ريال");

        Picasso.with(this)
                .load(items.getPhoto())
                .into(imgProductDetails);


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
