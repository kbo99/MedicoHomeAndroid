package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.vo.NotificacionFcm;
import com.kanpekiti.doctoresensucasa.vo.TokenUser;
import com.kanpekiti.doctoresensucasa.vo.VideoCallChannel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChannelService {


    @POST("channelVideoCall")
    Call<NotificacionFcm> sendNotifica(@Body List<NotificacionFcm> user);
}
