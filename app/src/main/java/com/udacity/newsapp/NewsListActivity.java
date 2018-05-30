package com.udacity.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.udacity.newsapp.adapters.NewsAdapter;
import com.udacity.newsapp.fragments.NewsFragment;
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
    private String pageSize = "20";
    private String orderBy = "newest";

    private NewsAdapter newsAdapter;
    private TextView mEmptyStateTextView, textViewNoResultsFound;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_whatshot_black_24dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
            R.drawable.ic_android_black_48dp,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        /* ViewPager and Tabs implementation. */
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartLoaderNews();
            }
        });

    }

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


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("section", sectionId);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("page", page);
        uriBuilder.appendQueryParameter("page-size", pageSize);
        uriBuilder.appendQueryParameter("show-fields", "trailText,headline,thumbnail");
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

    /* ViewPager and Tabs implementation. */
    private void setupTabIcons() {

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
        tabLayout.getTabAt(6).setIcon(tabIcons[6]);
        tabLayout.getTabAt(7).setIcon(tabIcons[7]);
        tabLayout.getTabAt(8).setIcon(tabIcons[8]);
        tabLayout.getTabAt(9).setIcon(tabIcons[9]);
        tabLayout.getTabAt(10).setIcon(tabIcons[10]);
        tabLayout.getTabAt(11).setIcon(tabIcons[11]);
    }
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "Top 50");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "WORLD");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "UK NEWS");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "SCIENCE");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "LIFESTYLE");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "CITIES");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "MUSIC");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "SPORT");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "TECH");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "BUSINESS");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "BOOKS");
        viewPagerAdapter.addFragAndTitle(new NewsFragment(), "FILM");
        viewPager.setAdapter(viewPagerAdapter);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragAndTitle(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
