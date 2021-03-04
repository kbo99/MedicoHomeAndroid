package com.kanpekiti.doctoresensucasa.vo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * The persistent class for the doctor database table.
 * 
 */

public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("docId")
	@Expose
	private int docId;

	@SerializedName("docCedula")
	@Expose
	private String docCedula;

	@SerializedName("docDescripcion")
	@Expose
	private String docDescripcion;

	@SerializedName("persona")
	@Expose
	private Persona persona;

	public Doctor() {
	}

	public int getDocId() {
		return this.docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocCedula() {
		return this.docCedula;
	}

	public void setDocCedula(String docCedula) {
		this.docCedula = docCedula;
	}

	public String getDocDescripcion() {
		return this.docDescripcion;
	}

	public void setDocDescripcion(String docDescripcion) {
		this.docDescripcion = docDescripcion;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	


}