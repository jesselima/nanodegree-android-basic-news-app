package com.udacity.newsapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.udacity.newsapp.adapters.PageAdapter;
import com.udacity.newsapp.fragments.NewsFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_main);
/*
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        PageAdapter pageAdapter = new PageAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(pageAdapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
*/

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

    }

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
