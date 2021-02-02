package com.kanpekiti.doctoresensucasa.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grupos {

    private int gprId;

    private String gprNombre;

    public Grupos(){

    }

    public Grupos(int gprId, String gprNombre){
    this.gprId = gprId;
    this.gprNombre = gprNombre;
    }


   public static void guardaGrupos (Grupos gpr, DoctorDB database) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("gprNombre", gpr.gprNombre);
        long i = db.insert("Grupos", null, values);

        db.close();
    }

    public static List<Grupos> consultarGrupo(DoctorDB database){
        List<Grupos> lstGrp = new ArrayList<>();

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Grupos", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String gpr_nombre = cursor.getString(1);

                lstGrp.add(new Grupos(id,gpr_nombre));

            } while (cursor.moveToNext());
        }

        return lstGrp;
    }

    public int getGprId() {
        return gprId;
    }

    public void setGprId(int gprId) {
        this.gprId = gprId;
    }

    public String getGprNombre() {
        return gprNombre;
    }

    public void setGprNombre(String gprNombre) {
        this.gprNombre = gprNombre;
    }
}
