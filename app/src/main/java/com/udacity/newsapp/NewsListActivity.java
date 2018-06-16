package com.udacity.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.LoaderManager.LoaderCallbacks;
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
import com.udacity.newsapp.loaders.NewsLoader;
import com.udacity.newsapp.models.News;
import com.udacity.newsapp.privatedata.MyApiKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NewsListActivity extends AppCompatActivity
        implements LoaderCallbacks<List<News>>{

    private static final String LOG_TAG = NewsListActivity.class.getName();

    // Url parameters are set as constant values.
    private static final String CONST_ORDER_BY = "order-by";
    private static final String CONST_PAGE = "page";
    private static final String CONST_PAGE_SIZE = "page-size";
    private static final String CONST_SEARCH_TYPE_KEY = "searchType";
    private static final String CONST_SHOW_FIELDS_KEY = "show-fields";
    private static final String CONST_SHOW_FIELDS_VALUE = "trailText,headline,thumbnail";
    private static final String CONST_SHOW_TAGS_KEY = "show-tags";
    private static final String CONST_SHOW_TAGS_VALUE = "contributor";
    private static final String CONST_SECTION = "section";
    private static final String CONST_API = "api-key";
    private static final String CONST_FROM_DATE = "from-date";
    private static final String CONST_TO_DATE = "to-date";
    private static final String CONST_Q = "q";

    private static final int NEWS_LOADER_ID = 1;

    /* Variables for query strings */
    private String sectionId = "";
    private String fromDate = "2017-01-07";
    private String toDate = "2017-01-30";
    private String orderBy = "newest";
    private String page = "1";
    private String q = "";
    private int pageNumber = 1;

    private String searchType = "default";

    private NewsAdapter newsAdapter;
    private TextView mEmptyStateTextView, textViewNoResultsFound;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        if( getIntent().getExtras() != null) {
            Bundle newsData = getIntent().getExtras();

            if (Objects.equals(newsData.getString(CONST_SEARCH_TYPE_KEY), "category")){
                searchType = newsData.getString(CONST_SEARCH_TYPE_KEY);
                sectionId = newsData.getString("sectionId");
            }
            if (Objects.equals(newsData.getString(CONST_SEARCH_TYPE_KEY), "advanced")){
                searchType = newsData.getString(CONST_SEARCH_TYPE_KEY);
                orderBy = newsData.getString(CONST_ORDER_BY);
                fromDate = newsData.getString(CONST_FROM_DATE);
                toDate = newsData.getString(CONST_TO_DATE);
                q = newsData.getString(CONST_Q);
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
                    case R.id.button_pagination_backward:
                        paginationBackward();
                        break;
                    case R.id.button_pagination_forward:
                        paginationForward();
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
                String BASE_WEB_URL =  MyApiKey.getBaseWebUrlNews();
                String webUrl = BASE_WEB_URL + id;
                openWebPage(webUrl);
            }
        });

    // Close onCreate
    }

    /**
     * When a item list is clicked the news item will be shown on the device browser.
     * @param url is Web Url of the news item.
     */
    private void openWebPage(String url){
        Uri uriWebPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriWebPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /* Pagination forward control */
    private void paginationForward(){
        pageNumber = Integer.parseInt(String.valueOf(page));
        pageNumber++;
        page = String.valueOf(pageNumber);
        doToast(getString(R.string.page) + String.valueOf(pageNumber));
        restartLoaderNews();
    }
    /* Pagination backward control */
    private void paginationBackward(){
        pageNumber = Integer.parseInt(String.valueOf(page));
        if (pageNumber == 1){
            page = String.valueOf(pageNumber);
            doToast(getString(R.string.warning_you_are_at_page_one));
        }else {
            pageNumber--;
            page = String.valueOf(pageNumber);
            restartLoaderNews();
            doToast(getString(R.string.page) + String.valueOf(pageNumber));
        }
    }

    private void doToast(String string){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }


    /* Methods for LoaderCallbacks<List<News>> */

    private void restartLoaderNews(){
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

        String pageSize = "30";
        String API_KEY =  MyApiKey.getApiKey();
        String BASE_URL =  MyApiKey.getBaseUrl();

        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if (searchType.equals("default")) {
            uriBuilder.appendQueryParameter(CONST_ORDER_BY, orderBy);
            uriBuilder.appendQueryParameter(CONST_PAGE, page);
            uriBuilder.appendQueryParameter(CONST_PAGE_SIZE, pageSize);
            uriBuilder.appendQueryParameter(CONST_SHOW_FIELDS_KEY, CONST_SHOW_FIELDS_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_TAGS_KEY, CONST_SHOW_TAGS_VALUE);
            uriBuilder.appendQueryParameter(CONST_API, API_KEY);
            // Log the requested URL
            Log.v("Requested URL: ", uriBuilder.toString());
        }

        if (searchType.equals("category")) {
            uriBuilder.appendQueryParameter(CONST_SECTION, sectionId);
            uriBuilder.appendQueryParameter(CONST_ORDER_BY, orderBy);
            uriBuilder.appendQueryParameter(CONST_PAGE, page);
            uriBuilder.appendQueryParameter(CONST_PAGE_SIZE, pageSize);
            uriBuilder.appendQueryParameter(CONST_SHOW_FIELDS_KEY, CONST_SHOW_FIELDS_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_TAGS_KEY, CONST_SHOW_TAGS_VALUE);
            uriBuilder.appendQueryParameter(CONST_API, API_KEY);
            // Log the requested URL
            Log.v("Requested URL: ", uriBuilder.toString());
        }

        if (searchType.equals("advanced")) {
            uriBuilder.appendQueryParameter(CONST_FROM_DATE, fromDate);
            uriBuilder.appendQueryParameter(CONST_TO_DATE, toDate);
            uriBuilder.appendQueryParameter(CONST_ORDER_BY, orderBy);
            uriBuilder.appendQueryParameter(CONST_PAGE, page);
            uriBuilder.appendQueryParameter(CONST_PAGE_SIZE, pageSize);
            uriBuilder.appendQueryParameter(CONST_Q, q);
            uriBuilder.appendQueryParameter(CONST_SHOW_FIELDS_KEY, CONST_SHOW_FIELDS_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_TAGS_KEY, CONST_SHOW_TAGS_VALUE);
            uriBuilder.appendQueryParameter(CONST_API, API_KEY);
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
        mEmptyStateTextView.setText(R.string.no_news);

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
