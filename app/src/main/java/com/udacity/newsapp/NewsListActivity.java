package com.udacity.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.udacity.newsapp.adapters.NewsAdapter;
import com.udacity.newsapp.loaders.NewsLoader;
import com.udacity.newsapp.models.News;
import com.udacity.newsapp.privatedata.MyApiKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NewsListActivity extends AppCompatActivity
        implements LoaderCallbacks<List<News>>{

    private static final String LOG_TAG = NewsListActivity.class.getName();
    private static final int NEWS_LOADER_ID = 1;

    /* Variables for query strings */
    private String sectionId = "";
    private String fromDate = "2017-01-07";
    private String toDate = "2017-01-30";
    private String orderBy = "newest";
    private String page = "1";
    private String pageSize = "25";
    private String q = "";

    private String searchType = "default";

//    private boolean searchDefault = true;
//    private boolean searchBySectionId = false;
//    private boolean searchAdvanced = false;

    private NewsAdapter newsAdapter;
    private TextView mEmptyStateTextView, textViewNoResultsFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        if( getIntent().getExtras() != null) {
            Bundle newsData = getIntent().getExtras();


            if (Objects.equals(newsData.getString("searchType"), "category")){
                searchType = newsData.getString("searchType");
//                searchBySectionId = true;
//                searchAdvanced = false;
//                searchDefault = false;
                sectionId = newsData.getString("sectionId");
            }
            if (Objects.equals(newsData.getString("searchType"), "advanced")){
                searchType = newsData.getString("searchType");
                Log.v("Search AVD?", searchType);
//                searchAdvanced = true;
//                searchBySectionId = false;
//                searchDefault = false;
                pageSize = newsData.getString("page-size");
                orderBy = newsData.getString("order-by");
                fromDate = newsData.getString("from-date");
                toDate = newsData.getString("to-date");
                q = newsData.getString("q");
            }
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.button_nav_top_50:
                        Intent intentNews = new Intent(getApplicationContext(), NewsListActivity.class);
                        startActivity(intentNews);
                        break;
                    case R.id.button_nav_categories:
                        Intent intentCategories = new Intent(getApplicationContext(), CategoriesActivity.class);
                        startActivity(intentCategories);
                        break;
                    case R.id.button_nav_search:
                        Intent intentSearch = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intentSearch);
                        break;
                }
                return true;
            }
        });

        /* Check internet connection */
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        /* If there are no results shows a message */
        textViewNoResultsFound = findViewById(R.id.no_news_found_text);
        /* Implement the ListView */
        ListView newsListView = findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News newsItem = newsAdapter.getItem(position);
                String id = newsItem.getId();
                Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartLoaderNews();
            }
        });

    }

    /* Methods for LoaderCallbacks<List<News>> */

    /**
     * Restart the loader.
     */
    public void restartLoaderNews(){
        // Clear the ListView as a new query will be kicked off
        newsAdapter.clear();
        // Hide the empty state text view as the loading indicator will be displayed
        mEmptyStateTextView.setVisibility(View.GONE);
        // Show the loading indicator while new data is being fetched
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        // Restart the loader to requery the News as the query settings have been updated
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
    }

    /**
    * Build the query string and return a NewsLoader object with the URI and the context.
    */
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        String API_KEY =  MyApiKey.getApiKey();
        String BASE_URL =  MyApiKey.getBaseUrl();

        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if (searchType.equals("default")) {
            uriBuilder.appendQueryParameter("order-by", orderBy);
            uriBuilder.appendQueryParameter("page", page);
            uriBuilder.appendQueryParameter("page-size", pageSize);
            uriBuilder.appendQueryParameter("show-fields", "trailText,headline,thumbnail");
            uriBuilder.appendQueryParameter("show-tags", "contributor");
            uriBuilder.appendQueryParameter("api-key", API_KEY);
            // Log the requested URL
            Log.v("Requested URL: ", uriBuilder.toString());
        }

        if (searchType.equals("category")) {
            uriBuilder.appendQueryParameter("section", sectionId);
            uriBuilder.appendQueryParameter("order-by", orderBy);
            uriBuilder.appendQueryParameter("page", page);
            uriBuilder.appendQueryParameter("page-size", pageSize);
            uriBuilder.appendQueryParameter("show-fields", "trailText,headline,thumbnail");
            uriBuilder.appendQueryParameter("show-tags", "contributor");
            uriBuilder.appendQueryParameter("api-key", API_KEY);
            // Log the requested URL
            Log.v("Requested URL: ", uriBuilder.toString());
        }

        if (searchType.equals("advanced")) {
            uriBuilder.appendQueryParameter("from-date", fromDate);
            uriBuilder.appendQueryParameter("to-date", toDate);
            uriBuilder.appendQueryParameter("order-by", orderBy);
            uriBuilder.appendQueryParameter("page", page);
            uriBuilder.appendQueryParameter("page-size", pageSize);
            uriBuilder.appendQueryParameter("q", q);
            uriBuilder.appendQueryParameter("show-fields", "trailText,headline,thumbnail");
            uriBuilder.appendQueryParameter("show-tags", "contributor");
            uriBuilder.appendQueryParameter("api-key", API_KEY);
            // Log the requested URL
            Log.v("Requested URL: ", uriBuilder.toString());
        }


        return new NewsLoader(this, uriBuilder.toString());
    }

    /**
     * Take action when the loader finishes its task. Hide the loading indicator and add
     * the list of news do the {@link NewsAdapter} object. But is the list of news is empty
     * show a text message in the UI.
     * @param loader is the {@link NewsLoader} object
     * @param newsList is the list of news.
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No news found."
        mEmptyStateTextView.setText("No news!");

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsList != null && !newsList.isEmpty()) {
            textViewNoResultsFound.setVisibility(View.GONE);
            newsAdapter.addAll(newsList);
        }else{
            textViewNoResultsFound.setVisibility(View.VISIBLE);
        }
    }

    /**
     * When the loader is reset, its clear the adapter for a better performance.
     * @param loader is the loader of the list of news
     */
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        newsAdapter.clear();
    }

}
