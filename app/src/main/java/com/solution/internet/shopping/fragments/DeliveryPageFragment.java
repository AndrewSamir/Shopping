package com.solution.internet.shopping.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.adapters.AdapterItems;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.models.ModelCallDelivery.ModelCallDelivery;
import com.solution.internet.shopping.models.ModelChatMessage.ModelChatMessage;
import com.solution.internet.shopping.models.ModelChatNewRequest.ModelChatNewRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.singleton.SingletonShopping;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class DeliveryPageFragment extends BaseFragment implements HandleRetrofitResp {
    //region fields

    List<Items> itemsList;
    AdapterItems adapterItems;
    ModelCallDelivery modelCallDelivery;
    static int deliveryId;
    private Dialog dialogAddReset;

    //endregion

    //region views
    @BindView(R.id.imgDelegate)
    CircleImageView imgDelegate;
    @BindView(R.id.tvDelegateName)
    TextView tvDelegateName;
    @BindView(R.id.rvDelegate)
    RecyclerView rvDelegate;
    @BindView(R.id.tvDelegateCity)
    TextView tvDelegateCity;
    @BindView(R.id.tvDelegateLink)
    TextView tvDelegateLink;
    @BindView(R.id.tvDelegateContent)
    TextView tvDelegateContent;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.content_delegate, container, false);

        unbinder = ButterKnife.bind(this, view);

        itemsList = new ArrayList<>();
        adapterItems = new AdapterItems(itemsList,  getBaseActivity());
        rvDelegate.setLayoutManager(new GridLayoutManager(getBaseActivity(), 3));
        rvDelegate.setAdapter(adapterItems);
        rvDelegate.setNestedScrollingEnabled(false);

        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);

        callDelivery();
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
//        appHeader.setRight(0, 0, 0);
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
        JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

        if (flag.equals(DataEnum.callDelivery.name())) {
            modelCallDelivery = gson.fromJson(jsonObject, ModelCallDelivery.class);
            adjustView();
        }

    }

    @Override
    public void onNoContent(String flag, int code) {
        if (flag.equals(DataEnum.callChatNew.name())) {
            addFragment(ChatFragment.init(), true);
        }
    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion

    //region clicks

    @OnClick(R.id.tvDelegateSpecialRequest)
    void onClicktvDelegateSpecialRequest(View view) {
        dialogAddSpecialRrequest();
    }
    //endregion

    //region calls
    private void callDelivery() {
        Call call = HandleCalls.restShopping.getClientService().callDelivery(deliveryId);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callDelivery.name(), true);
    }


    private void callChatNew(String message, String type, String price) {

        ModelChatNewRequest modelChatNewRequest = new ModelChatNewRequest();
        modelChatNewRequest.setMessage(message);
        modelChatNewRequest.setType(type);
        modelChatNewRequest.setPrice(price);
        modelChatNewRequest.setUserid(deliveryId);

        Call call = HandleCalls.restShopping.getClientService().callChatNew(modelChatNewRequest);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callChatNew.name(), true);
    }
    //endregion

    //region functions

    public static DeliveryPageFragment init(int deliveryId) {
        setDeliveryId(deliveryId);
        return new DeliveryPageFragment();
    }

    public static void setDeliveryId(int deliveryId) {
        DeliveryPageFragment.deliveryId = deliveryId;
    }

    private void adjustView() {

        tvDelegateName.setText(modelCallDelivery.getInfo().getFullname());
        tvDelegateCity.setText(modelCallDelivery.getInfo().getCityId() + "");
        tvDelegateLink.setText(modelCallDelivery.getInfo().getEmail());
        tvDelegateContent.setText(modelCallDelivery.getInfo().getNotes());
        adapterItems.addAll(modelCallDelivery.getItems());
    }

    private void dialogAddSpecialRrequest() {
        dialogAddReset = new Dialog(getBaseActivity());
        // Include dialog.xml file
        dialogAddReset.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddReset.setContentView(R.layout.dialog_special_request);

        dialogAddReset.setCancelable(false);
        final TextView tvDialogExit = dialogAddReset.findViewById(R.id.tvDialogExit);
        final EditText edtDialogMessage = dialogAddReset.findViewById(R.id.edtDialogMessage);


        dialogAddReset.show();

        Button btnDialogAction = dialogAddReset.findViewById(R.id.btnDialogAction);
        btnDialogAction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (edtDialogMessage.getText().toString().length() == 0)
                    edtDialogMessage.setError(getString(R.string.required));
                else {
                    callChatNew(edtDialogMessage.getText().toString(), "text", "200");
                    dialogAddReset.dismiss();
                }

            }
        });

        tvDialogExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddReset.dismiss();
            }
        });

    }
    //endregion

}
