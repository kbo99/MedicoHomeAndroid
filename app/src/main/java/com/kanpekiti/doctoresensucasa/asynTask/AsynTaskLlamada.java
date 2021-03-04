package com.kanpekiti.doctoresensucasa.asynTask;

import android.app.Activity;
import android.os.AsyncTask;

import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.vo.Grupo;
import com.kanpekiti.doctoresensucasa.vo.MedicoLlamada;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsynTaskLlamada extends AsyncTask<String, String, String[]> {

    private DoctorService doctorService;

    private Call<MedicoLlamada> medicoLlamadaCall;

    private Activity activity;

    MedicoLlamada medicoLlamada;

    public AsynTaskLlamada(Activity activity, MedicoLlamada medicoLlamada){
        this.activity = activity;
        this.medicoLlamada = medicoLlamada;

    }
    @Override
    protected String[] doInBackground(String... strings) {
        doctorService = DoctorApi.doctorApi(activity).create(DoctorService.class);
        medicoLlamadaCall = doctorService.saveNotificaMedico(this.medicoLlamada);
        medicoLlamadaCall.enqueue(new Callback<MedicoLlamada>() {
            @Override
            public void onResponse(Call<MedicoLlamada> call, Response<MedicoLlamada> response) {

            }

            @Override
            public void onFailure(Call<MedicoLlamada> call, Throwable t) {

            }
        });
        return new String[0];
    }
}
