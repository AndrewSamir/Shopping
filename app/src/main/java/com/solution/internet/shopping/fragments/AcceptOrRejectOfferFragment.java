package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelPrivateOrderInfo.Offers;
import com.solution.internet.shopping.models.ModelPrivateOrderOfferNewRequest.ModelPrivateOrderOfferNewRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class AcceptOrRejectOfferFragment extends BaseFragment implements HandleRetrofitResp
{
    //region fields

    static Offers offer;
    //endregion

    //region views

    @BindView(R.id.tvProductDetailsName)
    TextView tvProductDetailsName;
    @BindView(R.id.tvProductDetailsDate)
    TextView tvProductDetailsDate;
    @BindView(R.id.tvProductDetailsCountry)
    TextView tvProductDetailsCountry;
    @BindView(R.id.tvProductDetailsContent)
    TextView tvProductDetailsContent;
    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.user_special_order_offer_details, container, false);

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
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position)
    {

    }

    //endregion

    //region clicks
    @OnClick(R.id.btnSpecialOrderOffersAccept)
    void onClickbtnSpecialOrderOffersAccept(View view)
    {
        callPrivateOrderOfferAccept();
    }

    @OnClick(R.id.btnSpecialOrderOffersReject)
    void onClickbtnSpecialOrderOffersReject(View view)
    {
        callPrivateOrderOfferReject();
    }
    //endregion

    //region calls

    private void callPrivateOrderOfferAccept()
    {
        ModelPrivateOrderOfferNewRequest body = new ModelPrivateOrderOfferNewRequest();
        body.setOfferid(offer.getOrderid());
        Call call = HandleCalls.restShopping.getClientService().callPrivateOrderOfferAccept(body);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callPrivateOrderOfferAccept.name(), true);
    }

    private void callPrivateOrderOfferReject()
    {
        ModelPrivateOrderOfferNewRequest body = new ModelPrivateOrderOfferNewRequest();
        body.setOfferid(offer.getOrderid());
        Call call = HandleCalls.restShopping.getClientService().callPrivateOrderOfferReject(body);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callPrivateOrderOfferReject.name(), true);
    }
    //endregion

    //region functions

    public static AcceptOrRejectOfferFragment init(Offers offer)
    {
        setOffer(offer);
        return new AcceptOrRejectOfferFragment();
    }

    public static void setOffer(Offers offer)
    {
        AcceptOrRejectOfferFragment.offer = offer;
    }

    private void adjustView()
    {

        tvProductDetailsName.setText(offer.getFullname());
        tvProductDetailsDate.setText(offer.getCreatedAt());
        tvProductDetailsCountry.setText(offer.getPrice() + "  ريال ");
        tvProductDetailsContent.setText(offer.getContent());
    }
    //endregion

}
