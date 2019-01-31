package com.solution.internet.shopping.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.LoginActivity;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelAddProductRequest.ModelAddProductRequest;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.models.ModelChatNewRequest.ModelChatNewRequest;
import com.solution.internet.shopping.models.ModelContactUs.ModelContactUs;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ProductDetailsUserFragment extends BaseFragment implements HandleRetrofitResp {
    //region fields

    static Items item;
    static int userId;
    static int itemId;
    //endregion

    //region views

    @BindView(R.id.tvProductDetailsTitle)
    TextView tvProductDetailsTitle;
    @BindView(R.id.tvProductDetailsContent)
    TextView tvProductDetailsContent;
    @BindView(R.id.tvProductDetailsPrice)
    TextView tvProductDetailsPrice;
    @BindView(R.id.imgProductDetails)
    ImageView imgProductDetails;

    @BindView(R.id.tvProductDetailsReport)
    View tvProductDetailsReport;
    @BindView(R.id.btnProductDetails)
    View btnProductDetails;
    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.product_details_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        if (item != null)
            adjustView();
        else
            callProductsInfo();
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

        if (flag.equals(DataEnum.callProductsInfo.name())) {
            JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
            item = gson.fromJson(jsonObject, Items.class);
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


    @OnClick(R.id.btnProductDetails)
    public void onClickbtnProductDetails() {
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
            callChatNew();
    }

    @OnClick(R.id.tvProductDetailsReport)
    public void onClicktvProductDetailsReport() {
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
        else {
            dialogReport();
        }
    }

    @OnClick(R.id.ProductDetailsShare)
    public void onClickProductDetailsShare() {
        shareTheProduct();
    }
    //endregion

    //region calls
    private void callChatNew() {

        ModelChatNewRequest modelChatNewRequest = new ModelChatNewRequest();
        modelChatNewRequest.setMessage(" بخصوص " + item.getTitle());
        modelChatNewRequest.setType("text");
        modelChatNewRequest.setPrice("200");
        modelChatNewRequest.setUserid(userId);

        Call call = HandleCalls.restShopping.getClientService().callChatNew(modelChatNewRequest);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callChatNew.name(), true);
    }

    private void callReportProduct() {

        ModelAddProductRequest modelAddProductRequest = new ModelAddProductRequest();
        modelAddProductRequest.setItem_id(item.getItemId() + "");
        Call call = HandleCalls.restShopping.getClientService().callReportProduct(modelAddProductRequest);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callReportProduct.name(), true);
    }

    private void callProductsInfo() {

        Call call = HandleCalls.restShopping.getClientService().callProductsInfo(itemId);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callProductsInfo.name(), true);
    }
    //endregion

    //region functions

    public static ProductDetailsUserFragment init(Items item, int userId) {
        setItem(item);
        setUserId(userId);
        setItemId(0);
        return new ProductDetailsUserFragment();
    }

    public static ProductDetailsUserFragment init(String itemId) {
        setItem(null);
        setUserId(0);
        setItemId(Integer.parseInt(itemId));
        return new ProductDetailsUserFragment();
    }

    public static void setUserId(int userId) {
        ProductDetailsUserFragment.userId = userId;
    }

    public static void setItem(Items item) {
        ProductDetailsUserFragment.item = item;
    }

    private void adjustView() {
//        tvProductDetailsName.setText(item.getCategoryname());
        tvProductDetailsTitle.setText(item.getTitle());
        tvProductDetailsContent.setText(item.getCityname());
        tvProductDetailsPrice.setText(item.getPrice() + " ريال ");

     /*   if (!SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("user")) {
            tvProductDetailsReport.setVisibility(View.GONE);
            btnProductDetails.setVisibility(View.GONE);
        }*/
        Picasso.with(getBaseActivity())
                .load(item.getPhoto())
                .into(imgProductDetails);


    }

    private void shareTheProduct() {

        Uri imgUri = Uri.parse(item.getPhoto());

        /*  Task<ShortDynamicLink> dynamicLink = */
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://onmall.com?productId=" + item.getItemId()))
                .setDynamicLinkDomain("onmall.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.solution.internet.shopping")
                                .build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.Slash.Tasawk")
//                                .setAppStoreId("1161347744")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(item.getTitle())
                                .setImageUrl(imgUri)
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(getBaseActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
//                            Uri flowchartLink = task.getResult().getPreviewLink();
                            share(shortLink.toString());
                        } else {
                        }
                    }
                });
    }

    public static int getItemId() {
        return itemId;
    }

    public static void setItemId(int itemId) {
        ProductDetailsUserFragment.itemId = itemId;
    }

    private void dialogReport() {
        final Dialog dialogAddReset = new Dialog(getBaseActivity());
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
                    callReportProduct();
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
