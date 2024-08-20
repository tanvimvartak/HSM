package com.example.hsm;

public class Notice {

    private String title;

    public Notice(String title, String description, String imgLink, String timestamp) {
        this.title = title;
        this.description = description;
        this.imgLink = imgLink;
        this.timestamp = timestamp;
    }

    private String description;
    private String imgLink;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }




    public Notice() {}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
