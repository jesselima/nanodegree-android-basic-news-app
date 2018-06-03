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


        final ArrayList categoriesList = new ArrayList<NewsCategory>();

            categoriesList.add(new NewsCategory("technology", "Technology", R.drawable.ic_technology));
            categoriesList.add(new NewsCategory("games", "Games", R.drawable.ic_games));
            categoriesList.add(new NewsCategory("business", "Business", R.drawable.ic_business));
            categoriesList.add(new NewsCategory("cities", "Cities", R.drawable.ic_cities));
            categoriesList.add(new NewsCategory("music", "Music", R.drawable.ic_music));
            categoriesList.add(new NewsCategory("world", "World News", R.drawable.ic_world));

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
