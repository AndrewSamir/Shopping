package com.solution.internet.shopping.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.adapters.AdapterItems;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.models.ModelCallDelivery.ModelCallDelivery;
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

public class SearchActivity extends AppCompatActivity implements HandleRetrofitResp, TextWatcher
{

    //region fields

    List<ModelGetCities> modelGetCitiesList;
    List<String> citiesName;
    int cityId = -1;
    List<ModelGetCategories> modelGetCategoriesList;
    List<String> categoriesName;
    int categoryId = -1;
    List<Items> itemsList;
    AdapterItems adapterItems;
    String searchText = "";
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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        itemsList = new ArrayList<>();
        adapterItems = new AdapterItems(itemsList, this);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(adapterItems);
        rvSearch.setNestedScrollingEnabled(false);

        edtSearch.addTextChangedListener(this);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (edtSearch.getText().toString().length() > 0)
                {
                    searchText = edtSearch.getText().toString();
                    callProducts();
                }
                return true;
            }
        });
        callCities();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        HandleCalls.getInstance(this).setonRespnseSucess(this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    @OnClick(R.id.tvNavBarMore)
    public void onClicktvNavBarMore()
    {
        startActivity(new Intent(this, DelegateDetailsActivity.class));
    }

    @OnClick(R.id.tvNavBarProducts)
    public void onClicktvNavBarProducts()
    {
        startActivity(new Intent(this, SearchActivity.class));
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
        } else if ((flag.equals(DataEnum.callProducts.name())))
        {
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

    //region calls
    private void callCities()
    {

        Call call = HandleCalls.restShopping.getClientService().callCities();
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callCities.name(), true);
    }

    private void callcategories()
    {

        Call call = HandleCalls.restShopping.getClientService().callcategories();
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callcategories.name(), true);
    }

    private void callProducts()
    {

        Call call = HandleCalls.restShopping.getClientService().callProducts(cityId, categoryId, searchText);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callProducts.name(), true);
    }


    //endregion

    //region functions

    private void adjustView()
    {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {


    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        if (editable.toString().length() == 0)
            searchText = "";
    }

    //endregion
}
