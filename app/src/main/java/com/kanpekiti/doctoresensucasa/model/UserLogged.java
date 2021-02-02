package com.kanpekiti.doctoresensucasa.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


//@Entity(tableName = "user_logged")
public class UserLogged {

   // @PrimaryKey
   // @NonNull
   // @ColumnInfo(name = "username")
    private Integer id;

    private String username;

    //@ColumnInfo(name = "nombre")
    private String nombre;

    //@ColumnInfo(name = "apellido")
    private String apePa;

   // @ColumnInfo(name = "tknlog")
    private String tkn;

    private String tknFCM;

    public UserLogged() {

    }

    public UserLogged(Integer id, String username, String nombre, String apePa, String tkn) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apePa = apePa;
        this.tkn = tkn;
    }


    public static UserLogged consultarUsuario(DoctorDB database){
        UserLogged usuario = null;

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM UserLogged", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String nombre = cursor.getString(2);
                String apellido = cursor.getString(3);
                String tkn = cursor.getString(4);


                usuario = new UserLogged (id,username,nombre,apellido,tkn);

            } while (cursor.moveToNext());
        }

        return usuario;
    }

    public static void deleteUser(DoctorDB database){

        SQLiteDatabase db = database.getReadableDatabase();


        int i = db.delete("UserLogged", "id > 0", null);

        if(i>0){
            Log.d("UserLogged.class","Eliminado");
        }else{
            Log.d("UserLogged.class","No Eliminado");
        }
        db.close();
    }


    public static void agregarUsuario(UserLogged usuario, DoctorDB database) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", usuario.getId());
        values.put("username", usuario.getUsername());
        values.put("nombre", usuario.getNombre());
        values.put("apellido", usuario.getApePa());
        values.put("token", usuario.getTkn());
        long i = db.insert("UserLogged", null, values);

        db.close();
    }


    public  static int updateTknFCM(DoctorDB dataBase, Integer usuId, String tknFCM){
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tknFCM", tknFCM);

        int i = db.update("UserLogged", values, " id = ?",
                new String[]{usuId.toString()});

        db.close();
        return i;
    }






    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApePa() {
        return apePa;
    }

    public String getTkn() {
        return tkn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApePa(String apePa) {
        this.apePa = apePa;
    }

    public void setTkn(String tkn) {
        this.tkn = tkn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTknFCM() {
        return tknFCM;
    }

    public void setTknFCM(String tknFCM) {
        this.tknFCM = tknFCM;
    }
}
