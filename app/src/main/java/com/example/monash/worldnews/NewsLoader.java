package com.example.monash.worldnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by monash on 11/16/2016.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    /** Constructor for class
     *
     * @param context
     */
    public NewsLoader(Context context){
        super(context);
    }

    /**
     * Start the loader
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     *
     * @return ArrayList containing all of data that has been queried, returned, extracted
     *         and stored.
     */
    @Override
    public ArrayList<News> loadInBackground() {

        // fetch the data and store in array list
        ArrayList<News> newsArticles = QueryUtils.fetchNewsArticleData(QueryUtils.GUARDIAN_URL_QUERY);

        return newsArticles;
    }
}
