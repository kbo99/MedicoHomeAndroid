package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.util.Const;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChannelApi {

    private static Retrofit retrofit = null;

    public static Retrofit runApi(){
        if(retrofit == null){
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL_CHANNEL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return  retrofit;
    }
}
