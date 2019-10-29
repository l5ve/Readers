package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("user_name")
    private String user_name;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_pwd")
    private String user_pwd;

    public JoinData(String user_name, String user_id, String user_pwd) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_pwd = user_pwd;
    }
}
