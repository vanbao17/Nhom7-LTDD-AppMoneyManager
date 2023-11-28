package com.example.myapplication;

public class ThongKeEntity {
    private String image;
    private String title;
    private String percent;
    private int dem;

    public ThongKeEntity() {

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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public int getDem() {
        return dem;
    }

    public void setDem(int dem) {
        this.dem = dem;
    }

    public ThongKeEntity(String image, String title, String percent, int dem) {
        this.image = image;
        this.title = title;
        this.percent = percent;
        this.dem = dem;
    }
}
