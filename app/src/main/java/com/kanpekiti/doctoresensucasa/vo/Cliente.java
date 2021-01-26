package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.Date;


/**
 * The persistent class for the cliente database table.
 * 
 */

public class Cliente {


	@SerializedName("cliId")
	@Expose
	private int cliId;

	@SerializedName("cliEmail")
	@Expose
	private String cliEmail;

	@SerializedName("cliEstatus")
	@Expose
	private String cliEstatus;


	@SerializedName("cliFregistro")
	@Expose
	private Date cliFregistro;


	@SerializedName("cliFultimaOp")
	@Expose
	private Date cliFultimaOp;

	@SerializedName("cliNomCorto")
	@Expose
	private String cliNomCorto;

	@SerializedName("cliObs")
	@Expose
	private String cliObs;

	@SerializedName("cliRfc")
	@Expose
	private String cliRfc;

	@SerializedName("cliWebFac")
	@Expose
	private String cliWebFac;

	@SerializedName("usuRegistra")
	@Expose
	private String usuRegistra;



	public Cliente() {
	}

	public int getCliId() {
		return this.cliId;
	}

	public void setCliId(int cliId) {
		this.cliId = cliId;
	}

	public String getCliEmail() {
		return this.cliEmail;
	}

	public void setCliEmail(String cliEmail) {
		this.cliEmail = cliEmail;
	}

	public String getCliEstatus() {
		return this.cliEstatus;
	}

	public void setCliEstatus(String cliEstatus) {
		this.cliEstatus = cliEstatus;
	}

	public Date getCliFregistro() {
		return this.cliFregistro;
	}

	public void setCliFregistro(Date cliFregistro) {
		this.cliFregistro = cliFregistro;
	}

	public Date getCliFultimaOp() {
		return this.cliFultimaOp;
	}

	public void setCliFultimaOp(Date cliFultimaOp) {
		this.cliFultimaOp = cliFultimaOp;
	}

	public String getCliNomCorto() {
		return this.cliNomCorto;
	}

	public void setCliNomCorto(String cliNomCorto) {
		this.cliNomCorto = cliNomCorto;
	}

	public String getCliObs() {
		return this.cliObs;
	}

	public void setCliObs(String cliObs) {
		this.cliObs = cliObs;
	}

	public String getCliRfc() {
		return this.cliRfc;
	}

	public void setCliRfc(String cliRfc) {
		this.cliRfc = cliRfc;
	}

	public String getCliWebFac() {
		return this.cliWebFac;
	}

	public void setCliWebFac(String cliWebFac) {
		this.cliWebFac = cliWebFac;
	}

	public String getUsuRegistra() {
		return this.usuRegistra;
	}

	public void setUsuRegistra(String usuRegistra) {
		this.usuRegistra = usuRegistra;
	}






}