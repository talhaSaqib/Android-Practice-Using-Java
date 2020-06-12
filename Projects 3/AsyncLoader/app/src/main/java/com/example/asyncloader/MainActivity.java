package com.example.asyncloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        //implements LoaderManager.LoaderCallbacks<String>
{
    EditText input;
    TextView title;
    TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        if(getSupportLoaderManager().getLoader(0)!=null)
//        {
//            getSupportLoaderManager().initLoader(0,null,this);
//        }

        input = findViewById(R.id.input);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
    }

    public void searchBooks(View view)
    {
        String queryString = input.getText().toString();

        //Hide keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
        {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //Getting Network info
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null)
        {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // Checking internet connection and query length
        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0)
        {
            new FetchBook(title, author).execute(queryString);
            title.setText("Loading...");
            author.setText("");
        }
        else
         {
            if (queryString.length() == 0)
            {
                author.setText("");
                title.setText("No search item");
            }
            else
            {
                author.setText("");
                title.setText("No internet connection.");
            }
        }
    }


    //NOT WORKING
//
//    public void searchBooks2(View view)
//    {
//        String queryString = input.getText().toString();
//
//        //Hide keyboard
//        InputMethodManager inputManager = (InputMethodManager)
//                getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (inputManager != null)
//        {
//            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//
//        //Getting Network info
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = null;
//        if (connMgr != null)
//        {
//            networkInfo = connMgr.getActiveNetworkInfo();
//        }
//
//        // Checking internet connection and query length
//        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0)
//        {
//            Bundle queryBundle = new Bundle();
//            queryBundle.putString("queryString", queryString);
//            getSupportLoaderManager().restartLoader(0, queryBundle, this);
//
//            title.setText("Loading...");
//            author.setText("");
//        }
//        else
//        {
//            if (queryString.length() == 0)
//            {
//                author.setText("");
//                title.setText("No search item");
//            }
//            else
//            {
//                author.setText("");
//                title.setText("No internet connection.");
//            }
//        }
//    }
//
//    //Loader Manager Functions
//    @NonNull
//    @Override
//    public Loader<String> onCreateLoader(int id, @Nullable Bundle args)
//    {
//        String queryString = "";
//
//        if (args != null)
//        {
//            queryString = args.getString("queryString");
//        }
//
//        return new BookLoader(this, queryString);
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<String> loader, String data)
//    {
//        try {
//            // Convert the response into a JSON object.
//            JSONObject jsonObject = new JSONObject(data);
//            // Get the JSONArray of book items.
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//
//            // Initialize iterator and results fields.
//            int i = 0;
//            String title1 = null;
//            String authors1 = null;
//
//            // Look for results in the items array, exiting
//            // when both the title1 and author
//            // are found or when all items have been checked.
//            while (i < itemsArray.length() && (authors1 == null && title1 == null)) {
//                // Get the current item information.
//                JSONObject book = itemsArray.getJSONObject(i);
//                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
//
//                // Try to get the author and title1 from the current item,
//                // catch if either field is empty and move on.
//                try {
//                    title1 = volumeInfo.getString("title1");
//                    authors1 = volumeInfo.getString("authors1");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                // Move to the next item.
//                i++;
//            }
//
//            // If both are found, display the result.
//            if (title1 != null && authors1 != null)
//            {
//                title.setText(title1);
//                author.setText(authors1);
//            } else {
//                // If none are found, update the UI to
//                // show failed results.
//                title.setText("No Results");
//                author.setText("");
//            }
//
//        }
//        catch (Exception e)
//        {
//            // If onPostExecute does not receive a proper JSON string,
//            // update the UI to show failed results.
//            title.setText("No Results");
//            author.setText("");
//        }
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<String> loader)
//    {
//
//    }
}

class FetchBook extends AsyncTask<String, Void, String>
{
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    FetchBook(TextView titleText, TextView authorText)
    {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }

    @Override
    protected String doInBackground(String... strings)
    {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        try
        {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;

            // Look for results in the items array, exiting
            // when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() && (authors == null && title == null))
            {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try
                {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                }
                catch (Exception e) { e.printStackTrace(); }

                // Move to the next item.
                i++;
            }

            // If both are found, display the result.
            if (title != null && authors != null)
            {
                mTitleText.get().setText(title);
                mAuthorText.get().setText(authors);
            }
            else
            {
                // If none are found, update the UI to
                // show failed results.
                mTitleText.get().setText("No Results");
                mAuthorText.get().setText("");
            }

        }
        catch (Exception e)
        {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.get().setText("No Results");
            mAuthorText.get().setText("");
        }
    }

}

class NetworkUtils
{
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    static String getBookInfo(String queryString)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try
        {
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            // Converting URI to URL
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            // Reading Input
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
                builder.append("\n");
            }

            // Checking if result is empty
            if (builder.length() == 0) { return null; }

            bookJSONString = builder.toString();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (urlConnection != null) { urlConnection.disconnect(); }

            if (reader != null)
            {
                try { reader.close(); }
                catch (IOException e)
                { e.printStackTrace(); }
            }
        }

        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }
}
