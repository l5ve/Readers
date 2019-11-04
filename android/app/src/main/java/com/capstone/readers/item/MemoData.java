package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class MemoData {
    @SerializedName("user_id")
    private String user_id;

    public MemoData(String user_id) {
        this.user_id = user_id;
    }
}
