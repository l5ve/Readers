package com.capstone.readers.MypageBookmark;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BookmarkCard {
    @SerializedName("toon_id")
    private String tood_id;

    @SerializedName("epi_thumb_url")
    private String thumbnail;

    @SerializedName("toon_site")
    private String platform;

    @SerializedName("toon_name")
    private String title;

    @SerializedName("epi_name")
    private String epi_title;

    @SerializedName("wrt_name")
    private String author;

    @SerializedName("epi_date")
    private Timestamp epi_date;

    @SerializedName("epi_url")
    private String epi_url;


    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPlatform() {
        return platform;
    }

    public String getAuthor() {
        return author;
    }

    public String getEpi_title() {
        return epi_title;
    }

    public String getEpi_url() {
        return epi_url;
    }

    public String getTood_id() {
        return tood_id;
    }

    public String getEpi_date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(epi_date);
    }
}
