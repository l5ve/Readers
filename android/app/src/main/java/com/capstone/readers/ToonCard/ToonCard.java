package com.capstone.readers.ToonCard;

public class ToonCard {
    public String id;
    public String thumbnail;
    public String platform;
    public String title;
    public String author;
    public String update;

    public ToonCard(String id, String title, String platform, String author, String thumbnail, String update){
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.author = author;
        this.thumbnail = thumbnail;
        this.update = update;
    }

    public String getThumbnail(){
        return thumbnail;
    }
}
