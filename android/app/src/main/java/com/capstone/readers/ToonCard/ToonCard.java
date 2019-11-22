package com.capstone.readers.ToonCard;

import com.google.gson.annotations.SerializedName;

public class ToonCard {
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

    @SerializedName("last_date")
    private String update;

    public ToonCard(String id, String title, String platform, String author, String thumbnail, String update){
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.author = author;
        this.thumbnail = thumbnail;
        this.update = update;
    }

    public String getUpdate() {
        return update;
    }

    public String getTitle() {
        return title;
    }

    public String getPlatform() {
        return platform;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public String getThumbnail(){
        return thumbnail;
    }
}
