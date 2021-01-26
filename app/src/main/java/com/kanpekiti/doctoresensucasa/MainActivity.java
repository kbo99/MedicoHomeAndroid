package com.kanpekiti.doctoresensucasa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;



import com.kanpekiti.doctoresensucasa.model.DoctorDB;




public class MainActivity extends AppCompatActivity {

    private View mCustomView;

    private LayoutInflater mInflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void beneficio(View view) {

        startActivity(new Intent(MainActivity.this, BeneficioListActivity.class));
    }

    public void initCall(View view){
        startActivity(new Intent(MainActivity.this, VideoCallActivity.class));
    }

    public void  asesoria(View view){
        startActivity(new Intent(MainActivity.this, AsesoriaMedicaActivity.class));
    }


    /**
     *
     * @param activity
     */
    public void logout(Activity activity) {
        DoctorDB database = new DoctorDB(MainActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
//        ConexionGenerica(activity,WSCerrarSesion(ConsultarAliado(database).getId()));
        SQLiteDatabase db = database.getWritableDatabase();
         db.execSQL("DELETE FROM UserLogged");
        activity.finish();
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    public void cerrarSesion(){
        alertConfirm("Desea Cerrar sesion", MainActivity.this);
    }


    public void alertConfirm(String mensaje, final Activity context) {

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);

        alertDialogBuilder
                .setTitle("Mensaje de la Aplicación")
                .setMessage(mensaje)
                .setCancelable(false)
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_new:
                cerrarSesion();
                return true;
            case R.id.sesioon:
                cerrarSesion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}