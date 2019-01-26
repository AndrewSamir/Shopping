package com.solution.internet.shopping.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewFragment extends BaseFragment implements HandleRetrofitResp {

    //region fields
    private static String title, url;
    //endregion

    //region views
    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.tvWebViewTitle)
    TextView tvWebViewTitle;
    //endregion

    //region life cycle

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.web_view, container, false);

        unbinder = ButterKnife.bind(this, view);

        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl(url);
//        appHeader.setTitle(title);
        tvWebViewTitle.setText(title);
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
//        removeToolbar();
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader() {
        return true;
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
        return title;
    }

    @Override
    public int getSelectedMenuId() {
        return 0;
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


    //endregion

    //region calls
    //endregion

    //region functions

    public static WebViewFragment init(String title, String url) {
        setTitleString(title);
        setUrl(url);
        return new WebViewFragment();
    }

    public static void setTitleString(String title) {
        WebViewFragment.title = title;
    }

    public static void setUrl(String url) {
        WebViewFragment.url = url;
    }

    //endregion

}

/*
class HelloWebViewClient extends WebViewClient {
    boolean timeout;

    public HelloWebViewClient() {
        timeout = true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (timeout) {
                    Log.d("timeout", "timeout");
                }
            }
        }).start();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        timeout = false;
    }
}
*/
