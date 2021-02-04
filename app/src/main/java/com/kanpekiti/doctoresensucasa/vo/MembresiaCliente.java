package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;



/**
 * The persistent class for the membresia_cliente database table.
 * 
 */
public class MembresiaCliente implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3855152909007526658L;

	@SerializedName("mecId")
	@Expose
	private int mecId;

	@SerializedName("mecDuracion")
	@Expose
	private int mecDuracion;

	@SerializedName("mecEstatus")
	@Expose
	private String mecEstatus;

	@SerializedName("mecFinicio")
	@Expose
	private Date mecFinicio;

	@SerializedName("mecFultimaMod")
	@Expose
	private Date mecFultimaMod;

	@SerializedName("mecFvencimiento")
	@Expose
	private int mecFvencimiento;

	//bi-directional many-to-one association to ClientePersona

	@SerializedName("clientePersona")
	@Expose
	private ClientePersona clientePersona;

	//bi-directional many-to-one association to Membresia
	@SerializedName("membresia")
	@Expose
	private Membresia membresia;

	@SerializedName("mecFolio")
	@Expose
	private String mecFolio;

	public MembresiaCliente() {
	}

	public int getMecId() {
		return this.mecId;
	}

	public void setMecId(int mecId) {
		this.mecId = mecId;
	}

	public int getMecDuracion() {
		return this.mecDuracion;
	}

	public void setMecDuracion(int mecDuracion) {
		this.mecDuracion = mecDuracion;
	}

	public String getMecEstatus() {
		return this.mecEstatus;
	}

	public void setMecEstatus(String mecEstatus) {
		this.mecEstatus = mecEstatus;
	}

	public Date getMecFinicio() {
		return this.mecFinicio;
	}

	public void setMecFinicio(Date mecFinicio) {
		this.mecFinicio = mecFinicio;
	}

	public Date getMecFultimaMod() {
		return this.mecFultimaMod;
	}

	public void setMecFultimaMod(Date mecFultimaMod) {
		this.mecFultimaMod = mecFultimaMod;
	}

	public int getMecFvencimiento() {
		return this.mecFvencimiento;
	}

	public void setMecFvencimiento(int mecFvencimiento) {
		this.mecFvencimiento = mecFvencimiento;
	}

	public ClientePersona getClientePersona() {
		return this.clientePersona;
	}

	public void setClientePersona(ClientePersona clientePersona) {
		this.clientePersona = clientePersona;
	}

	public Membresia getMembresia() {
		return this.membresia;
	}

	public void setMembresia(Membresia membresia) {
		this.membresia = membresia;
	}

	


	/**
	 * @return the mecFolio
	 */
	public String getMecFolio() {
		return mecFolio;
	}

	/**
	 * @param mecFolio the mecFolio to set
	 */
	public void setMecFolio(String mecFolio) {
		this.mecFolio = mecFolio;
	}
	
	

}