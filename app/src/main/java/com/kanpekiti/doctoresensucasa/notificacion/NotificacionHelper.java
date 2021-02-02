package com.kanpekiti.doctoresensucasa.notificacion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kanpekiti.doctoresensucasa.LlamadaVozActivity;
import com.kanpekiti.doctoresensucasa.R;
import com.kanpekiti.doctoresensucasa.VideoCallActivity;
import com.kanpekiti.doctoresensucasa.util.Const;

import java.util.Random;

public class NotificacionHelper {



    private Context context;

    private NotificationManagerCompat notificationManagerCompat;



    public NotificacionHelper() {

    }




    public void createNotification(Context context, String titulo, String mensaje){
        this.context = context;
     if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
         crrateNotificactionWithChannel(titulo,mensaje, titulo.equals(Const.TITULO_VI) ?
                 Const.CHANNEL_ID_VC : Const.CHANNEL_ID_LL);
     }else {
         crrateNotificactionNohannel(titulo,mensaje);
     }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void crrateNotificactionWithChannel(String titulo, String mensaje,String channelId) {
        try {
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.btn_startcall_normal);
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, channelId).setContentTitle(titulo).
                            setContentText(mensaje).setSmallIcon(R.drawable.favicon1)
                            .setLargeIcon(icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(getPendingIntent(channelId));
            notificationManagerCompat = getManager();
            Random random = new Random();
            int m = random.nextInt(9999 - 1000) + 1000;
            notificationManagerCompat.notify(m,builder.build());
        }catch (Exception e){
            e.getMessage();
        }

    }


    private void crrateNotificactionNohannel(String titulo, String mensaje) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context).setContentTitle(titulo).setContentText(mensaje).
                        setContentText(mensaje).setSmallIcon(R.drawable.favicon1);
        notificationManagerCompat = getManager();
        int i = 0;
        notificationManagerCompat.notify(i++,builder.build());


    }

    private NotificationManagerCompat getManager(){
        if(notificationManagerCompat == null){
            notificationManagerCompat = NotificationManagerCompat.from(context);
        }

        return notificationManagerCompat;
    }

    private PendingIntent getPendingIntent(String channel){
        int num = (int) System.currentTimeMillis();
        Intent intent = new Intent(context, channel.endsWith(Const.CHANNEL_ID_VC) ?
                VideoCallActivity.class : LlamadaVozActivity.class);
        intent.putExtra(Const.DOCTOR_PARAM, Const.DOCTOR_PARAM_NAME);
        return PendingIntent.getActivity(context, num, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
