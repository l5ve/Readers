package com.capstone.readers.Recommend;

import com.google.gson.annotations.SerializedName;

public class RecommendCard {
    @SerializedName("toon_id")
    private String id;

    @SerializedName("toon_thumb_url")
    private String thumbnail;

    @SerializedName("toon_site")
    private String platform;

    @SerializedName("toon_name")
    private String title;

    @SerializedName("wrt_name")
    private String author;

    @SerializedName("toon_desc")
    private String description;

    public String getAuthor() {
        return author;
    }

    public String getPlatform() {
        return platform;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
