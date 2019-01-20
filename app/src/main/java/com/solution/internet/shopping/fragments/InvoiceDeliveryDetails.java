package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelInvoiceDetails.ModelInvoiceDetails;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class InvoiceDeliveryDetails extends BaseFragment implements HandleRetrofitResp
{
    //region fields
    static int crId;
    //endregion

    //region views

    @BindView(R.id.tvInvoicesDetailsId)
    TextView tvInvoicesDetailsId;

    @BindView(R.id.tvInvoicesDetailsPrice)
    TextView tvInvoicesDetailsPrice;

    @BindView(R.id.tvInvoicesDetailsDate)
    TextView tvInvoicesDetailsDate;

    @BindView(R.id.tvInvoicesDetailsName)
    TextView tvInvoicesDetailsName;

    @BindView(R.id.tvInvoicesDetailsState)
    TextView tvInvoicesDetailsState;

    @BindView(R.id.tvInvoicesDetailsContent)
    TextView tvInvoicesDetailsContent;


    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.invoice_details, container, false);

        unbinder = ButterKnife.bind(this, view);

        callInvoiceDetails();
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
        if (flag.equals(DataEnum.callInvoiceDetails.name()))
        {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

            ModelInvoiceDetails modelInvoiceDetails = gson.fromJson(jsonObject, ModelInvoiceDetails.class);
            adjustView(modelInvoiceDetails);
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

    //region clicks

    //endregion

    //region calls

    private void callInvoiceDetails()
    {
        Call call = HandleCalls.restShopping.getClientService().callInvoiceDetails(crId);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callInvoiceDetails.name(), true);
    }
    //endregion

    //region functions

    public static InvoiceDeliveryDetails init(int crId)
    {
        setCrId(crId);
        return new InvoiceDeliveryDetails();
    }

    public static void setCrId(int crId)
    {
        InvoiceDeliveryDetails.crId = crId;
    }

    private void adjustView(ModelInvoiceDetails modelInvoiceDetails)
    {

        Locale locale = new Locale("en");

        SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/yy", locale);
        long dateTime = Long.parseLong(modelInvoiceDetails.getTime() + "000");
        String dateString = formatter.format(new Date(dateTime));


        tvInvoicesDetailsId.setText("#" + modelInvoiceDetails.getCrId());
        tvInvoicesDetailsPrice.setText(modelInvoiceDetails.getPrice() + " ريال ");
        tvInvoicesDetailsDate.setText(dateString);
        tvInvoicesDetailsName.setText(modelInvoiceDetails.getFullname());
        tvInvoicesDetailsContent.setText(modelInvoiceDetails.getReply());
    }

    //endregion

}
