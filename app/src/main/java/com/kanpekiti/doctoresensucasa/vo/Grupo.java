package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * The persistent class for the grupo database table.
 * 
 */

public class Grupo {


	@SerializedName("grpId")
	@Expose
	private int grpId;

	@SerializedName("grpDesc")
	@Expose
	private String grpDesc;

	@SerializedName("grpEstatus")
	@Expose
	private String grpEstatus;

	@SerializedName("grpNombre")
	@Expose
	private String grpNombre;

	public Grupo() {
	}

	public int getGrpId() {
		return this.grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

	public String getGrpDesc() {
		return this.grpDesc;
	}

	public void setGrpDesc(String grpDesc) {
		this.grpDesc = grpDesc;
	}

	public String getGrpEstatus() {
		return this.grpEstatus;
	}

	public void setGrpEstatus(String grpEstatus) {
		this.grpEstatus = grpEstatus;
	}

	public String getGrpNombre() {
		return this.grpNombre;
	}

	public void setGrpNombre(String grpNombre) {
		this.grpNombre = grpNombre;
	}



}