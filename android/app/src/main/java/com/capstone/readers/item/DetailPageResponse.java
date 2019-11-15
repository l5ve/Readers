package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DetailPageResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("toon_thumb_url")
    private String thumbnail;

    @SerializedName("toon_site")
    private String platform;

    @SerializedName("toon_name")
    private String title;

    @SerializedName("wrt_name")
    private String author;

    @SerializedName("toon_desc")
    private String desc;

    public int getCode() {
        return code;
    }

    public String getThumbnail() {
        return thumbnail;
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

    public String getDesc() {
        return desc;
    }
}
