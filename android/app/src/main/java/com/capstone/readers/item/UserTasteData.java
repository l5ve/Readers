package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class UserTasteData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("genre_name")
    private String genre_name;

    public UserTasteData(String user_id, String genre_name) {
        this.user_id = user_id;
        this.genre_name = genre_name;
    }
}
