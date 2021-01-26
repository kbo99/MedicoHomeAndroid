package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.vo.TokenUser;
import com.kanpekiti.doctoresensucasa.vo.VideoCallChannel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChannelService {

    @FormUrlEncoded
    @POST("channelVideoCall")
    Call<VideoCallChannel> login(@Field("user") String user);
}
