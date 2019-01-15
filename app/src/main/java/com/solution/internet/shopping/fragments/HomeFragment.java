package com.solution.internet.shopping.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.adapters.AdapterItems;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.models.ModelGetCategories.ModelGetCategories;
import com.solution.internet.shopping.models.ModelGetCities.ModelGetCities;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class HomeFragment extends BaseFragment implements HandleRetrofitResp
{

    //region fields
    List<ModelGetCities> modelGetCitiesList;
    List<String> citiesName;
    int cityId = 0;
    List<ModelGetCategories> modelGetCategoriesList;
    List<String> categoriesName;
    int categoryId = 0;
    List<Items> itemsList;
    AdapterItems adapterItems;
    //endregion

    //region views

    @BindView(R.id.tvSearchCity)
    TextView tvSearchCity;

    @BindView(R.id.tvSearchCategory)
    TextView tvSearchCategory;

    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.content_search, container, false);

        unbinder = ButterKnife.bind(this, view);
        itemsList = new ArrayList<>();
        adapterItems = new AdapterItems(itemsList, getBaseActivity());
        rvSearch.setLayoutManager(new GridLayoutManager(getBaseActivity(), 2));
        rvSearch.setAdapter(adapterItems);
        rvSearch.setNestedScrollingEnabled(false);
        callCities();
//        callProducts();
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

        if (flag.equals(DataEnum.callCities.name()))
        {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            modelGetCitiesList = new ArrayList<>();
            citiesName = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++)
            {
                ModelGetCities modelGetCity = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelGetCities.class);
                modelGetCitiesList.add(modelGetCity);
                citiesName.add(modelGetCity.getCity());
            }
            callcategories();
        } else if (flag.equals(DataEnum.callcategories.name()))
        {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            modelGetCategoriesList = new ArrayList<>();
            categoriesName = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++)
            {
                ModelGetCategories modelGetCategories = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelGetCategories.class);
                modelGetCategoriesList.add(modelGetCategories);
                categoriesName.add(modelGetCategories.getCategory());
            }
            callProducts();
        } else if ((flag.equals(DataEnum.callProducts.name())))
        {
            adapterItems.clearAllListData();
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++)
            {
                Items item = gson.fromJson(jsonArray.get(i).getAsJsonObject(), Items.class);
                adapterItems.addItem(item);
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
    @OnClick(R.id.tvSearchCity)
    public void onClicktvSearchCity()
    {
        final CharSequence[] items = new CharSequence[citiesName.size()];

        for (int i = 0; i < citiesName.size(); i++)
        {
            items[i] = citiesName.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("أختر مدينة");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                // Do something with the selection
                tvSearchCity.setText(citiesName.get(item));
                cityId = modelGetCitiesList.get(item).getCityId();
                if (categoryId != -1)
                    callProducts();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.tvSearchCategory)
    public void onClicktvSearchCategory()
    {
        final CharSequence[] items = new CharSequence[categoriesName.size()];

        for (int i = 0; i < categoriesName.size(); i++)
        {
            items[i] = categoriesName.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("أختر القسم");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                // Do something with the selection
                tvSearchCategory.setText(categoriesName.get(item));
                categoryId = modelGetCategoriesList.get(item).getCategoryId();
                if (cityId != -1)
                    callProducts();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    //endregion

    //region calls
    private void callCities()
    {

        Call call = HandleCalls.restShopping.getClientService().callCities();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callCities.name(), true);
    }

    private void callcategories()
    {

        Call call = HandleCalls.restShopping.getClientService().callcategories();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callcategories.name(), true);
    }

    private void callProducts()
    {

        Call call = HandleCalls.restShopping.getClientService().callProducts(cityId, categoryId);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callProducts.name(), true);
    }

    //endregion

    //region functions

    public static HomeFragment init()
    {
        return new HomeFragment();
    }
    //endregion

}
