package com.udacity.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.udacity.newsapp.adapters.NewsCategoriesAdapter;
import com.udacity.newsapp.fragments.CategoriesFragment;
import com.udacity.newsapp.models.NewsCategory;

import java.util.ArrayList;


public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        final ArrayList categories = new ArrayList<NewsCategory>();

        categories.add(new NewsCategory("technology", "Technology", R.drawable.ic_technology));
        categories.add(new NewsCategory("games", "Games", R.drawable.ic_games));


        NewsCategoriesAdapter newsCategoriesAdapter = new NewsCategoriesAdapter(this, categories);

        ListView listView = findViewById(R.id.list_view_categories);

        listView.setAdapter(newsCategoriesAdapter);



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
////                NewsCategory category = categories.get(position);
////                Log.v("Clicked category: ", category.toString());
//
//            }
//        });

    }
}
