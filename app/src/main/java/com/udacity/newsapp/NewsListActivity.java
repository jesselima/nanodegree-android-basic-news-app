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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.newsapp.adapters.NewsAdapter;
import com.udacity.newsapp.fragments.CategoriesFragment;
import com.udacity.newsapp.fragments.MainFragmentPagerAdapter;
import com.udacity.newsapp.fragments.NewsFragment;
import com.udacity.newsapp.fragments.SearchFragment;
import com.udacity.newsapp.loaders.NewsLoader;
import com.udacity.newsapp.models.News;

import java.util.ArrayList;
import java.util.List;


public class NewsListActivity extends AppCompatActivity
        implements LoaderCallbacks<List<News>>{

    private static final String LOG_TAG = NewsListActivity.class.getName();
    private static final String REQUEST_URL = "https://content.guardianapis.com/search";
    private static final String API_KEY = "d2c90caa-dfa8-4acc-aad6-3021f69440b8";
    private static final int NEWS_LOADER_ID = 1;
    private String sectionId = "news";
    private String page = "1";
    private String pageSize = "50";
    private String orderBy = "newest";
    private boolean searchBySectionId = false;

    private NewsAdapter newsAdapter;
    private TextView mEmptyStateTextView, textViewNoResultsFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                .replace(R.id.container, new NewsFragment())
//                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.button_nav_top_50:
                        Toast.makeText(NewsListActivity.this, "Read the top 50 newest", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                                .replace(R.id.container, new NewsFragment())
//                                .commit();
                        break;
                    case R.id.button_nav_categories:
                        Toast.makeText(NewsListActivity.this, "Select a desired category", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                                .replace(R.id.container, new CategoriesFragment())
//                                .commit();
                        break;
                    case R.id.button_nav_search:
                        Toast.makeText(NewsListActivity.this, "Advanced search", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                                .replace(R.id.container, new SearchFragment())
//                                .commit();
                        break;
                    case R.id.button_nav_refresh:
                        restartLoaderNews();

                        Toast.makeText(NewsListActivity.this, "Refreshing the list", Toast.LENGTH_SHORT).show();
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
            mEmptyStateTextView.setText("No internet connection!");
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

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if (searchBySectionId) {
            uriBuilder.appendQueryParameter("section", sectionId);
        }

        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("page", page);
        uriBuilder.appendQueryParameter("page-size", pageSize);
        uriBuilder.appendQueryParameter("show-fields", "trailText,headline,thumbnail");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        Log.v("Requested URL: ", uriBuilder.toString());

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
