package com.capstone.readers.EpisodeCard;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class EpisodeCard {
    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("curr_epi")
    private String epi_title;

    @SerializedName("epi_url")
    private String epi_url;

    @SerializedName("epi_thumb_url")
    private String epi_thumbnail;

    @SerializedName("epi_date")
    private Timestamp epi_date;

    @SerializedName("bm_flag")
    private double isBookmarked;


    public String getEpi_date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(epi_date);
    }

    public String getEpi_url() {
        return epi_url;
    }

    public String getEpi_title() {
        return epi_title;
    }

    public String getToon_id() {
        return toon_id;
    }

    public double getIsBookmarked() {
        return isBookmarked;
    }

    public String getEpi_thumbnail() {
        return epi_thumbnail;
    }

    public void setIsBookmarked(double value) {
        this.isBookmarked = value;
    }
}
