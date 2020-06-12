package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
        }
        catch (Exception e)
        {
            Log.e(TAG, "onCreate: Error = " + e.toString());
        }
    }

    public void exit(View view)
    {
        try
        {
            finish();
            System.exit(0);
        }
        catch (Exception e)
        {
            Log.e(TAG, "exit: Error = " + e.toString());
        }
    }

    public void signUp(View view)
    {
        try
        {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Log.e(TAG, "exit: Error = " + e.toString());
        }
    }
}
