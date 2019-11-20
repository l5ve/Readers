package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class UserToonEpiData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("epi_name")
    private String epi_title;

    public UserToonEpiData(String user_id, String toon_id, String epi_title) {
        this.user_id = user_id;
        this.toon_id = toon_id;
        this.epi_title = epi_title;
    }
}
