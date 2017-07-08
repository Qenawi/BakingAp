package com.example.qenawi.bakingap.items;

import java.io.Serializable;

/**
 * Created by QEnawi on 6/15/2017.
 */

public class StepItem implements Serializable
{
 private    String shortDescription;
    private String description,videoURL,thumbnailURL;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public StepItem(String shortDescription, String description, String videoURL, String thumbnailURL)
    {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public StepItem()
    {
    }
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
