/**
 * 
 */
package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author macpro
 *
 */

public class LlamadaPendiente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7960584230760584823L;

	@SerializedName("llpId")
	@Expose
	private Long llpId;


	@SerializedName("usuSol")
	@Expose
	private String 	usuSol;

	@SerializedName("llpEstatus")
	@Expose
	private String llpEstatus;

	@SerializedName("llpFecha")
	@Expose
	private Date llpFecha;


	@SerializedName("llpAtendida")
	@Expose
	private String llpAtendida;

	@SerializedName("usuAtiende")
	@Expose
	private String usuAtiende;

	@SerializedName("tknAgora")
	@Expose
	private String tknAgora;

	@SerializedName("llpFechaFin")
	@Expose
	private Date llpFechaFin;

	@SerializedName("duracion")
	@Expose
	private String duracion;
	
	
	public LlamadaPendiente() {
		
	}


	/**
	 * @return the llpId
	 */
	public Long getLlpId() {
		return llpId;
	}


	/**
	 * @param llpId the llpId to set
	 */
	public void setLlpId(Long llpId) {
		this.llpId = llpId;
	}


	/**
	 * @return the usuSol
	 */
	public String getUsuSol() {
		return usuSol;
	}


	/**
	 * @param usuSol the usuSol to set
	 */
	public void setUsuSol(String usuSol) {
		this.usuSol = usuSol;
	}


	/**
	 * @return the llpEstatus
	 */
	public String getLlpEstatus() {
		return llpEstatus;
	}


	/**
	 * @param llpEstatus the llpEstatus to set
	 */
	public void setLlpEstatus(String llpEstatus) {
		this.llpEstatus = llpEstatus;
	}


	/**
	 * @return the llpFecha
	 */
	public Date getLlpFecha() {
		return llpFecha;
	}


	/**
	 * @param llpFecha the llpFecha to set
	 */
	public void setLlpFecha(Date llpFecha) {
		this.llpFecha = llpFecha;
	}


	/**
	 * @return the llpAtendida
	 */
	public String getLlpAtendida() {
		return llpAtendida;
	}


	/**
	 * @param llpAtendida the llpAtendida to set
	 */
	public void setLlpAtendida(String llpAtendida) {
		this.llpAtendida = llpAtendida;
	}


	/**
	 * @return the tknAgora
	 */
	public String getTknAgora() {
		return tknAgora;
	}


	/**
	 * @param tknAgora the tknAgora to set
	 */
	public void setTknAgora(String tknAgora) {
		this.tknAgora = tknAgora;
	}


	/**
	 * @return the usuAtiende
	 */
	public String getUsuAtiende() {
		return usuAtiende;
	}


	/**
	 * @param usuAtiende the usuAtiende to set
	 */
	public void setUsuAtiende(String usuAtiende) {
		this.usuAtiende = usuAtiende;
	}
	
	/**
	 * @return the llpFechaFin
	 */
	public Date getLlpFechaFin() {
		return llpFechaFin;
	}


	/**
	 * @param llpFechaFin the llpFechaFin to set
	 */
	public void setLlpFechaFin(Date llpFechaFin) {
		this.llpFechaFin = llpFechaFin;
	}


	/**
	 * @return the duracion
	 */
	public String getDuracion() {
		return duracion;
	}


	/**
	 * @param duracion the duracion to set
	 */
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	
	
	

}
