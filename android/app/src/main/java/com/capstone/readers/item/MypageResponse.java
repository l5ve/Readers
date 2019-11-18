package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class MypageResponse {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("bookmark_num")
    private int bookmark_num;

    @SerializedName("subs_num")
    private int subs_num;

    @SerializedName("memo_num")
    private int memo_num;

    public String getUser_id() {
        return user_id;
    }

    public int getBookmark_num() {
        return bookmark_num;
    }

    public int getMemo_num() {
        return memo_num;
    }

    public int getSubs_num() {
        return subs_num;
    }
}
