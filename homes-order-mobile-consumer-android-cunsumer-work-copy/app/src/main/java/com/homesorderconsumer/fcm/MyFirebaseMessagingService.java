package com.homesorderconsumer.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.homesorderconsumer.R;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.user.myorder.MyOrderDetailActivity;

import org.json.JSONObject;
import static android.content.ContentValues.TAG;


/**
 * Created by innoppl on 22/05/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String orderId="";
    String pushNotificationType="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject jsonObject=new JSONObject(remoteMessage.getData());
                if (jsonObject.has("orderId")){
                    orderId=jsonObject.getString("orderId");
                }
                if (jsonObject.has("pushNotificationType")){
                    pushNotificationType=jsonObject.getString("pushNotificationType");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(),orderId,pushNotificationType);
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    private void sendNotification(String messageBody,String orderId,String pushNotificationType) {
        Intent intent;

        if (pushNotificationType.equals(NotificationTypeEnum.ORDER.getValue())){
            intent = new Intent(this, MyOrderDetailActivity.class);
            intent.putExtra("orderId",orderId);
        }else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"my_channel_id_01")
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.mipmap.ic_notification_transparent);//Need to place
            // transparent icon
            notificationBuilder.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
