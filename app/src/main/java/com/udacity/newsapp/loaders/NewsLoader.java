package com.udacity.newsapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.udacity.newsapp.models.News;

import java.util.List;

import static com.udacity.newsapp.utils.QueryUtils.fetchNewsData;


public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * Query URL
     */
    private final String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     * If the input Url object is null the method will nos proceed.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of News.
        // fetchNewsData() is a method from QueryUtils class.
        return fetchNewsData(mUrl);
    }
}
