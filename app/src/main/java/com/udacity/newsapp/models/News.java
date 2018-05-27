package com.udacity.newsapp.models;

/**
 * Created by jesse on 23/05/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class News {

    private String id;
    private String type;
    private String sectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webURL;
    private String apiURL;
    private String pillarName;

    public News() {
    }

    public News(String id, String type, String sectionName, String webPublicationDate, String webTitle, String webURL,String apiURL, String pillarName) {
        this.id = id;
        this.type = type;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webURL = webURL;
        this.webURL = apiURL;
        this.pillarName = pillarName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }
}
