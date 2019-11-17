package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class MypageMemoData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("order_by")
    private String order_by;

    public MypageMemoData(String user_id, String order_by) {
        this.user_id = user_id;
        this.order_by = order_by;
    }
}
