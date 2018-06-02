package com.udacity.newsapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.newsapp.R;
import com.udacity.newsapp.models.NewsCategory;

import java.util.ArrayList;

/**
 * Created by jesse on 02/06/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class NewsCategoriesAdapter extends ArrayAdapter<NewsCategory> {

    public NewsCategoriesAdapter(Activity context, ArrayList<NewsCategory> newsCategories){
        super(context, 0, newsCategories);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_categories, parent, false);

            NewsCategory currentCategory = getItem(position);

            TextView sectionName = listItemView.findViewById(R.id.text_view_section_name);
            assert currentCategory != null;
            sectionName.setText(currentCategory.getSectionName());

            ImageView imageView = listItemView.findViewById(R.id.image_view_section);
            imageView.setImageResource(currentCategory.getSectionImageResId());

        }

        return listItemView;
    }

}
