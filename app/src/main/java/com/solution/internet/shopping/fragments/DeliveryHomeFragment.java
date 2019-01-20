package com.solution.internet.shopping.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.adapters.AdapterDeliveryItems;
import com.solution.internet.shopping.adapters.AdapterItems;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.models.ModelCallDelivery.ModelCallDelivery;
import com.solution.internet.shopping.models.ModelGetCategories.ModelGetCategories;
import com.solution.internet.shopping.models.ModelGetCities.ModelGetCities;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class DeliveryHomeFragment extends BaseFragment implements HandleRetrofitResp
{

    //region fields


    List<Items> itemsList;
    AdapterDeliveryItems adapterItems;
    //endregion

    //region views

    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;


    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.delivery_home_content_search, container, false);

        unbinder = ButterKnife.bind(this, view);
        itemsList = new ArrayList<>();
        adapterItems = new AdapterDeliveryItems(itemsList, getBaseActivity());
        rvSearch.setLayoutManager(new GridLayoutManager(getBaseActivity(), 2));
        rvSearch.setAdapter(adapterItems);
        rvSearch.setNestedScrollingEnabled(false);
        callDelivery();

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
//               appHeader.setRight(0, 0, 0);
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
        return true;
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
        return R.id.tvNavBarHome;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o)
    {
        Gson gson = new Gson();
        if ((flag.equals(DataEnum.callProducts.name())))
        {
            adapterItems.clearAllListData();
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++)
            {
                Items item = gson.fromJson(jsonArray.get(i).getAsJsonObject(), Items.class);
                adapterItems.addItem(item);
            }
        } else if (flag.equals(DataEnum.callDelivery.name()))
        {
            JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

            ModelCallDelivery modelCallDelivery = gson.fromJson(jsonObject, ModelCallDelivery.class);
            adapterItems.addAll(modelCallDelivery.getItems());

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

    @OnClick(R.id.imgAddProduct)
    void onClickimgAddProduct(View view)
    {
        addFragment(AddPeoductFragment.init(), true);
    }
    //endregion

    //region calls


/*
    private void callProducts()
    {

        Call call = HandleCalls.restShopping.getClientService().callProductsByUser(SharedPrefHelper.getInstance(getBaseActivity()).getUserid());
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callProducts.name(), true);
    }
*/

    private void callDelivery()
    {
        Call call = HandleCalls.restShopping.getClientService().callDelivery(SharedPrefHelper.getInstance(getBaseActivity()).getUserid());
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callDelivery.name(), true);
    }
    //endregion

    //region functions

    public static DeliveryHomeFragment init()
    {
        return new DeliveryHomeFragment();
    }
    //endregion

}
