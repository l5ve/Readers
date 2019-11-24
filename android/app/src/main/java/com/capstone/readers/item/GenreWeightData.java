package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class GenreWeightData {
    @SerializedName("genre_name")
    private String genre_name;

    @SerializedName("rec_num")
    private float weight;

    public String getGenre_name() {
        return genre_name;
    }

    public float getWeight() {
        return weight;
    }
}
