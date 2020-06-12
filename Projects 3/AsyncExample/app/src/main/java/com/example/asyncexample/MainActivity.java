package com.example.asyncexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private TextView mTextView;
    private static final String KEY_TEXT = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);

        // Restore TextView if there is a savedInstanceState
        if(savedInstanceState!=null)
        {
            mTextView.setText(savedInstanceState.getString(KEY_TEXT));
        }
    }

    public void startTask(View view)
    {
        mTextView.setText("Async Task is initiated");

        //starting Async Task
        new MyAsyncTask(mTextView).execute();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString(KEY_TEXT, mTextView.getText().toString());
    }
}
