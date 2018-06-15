package com.udacity.newsapp.models;

/**
 * Created by jesse on 02/06/18.
 * This is a part of the project nanodegree-android-basic-news-app.
 */
public class NewsCategory {

    private String sectionId;
    private String sectionName;
    private int sectionImageResId;

    /* Full constructor */
    public NewsCategory(String sectionId, String sectionName, int sectionImageResId) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.sectionImageResId = sectionImageResId;
    }

    /* Getters and Setters */
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionImageResId() {
        return sectionImageResId;
    }

    public void setSectionImageResId(int sectionImageResId) {
        this.sectionImageResId = sectionImageResId;
    }
}
