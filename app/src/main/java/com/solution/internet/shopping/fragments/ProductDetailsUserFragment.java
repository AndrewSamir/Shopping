package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProductDetailsUserFragment extends BaseFragment implements HandleRetrofitResp
{
    //region fields

    static Items item;
    //endregion

    //region views

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.product_details_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        adjustView();
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
//        appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader()
    {
        return false;
    }

    @Override
    protected boolean canShowBottomBar()
    {
        return false;
    }

    @Override
    protected boolean canShowBackArrow()
    {
        return false;
    }

    @Override
    protected String getTitle()
    {
        return null;
    }

    @Override
    public int getSelectedMenuId()
    {
        return 0;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o)
    {

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

    //region clicks

    @OnClick(R.id.tvProductDetailsReport)
    void onClicktvProductDetailsReport(View view)
    {

    }
    //endregion

    //region calls

    //endregion

    //region functions

    public static ProductDetailsUserFragment init(Items item)
    {
        setItem(item);
        return new ProductDetailsUserFragment();
    }

    public static void setItem(Items item)
    {
        ProductDetailsUserFragment.item = item;
    }

    private void adjustView()
    {
//        tvProductDetailsName.setText(item.getCategoryname());
        tvProductDetailsTitle.setText(item.getTitle());
        tvProductDetailsContent.setText(item.getCityname());
        tvProductDetailsPrice.setText(item.getPrice() + " ريال ");

        Picasso.with(getBaseActivity())
                .load(item.getPhoto())
                .into(imgProductDetails);


    }
    //endregion

}
