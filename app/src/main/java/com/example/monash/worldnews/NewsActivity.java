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
        ArrayList<News> dummyNews = new ArrayList<>();

        dummyNews.add(new News("www.google.com",
                "http://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));
        dummyNews.add(new News("www.google.com",
                "https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb980f760c21e/0_0_4081_2448/500.jpg",
                "01 12 2016", "JK Rowling", "Men In Black Return", "The infamous duo have returned once again" +
                "to save the earth from ..."));

        // create the adapter so I can convert the array of data to views
        NewsAdapter myAdapter = new NewsAdapter(this, dummyNews);

        // find a reference to the listview
        ListView listView = (ListView)findViewById(R.id.list);
        // attach the adapter to a list view
        listView.setAdapter(myAdapter);


    }
}
