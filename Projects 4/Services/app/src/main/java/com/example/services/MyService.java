package com.example.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends IntentService
{

    public MyService()
    {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String message = intent.getStringExtra("message");
        intent.setAction(MainActivity.FILTER_ACTION_KEY);
        SystemClock.sleep(3000);
        String echoMessage = "IntentService after a pause of 3 seconds echoes " + message;
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage", echoMessage));
    }
}
