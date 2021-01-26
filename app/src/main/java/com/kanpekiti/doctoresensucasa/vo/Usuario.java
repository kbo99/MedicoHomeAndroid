	package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */

public class Usuario {


	@SerializedName("usuId")
	@Expose
	private int usuId;

	@SerializedName("usuEstatus")
	@Expose
	private String usuEstatus;

	@SerializedName("usuPassword")
	@Expose
	private String usuPassword;

	@SerializedName("usuUsuario")
	@Expose
	private String usuUsuario;

	@SerializedName("perId")
	@Expose
	private Integer perId;



	@SerializedName("grupos")
	@Expose
	private List<Grupo> grupos;

	public Usuario() {
	}

	public int getUsuId() {
		return this.usuId;
	}

	public void setUsuId(int usuId) {
		this.usuId = usuId;
	}

	public String getUsuEstatus() {
		return this.usuEstatus;
	}

	public void setUsuEstatus(String usuEstatus) {
		this.usuEstatus = usuEstatus;
	}

	public String getUsuPassword() {
		return this.usuPassword;
	}

	public void setUsuPassword(String usuPassword) {
		this.usuPassword = usuPassword;
	}

	public String getUsuUsuario() {
		return this.usuUsuario;
	}

	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}

	/**
	 * @return the perId
	 */
	public Integer getPerId() {
		return perId;
	}

	/**
	 * @param perId the perId to set
	 */
	public void setPerId(Integer perId) {
		this.perId = perId;
	}

	public List<Grupo> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}




}