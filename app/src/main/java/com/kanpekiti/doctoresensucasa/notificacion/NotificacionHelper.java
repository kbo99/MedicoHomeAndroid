package com.kanpekiti.doctoresensucasa.notificacion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

public class NotificacionHelper extends ContextWrapper {

    private NotificationManager notificationManager;

    private String CHANNEL_ID ="1";
    private String CHANNEL_NAME = "VideoLLamada";


    public NotificacionHelper(Context base) {
        super(base);
        createChannnels();
    }

    private void createChannnels() {
    if(Build.VERSION.SDK_INT >= 26){
        NotificationChannel notificationChannel =
                new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        
        getManagerNotification().createNotificationChannel(notificationChannel);
    }

    }

    private NotificationManager getManagerNotification() {
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public Notification.Builder createNotification(String titulo, String mensaje){

    return null;
    }
}
