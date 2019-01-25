package com.solution.internet.shopping.firebaseNotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.solution.internet.shopping.MainActivity;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.DeliveryMainActivity;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelRefreshTokenRequest.ModelRefreshTokenRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import retrofit2.Call;


public class CustomFirebaseMessagingService extends FirebaseMessagingService implements HandleRetrofitResp {

    private static final String TAG = CustomFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String Type;
        int itemId;
        String Body;
        String Title = null;

        Log.d("recievedPushNotificati", remoteMessage.getData().toString());
        if (remoteMessage.getData().size() > 0) {

            Type = remoteMessage.getData().get("type");
            Body = remoteMessage.getData().get("body");
            Title = remoteMessage.getData().get("title");
            itemId = Integer.parseInt(remoteMessage.getData().get("itemid"));


            NotificationData notificationData = new NotificationData();
            notificationData.setType(Type);
            notificationData.setTitle(Title);
            notificationData.setBody(Body);
            notificationData.setItemid(itemId);

            this.sendNotification(notificationData);

        }

        //=======================================================================//
      /*  Intent intentBroadCast = new Intent("order_chnaged_statuss");
        // intent.putExtra("value", "test");
        sendBroadcast(intentBroadCast);
*/
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        HandleCalls.getInstance(getApplicationContext()).setonRespnseSucess(this);
        callRefreshToken(s);
    }


    private void sendNotification(NotificationData notificationData) {

        PendingIntent pendingIntent;
        pendingIntent = intentPending(notificationData);


        NotificationCompat.Builder notificationBuilder = null;
        try {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(URLDecoder.decode(notificationData.getTitle(), "UTF-8"))
                    .setContentText(URLDecoder.decode(notificationData.getBody(), "UTF-8"))
                    .setAutoCancel(true)
                    .setChannelId(getString(R.string.default_notification_channel_id))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (notificationBuilder != null) {

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id),
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } else {
            Log.d(TAG, "Não foi possível criar objeto notificationBuilder");
        }
    }


    private PendingIntent intentPending(NotificationData notificationData) {
        int requestID = (int) System.currentTimeMillis();

        Intent intent = null;

        if (SharedPrefHelper.getInstance(this).getUserType().equals("user"))
            intent = new Intent(this, com.solution.internet.shopping.activities.MainActivity.class);
        else
            intent = new Intent(this, DeliveryMainActivity.class);

        intent.putExtra(DataEnum.extraNotificationType.name(), notificationData.getType());
        intent.putExtra(DataEnum.extraNotificationItemId.name(), notificationData.getItemid());


//        intent.putExtra(DataEnum.extraNotificationType.name(), notificationData.getType());
//        intent.putExtra(DataEnum.extraUserID.name(), notificationData.getUserId());
      /*  SingletonMalaaby.getInstance().setMalaabyId(notificationData.getUserId());
        SingletonMalaaby.getInstance().setMalaabyName(notificationData.getType());
  */
//        Log.d("notification", notificationData.getUserId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;


    }


    //region calls and response

    private void callRefreshToken(String s) {
        ModelRefreshTokenRequest modelRefreshTokenRequest = new ModelRefreshTokenRequest();
        modelRefreshTokenRequest.setDeviceToken(s);

        Call call = HandleCalls.restShopping.getClientService().callRefreshToken(modelRefreshTokenRequest);
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callRefreshToken.name(), false);
    }

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
}
