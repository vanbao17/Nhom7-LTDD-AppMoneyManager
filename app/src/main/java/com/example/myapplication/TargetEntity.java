package com.example.myapplication;

import java.io.Serializable;
import java.util.Date;

public class TargetEntity implements Serializable {
    private String title;
    private long monney;
    private String dateBegin;
    private String dateFinish;
    public TargetEntity() {
        // Phải có constructor mặc định không tham số để Firebase có thể tạo đối tượng từ dữ liệu.
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMonney() {
        return monney;
    }

    public void setMonney(int monney) {
        this.monney = monney;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(String dateFinish) {
        this.dateFinish = dateFinish;
    }

    public TargetEntity(String title, long monney, String dateBegin, String dateFinish) {
        this.title = title;
        this.monney = monney;
        this.dateBegin = dateBegin;
        this.dateFinish = dateFinish;
    }
}
