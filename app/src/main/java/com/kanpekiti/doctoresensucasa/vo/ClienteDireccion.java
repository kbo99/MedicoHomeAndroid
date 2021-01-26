package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * The persistent class for the cliente_direccion database table.
 * 
 */

public class ClienteDireccion {


	@SerializedName("cldId")
	@Expose
	private int cldId;

	@SerializedName("cldActiva")
	@Expose
	private String cldActiva;

	//bi-directional many-to-one association to ClientePersona
	@SerializedName("clientePersona")
	@Expose
	private ClientePersona clientePersona;



	public ClienteDireccion() {
	}

	public int getCldId() {
		return this.cldId;
	}

	public void setCldId(int cldId) {
		this.cldId = cldId;
	}

	public String getCldActiva() {
		return this.cldActiva;
	}

	public void setCldActiva(String cldActiva) {
		this.cldActiva = cldActiva;
	}

	public ClientePersona getClientePersona() {
		return this.clientePersona;
	}

	public void setClientePersona(ClientePersona clientePersona) {
		this.clientePersona = clientePersona;
	}


}