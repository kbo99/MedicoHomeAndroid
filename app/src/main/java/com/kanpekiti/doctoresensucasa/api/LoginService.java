package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.vo.TokenUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {

    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Authorization: Basic ZnJvbmRFbmRBcHA6MTIzNDU2"
    })
    @FormUrlEncoded
    @POST("api/security/oauth/token")
    Call<TokenUser> login(@Field("grant_type") String grantType,
                          @Field("username") String username, @Field("password") String password);
}
