package com.kanpekiti.doctoresensucasa.asynTask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.kanpekiti.doctoresensucasa.LoginActivity;
import com.kanpekiti.doctoresensucasa.MainActivity;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.api.LoginApi;
import com.kanpekiti.doctoresensucasa.api.LoginService;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.vo.Beneficio;
import com.kanpekiti.doctoresensucasa.vo.TokenUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsynTaskLogin extends AsyncTask<String, String, String[]> {

    private LoginService loginService;

    private Call<TokenUser> loginObject;

    private DoctorDB database;

    private Activity activity;


    public AsynTaskLogin (Activity activity){
        this.activity = activity;
        database = new DoctorDB(activity,DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
    }


    @Override
    protected String[] doInBackground(String... strings) {

        loginService = LoginApi.loginApi().create(LoginService.class);
        loginObject = loginService.login("password",strings[0],strings[1]);
        loginObject.enqueue(new Callback<TokenUser>() {
            @Override
            public void onResponse(Call<TokenUser> call, Response<TokenUser> response) {
                if(response.body() != null){
                    TokenUser tkn = response.body();

                    UserLogged userLogged = new UserLogged(0,strings[0].toString(),
                            tkn.getNombre(),tkn.getApeP(),tkn.getAccessTtoken());
                    UserLogged.agregarUsuario(userLogged,database);
                    Intent intent = new Intent(activity,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    database = new DoctorDB(activity,DoctorDB.databaseName,
                            DoctorDB.databaseFactory, DoctorDB.databaseVersion);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }

            @Override
            public void onFailure(Call<TokenUser> call, Throwable t) {
                TokenUser tkn  = new TokenUser();
                tkn = null;

            }

        });
        return new String[0];
    }
}
