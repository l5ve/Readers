package com.capstone.readers.Block;

import com.google.gson.annotations.SerializedName;

public class BlockCard {
    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("toon_thumb_url")
    private String thumbnail;

    @SerializedName("toon_site")
    private String platform;

    @SerializedName("toon_name")
    private String title;

    @SerializedName("wrt_name")
    private String author;

    public BlockCard(String toon_id, String title, String platform, String author, String thumbnail) {
        this.toon_id = toon_id;
        this.title = title;
        this.platform = platform;
        this.author = author;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getPlatform() {
        return platform;
    }

    public String getAuthor() {
        return author;
    }

    public String getToon_id() {
        return toon_id;
    }

    public String getThumbnail(){
        return thumbnail;
    }
}
