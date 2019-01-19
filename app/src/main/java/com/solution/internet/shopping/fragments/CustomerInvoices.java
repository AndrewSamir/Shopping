package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.adapters.AdapterCustomerInvoice;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelGetCities.ModelGetCities;
import com.solution.internet.shopping.models.ModelInvoice.ModelInvoice;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class CustomerInvoices extends BaseFragment implements HandleRetrofitResp
{
    //region fields

    List<ModelInvoice> modelInvoiceList;
    AdapterCustomerInvoice adapterCustomerInvoice;

    //endregion

    //region views

    @BindView(R.id.rvCustomerInvoices)
    RecyclerView rvCustomerInvoices;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.customer_invoices_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        modelInvoiceList = new ArrayList<>();
        adapterCustomerInvoice = new AdapterCustomerInvoice(modelInvoiceList, getBaseActivity());
        rvCustomerInvoices.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        rvCustomerInvoices.setAdapter(adapterCustomerInvoice);

        callInvoicesCustomer();
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
        Gson gson = new Gson();
        if (flag.equals(DataEnum.callInvoicesCustomer.name()))
        {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++)
            {
                ModelInvoice modelInvoice = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelInvoice.class);
                adapterCustomerInvoice.addItem(modelInvoice);
            }
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

    private void callInvoicesCustomer()
    {

        Call call = HandleCalls.restShopping.getClientService().callInvoicesCustomer();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callInvoicesCustomer.name(), true);
    }
    //endregion

    //region functions

    public static CustomerInvoices init()
    {
        return new CustomerInvoices();
    }
    //endregion

}
