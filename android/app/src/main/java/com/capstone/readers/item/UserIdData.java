package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class UserIdData {
    @SerializedName("user_id")
    private String user_id;

    public UserIdData(String user_id) {
        this.user_id = user_id;
    }
}
