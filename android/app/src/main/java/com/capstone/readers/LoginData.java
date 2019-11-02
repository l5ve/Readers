package com.capstone.readers;

import com.google.gson.annotations.SerializedName;

/* 로그인 요청 시 보낼 데이터 */
public class LoginData {
    @SerializedName("userID")
    String user_id;

    @SerializedName("userPwd")
    String user_pwd;

    public LoginData(String userID, String userPwd) {
        this.user_id = userID;
        this.user_pwd = userPwd;
    }
}
