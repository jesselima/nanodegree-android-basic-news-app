package com.udacity.newsapp.models;


public class News {

    private String id;
    private String type;
    private String sectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webURL;
    private String contributors;

    public News(String id, String type, String sectionName, String webPublicationDate, String webTitle, String webURL, String contributors) {
        this.id = id;
        this.type = type;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webURL = webURL;
        this.contributors = contributors;
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

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getContributors() {
        return contributors;
    }

}
