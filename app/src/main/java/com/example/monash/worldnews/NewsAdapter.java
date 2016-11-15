package com.example.monash.worldnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by monash on 11/15/2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        News newsArticle = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        // Find the reference to the article's ImageView
        ImageView urlImage = (ImageView)convertView.findViewById(R.id.article_image);
        // parse the json image url to convert the url to display an Image
        urlImage.setImageResource(R.drawable.turny_temp_img);

        // find the reference to the author and set it
        TextView author = (TextView)convertView.findViewById(R.id.article_author);
        author.setText(newsArticle.getAuthor());

        // find the reference to the date and set it
        TextView date = (TextView)convertView.findViewById(R.id.article_date);
        date.setText(newsArticle.getDate());

        // find the reference to the title and set it
        TextView title = (TextView)convertView.findViewById(R.id.article_title);
        title.setText(newsArticle.getArticleTitle());

        // find the reference to the description and set it
        TextView description = (TextView)convertView.findViewById(R.id.article_description);
        description.setText(newsArticle.getArticleDescription());

        return convertView;
    }
}
