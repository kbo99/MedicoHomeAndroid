package com.kanpekiti.doctoresensucasa.vo;

import java.math.BigDecimal;
import java.util.Date;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * The persistent class for the consulta database table.
 * 
 */

public class Consulta {


	@SerializedName("conId")
	@Expose
	private int conId;


	@SerializedName("conFechaConsulta")
	@Expose
	private Date conFechaConsulta;


	@SerializedName("conFregsitro")
	@Expose
	private Date conFregsitro;

	@SerializedName("conPacEdad")
	@Expose
	private int conPacEdad;

	@SerializedName("conPacEstatura")
	@Expose
	private BigDecimal conPacEstatura;

	@SerializedName("conPacPeso")
	@Expose
	private BigDecimal conPacPeso;

	@SerializedName("usuRegistra")
	@Expose
	private String usuRegistra;

	//bi-directional many-to-one association to ConsultaEstatus
	@SerializedName("consultaEstatus")
	@Expose
	private ConsultaEstatus consultaEstatus;




	//bi-directional many-to-one association to Paciente
	@SerializedName("clientePersona")
	@Expose
	private ClientePersona clientePersona;




	public Consulta() {
	}

	public int getConId() {
		return this.conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	public Date getConFechaConsulta() {
		return this.conFechaConsulta;
	}

	public void setConFechaConsulta(Date conFechaConsulta) {
		this.conFechaConsulta = conFechaConsulta;
	}

	public Date getConFregsitro() {
		return this.conFregsitro;
	}

	public void setConFregsitro(Date conFregsitro) {
		this.conFregsitro = conFregsitro;
	}

	public int getConPacEdad() {
		return this.conPacEdad;
	}

	public void setConPacEdad(int conPacEdad) {
		this.conPacEdad = conPacEdad;
	}

	public BigDecimal getConPacEstatura() {
		return this.conPacEstatura;
	}

	public void setConPacEstatura(BigDecimal conPacEstatura) {
		this.conPacEstatura = conPacEstatura;
	}

	public BigDecimal getConPacPeso() {
		return this.conPacPeso;
	}

	public void setConPacPeso(BigDecimal conPacPeso) {
		this.conPacPeso = conPacPeso;
	}

	public String getUsuRegistra() {
		return this.usuRegistra;
	}

	public void setUsuRegistra(String usuRegistra) {
		this.usuRegistra = usuRegistra;
	}

	public ConsultaEstatus getConsultaEstatus() {
		return this.consultaEstatus;
	}

	public void setConsultaEstatus(ConsultaEstatus consultaEstatus) {
		this.consultaEstatus = consultaEstatus;
	}





//	public List<ServicioConsulta> getServicioConsultas() {
//		return this.servicioConsultas;
//	}
//
//	public void setServicioConsultas(List<ServicioConsulta> servicioConsultas) {
//		this.servicioConsultas = servicioConsultas;
//	}

//	public ServicioConsulta addServicioConsulta(ServicioConsulta servicioConsulta) {
//		getServicioConsultas().add(servicioConsulta);
//		servicioConsulta.setConsulta(this);
//
//		return servicioConsulta;
//	}
//
//	public ServicioConsulta removeServicioConsulta(ServicioConsulta servicioConsulta) {
//		getServicioConsultas().remove(servicioConsulta);
//		servicioConsulta.setConsulta(null);
//
//		return servicioConsulta;
//	}

	/**
	 * @return the clientePersona
	 */
	public ClientePersona getClientePersona() {
		return clientePersona;
	}

	/**
	 * @param clientePersona the clientePersona to set
	 */
	public void setClientePersona(ClientePersona clientePersona) {
		this.clientePersona = clientePersona;
	}

}