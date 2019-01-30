package com.solution.internet.shopping.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.DeliveryMainActivity;
import com.solution.internet.shopping.activities.MainActivity;
import com.solution.internet.shopping.activities.RegisterActivity;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelContactUs.ModelContactUs;
import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ContactFragment extends BaseFragment implements HandleRetrofitResp {

    //region fields
    ModelContactUs modelContactUs;
    //endregion

    //region views

    @BindView(R.id.tvContactMobile)
    TextView tvContactMobile;
    @BindView(R.id.tvContactMail)
    TextView tvContactMail;
    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.contact_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        callGetContact();

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

        if (flag.equals(DataEnum.callGetContact.name())) {
            JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
            modelContactUs = gson.fromJson(jsonObject, ModelContactUs.class);

            tvContactMobile.setText(modelContactUs.getPhone());
            tvContactMail.setText(modelContactUs.getEmail());
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

    @OnClick(R.id.tvContactMobile)
    public void onClicktvContactMobile() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + modelContactUs.getPhone()));
        startActivity(intent);
    }

    @OnClick(R.id.tvContactMail)
    public void onClicktvContactMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + modelContactUs.getEmail()));

        startActivity(emailIntent);
    }

    @OnClick(R.id.imgContactTwitter)
    public void onClickimgContactTwitter() {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            getBaseActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" +
                    modelContactUs.getTwitter().split("com/")[1]));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(modelContactUs.getTwitter()));
            startActivity(intent);

        }
    }

    @OnClick(R.id.imgContactFacebook)
    public void onClickimgContactFacebook() {
        PackageManager packageManager = getBaseActivity().getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = "fb://facewebmodal/f?href=" + modelContactUs.getFacebook();
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            } else { //older versions of fb app
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = "fb://page/" + modelContactUs.getFacebook();
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        } catch (PackageManager.NameNotFoundException e) {

            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = modelContactUs.getFacebook();
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        }
    }

    @OnClick(R.id.imgContactInsta)
    public void onClickimgContactInsta() {
        Uri uri = Uri.parse(modelContactUs.getInstagram());
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(modelContactUs.getInstagram())));
        }
    }
    //endregion

    //region calls
    private void callGetContact() {
        Call call = HandleCalls.restShopping.getClientService().callGetContact();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callGetContact.name(), true);
    }

    //endregion

    //region functions

    public static ContactFragment init() {
        return new ContactFragment();
    }
    //endregion

}
