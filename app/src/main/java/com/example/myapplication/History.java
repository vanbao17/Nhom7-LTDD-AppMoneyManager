package com.example.myapplication;

import java.io.Serializable;
import java.util.Date;

public class History implements Serializable {
    private int count;
    private String idHistory;
    private String note;
    private String date;
    private Boolean status;
    private String titleStatus;
    private String phone;
    private String image;
    public History() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(String idHistory) {
        this.idHistory = idHistory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTitleStatus() {
        return titleStatus;
    }

    public void setTitleStatus(String titleStatus) {
        this.titleStatus = titleStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public History(int count, String note, String date, Boolean status, String titleStatus, String phone, String idHistory) {
        this.count = count;
        this.note = note;
        this.date = date;
        this.status = status;
        this.titleStatus = titleStatus;
        this.phone = phone;
        this.idHistory = idHistory;
    }
}
