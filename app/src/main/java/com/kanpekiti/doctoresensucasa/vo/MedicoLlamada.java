package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import java.util.Date;


/**
 * The persistent class for the medico_llamada database table.
 * 
 */

public class MedicoLlamada {


	@SerializedName("mllId")
	@Expose
	private int mllId;

	@SerializedName("llpId")
	@Expose
	private int llpId;

	@SerializedName("mllEstatus")
	@Expose
	private String mllEstatus;

	@SerializedName("mllHora")
	@Expose
	private Date mllHora;

	@SerializedName("mllObservaciones")
	@Expose
	private String mllObservaciones;

	@SerializedName("userMedico")
	@Expose
	private String userMedico;

	public MedicoLlamada() {
	}

	public int getMllId() {
		return this.mllId;
	}

	public void setMllId(int mllId) {
		this.mllId = mllId;
	}

	public int getLlpId() {
		return this.llpId;
	}

	public void setLlpId(int llpId) {
		this.llpId = llpId;
	}

	public String getMllEstatus() {
		return this.mllEstatus;
	}

	public void setMllEstatus(String mllEstatus) {
		this.mllEstatus = mllEstatus;
	}

	public Date getMllHora() {
		return this.mllHora;
	}

	public void setMllHora(Date mllHora) {
		this.mllHora = mllHora;
	}

	public String getMllObservaciones() {
		return this.mllObservaciones;
	}

	public void setMllObservaciones(String mllObservaciones) {
		this.mllObservaciones = mllObservaciones;
	}

	public String getUserMedico() {
		return this.userMedico;
	}

	public void setUserMedico(String userMedico) {
		this.userMedico = userMedico;
	}

}