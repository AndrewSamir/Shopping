package com.solution.internet.shopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelGetCategories.ModelGetCategories;
import com.solution.internet.shopping.models.ModelGetCities.ModelGetCities;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SplashActivity extends AppCompatActivity implements HandleRetrofitResp {

    //region fields
    public static List<ModelGetCities> modelGetCitiesList;
    public static List<ModelGetCategories> modelGetCategoriesList;
    public static List<String> citiesName;
    public static List<String> categoriesName;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        HandleCalls.getInstance(this).setonRespnseSucess(this);
        callCities();


    }

    //region calls

    private void callCities() {

        Call call = HandleCalls.restShopping.getClientService().callCities();
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callCities.name(), false);
    }

    private void callcategories() {

        Call call = HandleCalls.restShopping.getClientService().callcategories();
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callcategories.name(), false);
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {
        Gson gson = new Gson();

        if (flag.equals(DataEnum.callCities.name())) {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            modelGetCitiesList = new ArrayList<>();
            citiesName = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                ModelGetCities modelGetCity = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelGetCities.class);
                modelGetCitiesList.add(modelGetCity);
                citiesName.add(modelGetCity.getCity());
            }
            callcategories();
        } else if (flag.equals(DataEnum.callcategories.name())) {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            modelGetCategoriesList = new ArrayList<>();
            categoriesName = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                ModelGetCategories modelGetCategories = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelGetCategories.class);
                modelGetCategoriesList.add(modelGetCategories);
                categoriesName.add(modelGetCategories.getCategory());
                if (SharedPrefHelper.getInstance(this).getUserid() == -1)
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                else if (SharedPrefHelper.getInstance(this).getUserType().equals("user"))
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, DeliveryMainActivity.class));
                finish();
            }
        }
    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion
}
