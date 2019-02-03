package com.solution.internet.shopping.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.LoginActivity;
import com.solution.internet.shopping.activities.MainActivity;
import com.solution.internet.shopping.activities.SplashActivity;
import com.solution.internet.shopping.adapters.AdapterItems;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
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

public class HomeFragment extends BaseFragment implements HandleRetrofitResp, TextWatcher {

    //region fields
    int cityId = 0;
    int categoryId = 0;
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
    @BindView(R.id.imgNotifications)
    View imgNotifications;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.content_search, container, false);

        unbinder = ButterKnife.bind(this, view);
        itemsList = new ArrayList<>();
        adapterItems = new AdapterItems(itemsList, getBaseActivity());
        rvSearch.setLayoutManager(new GridLayoutManager(getBaseActivity(), 3));
        rvSearch.setAdapter(adapterItems);
        rvSearch.setNestedScrollingEnabled(false);
        edtSearch.addTextChangedListener(this);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (edtSearch.getText().toString().length() > 0) {
                    searchText = edtSearch.getText().toString();
                    callProducts();
                }
                return true;
            }
        });

        if (SharedPrefHelper.getInstance(getBaseActivity()).equals("visitor"))
            imgNotifications.setVisibility(View.GONE);
        callProducts();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//               appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader() {
        return false;
    }

    @Override
    protected boolean canShowBottomBar() {
        return true;
    }

    @Override
    protected boolean canShowBackArrow() {
        return false;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    public int getSelectedMenuId() {
        return R.id.tvNavBarHome;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {
        Gson gson = new Gson();
        if ((flag.equals(DataEnum.callProducts.name()))) {
            adapterItems.clearAllListData();
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                Items item = gson.fromJson(jsonArray.get(i).getAsJsonObject(), Items.class);
                adapterItems.addItem(item);
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

    //region clicks
    @OnClick(R.id.tvSearchCity)
    public void onClicktvSearchCity() {
        final CharSequence[] items = new CharSequence[SplashActivity.citiesName.size()];

        for (int i = 0; i < SplashActivity.citiesName.size(); i++) {
            items[i] = SplashActivity.citiesName.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("أختر مدينة");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                tvSearchCity.setText(SplashActivity.citiesName.get(item));
                cityId = SplashActivity.modelGetCitiesList.get(item).getCityId();
                if (categoryId != -1)
                    callProducts();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.tvSearchCategory)
    public void onClicktvSearchCategory() {
        final CharSequence[] items = new CharSequence[SplashActivity.categoriesName.size()];

        for (int i = 0; i < SplashActivity.categoriesName.size(); i++) {
            items[i] = SplashActivity.categoriesName.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("أختر القسم");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                tvSearchCategory.setText(SplashActivity.categoriesName.get(item));
                categoryId = SplashActivity.modelGetCategoriesList.get(item).getCategoryId();
                if (cityId != -1)
                    callProducts();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.imgAddSpecialOrder)
    void onClickimgAddSpecialOrder(View view) {
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("visitor"))
            showMessage(R.string.login_first, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    Intent intent = new Intent(getBaseActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
        else
        addFragment(AddSpecialOrderFragment.init(), true);
    }

    @OnClick(R.id.imgNotifications)
    public void onClickimgNotifications() {
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("visitor"))
            showMessage(R.string.login_first, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    Intent intent = new Intent(getBaseActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
        else
            addFragment(NotificationsFragment.init(), true);
    }
    //endregion

    //region calls


    private void callProducts() {

        Call call = HandleCalls.restShopping.getClientService().callProducts(cityId, categoryId, searchText);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callProducts.name(), true);
    }

    //endregion

    //region functions

    public static HomeFragment init() {
        return new HomeFragment();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().length() == 0)
            searchText = "";
    }
    //endregion

}
