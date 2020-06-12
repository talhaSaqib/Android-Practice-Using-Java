package com.example.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.provider.LiveFolders.INTENT;

public class MainActivity extends AppCompatActivity
{
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);



        // For alarm manager
        final Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        AlarmReceiver alarmReceiver = new AlarmReceiver();
        IntentFilter intentFilter = new IntentFilter();
        notifyIntent.setAction("android.intent.action.NOTIFY");
        this.registerReceiver(alarmReceiver, intentFilter);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_ONE_SHOT);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



        //Checking state of Alarm Button
        final boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                String toastMessage;

                if(isChecked)
                {
                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    // Getting current time
                    long triggerTime = SystemClock.elapsedRealtime();
                    Log.e("TAG", "TRIGGER TIME ="+triggerTime );
                    Log.e("TAG", "PENDING INTENT ="+notifyIntent );
                    Log.e("TAG", "ALARM UP ="+alarmUp );

                    if (alarmManager != null)
                    {
                        alarmManager.setInexactRepeating
                                (AlarmManager.RTC_WAKEUP, triggerTime, repeatInterval,
                                        notifyPendingIntent);
//                        alarmManager.setRepeating
//                                (AlarmManager.ELAPSED_REALTIME, triggerTime, repeatInterval,
//                                        notifyPendingIntent);

                        Log.e("TAG", "onCheckedChanged: IN TOGGLE ON CHECK" );
                    }

                    toastMessage = "Stand Up Alarm On!";
                }
                else
                {
                    if (alarmManager != null)
                    {
                        alarmManager.cancel(notifyPendingIntent);
                        Log.e("TAG", "onCheckedChanged: IN TOGGLE OFF CHECK" );
                    }
                    mNotificationManager.cancelAll();

                    final boolean alarmUp1 = (PendingIntent.getBroadcast(MainActivity.this, NOTIFICATION_ID, notifyIntent,
                            PendingIntent.FLAG_NO_CREATE) != null);
                    Log.e("TAG", "ALARM UP1 ="+alarmUp1 );

                    toastMessage = "Stand Up Alarm Off!";
                }

                //Show a toast to say the alarm is turned on or off.
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        createNotificationChannel();
    }

    public void createNotificationChannel()
    {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID, "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }


}




