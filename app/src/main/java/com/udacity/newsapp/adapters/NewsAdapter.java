package com.udacity.newsapp.adapters;

import android.widget.ArrayAdapter;

/**
 * Created by jesse on 28/05/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.newsapp.R;
import com.udacity.newsapp.models.News;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each news item
 * in the data source (a list of {@link News} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * @param position is the position of each news item object in the list.
     * @param convertView is the View object the receives the inflated layout.
     * @param parent is the ViewGroup object used by the Inflater.
     * @return a listItemView object represents the inflated layout filled with
     * data for each item in the list on the UI
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_news, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.webTitle = convertView.findViewById(R.id.text_view_web_title);
            viewHolder.webPublicationDate = convertView.findViewById(R.id.text_view_publication_date);
            viewHolder.sectionName = convertView.findViewById(R.id.text_view_section_name);
            viewHolder.contributors = convertView.findViewById(R.id.text_view_contributor);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        News currentNews = getItem(position);

        viewHolder.webTitle.setText(currentNews.getWebTitle());
        viewHolder.contributors.setText(currentNews.getContributors());
        viewHolder.sectionName.setText(currentNews.getSectionName());
        viewHolder.webPublicationDate.setText(newsSimpleDateFormat(currentNews.getWebPublicationDate()));

        // Return the list item view that is now showing the appropriate data
        return convertView;
    }

    private class ViewHolder{
        private TextView webTitle;
        private TextView webPublicationDate;
        private TextView sectionName;
        private TextView contributors;
    }


    /**
     *
     * @param dateString is the date in a string format is this pattern yyyy-MM-dd'T'HH:mm:ss
     * @return the date as a string with this new format (HH:mm LLL dd, yyyy) using the formatDate method.
     */
    private String newsSimpleDateFormat(String dateString){
        String dateStringFormated = null;
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            date = dateFormat.parse(dateString);
            dateStringFormated = formatDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStringFormated;
    }

    /**
     * This method receives the date (data type Date) as input parameter.
     * @param dateObject is the Date object to be formated.
     * @return a String object with the date formated according to the SimpleDateFormat method pattern: HH:mm LLL dd, yyyy.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

}