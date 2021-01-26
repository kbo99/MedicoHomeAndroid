package com.kanpekiti.doctoresensucasa.api;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class Interceptor implements okhttp3.Interceptor {

    private String token;

    public Interceptor(String token) {
        this.token = token;
    }

    public Interceptor(){

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request urlOriginal = chain.request();

        Request.Builder builder = urlOriginal.newBuilder()
                .addHeader("Authorization", "Bearer "+token);

        Request request = builder.build();

        return chain.proceed(request);

    }
}
