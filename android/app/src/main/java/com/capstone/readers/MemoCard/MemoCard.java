package com.capstone.readers.MemoCard;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MemoCard {
    @SerializedName("toon_id")
    private String toon_id;

    @SerializedName("toon_name")
    private String title;

    @SerializedName("toon_site")
    private String platform;

    @SerializedName("wrt_name")
    private String author;

    @SerializedName("toon_thumb_url")
    private String thumbnail;

    @SerializedName("content")
    private String content;

    @SerializedName("memo_date")
    private Timestamp memo_date;


    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getPlatform() {
        return platform;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getToon_id() {
        return toon_id;
    }

    public String getTitle() {
        return title;
    }

    public String getMemo_date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(memo_date);
    }

    public MemoCard getMemoCard(){
        return this;
    }

    public MemoCard(String id, String thumbnail, String platform, String title, String author, String memo, Timestamp update){
        this.toon_id = id;
        this.thumbnail = thumbnail;
        this.platform = platform;
        this.title = title;
        this.author = author;
        this.content = memo;
        this.memo_date = update;
    }
}
