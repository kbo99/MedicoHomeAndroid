package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the membresia database table.
 * 
 */

public class Membresia implements Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("memId")
	@Expose
	private int memId;

	@SerializedName("memDescripcion")
	@Expose
	private String memDescripcion;

	@SerializedName("memEstatus")
	@Expose
	private String memEstatus;

	@SerializedName("memFcreacion")
	@Expose
	private Date memFcreacion;

	@SerializedName("memNombre")
	@Expose
	private String memNombre;

	@SerializedName("memVigenciaMeses")
	@Expose
	private int memVigenciaMeses;

	@SerializedName("usuRegistra")
	@Expose
	private String usuRegistra;

	@SerializedName("memCosto")
	@Expose
	private Integer memCosto;

	@SerializedName("memCostoBenAd")
	@Expose
	private Integer memCostoBenAd;

	@SerializedName("memNumeroBene")
	@Expose
	private Integer memNumeroBene;

	@SerializedName("beneficios")
	@Expose
	private List<Beneficio> beneficios;

	public Membresia() {
	}

	public int getMemId() {
		return this.memId;
	}

	public void setMemId(int memId) {
		this.memId = memId;
	}

	public String getMemDescripcion() {
		return this.memDescripcion;
	}

	public void setMemDescripcion(String memDescripcion) {
		this.memDescripcion = memDescripcion;
	}

	public String getMemEstatus() {
		return this.memEstatus;
	}

	public void setMemEstatus(String memEstatus) {
		this.memEstatus = memEstatus;
	}

	public Date getMemFcreacion() {
		return this.memFcreacion;
	}

	public void setMemFcreacion(Date memFcreacion) {
		this.memFcreacion = memFcreacion;
	}

	public String getMemNombre() {
		return this.memNombre;
	}

	public void setMemNombre(String memNombre) {
		this.memNombre = memNombre;
	}

	public int getMemVigenciaMeses() {
		return this.memVigenciaMeses;
	}

	public void setMemVigenciaMeses(int memVigenciaMeses) {
		this.memVigenciaMeses = memVigenciaMeses;
	}

	public String getUsuRegistra() {
		return this.usuRegistra;
	}

	public void setUsuRegistra(String usuRegistra) {
		this.usuRegistra = usuRegistra;
	}


	public Integer getMemCosto() {
		return memCosto;
	}

	public void setMemCosto(Integer memCosto) {
		this.memCosto = memCosto;
	}

	public Integer getMemCostoBenAd() {
		return memCostoBenAd;
	}

	public void setMemCostoBenAd(Integer memCostoBenAd) {
		this.memCostoBenAd = memCostoBenAd;
	}

	public Integer getMemNumeroBene() {
		return memNumeroBene;
	}

	public void setMemNumeroBene(Integer memNumeroBene) {
		this.memNumeroBene = memNumeroBene;
	}

	public List<Beneficio> getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(List<Beneficio> beneficios) {
		this.beneficios = beneficios;
	}

}