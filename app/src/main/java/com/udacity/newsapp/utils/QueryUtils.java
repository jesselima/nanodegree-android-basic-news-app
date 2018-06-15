package com.udacity.newsapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.newsapp.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * This class offers Helper methods related to requesting and receiving a list of news data from The Guardian.
 */
public final class QueryUtils {

    /** Tag for the log messages output */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the The Guardin News API data and return a list of {@link News} objects.
     * @param requestUrl is the URL request to the API.
     * @return a list of News.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Ops! Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}
        List<News> newsList = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}
        return newsList;
    }

    /**
     * Returns new URL object from the given string URL.
     * @param stringUrl is the String URl for the request
     * @return a URL object
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     * @param url is the given URL object
     * @return a json in a String data type
     * @throws IOException if there is a problem during the request throw a error at the log.
     */
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, do not make the request.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the whole JSON response from the server.
     * @param inputStream
     * @return a String with the JSON data inside it.
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding News.
        List<News> newsList = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject rootJsonObject = new JSONObject(newsJSON);

            JSONObject responseObject = rootJsonObject.getJSONObject("response");

            // Create a JSONArray and put the array of News (results) inside it.
            JSONArray resultsArray = responseObject.getJSONArray("results");

            // For each position in the newsArray (inside the JSONArray object)
            // extract the JSON data from such position in the array and put the data into a new News class object.
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single News object in the newsArray (in within the list of News)
                JSONObject currentNewsResult = resultsArray.getJSONObject(i);
                JSONObject currentNewsFields = currentNewsResult.getJSONObject("fields");
                JSONArray tagsArrayCurrentNews = currentNewsResult.getJSONArray("tags");

                String id = currentNewsResult.getString("id");

                String type = "";
                if (currentNewsResult.has("type")) {
                    type = currentNewsResult.getString("type");
                }

                String sectionName = "";
                if (currentNewsResult.has("sectionName")) {
                    sectionName = currentNewsResult.getString("sectionName");
                }

                String webPublicationDate = "";
                if (currentNewsResult.has("webPublicationDate")) {
                    webPublicationDate = currentNewsResult.getString("webPublicationDate");
                }

                String webTitle = "";
                if (currentNewsResult.has("webTitle")) {
                    webTitle = currentNewsResult.getString("webTitle");
                }

                String webURL = "";
                if (currentNewsResult.has("webURL")) {
                    webURL = currentNewsResult.getString("webURL");
                }

                String apiURL = "";
                if (currentNewsResult.has("apiURL")) {
                    apiURL = currentNewsResult.getString("apiURL");
                }

                String pillarName = "";
                if (currentNewsResult.has("pillarName")) {
                    pillarName = currentNewsResult.getString("pillarName");
                }

                // Inside each News item, I access its array called "tags".

                StringBuilder authors = new StringBuilder();
                String currentContributor = "";

                if (tagsArrayCurrentNews.length() > 0) {
                    for (int t = 0; tagsArrayCurrentNews.length() > t; t++ ){

                        JSONObject currentTagObject = tagsArrayCurrentNews.getJSONObject(t);

                        currentContributor = currentTagObject.getString("webTitle");

                        // If there is only one contributor adds a dot.
                        if (tagsArrayCurrentNews.length() == 1){
                            currentContributor += ".";
                        }else if (tagsArrayCurrentNews.length() > 1){
                            currentContributor += ", ";
                        }else{
                            currentContributor += ".";
                        }
                        authors.append(currentContributor);
                    }
                }
                String contributors = String.valueOf(authors);

                // Extract the news URL image.
                String thumbnailURL = null;
                if(currentNewsResult.has("fields")) {
                    if (currentNewsFields.has("thumbnail")) {
                        thumbnailURL = currentNewsFields.getString("thumbnail");
                    }
                }

                // Instantiate a News class object and add the JSON data as inputs parameters.
                News newsItem = new News(id, type, sectionName, webPublicationDate, webTitle, webURL, apiURL, contributors, thumbnailURL);
                newsList.add(newsItem);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        // Return the list of news
        return newsList;
    }

}
