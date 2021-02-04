package com.kanpekiti.doctoresensucasa.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kanpekiti.doctoresensucasa.LoginActivity;
import com.kanpekiti.doctoresensucasa.SlashActivity;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.util.Const;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorApi {



    private static Retrofit retrofit = null;



    private static String token = null;

    public static Retrofit doctorApi(Activity activity){

        if(retrofit == null){
            if(token == null){
                DoctorDB  database = new DoctorDB(activity,DoctorDB.databaseName,
                        DoctorDB.databaseFactory, DoctorDB.databaseVersion);
                UserLogged userTmp = UserLogged.consultarUsuario(database);
                if(userTmp == null){
                    Intent intent = new Intent(activity,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    activity.startActivity(intent);

                }else {
                    token = userTmp.getTkn();
                     OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(token);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Const.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();
                }
            }

        }
        return  retrofit;
    }

    public static Retrofit doctorApi(Context activity){

        if(retrofit == null){
            if(token == null){
                DoctorDB  database = new DoctorDB(activity,DoctorDB.databaseName,
                        DoctorDB.databaseFactory, DoctorDB.databaseVersion);
                UserLogged userTmp = UserLogged.consultarUsuario(database);
                if(userTmp == null){
                    Intent intent = new Intent(activity,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    activity.startActivity(intent);

                }else {
                    token = userTmp.getTkn();
                    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(token);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Const.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();
                }
            }

        }
        return  retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        DoctorApi.retrofit = retrofit;
    }

    public static void setToken(String token) {
        DoctorApi.token = token;
    }




}
