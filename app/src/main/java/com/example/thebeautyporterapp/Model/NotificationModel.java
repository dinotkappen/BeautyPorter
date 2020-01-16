package com.example.thebeautyporterapp.Model;

public class NotificationModel {

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    String notificationTitle,notificationDescription,notificationID;

    public NotificationModel() {
    }
    public NotificationModel(String notificationID, String notificationTitle, String notificationDescription) {

        this.notificationTitle = notificationTitle;
        this.notificationDescription = notificationDescription;
        this.notificationID = notificationID;
    }
}
