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
        viewHolder.sectionName.setText(currentNews.getSectionName());
        viewHolder.contributors.setText(currentNews.getContributors());


        String dateString = currentNews.getWebPublicationDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date;
        try {
            date = dateFormat.parse(dateString);
            viewHolder.webPublicationDate.setText(String.valueOf(formatDate(date)));
        } catch (ParseException e) {
            viewHolder.webPublicationDate.setText(R.string.unknown_date);
            e.printStackTrace();
        }

        // Return the list item view that is now showing the appropriate data
        return convertView;
    }


    public class ViewHolder{
        private TextView webTitle;
        private TextView webPublicationDate;
        private TextView sectionName;
        private TextView contributors;
    }






    /**
     * This method receives the date (data type Date) as input parameter and
     * @param dateObject is the date to be formatted.
     * @return a string with the date formatted according to the SimpleDateFormat method pattern.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }


}