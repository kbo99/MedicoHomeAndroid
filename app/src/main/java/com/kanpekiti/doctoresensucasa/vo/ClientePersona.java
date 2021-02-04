package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;




/**
 * The persistent class for the cliente_persona database table.
 * 
 */

public class ClientePersona {


	@SerializedName("cpeId")
	@Expose
	private int cpeId;


	@SerializedName("cpeFregistro")
	@Expose
	private Date cpeFregistro;


	@SerializedName("cpeFultimaMod")
	@Expose
	private Date cpeFultimaMod;


	//bi-directional many-to-one association to Cliente
	@SerializedName("cliente")
	@Expose
	private Cliente cliente;

	@SerializedName("persona")
	@Expose
	private Persona persona;




	public ClientePersona() {
	}

	public int getCpeId() {
		return this.cpeId;
	}

	public void setCpeId(int cpeId) {
		this.cpeId = cpeId;
	}

	public Date getCpeFregistro() {
		return this.cpeFregistro;
	}

	public void setCpeFregistro(Date cpeFregistro) {
		this.cpeFregistro = cpeFregistro;
	}

	public Date getCpeFultimaMod() {
		return this.cpeFultimaMod;
	}

	public void setCpeFultimaMod(Date cpeFultimaMod) {
		this.cpeFultimaMod = cpeFultimaMod;
	}

	

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
}