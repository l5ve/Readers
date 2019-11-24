package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class SearchData {
    @SerializedName("word")
    private String word;

    public SearchData(String word) {
        this.word = word;
    }
}
