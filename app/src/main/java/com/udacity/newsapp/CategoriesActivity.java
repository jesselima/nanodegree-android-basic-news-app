package com.udacity.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.udacity.newsapp.adapters.NewsCategoriesAdapter;
import com.udacity.newsapp.models.NewsCategory;

import java.util.ArrayList;


public class CategoriesActivity extends AppCompatActivity {

    private NewsCategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        final ArrayList<NewsCategory> categoriesList = new ArrayList<NewsCategory>();

            categoriesList.add(new NewsCategory("technology", "Technology", R.drawable.ic_technology));
            categoriesList.add(new NewsCategory("games", "Games", R.drawable.ic_games));
            categoriesList.add(new NewsCategory("business", "Business", R.drawable.ic_business));
            categoriesList.add(new NewsCategory("cities", "Cities", R.drawable.ic_cities));
            categoriesList.add(new NewsCategory("music", "Music", R.drawable.ic_music));
            categoriesList.add(new NewsCategory("news", "News", R.drawable.ic_news));
            categoriesList.add(new NewsCategory("world-news", "World News", R.drawable.ic_world));
            categoriesList.add(new NewsCategory("us-news", "US News", R.drawable.ic_us_news));
            categoriesList.add(new NewsCategory("uk-news", "UK News", R.drawable.ic_uk_news));
            categoriesList.add(new NewsCategory("australia-news", "Australia News", R.drawable.ic_aus_news));
            categoriesList.add(new NewsCategory("sport", "Sport", R.drawable.ic_sport));
            categoriesList.add(new NewsCategory("media", "Media", R.drawable.ic_media));
            categoriesList.add(new NewsCategory("books", "Books", R.drawable.ic_books));
            categoriesList.add(new NewsCategory("politics", "Politics", R.drawable.ic_politics));
            categoriesList.add(new NewsCategory("culture", "Culture", R.drawable.ic_culture));
            categoriesList.add(new NewsCategory("society", "Society", R.drawable.ic_society));
            categoriesList.add(new NewsCategory("football", "Football", R.drawable.ic_football));
            categoriesList.add(new NewsCategory("tv-and-radio", "Television & radio", R.drawable.ic_radio_and_television));
            categoriesList.add(new NewsCategory("environment", "Environment", R.drawable.ic_environment));
            categoriesList.add(new NewsCategory("education", "Education", R.drawable.ic_education));
            categoriesList.add(new NewsCategory("commentisfree", "Opinion", R.drawable.ic_opinion));
            categoriesList.add(new NewsCategory("fashion", "Fashion", R.drawable.ic_fashion));
            categoriesList.add(new NewsCategory("film", "Film", R.drawable.ic_film));
            categoriesList.add(new NewsCategory("global-development", "Global development", R.drawable.ic_global_development));
            categoriesList.add(new NewsCategory("why-we-travel", "Why we travel", R.drawable.ic_why_we_travel));
            categoriesList.add(new NewsCategory("science", "Science", R.drawable.ic_science));
            categoriesList.add(new NewsCategory("lifeandstyle", "Life and style", R.drawable.ic_life_and_style));

        categoriesAdapter = new NewsCategoriesAdapter(this, categoriesList);
        ListView listViewCategories = findViewById(R.id.list_view_categories);
        listViewCategories.setAdapter(categoriesAdapter);


        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsCategory newsCategoryItem = categoriesAdapter.getItem(position);
                    String sectionID = newsCategoryItem.getSectionId();
                    String sectionName = newsCategoryItem.getSectionName();

                Intent intent = new Intent(getApplicationContext(), NewsListActivity.class);
                    intent.putExtra("sectionId", sectionID);
                    intent.putExtra("sectionName", sectionName);
                    String searchType = "category";
                    intent.putExtra("searchType", searchType);
                startActivity(intent);

                // Testing
                Log.v("sectionID/Name: ", sectionID + "/" + sectionName);
            }
        });

    }
}
