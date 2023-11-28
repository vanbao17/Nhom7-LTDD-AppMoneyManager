package com.example.myapplication;

public class ThuEntity {
    private String image;
    private String title;
    private boolean status;
    public ThuEntity() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ThuEntity(String image, String title, boolean status) {
        this.image = image;
        this.title = title;
        this.status = status;
    }
}
