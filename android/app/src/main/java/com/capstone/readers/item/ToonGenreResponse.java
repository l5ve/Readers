package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class ToonGenreResponse {
    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("genre_name")
    private String genre_name;

    public String getGenre_name() {
        return genre_name;
    }

    public String getToon_id() {
        return toon_id;
    }
}
