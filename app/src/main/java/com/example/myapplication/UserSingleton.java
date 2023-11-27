package com.example.myapplication;

import java.util.ArrayList;

public class UserSingleton {
    private static UserSingleton instance;
    private UserEnity user;
    private ArrayList<History> histories;
    private UserSingleton() {
        // Private constructor để ngăn chặn việc tạo thêm instances từ bên ngoài.
    }
    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public static void setInstance(UserSingleton instance) {
        UserSingleton.instance = instance;
    }

    public UserEnity getUser() {
        return user;
    }

    public void setUser(UserEnity user) {
        this.user = user;
    }

    public ArrayList<History> getHistories() {
        return histories;
    }

    public void setHistories(ArrayList<History> histories) {
        this.histories = histories;
    }
}
