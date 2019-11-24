package com.capstone.readers.item;

import com.capstone.readers.Toon.ToonCard;
import com.google.gson.annotations.SerializedName;

public class ToonResponse {
    @SerializedName("toon_id")
    private String id;

    @SerializedName("toon_name")
    private String title;

    @SerializedName("toon_site")
    private String platform;

    @SerializedName("wrt_name")
    private String author;

    @SerializedName("toon_thumb_url")
    private String thumbnail;

    @SerializedName("last_date")
    private String update;

    public String getId() {
        return id;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUpdate() {
        return update;
    }

    public ToonCard getToonCard() {
        ToonCard t = new ToonCard(id, title, platform, author, thumbnail, update);

        return t;
    }
}
