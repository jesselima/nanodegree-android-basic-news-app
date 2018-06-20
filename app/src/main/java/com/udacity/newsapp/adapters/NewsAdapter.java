package com.udacity.newsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.newsapp.R;
import com.udacity.newsapp.models.News;
import com.udacity.newsapp.utils.DateUtils;

import java.util.List;
import java.util.Objects;

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each news item
 * in the data source (a list of {@link News} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context  of the app
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    is the position of each news item object in the list.
     * @param convertView is the View object the receives the inflated layout.
     * @param parent      is the ViewGroup object used by the Inflater.
     * @return a listItemView object represents the inflated layout filled with
     * data for each item in the list on the UI
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_news, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.webTitle = convertView.findViewById(R.id.text_view_web_title);
            viewHolder.webPublicationDate = convertView.findViewById(R.id.text_view_publication_date);
            viewHolder.sectionName = convertView.findViewById(R.id.text_view_section_name);
            viewHolder.contributors = convertView.findViewById(R.id.text_view_contributor);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        News currentNews = getItem(position);

        viewHolder.webTitle.setText(Objects.requireNonNull(currentNews).getWebTitle());
        viewHolder.contributors.setText(currentNews.getContributors());
        viewHolder.sectionName.setText(currentNews.getSectionName());
        viewHolder.webPublicationDate.setText(DateUtils.newsSimpleDateFormat(currentNews.getWebPublicationDate()));

        // Return the list item view that is now showing the appropriate data
        return convertView;
    }

    private class ViewHolder {
        private TextView webTitle;
        private TextView webPublicationDate;
        private TextView sectionName;
        private TextView contributors;
    }

}