package com.solution.internet.shopping.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.BaseActivity;
import com.solution.internet.shopping.activities.SplashActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.Unbinder;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * This is base code that will help other fragments. <br\></br\>
 * Remember This will require you to implement few functions like showing header. <br\></br\>
 * You may need to add Margin to the top of your fragment, if you are going to show the header
 */
public abstract class BaseFragment extends RxFragment {
    //region fields
    public final String TAG = this.getClass().getSimpleName();
    protected BottomNavigationViewEx bottomNavigationView;
    protected Unbinder unbinder;
    //endregion

    //region life cycles


    @Nullable
    @CallSuper
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
      /*  bottomNavigationView = getBaseActivity().getBottomNavigationView();
        if (qBadgeView == null)
            qBadgeView = new QBadgeView(getBaseActivity());
*/
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (canShowBottomBar() && getBaseActivity().getBottomNavigationView() != null) {
            getBaseActivity().getBottomNavigationView().setVisibility(View.VISIBLE);
        } else if (!canShowBottomBar() && getBaseActivity().getBottomNavigationView() != null) {
            getBaseActivity().getBottomNavigationView().setVisibility(View.GONE);
        }

    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (unbinder != null)
            unbinder.unbind();
    /*    qBadgeView.bindTarget(bottomNavigationView.getBottomNavigationItemView(0))
                .hide(true);*/
        super.onDestroy();
    }

//endregion

    //region abstract methods

    protected abstract boolean canShowAppHeader();

    protected abstract boolean canShowBottomBar();

    protected abstract boolean canShowBackArrow();

    protected abstract String getTitle();

    public abstract int getSelectedMenuId();

//endregion

    //region showing message helpers
    protected void showMessage(@StringRes int stringID) {
        if (getBaseActivity() != null) {
            getBaseActivity().showMessage(stringID);
        }
    }

    protected void showMessage(String text) {
        if (getBaseActivity() != null) {
            getBaseActivity().showMessage(text);
        }
    }

    public void showMessage(@StringRes int stringResId, @NonNull MaterialDialog.SingleButtonCallback positiveClick) {
        if (getBaseActivity() != null) {
            getBaseActivity().showMessage(getString(stringResId), positiveClick, null);
        }
    }

    public void showMessage(@StringRes int stringResId, @NonNull MaterialDialog.SingleButtonCallback positiveClick, @NonNull MaterialDialog.SingleButtonCallback negativeClick) {
        if (getBaseActivity() != null) {
            getBaseActivity().showMessage(getString(stringResId), positiveClick, negativeClick);
        }
    }

    public void showMessage(@StringRes int stringResId, @NonNull MaterialDialog.SingleButtonCallback positiveClick,
                            @NonNull MaterialDialog.SingleButtonCallback negativeClick, String positiveText, String negativeText) {
        if (getBaseActivity() != null) {
            getBaseActivity().showMessage(getString(stringResId), positiveClick, negativeClick, positiveText, negativeText);
        }
    }


//endregion


    //region bottom navigation type
    //Those are used to select the correct bottom button

    /**
     * @return {@link #BOTTOM_NAVIGATION_MY_RESERVATIONS}
     * {@link #BOTTOM_NAVIGATION_PERIODS}
     * {@link #BOTTOM_NAVIGATION_MY_ACCOUNT}
     */

    protected final int BOTTOM_NAVIGATION_MY_RESERVATIONS = R.id.bottomItem_main;
    protected final int BOTTOM_NAVIGATION_PERIODS = R.id.bottomItem_messages;
    protected final int BOTTOM_NAVIGATION_MY_ACCOUNT = R.id.bottomItem_map;
    protected final int BOTTOM_NAVIGATION_CONVERSATIONS = R.id.bottomItem_more;
//endregion

    //region helper functions

    /**
     * Method to add fragment from a fragment.
     *
     * @param fragment       the fragment to be added
     * @param addToBackStack keep track of fragments been added. true to track other wise pass false.
     */
    public void addFragment(Fragment fragment, boolean addToBackStack) {
        if (getActivity() != null) {
            //            showLoading(false);
            ((BaseActivity) getActivity()).addContentFragment(fragment, addToBackStack);
        }
    }

    public void setBottomNavigationView(int actionId) {
        bottomNavigationView.getMenu().findItem(actionId).setChecked(true);

    }


    public void hideSoftKeyboard() {
        if (getBaseActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getBaseActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getBaseActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }


    protected void signOut() {
//        SharedPreferenceController.Companion.signOut();
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        startActivity(intent);
    }

    protected void share(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    /**
     * show\hide loading which is in title bar
     */
    public void showLoading(boolean loading) {
        if (getActivity() == null)
            return;

        ((BaseActivity) getActivity()).showLoading(loading);
    }

    /**
     * this method will return baseActivity only if it is instanceOf {@link BaseActivity}
     *
     * @return {@link BaseActivity}
     * throws NullPointerException
     */
    protected BaseActivity getBaseActivity() throws NullPointerException {
        if (getActivity() instanceof BaseActivity)
            return (BaseActivity) getActivity();
        else
            return null;
    }

    //endregion
}