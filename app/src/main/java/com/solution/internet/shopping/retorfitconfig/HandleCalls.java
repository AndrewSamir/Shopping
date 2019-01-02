package com.solution.internet.shopping.retorfitconfig;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.interfaces.HandleRetrofitRespAdapter;
import com.solution.internet.shopping.models.ModelCommenResponse.ModelCommenResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import developer.mokadim.projectmate.dialog.IndicatorStyle;
import developer.mokadim.projectmate.dialog.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class    HandleCalls
{
    private static Context context;
    private static HandleCalls instance = null;
    public static RestShopping restShopping;
    private HandleRetrofitResp onRespnse;
    private HandleRetrofitRespAdapter onRespnseAdapter;
    Dialog progressDialog;

    //private HandleNoContent onNoContent;

    public static HandleCalls getInstance(Context context)
    {
        HandleCalls.context = context;
        if (instance == null)
        {
            instance = new HandleCalls();
            restShopping = RestShopping.getInstance(context);
        }
        return instance;
    }

    public void setonRespnseSucess(HandleRetrofitResp onRespnseSucess)
    {
        this.onRespnse = onRespnseSucess;
    }

    public void setonRespnseSucessApapter(HandleRetrofitRespAdapter onRespnseAdapter)
    {
        this.onRespnseAdapter = onRespnseAdapter;
    }


    public <T> void callRetrofit(Call<ModelCommenResponse> call, final String flag, final Boolean showLoading)
    {
        if (showLoading)
            progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        call.enqueue(new Callback<ModelCommenResponse>()
        {
            @Override
            public void onResponse(Call<ModelCommenResponse> call, Response<ModelCommenResponse> response)
            {
                if (showLoading)
                    progressDialog.dismiss();
                if (response.code() == 200)
                {

                    ModelCommenResponse modelCommenResponse = response.body();
                    if (modelCommenResponse.getResponseMessage() != null)
                        showMessage(modelCommenResponse.getResponseMessage());
                    if (modelCommenResponse.getData() != null && modelCommenResponse.getStatus().equals(context.getString(R.string.done)))
                        onRespnse.onResponseSuccess(flag, modelCommenResponse.getData());
                    else if (modelCommenResponse.getStatus().equals(context.getString(R.string.done)))
                        onRespnse.onNoContent(flag, response.code());


                } else if (response.code() == 406)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showMessage(jObjError.getString("Message"));

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                } else if (response.code() == 500)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showMessage(jObjError.getString("Message"));

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelCommenResponse> call, Throwable t)
            {
                if (showLoading)
                    progressDialog.dismiss();

                showMessage(context.getString(R.string.internet_connection));

//                HelpMe.getInstance(context).retrofironFailure(t);
            }
        });

    }


    public MaterialDialog.Builder getMaterialDialogBuilder()
    {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
//        builder.typeface("TheSansArabic-Bold.otf", "TheSansArabic-Plain.otf");

        return builder;
    }


    public void showMessage(String message)
    {
        showMessage(null, message);
    }

    public void showMessage(@Nullable String title, @NonNull String message)
    {
        MaterialDialog.Builder builder = getMaterialDialogBuilder();
        builder.content(message);
        if (title != null)
        {
            builder.title(title);
        }

        builder.content(message).positiveText(R.string.agree).onPositive(new MaterialDialog.SingleButtonCallback()
        {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
            {
                dialog.dismiss();
            }
        }).autoDismiss(true).titleGravity(GravityEnum.CENTER).contentGravity(GravityEnum.CENTER).show();

    }

}