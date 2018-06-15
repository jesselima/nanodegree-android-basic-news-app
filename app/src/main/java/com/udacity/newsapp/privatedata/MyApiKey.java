package com.udacity.newsapp.privatedata;

/**
 * Created by jesse on 02/06/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class MyApiKey {

    private static final String API_KEY = "test";
    private static final String BASE_URL = "https://content.guardianapis.com/search";
    private static final String BASE_URL_NEWS_DETAILS = "https://content.guardianapis.com/";
    private static final String BASE_URL_WEB_NEWS_URL = "https://www.theguardian.com/";

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getBaseUrlNewsDetails() {
        return BASE_URL_NEWS_DETAILS;
    }

    public static String getBaseWebUrlNews() {
        return BASE_URL_WEB_NEWS_URL;
    }

}
