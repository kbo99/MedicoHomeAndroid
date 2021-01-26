package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.vo.Beneficio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DoctorService {

    @GET("api/servicio-admin/cliente/listar")
    Call<Object> listarClientes();

    @FormUrlEncoded
    @POST(("api/servicio-admin/findMiBeneficio"))
    Call<List<Beneficio>> findMyBenefico(@Field("username") String user);
}
