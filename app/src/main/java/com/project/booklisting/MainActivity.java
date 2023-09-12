package com.project.booklisting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Api variables
    public final String USGS_REQUEST_URL="https://www.googleapis.com/books/v1/volumes?q=android&maxResults=40";
    public static final String LOG_TAG = MainActivity.class.getName();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BookAdapter mAdapter;
    public  RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//Adapt Customer recycler view
/*
        String image= null;

        ArrayList<BookInfo> b = new ArrayList<>();
        b.add(new BookInfo("1","1","1","1",image," S"));
        BookAdapter  mAdapter = new BookAdapter(getApplicationContext(), b,this);
        mRecyclerView = findViewById(R.id.recycle);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
*/
        //JSON WORKONG HERE
        //Network Connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, display error
            TextView error = findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
            ProgressBar pg1= findViewById(R.id.progressBar1);
            pg1.setVisibility(View.INVISIBLE);
            TextView conn = findViewById(R.id.conect);
            conn.setVisibility(View.INVISIBLE);
        }

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
                // Create a new intent to view the earthquake URI



}

/*
    @Override
    public void onItemClick(int position) {
       // BookInfo currentBook = mAdapter.getItem(position);
        Log.e("Click me","why??");
        Toast.makeText(this,"worked",Toast.LENGTH_SHORT).show();
  // Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentBook.getPreview()));

        // Send the intent to launch a new activity
     // startActivity(websiteIntent);

    }
*/


    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<BookInfo>> implements RecylerViewInterface{


        protected ArrayList<BookInfo> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            Log.e("In the "," Background");
            ArrayList<BookInfo> result = BookAsyncTask.fetchEarthquakeData(urls[0]);
            return result;
        }


        @Override
        public void onItemClick(int position) {

               BookInfo books = mAdapter.getItem(position);
                Log.e("Now it work "," "+mAdapter.getItemCount());
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(books
                    .getPreview()));

            // Send the intent to launch a new activity
            startActivity(websiteIntent);

/*            if(currentBook!= null) {
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentBook.getPreview()));

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }*/
        }
        protected void onPostExecute(ArrayList<BookInfo> data) {
            // Clear the adapter of previous earthquake data
            //mAdapter.clear();


            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                Log.e("In the "," Post");

/*
                Toast.makeText(MainActivity.this,data.get(0).getBookName(),Toast.LENGTH_SHORT).show();
*/


                mAdapter = new BookAdapter(getApplicationContext(), data,this);

                TextView textView = findViewById(R.id.text);
                textView.setVisibility(View.VISIBLE);
                ProgressBar pg = findViewById(R.id.progressBar1);
                pg.setVisibility(View.INVISIBLE);
                TextView connect = findViewById(R.id.conect);
                connect.setVisibility(View.INVISIBLE);


                mRecyclerView = findViewById(R.id.recycle);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);

                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
            }
            if(data == null)
            {
         /*       Toast.makeText(MainActivity.this,"Try",Toast.LENGTH_SHORT).show();*/

                //Hide because to see internet connectivity
                // mEmptyStateTextView.setText("No Data To Show");

            }

        }
    }
}