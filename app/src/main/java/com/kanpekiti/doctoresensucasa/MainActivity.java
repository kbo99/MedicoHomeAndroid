package com.kanpekiti.doctoresensucasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskTknFCM;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.util.Const;


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

        createNotificationChannel();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        startActivity(new Intent(MainActivity.this, PerfilActivity.class));
                        break;
                    case R.id.membre:
                        Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_2:
                        startActivity(new Intent(MainActivity.this, MembresiaActivity.class));
                        break;
                }
                return true;
            }
        });
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

    public void maps(View view){
        startActivity(new Intent(MainActivity.this, AmbulanciaActivity.class));
    }


    /**
     *
     * @param activity
     */
    public void logout(Activity activity) {
        DoctorDB database = new DoctorDB(MainActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
        SQLiteDatabase db = database.getWritableDatabase();
         db.execSQL("DELETE FROM UserLogged");
        db.execSQL("DELETE FROM Grupos");
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Const.CHANNEL_ID_VC,
                    Const.CHANNEL_NAME_VC, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Video LLamada Entrante");

            NotificationChannel channelVoice = new NotificationChannel(Const.CHANNEL_ID_LL,
                    Const.CHANNEL_NAME_LL, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("LLamada");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channelVoice);
        }
    }

    public void getAmbulancia(View view){
        startActivity(new Intent(MainActivity.this, AmbulanciaActivity.class));
    }







}