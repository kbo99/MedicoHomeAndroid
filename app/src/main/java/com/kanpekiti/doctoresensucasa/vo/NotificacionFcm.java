package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the notificacion_fcm database table.
 * 
 */

public class NotificacionFcm implements Serializable {
	private static final long serialVersionUID = 1L;


	@SerializedName("nfcId")
	@Expose
	private int nfcId;


	@SerializedName("nfcDispositivo")
	@Expose
	private String nfcDispositivo;


	@SerializedName("nfcDoctor")
	@Expose
	private String nfcDoctor;


	@SerializedName("nfcEnllamada")
	@Expose
	private String nfcEnllamada;

	@SerializedName("nfcTknFcm")
	@Expose
	private String nfcTknFcm;

	@SerializedName("usuUsuario")
	@Expose
	private String usuUsuario;

	@SerializedName("lstTknFCM")
	@Expose
	private List<String> lstTknFCM;

	@SerializedName("titulo")
	@Expose
	private String titulo;

	@SerializedName("mensaje")
	@Expose
	private String mensaje;

	@SerializedName("longitude")
	@Expose
	private String longitude;

	@SerializedName("latitude")
	@Expose
	private String latitude;

	public NotificacionFcm() {
	}

	public int getNfcId() {
		return this.nfcId;
	}

	public void setNfcId(int nfcId) {
		this.nfcId = nfcId;
	}

	public String getNfcDispositivo() {
		return this.nfcDispositivo;
	}

	public void setNfcDispositivo(String nfcDispositivo) {
		this.nfcDispositivo = nfcDispositivo;
	}

	public String getNfcDoctor() {
		return this.nfcDoctor;
	}

	public void setNfcDoctor(String nfcDoctor) {
		this.nfcDoctor = nfcDoctor;
	}

	public String getNfcEnllamada() {
		return this.nfcEnllamada;
	}

	public void setNfcEnllamada(String nfcEnllamada) {
		this.nfcEnllamada = nfcEnllamada;
	}

	public String getNfcTknFcm() {
		return this.nfcTknFcm;
	}

	public void setNfcTknFcm(String nfcTknFcm) {
		this.nfcTknFcm = nfcTknFcm;
	}

	public String getUsuUsuario() {
		return this.usuUsuario;
	}

	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}

	public List<String> getLstTknFCM() {
		return lstTknFCM;
	}

	public void setLstTknFCM(List<String> lstTknFCM) {
		this.lstTknFCM = lstTknFCM;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}