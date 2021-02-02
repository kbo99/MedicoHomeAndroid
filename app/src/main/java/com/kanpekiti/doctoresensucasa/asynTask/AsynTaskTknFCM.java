package com.kanpekiti.doctoresensucasa.asynTask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.kanpekiti.doctoresensucasa.MainActivity;
import com.kanpekiti.doctoresensucasa.api.ChannelApi;
import com.kanpekiti.doctoresensucasa.api.ChannelService;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.api.LoginApi;
import com.kanpekiti.doctoresensucasa.api.LoginService;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.util.Const;
import com.kanpekiti.doctoresensucasa.vo.NotificacionFcm;
import com.kanpekiti.doctoresensucasa.vo.TokenUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsynTaskTknFCM extends AsyncTask<String, String, String[]> {

    private ChannelService channelService;

    private DoctorService doctorService;

    private Call<NotificacionFcm> tkn;

    private Call<List<NotificacionFcm>> lstNotifica;

    private Activity activity;

    public AsynTaskTknFCM(Activity activity){
        this.activity = activity;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        DoctorDB dataBase = new DoctorDB(activity, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);

        doctorService = DoctorApi.doctorApi(activity).create(DoctorService.class);
        NotificacionFcm notificacionFcm = new NotificacionFcm();
        notificacionFcm.setUsuUsuario(UserLogged.consultarUsuario(dataBase).getUsername());

        if (strings[0].equals(Const.SAVE_TKN_NOTIFICA)) {
            notificacionFcm.setNfcDispositivo("A");
            notificacionFcm.setNfcEnllamada(Const.STRING_F);
            notificacionFcm.setNfcTknFcm(strings[1]);
            notificacionFcm.setNfcDoctor(Const.STRING_F);
            List<Grupos> lstGrupo = Grupos.consultarGrupo(dataBase);
            for (Grupos grp : lstGrupo) {
                if (grp.getGprNombre().equals(Const.ROLE_DOCTOR)) {
                    notificacionFcm.setNfcDoctor(Const.STRING_V);
                    break;
                }
            }
            tkn = doctorService.saveToken(notificacionFcm);
            tkn.enqueue(new Callback<NotificacionFcm>() {
                @Override
                public void onResponse(Call<NotificacionFcm> call, Response<NotificacionFcm> response) {
                    NotificacionFcm responseToSer = response.body();
                    //  Log.d("This", responseToSer);
                }

                @Override
                public void onFailure(Call<NotificacionFcm> call, Throwable t) {
                    TokenUser tkn = new TokenUser();
                    tkn = null;

                }

            });
        } else if(strings[0].equals(Const.NOTIFICA_DOCTOR)){
                notificacionFcm.setTitulo(strings[1]);
                notificacionFcm.setMensaje(strings[2]);
            lstNotifica = doctorService.generaNotificaLlamada(notificacionFcm);
            lstNotifica.enqueue(new Callback<List<NotificacionFcm>>() {
                @Override
                public void onResponse(Call<List<NotificacionFcm>> call, Response<List<NotificacionFcm>> response) {
                       if(response.body() != null){
                           List<NotificacionFcm> tkn = response.body();
                       //    sendFCMServer(tkn);
                       }

                }

                @Override
                public void onFailure(Call<List<NotificacionFcm>> call, Throwable t) {

                }
            });

        }
        return strings;
    }

    private void sendFCMServer(List<NotificacionFcm> tkn) {
        List<String> lstStr = new ArrayList<>();
        for(NotificacionFcm fcm : tkn){
            lstStr.add(fcm.getNfcTknFcm());
        }
        NotificacionFcm notif = new NotificacionFcm();
        notif.setLstTknFCM(lstStr);
        channelService = ChannelApi.runApi().create(ChannelService.class);
        this.tkn = channelService.sendNotifica(tkn);
        this.tkn.enqueue(new Callback<NotificacionFcm>() {
            @Override
            public void onResponse(Call<NotificacionFcm> call, Response<NotificacionFcm> response) {

            }

            @Override
            public void onFailure(Call<NotificacionFcm> call, Throwable t) {

            }
        });


    }


}
