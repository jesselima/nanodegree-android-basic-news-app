package com.udacity.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.udacity.newsapp.privatedata.MyApiKey;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);


        if( getIntent().getExtras() != null){
            Bundle newsData = getIntent().getExtras();
            String id = newsData.getString("id");
        }

    }
}
