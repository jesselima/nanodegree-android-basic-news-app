package com.udacity.newsapp.privatedata;

/**
 * This class holds static methods and variables for a better use of API KEY and Base Urls through all the app.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class MyApiKey {

    // TODO: Paste you API KEY in "test" string value instead.
    private static final String API_KEY = "d2c90caa-dfa8-4acc-aad6-3021f69440b8";
    private static final String BASE_URL = "https://content.guardianapis.com/search";
    private static final String BASE_URL_WEB_NEWS_URL = "https://www.theguardian.com/";

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getBaseWebUrlNews() {
        return BASE_URL_WEB_NEWS_URL;
    }

}
