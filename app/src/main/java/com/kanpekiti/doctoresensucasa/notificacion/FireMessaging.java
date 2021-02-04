package com.kanpekiti.doctoresensucasa.notificacion;

import android.app.Activity;
import android.util.Log;


import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskTknFCM;
import com.kanpekiti.doctoresensucasa.util.Const;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Map;


public class FireMessaging extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


     @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Map data = remoteMessage.getData();
        NotificacionHelper nh = new NotificacionHelper();
         try {
             nh.createNotification(getApplicationContext(),
                     URLDecoder.decode(data.get("titulo").toString(), "UTF-8"),
                     URLDecoder.decode(data.get("mensaje").toString(), "UTF-8"),
                     URLDecoder.decode(data.get("latitud").toString(), "UTF-8"),
                     URLDecoder.decode(data.get("longitud").toString(), "UTF-8"));
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }


     }




    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

       sendRegistrationToServer(token);
    }



    private void scheduleJob() {
        // [START dispatch_job]
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
        // [END dispatch_job]
    }


    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    private void sendRegistrationToServer(String token) {
        new AsynTaskTknFCM(getApplicationContext()).execute(Const.SAVE_TKN_NOTIFICA, token);
    }


    private void sendNotification(String messageBody) {

    }
}
