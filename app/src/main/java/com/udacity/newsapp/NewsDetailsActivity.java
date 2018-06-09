package com.udacity.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.udacity.newsapp.privatedata.MyApiKey;

public class NewsDetailsActivity extends AppCompatActivity {

    String baseUrl;

    // TODO: Implement news details layout and Loaders. The layout must to all important content of the News item. Contributors names and pictures too.
    // TODO: This activity must have the images with actions Share, se on the web and save.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        baseUrl = MyApiKey.getBaseUrlNewsDetails();


        if( getIntent().getExtras() != null){
            Bundle newsData = getIntent().getExtras();
            String id = newsData.getString("id");
            Log.v("Clicked News id: ", id);
        }

    }

    private void openWebPage(String url){
        Uri uriWebPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriWebPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
