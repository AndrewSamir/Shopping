package com.solution.internet.shopping.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.LoginActivity;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MoreFragment extends BaseFragment implements HandleRetrofitResp {

    //region fields

    //endregion

    //region views

    @BindView(R.id.imgMoreProfile)
    CircleImageView imgMoreProfile;
    @BindView(R.id.tvMoreName)
    TextView tvMoreName;
    @BindView(R.id.tvMoreEditData)
    TextView tvMoreEditData;
    @BindView(R.id.tvMoreLogout)
    TextView tvMoreLogout;
    @BindView(R.id.tvMoreLogin)
    TextView tvMoreLogin;
    @BindView(R.id.tvMoreOrders)
    TextView tvMoreOrders;
    @BindView(R.id.tvMoreSpecialOrder)
    TextView tvMoreSpecialOrder;
    @BindView(R.id.tvMoreInvoices)
    TextView tvMoreInvoices;
    @BindView(R.id.tvMoreMessages)
    TextView tvMoreMessages;
    @BindView(R.id.tvMoreLoginView)
    View tvMoreLoginView;
    @BindView(R.id.tvMoreInvoicesView)
    View tvMoreInvoicesView;
    @BindView(R.id.tvMoreMessagesView)
    View tvMoreMessagesView;
    @BindView(R.id.tvMoreOrdersView)
    View tvMoreOrdersView;
    @BindView(R.id.tvMoreSpecialOrderView)
    View tvMoreSpecialOrderView;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.more_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        adjustView();
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
        return R.id.tvNavBarMore;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {

    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion

    //region clicks

    @OnClick(R.id.tvMoreEditData)
    void onClicktvMoreEditData(View view) {
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("user"))
            addFragment(UserProfileFragment.init(), true);
        else
            addFragment(DeliveryProfileFragment.init(), true);
    }

    @OnClick(R.id.tvMoreLogout)
    void onClicktvMoreLogout(View view) {
        SharedPrefHelper.getInstance(getBaseActivity()).signOut();
        Intent intent = new Intent(getBaseActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.tvMoreLogin)
    void onClicktvMoreLogin(View view) {
        SharedPrefHelper.getInstance(getBaseActivity()).signOut();
        Intent intent = new Intent(getBaseActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.tvMoreAbout)
    void onClicktvMoreAbout(View view) {
        addFragment(WebViewFragment.init("من نحن", "http://pharaohsland.tours/tasawk/app/page/1"), true);
//        addFragment(NotificationsFragment.init(), true);
    }

    @OnClick(R.id.tvMoreContact)
    void onClicktvMoreContact(View view) {
        addFragment(WebViewFragment.init("تواصل معنا", "http://pharaohsland.tours/tasawk/app/contact"), true);
    }

    @OnClick(R.id.tvMorePrivacy)
    void onClicktvMorePrivacy(View view) {
        addFragment(WebViewFragment.init("سياسة الإستخدام", "http://pharaohsland.tours/tasawk/app/page/2"), true);
    }

    @OnClick(R.id.tvMoreInvoices)
    void onClicktvMoreInvoices(View view) {
        if (SharedPrefHelper.getInstance(getBaseActivity()).getUserType().equals("user"))
            addFragment(CustomerInvoices.init(), true);
        else
            addFragment(DeliveryInvoices.init(), true);
    }

    @OnClick(R.id.tvMoreMessages)
    void onClicktvMoreMessages(View view) {
        addFragment(InboxFragment.init(), true);
    }

    @OnClick(R.id.tvMoreOrders)
    void onClicktvMoreOrders(View view) {
        addFragment(UserOrdersFragment.init(), true);
    }

    @OnClick(R.id.tvMoreSpecialOrder)
    void onClicktvMoreSpecialOrder(View view) {

    }

    //endregion

    //region calls

    //endregion

    //region functions

    public static MoreFragment init() {
        return new MoreFragment();
    }

    private void adjustView() {

        switch (SharedPrefHelper.getInstance(getBaseActivity()).getUserType()) {

            case "visitor":

                imgMoreProfile.setVisibility(View.GONE);
                tvMoreEditData.setVisibility(View.GONE);
                tvMoreLogout.setVisibility(View.GONE);
                tvMoreOrders.setVisibility(View.GONE);
                tvMoreOrdersView.setVisibility(View.GONE);
                tvMoreSpecialOrder.setVisibility(View.GONE);
                tvMoreSpecialOrderView.setVisibility(View.GONE);
                tvMoreInvoices.setVisibility(View.GONE);
                tvMoreInvoicesView.setVisibility(View.GONE);
                tvMoreMessages.setVisibility(View.GONE);
                tvMoreMessagesView.setVisibility(View.GONE);
                break;
            case "user":
                tvMoreName.setText(SharedPrefHelper.getInstance(getBaseActivity()).getFullName());
                tvMoreLogin.setVisibility(View.GONE);
                tvMoreLoginView.setVisibility(View.GONE);
                tvMoreOrders.setVisibility(View.GONE);
                tvMoreOrdersView.setVisibility(View.GONE);
                break;
            case "delivery":
                tvMoreName.setText(SharedPrefHelper.getInstance(getBaseActivity()).getFullName());
                tvMoreLogin.setVisibility(View.GONE);
                tvMoreLoginView.setVisibility(View.GONE);
                tvMoreSpecialOrder.setVisibility(View.GONE);
                tvMoreSpecialOrderView.setVisibility(View.GONE);
                break;
        }

        Picasso.with(getBaseActivity())
                .load(SharedPrefHelper.getInstance(getBaseActivity()).getAvatar())
                .into(imgMoreProfile);
    }
    //endregion

}
