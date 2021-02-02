package com.kanpekiti.doctoresensucasa.notificacion;

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
                     URLDecoder.decode(data.values().toArray()[0].toString(), "UTF-8"),
                     URLDecoder.decode(data.values().toArray()[1].toString(), "UTF-8"));
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
        // TODO: Implement this method to send token to your app server.
        //new AsynTaskTknFCM().execute(token);
    }


    private void sendNotification(String messageBody) {

    }
}
