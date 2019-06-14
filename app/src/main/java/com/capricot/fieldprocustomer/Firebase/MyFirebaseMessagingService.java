package com.capricot.fieldprocustomer.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.capricot.fieldprocustomer.Home;
import com.capricot.fieldprocustomer.Login;
import com.capricot.fieldprocustomer.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    NotificationManager mNotificationManager;
    private NotificationCompat.Builder notificationBuilder;
    public Notification notification;
    NotificationChannel mChannel;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /*public MyFirebaseMessagingService(){

    }*/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

       /* if(remoteMessage.getData().size()>0)
        {
            Log.d(TAG, "onMessageReceived: "+remoteMessage.getData() );

            JSONObject jsonObject=new JSONObject(remoteMessage.getData());

            try {
                String jsonmessage=jsonObject.getString("message");

                Log.d(TAG, "onMessageReceived: "+jsonmessage);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if(remoteMessage.getNotification()!=null)
        {
            Log.d(TAG, "onMessageReceived: "+remoteMessage.getNotification() );

            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();
            String clickAction=remoteMessage.getNotification().getClickAction();

            Log.d(TAG, "onMessageReceived: "+title);
            Log.d(TAG, "onMessageReceived: "+body);
            Log.d(TAG, "onMessageReceived: "+clickAction);

            sendNotification(title,body,clickAction);

        }*/
        Log.i("MyFirebaseMsgService", "Data Payload: " + remoteMessage.getData().toString());

        //data messages getdata object put as jsonobject
        //data messages using get json of remote message of data
        JSONObject json = new JSONObject(remoteMessage.getData());
        //when data messages can receive them to show to method
        Log.i("MyFirebaseMsgService", "Data Payload: " + json);

        handleDataMessage(json);

        Log.i("MyFirebaseMsgService", "Data Payload: " + json);


    }

    private void handleDataMessage(JSONObject json) {


        //api response get jsonobject of message
        try {
            //  JSONObject data1  = json.getJSONObject("notification");
            Log.i("MyFirebaseMsgService", "Data Payload: " + json);

            //get the json object of message inside the title
            String title = json.getString("title");
            //get the json object of message inside the message
            String body = json.getString("body");
            String click_action = json.getString("click_action");
            Log.i("MyFirebaseMsgService", "Data Payload: " + json);

            Intent intent;


            if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {

                PendingIntent resultIntent;
                // Do something for Navigations to Going Activity
                //if App is Foreground using Notification Sound show
                if (!NotificationUtils.isAppIsInBackground(this)) {

                    if (click_action.equalsIgnoreCase("ticket")) {

                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    } else if (click_action.equalsIgnoreCase("imprestspare")) {
                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    }
                } else {
                    if (click_action.equalsIgnoreCase("ticket")) {

                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    } else if (click_action.equalsIgnoreCase("imprestspare")) {
                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    }
                    // Create an Intent for the activity you want to start
                    intent = new Intent(this, Home.class);
                    // Create the TaskStackBuilder and add the intent, which inflates the back stack
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(intent);
                    // Get the PendingIntent containing the entire back stack
                    resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                }


                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        // .setStyle(inboxStyle)
                        .setLargeIcon(icon)
                        .setSmallIcon(R.drawable.appicon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(resultIntent);

                // Will display the notification in the notification bar
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notification = notificationBuilder.build();
                notificationManager.notify(Constants.NOTIFY_ID, notification);

            } else {

                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationManager mNotific = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                int importanceHigh = NotificationManager.IMPORTANCE_HIGH;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_ID, importanceHigh);
                    mChannel.setDescription(body);
                    mChannel.setLightColor(Color.GREEN);
                    mChannel.canShowBadge();
                    mChannel.setShowBadge(true);

                    mNotific.createNotificationChannel(mChannel);


                }

                PendingIntent resultIntent;
                if (!NotificationUtils.isAppIsInBackground(this)) {

                    if (click_action.equalsIgnoreCase("ticket")) {

                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    } else if (click_action.equalsIgnoreCase("imprestspare")) {

                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                } else {

                    // Create an Intent for the activity you want to start
                    intent = new Intent(this, Login.class);
                    // Create the TaskStackBuilder and add the intent, which inflates the back stack
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(intent);
                    // Get the PendingIntent containing the entire back stack
                    resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    if (click_action.equalsIgnoreCase("ticket")) {

                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    } else if (click_action.equalsIgnoreCase("imprestspare")) {

                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        // Create an Intent for the activity you want to start
                        intent = new Intent(this, Home.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        stackBuilder = TaskStackBuilder.create(this);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        // Get the PendingIntent containing the entire back stack
                        resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                }


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), Constants.CHANNEL_ID)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setBadgeIconType(R.drawable.appicon)
                            .setLargeIcon(icon)
                            .setSmallIcon(R.drawable.appicon)
                            .setWhen(System.currentTimeMillis())
                            .setGroup(body)
                            .setSound(defaultSoundUri)
                            //.setStyle(inboxStyle)
                            .setAutoCancel(true)
                            .setContentIntent(resultIntent)
                            .build();

                    mNotific.notify(Constants.NOTIFY_ID, notification);


                } else {


                    notificationBuilder = new NotificationCompat.Builder(this)
                            // .setStyle(inboxStyle)
                            .setLargeIcon(icon)
                            .setSmallIcon(R.drawable.appicon)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setWhen(System.currentTimeMillis())
                            .setGroup(body)
                            .setContentIntent(resultIntent);

                    // Will display the notification in the notification bar
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notification = notificationBuilder.build();
                    notificationManager.notify(Constants.NOTIFY_ID, notification);


                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
