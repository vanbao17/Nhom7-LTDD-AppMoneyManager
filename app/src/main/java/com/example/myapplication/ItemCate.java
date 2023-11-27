package com.example.myapplication;

import java.io.Serializable;

public class ItemCate implements Serializable {
    private String title,image;
    private boolean status;
    public ItemCate() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ItemCate(String title, String image, boolean status) {
        this.title = title;
        this.image = image;
        this.status = status;
    }
}
