package com.udacity.newsapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jesse on 30/05/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter{


    /** Context of the app */
    private Context mContext;

    private static final Integer PAGE_NUMBER = 3;

    /**
     * Create a new {@link MainFragmentPagerAdapter} object.
     *
     * @param context is the context of the app
     * @param fragmentManager is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public MainFragmentPagerAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewsFragment();
        } else if (position == 1){
            return new CategoriesFragment();
        } else {
            return new SearchFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (position == 0){
//            return mContext.getString(R.string.tab_tile_startups);
//        } else if (position == 1){
//            return  mContext.getString(R.string.tab_tile_eating);
//        } else if (position == 2){
//            return mContext.getString(R.string.tab_tile_sports);
//        } else {
//            return mContext.getString(R.string.tab_tile_tour);
//        }
//    }

}
