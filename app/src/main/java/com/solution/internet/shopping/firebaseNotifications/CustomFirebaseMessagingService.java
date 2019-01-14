package com.solution.internet.shopping.firebaseNotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.solution.internet.shopping.MainActivity;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.utlities.DataEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class CustomFirebaseMessagingService extends FirebaseMessagingService
{

    private static final String TAG = CustomFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        String Type;

        String ConversationName;
        String FirstWriterID;
        String SecondWriterID;
        String ConversationID;
        String UserID;

        String Text;
        String WriterID;
        String ToUserID;
        String Body;
        String Title = null;

        Log.d("recievedPushNotificati", remoteMessage.getData().toString());
        if (remoteMessage.getData().size() > 0)
        {
            ConversationName = remoteMessage.getData().get("ConversationName");
            Type = remoteMessage.getData().get("Type");
            FirstWriterID = remoteMessage.getData().get("FirstWriterID");
            SecondWriterID = remoteMessage.getData().get("SecondWriterID");
            ConversationID = remoteMessage.getData().get("ConversationID");
            Text = remoteMessage.getData().get("Text");
            WriterID = remoteMessage.getData().get("WriterID");
            ToUserID = remoteMessage.getData().get("ToUserID");
            Body = remoteMessage.getData().get("body");
            Title = remoteMessage.getData().get("title");

            UserID = remoteMessage.getData().get("UserID");

            NotificationData notificationData = new NotificationData();
            notificationData.setType(Type);
            notificationData.setTitle(Title);
            notificationData.setBody(Body);

            this.sendNotification(notificationData);

        }

        //=======================================================================//
      /*  Intent intentBroadCast = new Intent("order_chnaged_statuss");
        // intent.putExtra("value", "test");
        sendBroadcast(intentBroadCast);
*/
    }


//            this.sendNotification(new NotificationData(title, body, sound, notification_type_id, item_uuid));


    private void sendNotification(NotificationData notificationData)
    {

        PendingIntent pendingIntent;
        pendingIntent = intentPending(notificationData);


        NotificationCompat.Builder notificationBuilder = null;
        try
        {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(URLDecoder.decode(notificationData.getTitle(), "UTF-8"))
                    .setContentText(URLDecoder.decode(notificationData.getBody(), "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        if (notificationBuilder != null)
        {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        } else
        {
            Log.d(TAG, "Não foi possível criar objeto notificationBuilder");
        }
    }


    private PendingIntent intentPending(NotificationData notificationData)
    {
        int requestID = (int) System.currentTimeMillis();

        Intent intent = null;

        intent = new Intent(this, MainActivity.class);

        intent.putExtra(DataEnum.extraNotificationType.name(), notificationData.getType());


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


}
