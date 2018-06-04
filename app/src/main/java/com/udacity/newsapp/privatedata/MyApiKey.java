package com.udacity.newsapp.privatedata;

/**
 * Created by jesse on 02/06/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class MyApiKey {

    private static final String apiKey = "PUT_API_KEY_HERE";
    private static final String baseUrl = "https://content.guardianapis.com/search";

    public static String getBaseUrlNewsDetails() {
        return baseUrlNewsDetails;
    }

    private static final String baseUrlNewsDetails = "https://content.guardianapis.com/";

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

}
