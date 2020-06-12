package com.example.asyncexample;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MyAsyncTask extends AsyncTask<Void, Void, String>
{

    WeakReference<TextView> mtextView;

    MyAsyncTask(TextView textView)
    {
            mtextView = new WeakReference<>(textView);
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        Random r = new Random();
        int n = r.nextInt(11);
        int s = n*200;

        try
        {
            Thread.sleep(s);
        }
        catch(InterruptedException e)
        {
            Log.e("TAG", "doInBackground: " + e );
        }

        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    @Override
    protected void onPostExecute(String result)
    {
        mtextView.get().setText(result);
    }
}
