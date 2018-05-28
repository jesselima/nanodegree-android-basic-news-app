package com.udacity.newsapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.newsapp.R;
import com.udacity.newsapp.fragments.NewsFragment;


/**
 * Created by jesse on 22/04/18.
 * This is a part of the project TourGuide.
 */
public class PageAdapter extends FragmentPagerAdapter{

    /** Context of the app */
    private Context mContext;

    private static final Integer PAGE_NUMBER = 6;

    /**
     * Create a new {@link PageAdapter} object.
     *
     * @param context is the context of the app
     * @param fragmentManager is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public PageAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewsFragment();
        }
        else if (position == 1){
            return new NewsFragment();
        }
        else if (position == 2){
            return new NewsFragment();
        }
        else if (position == 3){
            return new NewsFragment();
        }
        else if (position == 4){
            return new NewsFragment();
        }
        else if (position == 5){
            return new NewsFragment();
        }
        else {
            return new NewsFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.tab_tile_top_news);
        } else if (position == 1) {
            return mContext.getString(R.string.tab_tile_world_news);
        } else if (position == 2) {
            return mContext.getString(R.string.tab_tile_uk_news);
        } else if (position == 3) {
            return mContext.getString(R.string.tab_tile_science_news);
        } else if (position == 4) {
            return mContext.getString(R.string.tab_tile_cities_news);
        } else if (position == 5) {
            return mContext.getString(R.string.tab_tile_global_development_news);
        } else {
            return mContext.getString(R.string.tab_tile_football_news);
        }
    }
}
