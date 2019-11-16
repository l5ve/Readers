package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class getGenreToonData {
    @SerializedName("genre_name")
    private String genre_name;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("order_by")
    private String order_by;

    public getGenreToonData(String genre_name, String user_id, String order_by) {
        this.genre_name = genre_name;
        this.user_id = user_id;
        this.order_by = order_by;
    }
}
