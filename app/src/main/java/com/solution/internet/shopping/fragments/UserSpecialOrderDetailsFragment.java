package com.solution.internet.shopping.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.models.ModelGetProfile.ModelGetProfile;
import com.solution.internet.shopping.models.ModelPrivateOrderInfo.ModelPrivateOrderInfo;
import com.solution.internet.shopping.models.ModelPrivateOrderOfferNewRequest.ModelPrivateOrderOfferNewRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class UserSpecialOrderDetailsFragment extends BaseFragment implements HandleRetrofitResp
{
    //region fields
    ModelPrivateOrderInfo modelPrivateOrderInfo;
    static int orderId;
    private Dialog dialogAddReset;

    //endregion

    //region views

    @BindView(R.id.tvProductDetailsName)
    TextView tvProductDetailsName;
    @BindView(R.id.tvProductDetailsTitle)
    TextView tvProductDetailsTitle;
    @BindView(R.id.tvProductDetailsContent)
    TextView tvProductDetailsContent;
    @BindView(R.id.tvProductDetailsPrice)
    TextView tvProductDetailsPrice;
    @BindView(R.id.tvProductDetailsCountry)
    TextView tvProductDetailsCountry;
    @BindView(R.id.tvProductDetailsDate)
    TextView tvProductDetailsDate;
    @BindView(R.id.imgProductDetails)
    ImageView imgProductDetails;

    @BindView(R.id.btnSpecialOrderDetailsOffers)
    Button btnSpecialOrderDetailsOffers;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.user_special_order_details, container, false);

        unbinder = ButterKnife.bind(this, view);
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("user"))
            btnSpecialOrderDetailsOffers.setText("العروض المقدمة");
        else
            btnSpecialOrderDetailsOffers.setText("قدم عرض");

        callPrivateOrderInfo();
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
        JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();

        if (flag.equals(DataEnum.callPrivateOrderInfo.name()))
        {
            modelPrivateOrderInfo = gson.fromJson(jsonObject, ModelPrivateOrderInfo.class);
            adjustView(modelPrivateOrderInfo);
        } else if (flag.equals(DataEnum.callPrivateOrderInfo.name()))
        {
            getBaseActivity().onBackPressed();
        }
    }

    @Override
    public void onNoContent(String flag, int code)
    {
        if (flag.equals(DataEnum.callPrivateOrderInfo.name()))
        {
            getBaseActivity().onBackPressed();
        }
    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position)
    {

    }

    //endregion

    //region clicks
    @OnClick(R.id.btnSpecialOrderDetailsOffers)
    void onClickbtnSpecialOrderDetailsOffers(View view)
    {
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("user"))
            addFragment(UserOrdersOffersFragment.init(modelPrivateOrderInfo.getOffers()), true);
        else
            dialogAddOffer();
    }
    //endregion

    //region calls

    private void callPrivateOrderInfo()
    {

        Call call = HandleCalls.restShopping.getClientService().callPrivateOrderInfo(orderId);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callPrivateOrderInfo.name(), true);
    }

    private void callPrivateOrderOfferNew(ModelPrivateOrderOfferNewRequest body)
    {
        Call call = HandleCalls.restShopping.getClientService().callPrivateOrderOfferNew(body);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callPrivateOrderInfo.name(), true);
    }
    //endregion

    //region functions

    public static UserSpecialOrderDetailsFragment init(int orderId)
    {
        setOrderId(orderId);
        return new UserSpecialOrderDetailsFragment();
    }

    public static void setOrderId(int orderId)
    {
        UserSpecialOrderDetailsFragment.orderId = orderId;
    }

    private void adjustView(ModelPrivateOrderInfo items)
    {
        tvProductDetailsName.setText(items.getFullname());
        tvProductDetailsTitle.setText(items.getTitle());
        tvProductDetailsContent.setText(items.getContent());
        tvProductDetailsCountry.setText(items.getCityname());
        tvProductDetailsDate.setText(items.getCreatedAt());
        tvProductDetailsPrice.setText(items.getPrice() + "ريال");

        Picasso.with(getBaseActivity())
                .load(items.getPhoto())
                .into(imgProductDetails);
    }


    private void dialogAddOffer()
    {
        dialogAddReset = new Dialog(getBaseActivity());
        // Include dialog.xml file
        dialogAddReset.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddReset.setContentView(R.layout.dialog_add_offer);

        dialogAddReset.setCancelable(false);
        final TextView tvDialogExit = dialogAddReset.findViewById(R.id.tvDialogExit);
        final EditText edtDialogPrice = dialogAddReset.findViewById(R.id.edtDialogPrice);
        final EditText edtDialogMessage = dialogAddReset.findViewById(R.id.edtDialogMessage);

        dialogAddReset.show();

        Button btnDialogAction = dialogAddReset.findViewById(R.id.btnDialogAction);
        btnDialogAction.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (edtDialogPrice.getText().toString().length() == 0)
                    edtDialogPrice.setError(getString(R.string.required));
                else if (edtDialogMessage.getText().toString().length() == 0)
                    edtDialogMessage.setError(getString(R.string.required));
                else
                {
                    ModelPrivateOrderOfferNewRequest body = new ModelPrivateOrderOfferNewRequest();
                    body.setOrderid(orderId);
                    body.setPrice(edtDialogPrice.getText().toString());
                    body.setContent(edtDialogMessage.getText().toString());
                    callPrivateOrderOfferNew(body);
                    dialogAddReset.dismiss();
                }

            }
        });

        tvDialogExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialogAddReset.dismiss();
            }
        });

    }
    //endregion

}
