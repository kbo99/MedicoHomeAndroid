package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * The persistent class for the benecifio_insumo database table.
 * 
 */

public class BenecifioInsumo  {

	@SerializedName("bniId")
	@Expose
	private int bniId;

	@SerializedName("besId")
	@Expose
	private int besId;

	@SerializedName("prodId")
	@Expose
	private int prodId;

	public BenecifioInsumo() {
	}

	public int getBniId() {
		return this.bniId;
	}

	public void setBniId(int bniId) {
		this.bniId = bniId;
	}

	public int getBesId() {
		return this.besId;
	}

	public void setBesId(int besId) {
		this.besId = besId;
	}

	public int getProdId() {
		return this.prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

}