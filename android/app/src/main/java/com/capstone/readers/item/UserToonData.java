package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class UserToonData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("toon_id")
    private String toon_id;

    public UserToonData(String user_id, String toon_id) {
        this.user_id = user_id;
        this.toon_id = toon_id;
    }
}
