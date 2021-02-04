package com.kanpekiti.doctoresensucasa.api;

import com.kanpekiti.doctoresensucasa.vo.Beneficio;
import com.kanpekiti.doctoresensucasa.vo.Grupo;
import com.kanpekiti.doctoresensucasa.vo.Membresia;
import com.kanpekiti.doctoresensucasa.vo.MembresiaCliente;
import com.kanpekiti.doctoresensucasa.vo.NotificacionFcm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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


    @POST(("api/notificacion/fcm/saveTkn"))
    Call<NotificacionFcm> saveToken(@Body NotificacionFcm notificacionFcm);


    @POST(("api/usuarios/findGpr"))
    Call<List<Grupo>> findGrpByUser(@Body String user);

    @POST(("api/notificacion/fcm/generaNotifica"))
    Call<List<NotificacionFcm>> generaNotificaLlamada(@Body NotificacionFcm notificacionFcm);


    @POST(("api/servicio-admin/findMembresia"))
    Call<MembresiaCliente> findMembresia(@Body String user);



}
