package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class DetailPageResponse {
    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("toon_desc")
    private String toon_desc;

    @SerializedName("content")
    private String content;

    @SerializedName("subs_flag")
    private double subs_flag;

    @SerializedName("block_flag")
    private double block_flag;

    public String getToon_id() {
        return toon_id;
    }

    public String getToon_desc() {
        return toon_desc;
    }

    public String getContent() {
        return content;
    }

    public double getSubs_flag() {
        return subs_flag;
    }

    public double getBlock_flag() {
        return block_flag;
    }
}
