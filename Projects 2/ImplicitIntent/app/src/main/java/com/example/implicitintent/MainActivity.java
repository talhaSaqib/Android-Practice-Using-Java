package com.example.implicitintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareTextEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebsiteEditText = findViewById(R.id.editText);
        mLocationEditText = findViewById(R.id.editText2);
        mShareTextEditText = findViewById(R.id.editText3);
    }

    public void openWebsite(View view)
    {

            String url = mWebsiteEditText.getText().toString();
            Uri webpage = Uri.parse(url);

            // Implicit Intent
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            // Find an activity to handle the intent and start that activity.
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(intent);
            }
            else
            {
                Log.d("ImplicitIntents", "Can't handle this!");
                Toast.makeText(this, "Open Website intent didn't work", Toast.LENGTH_SHORT).show();
            }
    }

    public void openLocation(View view)
    {
        String loc = mLocationEditText.getText().toString();
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);

        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        else
        {
            Log.d("ImplicitIntents", "Can't handle this intent!");
            Toast.makeText(this, "Open Location intent didn't work", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareText(View view)
    {
        String txt = mShareTextEditText.getText().toString();

        String mimeType = "text/plain";

        //Sharing data with other apps
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with: ")
                .setText(txt)
                .startChooser();
    }
}
