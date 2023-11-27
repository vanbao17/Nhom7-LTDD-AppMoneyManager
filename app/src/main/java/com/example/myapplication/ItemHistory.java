package com.example.myapplication;

public class ItemHistory {
    private String titleStatus;
    private int count;
    public ItemHistory() {

    }

    public String getTitleStatus() {
        return titleStatus;
    }

    public void setTitleStatus(String titleStatus) {
        this.titleStatus = titleStatus;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ItemHistory(String titleStatus, int count) {
        this.titleStatus = titleStatus;
        this.count = count;
    }
}
