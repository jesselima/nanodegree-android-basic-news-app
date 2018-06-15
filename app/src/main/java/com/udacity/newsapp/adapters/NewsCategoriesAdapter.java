package com.udacity.newsapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.newsapp.R;
import com.udacity.newsapp.models.NewsCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesse on 02/06/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class NewsCategoriesAdapter extends ArrayAdapter<NewsCategory> {

    public NewsCategoriesAdapter(Context context, List<NewsCategory> categoriesList){
        super(context, 0, categoriesList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO 2
        ViewHolder viewHolder;

        // TODO 3
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_categories, parent, false);

            // TODO 4
            viewHolder = new ViewHolder();

            // TODO 5
            viewHolder.sectionName = convertView.findViewById(R.id.text_view_section_name);
            viewHolder.imageViewSection = convertView.findViewById(R.id.image_view_section);

            // TODO 6
            convertView.setTag(viewHolder);

            // TODO 7
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // TODO 8
        NewsCategory currentCategory = getItem(position);

        // TODO 9
        viewHolder.sectionName.setText(currentCategory.getSectionName());
        viewHolder.imageViewSection.setImageResource(currentCategory.getSectionImageResId());

        return convertView;
    }

    // TODO 1
    private class ViewHolder{
        private TextView sectionName;
        private ImageView imageViewSection;
    }
}




























