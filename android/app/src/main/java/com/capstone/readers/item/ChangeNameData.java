package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class ChangeNameData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_name")
    private String user_name;

    public ChangeNameData(String user_id, String user_name) {
        this.user_id = user_id;
        this.user_name = user_name;
    }
}
