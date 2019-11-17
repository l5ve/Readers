package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class MemoSaveData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("content")
    private String content;

    public MemoSaveData(String user_id, String toon_id, String content) {
        this.user_id = user_id;
        this.toon_id = toon_id;
        this.content = content;
    }
}
