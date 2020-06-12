package com.example.intentfilterreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        //Intent data is always URI
        Uri uri = intent.getData();

        if (uri != null)
        {
            String uri_string = "URI: " + uri.toString();
            Toast.makeText(this, ""+uri_string, Toast.LENGTH_SHORT).show();
        }
    }
}
