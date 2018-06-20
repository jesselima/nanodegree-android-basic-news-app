package com.udacity.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.newsapp.R;
import com.udacity.newsapp.models.NewsCategory;

import java.util.List;


public class NewsCategoriesAdapter extends ArrayAdapter<NewsCategory> {

    public NewsCategoriesAdapter(Context context, List<NewsCategory> categoriesList) {
        super(context, 0, categoriesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_categories, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.sectionName = convertView.findViewById(R.id.text_view_section_name);
            viewHolder.imageViewSection = convertView.findViewById(R.id.image_view_section);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsCategory currentCategory = getItem(position);

        viewHolder.sectionName.setText(currentCategory.getSectionName());
        viewHolder.imageViewSection.setImageResource(currentCategory.getSectionImageResId());

        return convertView;
    }

    private class ViewHolder {
        private TextView sectionName;
        private ImageView imageViewSection;
    }
}




























