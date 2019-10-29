package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("user_id")
    String user_id;

    @SerializedName("user_pwd")
    String user_pwd;

    public LoginData(String user_id, String user_pwd) {
        this.user_id = user_id;
        this.user_pwd = user_pwd;
    }
}
