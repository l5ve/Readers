package com.capstone.readers.EpisodeCard;

public class EpisodeCard {
    private String id;
    private String thumbnail;
    private String title;
    private String update;

    public EpisodeCard(String id, String thumbnail, String title, String update) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.update = update;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdate() {
        return update;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
