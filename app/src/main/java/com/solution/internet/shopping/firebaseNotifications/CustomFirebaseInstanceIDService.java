package com.solution.internet.shopping.firebaseNotifications;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelLoginRequest.ModelLoginRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import retrofit2.Call;


public class CustomFirebaseInstanceIDService extends FirebaseInstanceIdService implements HandleRetrofitResp
{
    private static final String TAG = CustomFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh()
    {
        //Log.e("id","hh");
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        SharedPrefUtil.getInstance(getApplicationContext()).write(DataEnum.shFirebaseToken.name(),refreshedToken);
//        callUpdateDeviceToken();

//        Log.e(TAG, "Token Value: " + refreshedToken);

        // sendTheRegisteredTokenToWebServer(refreshedToken);
    }
/*

    private void callUpdateDeviceToken()
    {
        ModelLoginRequest modelLoginRequest = new ModelLoginRequest();
        modelLoginRequest.setDevicetoken(FirebaseInstanceId.getInstance().getToken());
        modelLoginRequest.setOs("Android");
        HandleCalls.getInstance(getApplicationContext()).setonRespnseSucess(this);
        Call call = HandleCalls.restSha3er.getClientService().callUpdateDeviceToken(modelLoginRequest);
        HandleCalls.getInstance(getApplicationContext()).callRetrofit(call, DataEnum.callUpdateDeviceToken.name(), false);
    }
*/


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
}
