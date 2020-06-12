package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    private CustomReceiver mReceiver;
    IntentFilter filter;
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new CustomReceiver();

        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);

        // Registering the receiver using the activity context.
        this.registerReceiver(mReceiver, filter);

        // Registering the local receiver.
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver,
                        new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    @Override
    protected void onDestroy()
    {
        //Unregistering the receiver to save system resources and memory leaks

        //System Broadcast
        this.unregisterReceiver(mReceiver);
        //Local Broadcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);

        super.onDestroy();
    }

    public void sendCustomBroadcast(View view)
    {
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);

        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
    }
}
