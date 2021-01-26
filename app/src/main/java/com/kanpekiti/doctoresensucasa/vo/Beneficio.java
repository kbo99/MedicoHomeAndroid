package com.kanpekiti.doctoresensucasa.vo;


import java.util.Date;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * The persistent class for the beneficio database table.
 * 
 */

public class Beneficio  {



	@SerializedName("benId")
	@Expose
	private int benId;

	@SerializedName("benDescripcion")
	@Expose
	private String benDescripcion;

	@SerializedName("ben_estatus")
	@Expose
	private String benEstatus;


	@SerializedName("benFregistra")
	@Expose
	private Date benFregistra;

	@SerializedName("benNombre")
	@Expose
	private String benNombre;

	@SerializedName("usuRegistra")
	@Expose
	private String usuRegistra;

	@SerializedName("benCantidadAplica")
	@Expose
	private int benCantidadAplica;

	@SerializedName("benDescuento")
	@Expose
	private int benDescuento;

	@SerializedName("benPeriodoRenovacionDias")
	@Expose
	private int benPeriodoRenovacionDias;



	
	public Beneficio() {
	}

	public int getBenId() {
		return this.benId;
	}

	public void setBenId(int benId) {
		this.benId = benId;
	}

	public String getBenDescripcion() {
		return this.benDescripcion;
	}

	public void setBenDescripcion(String benDescripcion) {
		this.benDescripcion = benDescripcion;
	}

	public String getBenEstatus() {
		return this.benEstatus;
	}

	public void setBenEstatus(String benEstatus) {
		this.benEstatus = benEstatus;
	}

	public Date getBenFregistra() {
		return this.benFregistra;
	}

	public void setBenFregistra(Date benFregistra) {
		this.benFregistra = benFregistra;
	}

	public String getBenNombre() {
		return this.benNombre;
	}

	public void setBenNombre(String benNombre) {
		this.benNombre = benNombre;
	}

	public String getUsuRegistra() {
		return this.usuRegistra;
	}

	public void setUsuRegistra(String usuRegistra) {
		this.usuRegistra = usuRegistra;
	}

	public int getBenCantidadAplica() {
		return benCantidadAplica;
	}

	public void setBenCantidadAplica(int benCantidadAplica) {
		this.benCantidadAplica = benCantidadAplica;
	}

	public int getBenDescuento() {
		return benDescuento;
	}

	public void setBenDescuento(int besDescuento) {
		this.benDescuento = besDescuento;
	}

	public int getBenPeriodoRenovacionDias() {
		return benPeriodoRenovacionDias;
	}

	public void setBenPeriodoRenovacionDias(int benPeriodoRenovacionDias) {
		this.benPeriodoRenovacionDias = benPeriodoRenovacionDias;
	}
}