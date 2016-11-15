package com.example.monash.worldnews;

/**
 * Created by monash on 11/15/2016.
 */

public class News {

    private String mUrlForImages;
    private String mArticleUrl;
    private String mDate;
    private String mAuthor;
    private String mArticleTitle;
    private String mArticleDescription;


    /**
     *
     * @param urlArticle the url for the article
     * @param urlImage the url for the image of that article
     * @param date the date the article was first published
     * @param author the author or authors that wrote the article
     * @param title the title or 'headline' of the article
     * @param description A small summary of the entire article
     */
    public News(String urlArticle, String urlImage, String date, String author,
                String title, String description){
        mArticleUrl = urlArticle;
        mUrlForImages = urlImage;
        mDate = date;
        mAuthor = author;
        mArticleTitle = title;
        mArticleDescription = description;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public String getUrlForImages() {
        return mUrlForImages;
    }

    public String getDate() {
        return mDate;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleDescription() {
        return mArticleDescription;
    }
}
