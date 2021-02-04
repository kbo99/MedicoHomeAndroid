package com.kanpekiti.doctoresensucasa.asynTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.kanpekiti.doctoresensucasa.BeneficioListActivity;
import com.kanpekiti.doctoresensucasa.LlamadaVozActivity;
import com.kanpekiti.doctoresensucasa.LoginActivity;
import com.kanpekiti.doctoresensucasa.MainActivity;
import com.kanpekiti.doctoresensucasa.R;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.api.LoginApi;
import com.kanpekiti.doctoresensucasa.api.LoginService;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.vo.Beneficio;
import com.kanpekiti.doctoresensucasa.vo.Grupo;
import com.kanpekiti.doctoresensucasa.vo.TokenUser;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsynTaskLogin extends AsyncTask<String, String, String[]> {

    private LoginService loginService;

    private Call<TokenUser> loginObject;



    private DoctorDB database;

    private Activity activity;

    private ProgressDialog dialogRec;


    public AsynTaskLogin (Activity activity, ProgressDialog dialogRec){
        this.activity = activity;
        database = new DoctorDB(activity,DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
        this.dialogRec = dialogRec;
    }


    @Override
    protected String[] doInBackground(String... strings) {

        loginService = LoginApi.loginApi().create(LoginService.class);
        loginObject = loginService.login("password",strings[0],strings[1]);
        loginObject.enqueue(new Callback<TokenUser>() {
            @Override
            public void onResponse(Call<TokenUser> call, Response<TokenUser> response) {
                dialogRec.dismiss();
                if(response.body() != null){
                    TokenUser tkn = response.body();

                    UserLogged userLogged = new UserLogged(0,strings[0].toString(),
                            tkn.getNombre(),tkn.getApeP(),tkn.getAccessTtoken());
                    UserLogged.agregarUsuario(userLogged,database);
                    Intent intent = new Intent(activity,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    database = new DoctorDB(activity,DoctorDB.databaseName,
                            DoctorDB.databaseFactory, DoctorDB.databaseVersion);
                    new AsynTaskGrupo(activity,database).execute(strings[0]);
                    activity.startActivity(intent);
                    activity.finish();
                }else{
                    lanzaMensaje(activity,"Aviso","Usuario o Password Incorrecto");
                 }

            }

            @Override
            public void onFailure(Call<TokenUser> call, Throwable t) {
                dialogRec.dismiss();
                lanzaMensaje(activity,"Aviso","Servicio no Disponible.");
            }

        });
        return strings;
    }

    private void lanzaMensaje(Activity activity, String title, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(mensaje)
                .setTitle(title);
        builder.setIcon(R.drawable.favicon1);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /**
     *
     * @param activity
     */
    private static void logout(Activity activity) {
        DoctorDB database = new DoctorDB(activity, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
        SQLiteDatabase db = database.getWritableDatabase();
        db.execSQL("DELETE FROM UserLogged");
        db.execSQL("DELETE FROM Grupos");
        activity.finish();
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }


    public static void cerrarSesion(Activity activit){
        alertConfirm("Desea Cerrar sesion", activit);
    }


    private static void alertConfirm(String mensaje, final Activity context) {

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);

        alertDialogBuilder
                .setTitle("Mensaje de la Aplicación")
                .setMessage(mensaje)
                .setCancelable(false)
                .setIcon(R.drawable.favicon1)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                logout(context);
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                .show();
    }



}
