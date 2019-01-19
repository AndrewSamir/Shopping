package com.solution.internet.shopping.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MoreFragment extends BaseFragment implements HandleRetrofitResp
{
    //region fields

    //endregion

    //region views

    @BindView(R.id.imgMoreProfile)
    CircleImageView imgMoreProfile;
    @BindView(R.id.tvMoreName)
    TextView tvMoreName;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.more_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

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
        return R.id.tvNavBarMore;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o)
    {

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

    @OnClick(R.id.tvMoreEditData)
    void onClicktvMoreEditData(View view)
    {
        addFragment(UserProfileFragment.init(), true);
    }

    @OnClick(R.id.tvMoreLogout)
    void onClicktvMoreLogout(View view)
    {

    }

    @OnClick(R.id.tvMoreLogin)
    void onClicktvMoreLogin(View view)
    {

    }

    @OnClick(R.id.tvMoreAbout)
    void onClicktvMoreAbout(View view)
    {

    }

    @OnClick(R.id.tvMoreContact)
    void onClicktvMoreContact(View view)
    {

    }

    @OnClick(R.id.tvMorePrivacy)
    void onClicktvMorePrivacy(View view)
    {

    }

    @OnClick(R.id.tvMoreInvoices)
    void onClicktvMoreInvoices(View view)
    {
        addFragment(CustomerInvoices.init(), true);
    }

    @OnClick(R.id.tvMoreMessages)
    void onClicktvMoreMessages(View view)
    {

    }

    @OnClick(R.id.tvMoreOrders)
    void onClicktvMoreOrders(View view)
    {

    }

    @OnClick(R.id.tvMoreSpecialOrder)
    void onClicktvMoreSpecialOrder(View view)
    {

    }

    //endregion

    //region calls

    //endregion

    //region functions

    public static MoreFragment init()
    {
        return new MoreFragment();
    }
    //endregion

}
