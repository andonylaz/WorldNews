package com.example.monash.worldnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>{

    private NewsAdapter newsAdapter;
    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        // create the adapter so I can convert the array of data to views
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        // find a reference to the listview
        ListView newsListView = (ListView)findViewById(R.id.list);
        // attach the adapter to a list view
        newsListView.setAdapter(newsAdapter);


        // CHECK FOR INTERNET CONNECTION BEFORE QUERYING DATABASE
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            // fetch data

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER, null, this);

        } else{
            // display there is no connection
        }

        // set up an onClickListener so when a ListItem is Clicked it takes the user to the
        // original URL of the article
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get the url
                News clickedArticle = (News) parent.getItemAtPosition(position);

                // retrieve the url from the article
                String urlForArticle = clickedArticle.getArticleUrl();

                // create a new intent, parse the url and start it
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(urlForArticle));
                startActivity(urlIntent);
            }
        });

    }

    /**
     *
     * @param id the id of the loader
     * @param args
     * @return returns a new instance of the NewsLoader
     */
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    /** After the loader has finished grabbing the data update the UI
     *
     * @param loader
     * @param data
     * @throws NullPointerException
     */
    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) throws
            NullPointerException {

        // clear contents of the adapter before handing it new data
        if (newsAdapter != null) {
            newsAdapter.clear();
        }

        // add all the data to the adapter that was returned from the query
        newsAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }
}
