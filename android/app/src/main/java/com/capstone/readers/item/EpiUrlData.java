package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class EpiUrlData {
    @SerializedName("epi_url")
    private String url;

    public String getUrl() {
        return url;
    }
}
