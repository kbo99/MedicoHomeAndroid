package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.util.Const;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginApi {

    private static Retrofit retrofit = null;


    public static Retrofit loginApi(){
        if(retrofit == null){
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return  retrofit;
    }
}
