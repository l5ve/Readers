package com.capstone.readers.item;

import com.google.gson.annotations.SerializedName;

public class ToonIdData {
    @SerializedName("toon_id")
    private String toon_id;

    public ToonIdData(String toon_id) {
        this.toon_id = toon_id;
    }

    public String getToon_id() {
        return toon_id;
    }
}
