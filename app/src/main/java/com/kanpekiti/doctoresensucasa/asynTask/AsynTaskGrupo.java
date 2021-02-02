package com.kanpekiti.doctoresensucasa.asynTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kanpekiti.doctoresensucasa.MainActivity;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.util.Const;
import com.kanpekiti.doctoresensucasa.vo.Grupo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsynTaskGrupo extends AsyncTask<String, String, String[]> {

    private DoctorService doctorService;

    private Call<List<Grupo>> lstGrupo;

    private DoctorDB database;

    private Activity activity;

    public AsynTaskGrupo(Activity activity, DoctorDB dataBase){
        this.activity = activity;
        this.database = dataBase;
    }


    @Override
    protected String[] doInBackground(String... strings) {
        UserLogged userLogged = UserLogged.consultarUsuario(database);
        if(userLogged != null){
            doctorService = DoctorApi.doctorApi(activity).create(DoctorService.class);
            lstGrupo = doctorService.findGrpByUser(strings[0]);
            lstGrupo.enqueue(new Callback<List<Grupo>>() {
                @Override
                public void onResponse(Call<List<Grupo>> call, Response<List<Grupo>> response) {
                    if(response.code() == 200){
                        List<Grupo> lstGrp = response.body();
                        for(Grupo gpr : lstGrp){
                            Grupos gpo = new Grupos();
                            gpo.setGprNombre(gpr.getGrpNombre());
                            Grupos.guardaGrupos(gpo, database);
                        }
                        initFireBase();
                    } else if(response.code() == 401){

                    }else {

                    }

                }

                @Override
                public void onFailure(Call<List<Grupo>> call, Throwable t) {


                }
            });
        }
        return strings;
    }

    private  void initFireBase(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(MainActivity.class.getSimpleName(), "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        new AsynTaskTknFCM(activity).execute(Const.SAVE_TKN_NOTIFICA, token);
                        // Log and toast
                    }
                });
    }
}
