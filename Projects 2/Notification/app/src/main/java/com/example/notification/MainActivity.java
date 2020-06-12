package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;

    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private NotificationReceiver mReceiver = new NotificationReceiver();

    private static final String ACTION_CLEAR_NOTIFICATION =
            "com.example.android.notifyme.ACTION_CLEAR_NOTIFICATION";

    private Button button_notify;
    private Button button_update;
    private Button button_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.button);
        button_cancel = findViewById(R.id.button3);
        button_update = findViewById(R.id.button2);
        setNotificationButtonState(true, false, false);

        createNotificationChannel();

        //registering a broadcast receiver
        //It may seem as if the broadcast sent by the notification only concerns your app and
        // should be delivered with a LocalBroadcastManager. However, using a PendingIntent
        // delegates the responsibility of delivering the notification to the Android framework.
        // Because the Android runtime handles the broadcast, you cannot use LocalBroadcastManager.
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_NOTIFICATION);
        filter.addAction(ACTION_CLEAR_NOTIFICATION);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private NotificationCompat.Builder getNotificationBuilder()
    {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        // To open this activity when user taps on notification
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //To clear notification
        Intent clearIntent = new Intent(ACTION_CLEAR_NOTIFICATION);
        PendingIntent clearPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, clearIntent, PendingIntent.FLAG_ONE_SHOT);

        // Last 2 are for backward compatibility as we already specified these in channel
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                        .setContentTitle("You've been notified!")
                        .setContentText("This is your notification text.")
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentIntent(notificationPendingIntent)
                        .setAutoCancel(true)
                        .setDeleteIntent(clearPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);

        return notifyBuilder;
    }

    public void createNotificationChannel()
    {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Because notification channels are only available in API 26 and higher,
        //add a condition to check for the device's API version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "First Notification", NotificationManager.IMPORTANCE_HIGH);

            // Changing channel default settings
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled)
    {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    public void sendNotification(View view)
    {
        // our custom action
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);

        //To make sure that this pending intent is sent and used only once, set FLAG_ONE_SHOT.
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // adding action to notification
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification",
                updatePendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, true, true);
    }

    public void updateNotification(View view)
    {
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.mascot_1);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setSummaryText("This is updated content")
                .setBigContentTitle("Notification Updated!"));

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    public void cancelNotification(View view)
    {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }


    public class NotificationReceiver extends BroadcastReceiver
    {

        public NotificationReceiver() { }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            View view = null;
            String intentAction = intent.getAction();
            if (intentAction != null)
            {
                switch (intentAction)
                {
                    case ACTION_UPDATE_NOTIFICATION:
                        // Update the notification
                        updateNotification(view);
                        break;

                    case ACTION_CLEAR_NOTIFICATION:
                        cancelNotification(view);
                        break;
                }
            }
        }
    }

}
