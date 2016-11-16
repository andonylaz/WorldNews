package com.example.monash.worldnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // construct a bunch of fake data for News Articles
        ArrayList<News> newsTest = QueryUtils.extractJsonResponse();



        // create the adapter so I can convert the array of data to views
        NewsAdapter myAdapter = new NewsAdapter(this, newsTest);

        // find a reference to the listview
        ListView listView = (ListView)findViewById(R.id.list);
        // attach the adapter to a list view
        listView.setAdapter(myAdapter);


    }
}
