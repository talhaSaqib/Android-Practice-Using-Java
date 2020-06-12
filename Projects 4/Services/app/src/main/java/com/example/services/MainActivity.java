package com.example.services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView textView;
    Button button;
    EditText editText;
    MyReceiver myReceiver;

    public static final String FILTER_ACTION_KEY = "any_key";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.inputText);

        setReceiver();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra("message", message);
                startService(intent);
            }
        });

    }

//    @Override
//    protected void onStart()
//    {
//
//        super.onStart();
//    }

//    @Override
//    protected void onStop()
//    {
//        unregisterReceiver(myReceiver);
//        super.onStop();
//    }

    private void setReceiver()
    {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    private class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String message = intent.getStringExtra("broadcastMessage");
            textView.setText(textView.getText() + "\n" + message);
        }
    }

}
