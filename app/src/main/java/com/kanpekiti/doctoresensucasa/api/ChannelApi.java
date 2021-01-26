package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.util.Const;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChannelApi {

    private static Retrofit retrofit = null;

    public static Retrofit loginApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL_CHANNEL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }
}
