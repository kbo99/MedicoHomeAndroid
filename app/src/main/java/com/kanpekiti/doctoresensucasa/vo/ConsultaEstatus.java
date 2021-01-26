package com.kanpekiti.doctoresensucasa.vo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * The persistent class for the consulta_estatus database table.
 * 
 */

public class ConsultaEstatus  {



	@SerializedName("coeId")
	@Expose
	private int coeId;

	@SerializedName("coeNombre")
	@Expose
	private String coeNombre;

	//bi-directional many-to-one association to Consulta
	@SerializedName("consultas")
	@Expose
	private List<Consulta> consultas;

	public ConsultaEstatus() {
	}

	public int getCoeId() {
		return this.coeId;
	}

	public void setCoeId(int coeId) {
		this.coeId = coeId;
	}

	public String getCoeNombre() {
		return this.coeNombre;
	}

	public void setCoeNombre(String coeNombre) {
		this.coeNombre = coeNombre;
	}

	public List<Consulta> getConsultas() {
		return this.consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public Consulta addConsulta(Consulta consulta) {
		getConsultas().add(consulta);
		consulta.setConsultaEstatus(this);

		return consulta;
	}

	public Consulta removeConsulta(Consulta consulta) {
		getConsultas().remove(consulta);
		consulta.setConsultaEstatus(null);

		return consulta;
	}

}