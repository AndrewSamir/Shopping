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
import com.solution.internet.shopping.adapters.AdapterNotifications;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelChatMessage.ModelChatMessage;
import com.solution.internet.shopping.models.ModelInvoice.ModelInvoice;
import com.solution.internet.shopping.models.ModelNotification.ModelNotification;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class NotificationsFragment extends BaseFragment implements HandleRetrofitResp {
    //region fields

    List<ModelNotification> modelNotificationList;
    AdapterNotifications adapterNotifications;

    //endregion

    //region views

    @BindView(R.id.rvNotifications)
    RecyclerView rvNotifications;
    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.notifications_activity, container, false);

        unbinder = ButterKnife.bind(this, view);

        modelNotificationList = new ArrayList<>();
        adapterNotifications = new AdapterNotifications(modelNotificationList, getBaseActivity());
        rvNotifications.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        rvNotifications.setAdapter(adapterNotifications);

        callGetNotifications();
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
//           appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader() {
        return false;
    }

    @Override
    protected boolean canShowBottomBar() {
        return false;
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
        return 0;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {
        Gson gson = new Gson();
        if (flag.equals(DataEnum.callGetNotifications.name())) {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                ModelNotification modelNotification = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelNotification.class);
                adapterNotifications.addItem(modelNotification);
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


    //endregion

    //region calls

    private void callGetNotifications() {
        Call call = HandleCalls.restShopping.getClientService().callGetNotifications();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callGetNotifications.name(), true);
    }

    private void callClearNotifications() {
        Call call = HandleCalls.restShopping.getClientService().callClearNotifications();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callClearNotifications.name(), true);
    }
    //endregion

    //region functions

    public static NotificationsFragment init() {
        return new NotificationsFragment();
    }
    //endregion

}
