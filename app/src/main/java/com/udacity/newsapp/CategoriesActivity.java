package com.udacity.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        final ArrayList<NewsCategory> categoriesList = new ArrayList<>();

        /* Icons image from Icons https://www.freepik.com and https://www.kisspng.com */

        categoriesList.add(new NewsCategory(getString(R.string.technology_section_id), getString(R.string.technology_section_name), R.drawable.ic_technology));
        categoriesList.add(new NewsCategory(getString(R.string.games_section_id), getString(R.string.games_section_name), R.drawable.ic_games));
        categoriesList.add(new NewsCategory(getString(R.string.business_section_id), getString(R.string.business_section_name), R.drawable.ic_business));
        categoriesList.add(new NewsCategory(getString(R.string.cities_section_id), getString(R.string.cities_section_name), R.drawable.ic_cities));
        categoriesList.add(new NewsCategory(getString(R.string.music_section_id), getString(R.string.music_section_name), R.drawable.ic_music));
        categoriesList.add(new NewsCategory(getString(R.string.news_section_id), getString(R.string.news_section_name), R.drawable.ic_news));
        categoriesList.add(new NewsCategory(getString(R.string.world_news_section_id), getString(R.string.world_news_section_name), R.drawable.ic_world));
        categoriesList.add(new NewsCategory(getString(R.string.us_news_section_id), getString(R.string.us_news_section_name), R.drawable.ic_us_news));
        categoriesList.add(new NewsCategory(getString(R.string.uk_news_section_id), getString(R.string.uk_news_section_name), R.drawable.ic_uk_news));
        categoriesList.add(new NewsCategory(getString(R.string.australia_news_section_id), getString(R.string.australia_news_section_name), R.drawable.ic_aus_news));
        categoriesList.add(new NewsCategory(getString(R.string.sport_section_id), getString(R.string.sport_section_name), R.drawable.ic_sport));
        categoriesList.add(new NewsCategory(getString(R.string.media_section_id), getString(R.string.media_section_name), R.drawable.ic_media));
        categoriesList.add(new NewsCategory(getString(R.string.books_section_id), getString(R.string.books_section_name), R.drawable.ic_books));
        categoriesList.add(new NewsCategory(getString(R.string.politics_section_id), getString(R.string.politics_section_name), R.drawable.ic_politics));
        categoriesList.add(new NewsCategory(getString(R.string.culture_section_id), getString(R.string.culture_section_name), R.drawable.ic_culture));
        categoriesList.add(new NewsCategory(getString(R.string.society_section_id), getString(R.string.society_section_name), R.drawable.ic_society));
        categoriesList.add(new NewsCategory(getString(R.string.football_section_id), getString(R.string.football_section_name), R.drawable.ic_football));
        categoriesList.add(new NewsCategory(getString(R.string.tv_and_radio_section_id), getString(R.string.tv_and_radio_section_name), R.drawable.ic_radio_and_television));
        categoriesList.add(new NewsCategory(getString(R.string.environment_section_id), getString(R.string.environment_section_name), R.drawable.ic_environment));
        categoriesList.add(new NewsCategory(getString(R.string.education_section_id), getString(R.string.education_section_name), R.drawable.ic_education));
        categoriesList.add(new NewsCategory(getString(R.string.opinion_section_id), getString(R.string.opinion_section_name), R.drawable.ic_opinion));
        categoriesList.add(new NewsCategory(getString(R.string.fashion_section_id), getString(R.string.fashion_section_name), R.drawable.ic_fashion));
        categoriesList.add(new NewsCategory(getString(R.string.film_section_id), getString(R.string.film_section_name), R.drawable.ic_film));
        categoriesList.add(new NewsCategory(getString(R.string.global_development_section_id), getString(R.string.global_development_section_name), R.drawable.ic_global_development));
        categoriesList.add(new NewsCategory(getString(R.string.why_we_travel_section_id), getString(R.string.why_we_travel_section_name), R.drawable.ic_why_we_travel));
        categoriesList.add(new NewsCategory(getString(R.string.science_section_id), getString(R.string.science_section_name), R.drawable.ic_science));
        categoriesList.add(new NewsCategory(getString(R.string.life_and_style_section_id), getString(R.string.life_and_style_section_name), R.drawable.ic_life_and_style));

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
            }
        });

    }
}
