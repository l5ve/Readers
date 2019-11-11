package com.capstone.readers.ToonCard;

public class ToonCard {
    private String id;
    private String thumbnail;
    private String platform;
    private String title;
    private String author;
    private String update;

    public ToonCard(String id, String title, String platform, String author, String thumbnail, String update){
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.author = author;
        this.thumbnail = thumbnail;
        this.update = update;
    }

    public String getUpdate() {
        return update;
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

    public String getId() {
        return id;
    }

    public String getThumbnail(){
        return thumbnail;
    }
}
