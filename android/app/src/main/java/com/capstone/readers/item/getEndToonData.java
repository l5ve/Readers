package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class getEndToonData {
    @SerializedName("is_end")
    private String is_end;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("order_by")
    private String order_by;

    public getEndToonData(String user_id, String order_by) {
        this.is_end = "O";
        this.user_id = user_id;
        this.order_by = order_by;
    }
}
