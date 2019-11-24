package com.capstone.readers.Search;

import com.google.gson.annotations.SerializedName;

public class SearchCard {
    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("toon_thumb_url")
    private String thumbnail;

    @SerializedName("toon_site")
    private String platform;

    @SerializedName("toon_name")
    private String title;

    @SerializedName("wrt_name")
    private String author;

    public String getTitle() {
        return title;
    }

    public String getPlatform() {
        return platform;
    }

    public String getAuthor() {
        return author;
    }

    public String getToon_id() {
        return toon_id;
    }

    public String getThumbnail(){
        return thumbnail;
    }
}
