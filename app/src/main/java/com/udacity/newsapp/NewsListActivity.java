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
import android.widget.ImageView;
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
        implements LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = NewsListActivity.class.getName();

    /* Url parameters are set as constant values. */
    private static final String CONST_ORDER_BY_KEY = "order-by";
    private static final String CONST_PAGE = "page";
    private static final String CONST_PAGE_SIZE_KEY = "page-size";
    private static final String CONST_PAGE_SIZE_VALUE = "30";
    private static final String CONST_SHOW_FIELDS_KEY = "show-fields";
    private static final String CONST_SHOW_FIELDS_VALUE = "trailText,headline,thumbnail";
    private static final String CONST_SHOW_TAGS_KEY = "show-tags";
    private static final String CONST_SHOW_TAGS_VALUE = "contributor";
    private static final String CONST_SEARCH_TYPE_KEY = "searchType";
    private static final String CONST_SECTION_KEY = "section";
    private static final String CONST_API_KEY = "api-key";
    private static final String CONST_FROM_DATE_KEY = "from-date";
    private static final String CONST_TO_DATE_KEY = "to-date";
    private static final String CONST_Q_KEY = "q";

    private static final int NEWS_LOADER_ID = 1;

    /* Variables for query strings */
    private String sectionId = "";
    private String fromDate = "2017-01-07";
    private String toDate = "2017-01-30";
    private String orderBy = "newest";
    private String page = "1";
    private String q = "";
    private int pageNumber = 1;
    /* Default search type */
    private String searchType = "default";

    private NewsAdapter newsAdapter;
    private ListView newsListView;

    /* Variables to warning the user when need about no news results and no internet connection */
    private TextView textViewNoResultsFound, textViewNoInternetConnection;
    private ImageView imageViewNoResultsFound, imageViewNoInternetConnection;
    private boolean isNewsListEmpty;
    private View loadingIndicator;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        // References for loading indicator
        loadingIndicator = findViewById(R.id.loading_indicator);
        // References for UI elements tha warning for no internet connection
        textViewNoInternetConnection = findViewById(R.id.text_view_no__connection);
        imageViewNoInternetConnection = findViewById(R.id.image_view_connection_inactive);
        // References for UI elements tha warning for no news results
        textViewNoResultsFound = findViewById(R.id.no_news_found_text);
        imageViewNoResultsFound = findViewById(R.id.image_view_no_results_found);
        // References to the ListView that may be populated with data with data if the request to the server is success and returns any news.
        newsListView = findViewById(R.id.list);

        /* CHECK INTENT DATA*/
        if (getIntent().getExtras() != null) {
            Bundle newsData = getIntent().getExtras();
            if (Objects.equals(newsData.getString(CONST_SEARCH_TYPE_KEY), "category")) {
                searchType = newsData.getString(CONST_SEARCH_TYPE_KEY);
                sectionId = newsData.getString("sectionId");
            }
            if (Objects.equals(newsData.getString(CONST_SEARCH_TYPE_KEY), "advanced")) {
                searchType = newsData.getString(CONST_SEARCH_TYPE_KEY);
                orderBy = newsData.getString(CONST_ORDER_BY_KEY);
                fromDate = newsData.getString(CONST_FROM_DATE_KEY);
                toDate = newsData.getString(CONST_TO_DATE_KEY);
                q = newsData.getString(CONST_Q_KEY);
            }
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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

        // Check the internet connection before to call the Loader.
        if (!checkInternetConnection()) {
            hideLoadingIndicator();
            hideNoResultsWarning();
            showConnectionWarning();
        } else {
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }

        /* Implement the ListView */

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);

        /* When a item list is clicked the clicked news will be show in the default browser  */
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // When user clicks in a item list the internet connection is checked first.
                // If there is connection allows redirect to web browser.
                if (!checkInternetConnection()) {
                    doToast(getString(R.string.check_your_connection));
                } else {
                    News newsItem = newsAdapter.getItem(position);
                    String id = Objects.requireNonNull(newsItem).getId();
                    String BASE_WEB_URL = MyApiKey.getBaseWebUrlNews();
                    String webUrl = BASE_WEB_URL + id;
                    openWebPage(webUrl);
                }
            }
        });

        /* Close onCreate */
    }

    /**
     * When a item list is clicked the news item will be shown on the device browser.
     *
     * @param url is Web Url of the news item.
     */
    private void openWebPage(String url) {
        Uri uriWebPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriWebPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /* Pagination forward control */
    private void paginationForward() {
        /* Check if there is connection before move to the next page */
        if (!checkInternetConnection()) {
            doToast(getString(R.string.check_your_connection));
            /* Check is the next of news is empty and if the current list has less than 30 news (default page-size). So there is no need to load more pages */
        } else if (isNewsListEmpty || pageNumber < 30) {
            hideListView();
            showNoResultsWarning();
            textViewNoResultsFound.setText(R.string.no_more_news_in_this_list);
            doToast(getString(R.string.no_more_news));
        } else {
            pageNumber = Integer.parseInt(String.valueOf(page));
            pageNumber++;
            page = String.valueOf(pageNumber);
            doToast(getString(R.string.page) + String.valueOf(pageNumber));
            restartLoaderNews();
        }

    }

    /* Pagination backward control */
    private void paginationBackward() {
        /* Check if there is connection before move to the previous page */
        if (!checkInternetConnection()) {
            doToast(getString(R.string.check_your_connection));
        } else {
            hideNoResultsWarning();
            showListView();
            pageNumber = Integer.parseInt(String.valueOf(page));
            if (!checkInternetConnection()) {
                doToast(getString(R.string.check_your_connection));
            } else if (pageNumber == 1) {
                page = String.valueOf(pageNumber);
                doToast(getString(R.string.warning_you_are_at_page_one));
            } else {
                pageNumber--;
                page = String.valueOf(pageNumber);
                restartLoaderNews();
                doToast(getString(R.string.page) + String.valueOf(pageNumber));
            }
        }
    }

    /**
     * Thia method makes the reuse of toast object to avoid toasts queue
     *
     * @param string is the text you want to show in the toast
     */
    private void doToast(String string) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }

    /* Methods for LoaderCallbacks<List<News>> */

    private void restartLoaderNews() {
        // Clear the ListView as a new query will be kicked off
        newsAdapter.clear();
        // Show the loading indicator while new data is being fetched
        showLoadingIndicator();
        // Restart the loader to requery the News as the query settings have been updated
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
    }

    /**
     * Build the query string and return a NewsLoader object with the URI and the context.
     */
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        String API_KEY = MyApiKey.getApiKey();
        String BASE_URL = MyApiKey.getBaseUrl();

        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        /* This is the default Url loaded when the NewsListActivity opens or when the "News item in BottonNavigationView is clicked */
        if (searchType.equals("default")) {
            uriBuilder.appendQueryParameter(CONST_ORDER_BY_KEY, orderBy);
            uriBuilder.appendQueryParameter(CONST_PAGE, page);
            uriBuilder.appendQueryParameter(CONST_PAGE_SIZE_KEY, CONST_PAGE_SIZE_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_FIELDS_KEY, CONST_SHOW_FIELDS_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_TAGS_KEY, CONST_SHOW_TAGS_VALUE);
            uriBuilder.appendQueryParameter(CONST_API_KEY, API_KEY);
        }

        /* When the user clicks in a category item this query string takes action */
        if (searchType.equals("category")) {
            uriBuilder.appendQueryParameter(CONST_SECTION_KEY, sectionId);
            uriBuilder.appendQueryParameter(CONST_ORDER_BY_KEY, orderBy);
            uriBuilder.appendQueryParameter(CONST_PAGE, page);
            uriBuilder.appendQueryParameter(CONST_PAGE_SIZE_KEY, CONST_PAGE_SIZE_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_FIELDS_KEY, CONST_SHOW_FIELDS_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_TAGS_KEY, CONST_SHOW_TAGS_VALUE);
            uriBuilder.appendQueryParameter(CONST_API_KEY, API_KEY);
        }

        /* When the user request advanced search this query string takes action with the search parameters from SearchActivity */
        if (searchType.equals("advanced")) {
            uriBuilder.appendQueryParameter(CONST_FROM_DATE_KEY, fromDate);
            uriBuilder.appendQueryParameter(CONST_TO_DATE_KEY, toDate);
            uriBuilder.appendQueryParameter(CONST_ORDER_BY_KEY, orderBy);
            uriBuilder.appendQueryParameter(CONST_PAGE, page);
            uriBuilder.appendQueryParameter(CONST_PAGE_SIZE_KEY, CONST_PAGE_SIZE_VALUE);
            uriBuilder.appendQueryParameter(CONST_Q_KEY, q);
            uriBuilder.appendQueryParameter(CONST_SHOW_FIELDS_KEY, CONST_SHOW_FIELDS_VALUE);
            uriBuilder.appendQueryParameter(CONST_SHOW_TAGS_KEY, CONST_SHOW_TAGS_VALUE);
            uriBuilder.appendQueryParameter(CONST_API_KEY, API_KEY);
        }

        return new NewsLoader(this, uriBuilder.toString());
    }

    /**
     * Take action when the loader finishes its task. Hide the loading indicator and add
     * the list of news do the {@link NewsAdapter} object. But is the list of news is empty
     * show a text message in the UI.
     *
     * @param loader   is the {@link NewsLoader} object
     * @param newsList is the list of news.
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        // Hide loading indicator because the data has been loaded
        hideLoadingIndicator();
        hideConnectionWarning();

        if (newsList == null || newsList.isEmpty()) {
            isNewsListEmpty = true;
        }

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsList != null && !newsList.isEmpty()) {
            // Clear the adapter object before add the new list into it.
            newsAdapter.clear();
            showListView();
            // Hide the warnings for "no news results" or
            hideNoResultsWarning();
            hideConnectionWarning();
            // Add the list of news to the Adapter object
            newsAdapter.addAll(newsList);
        } else if (!checkInternetConnection()) {
            // If the news list is null or empty, hides the ListView and shows "no results found" warning in the UI.
            showConnectionWarning();
        } else {
            showNoResultsWarning();
        }
    }

    /**
     * When the loader is reset, its clear the adapter for a better performance.
     *
     * @param loader is the loader of the list of news
     */
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        newsAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        newsAdapter.clear();
        if (!checkInternetConnection()) {
            hideLoadingIndicator();
            hideNoResultsWarning();
            showConnectionWarning();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!checkInternetConnection()) {
            getLoaderManager().destroyLoader(NEWS_LOADER_ID);
        }
    }

    /* HELPER METHODS */

    /**
     * This method when called che
     *
     * @return a boolean true if there is internet connection.
     */
    public boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.GONE);
    }

    private void showListView() {
        newsListView.setVisibility(View.VISIBLE);
    }

    private void hideListView() {
        newsListView.setVisibility(View.GONE);
    }

    private void showConnectionWarning() {
        imageViewNoInternetConnection.setVisibility(View.VISIBLE);
        textViewNoInternetConnection.setVisibility(View.VISIBLE);
    }

    private void hideConnectionWarning() {
        imageViewNoInternetConnection.setVisibility(View.GONE);
        textViewNoInternetConnection.setVisibility(View.GONE);
    }

    private void showNoResultsWarning() {
        textViewNoResultsFound.setVisibility(View.VISIBLE);
        imageViewNoResultsFound.setVisibility(View.VISIBLE);
    }

    private void hideNoResultsWarning() {
        textViewNoResultsFound.setVisibility(View.GONE);
        imageViewNoResultsFound.setVisibility(View.GONE);
    }

}
