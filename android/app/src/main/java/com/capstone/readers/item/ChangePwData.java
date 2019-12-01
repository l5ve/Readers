package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class ChangePwData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_pwd")
    private String user_pw;

    public ChangePwData(String user_id, String user_pw) {
        this.user_id = user_id;
        this.user_pw = user_pw;
    }
}
