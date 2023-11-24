package com.example.myapplication;

import java.util.Date;

public class History {
    private int count;
    private String note;
    private Date date;
    private Boolean status;
    private String titleStatus;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public History(int count, String note, Date date, Boolean status, String titleStatus) {
        this.count = count;
        this.note = note;
        this.date = date;
        this.status = status;
        this.titleStatus = titleStatus;
    }
}
