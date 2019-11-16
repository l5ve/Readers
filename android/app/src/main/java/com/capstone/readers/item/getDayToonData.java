package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class getDayToonData {
    @SerializedName("is_end")
    private String is_end;

    @SerializedName("toon_weekday")
    private String toon_weekday;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("order_by")
    private String order_by;

    public getDayToonData(String toon_weekday, String user_id, String order_by) {
        this.is_end = "X";
        this.toon_weekday = toon_weekday;
        this.user_id = user_id;
        this.order_by = order_by;
    }
}
