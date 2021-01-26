package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenUser {

    @SerializedName("access_token")
    @Expose
    private String accessTtoken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("apeP")
    @Expose
    private String apeP;

    public TokenUser(){

    }

    public String getAccessTtoken() {
        return accessTtoken;
    }

    public void setAccessTtoken(String accessTtoken) {
        this.accessTtoken = accessTtoken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApeP() {
        return apeP;
    }

    public void setApeP(String apeP) {
        this.apeP = apeP;
    }
}
