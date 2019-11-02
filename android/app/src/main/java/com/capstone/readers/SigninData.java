package com.capstone.readers;

import com.google.gson.annotations.SerializedName;

public class SigninData {
    @SerializedName("userID")
    private String user_id;

    @SerializedName("userName")
    private String user_name;

    @SerializedName("userPwd")
    private String user_pwd;

    public SigninData(String userID, String userName, String userPwd) {
        this.user_id = userID;
        this.user_name = userName;
        this.user_pwd = userPwd;
    }
}
