package com.example.cleanxyandroid;

public class notification_data {
    String notification;
    String time;

    public notification_data(String notification, String time) {
        this.notification = notification;
        this.time = time;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
