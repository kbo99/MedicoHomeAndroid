package com.kanpekiti.doctoresensucasa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLogin;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskTknFCM;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.util.Const;

import java.util.List;

import static com.kanpekiti.doctoresensucasa.UbicacionActivity.MY_PERMISSIONS_REQUEST_LOCATION;


public class MainActivity extends AppCompatActivity {

    private View mCustomView;

    private LayoutInflater mInflater;

    private DoctorDB dataBase;

    private boolean isDoctor = false;

    private TextView nombre;




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

         dataBase = new DoctorDB(MainActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);

        createNotificationChannel();

        checkLocationPermission();

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
        initComp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void initComp(){
        UserLogged userTmp = UserLogged.consultarUsuario(dataBase);

        nombre = findViewById(R.id.welcomeTXT);
         nombre.setText("Bienvenido, "+ userTmp.getNombre());
    }

    public void beneficio(View view) {

        startActivity(new Intent(MainActivity.this, BeneficioListActivity.class));
    }

    public void initCall(View view){

        List<Grupos> lstGrupo = Grupos.consultarGrupo(dataBase);
        for (Grupos grp : lstGrupo) {
            if (grp.getGprNombre().equals(Const.ROLE_DOCTOR)) {
                this.isDoctor = true;
                break;
            }
        }
        if(!this.isDoctor){
            startActivity(new Intent(MainActivity.this, VideoCallActivity.class));
        }

    }

    public void  asesoria(View view){
        startActivity(new Intent(MainActivity.this, AsesoriaMedicaActivity.class));
    }

    public void maps(View view){
        startActivity(new Intent(MainActivity.this, AmbulanciaActivity.class));
    }




    public void cerrarSesion(){
        AsynTaskLogin.cerrarSesion( MainActivity.this);
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
            channelVoice.setDescription("LLamada");

            NotificationChannel channelAmbulancia = new NotificationChannel(Const.CHANNEL_ID_AM,
                    Const.CHANNEL_NAME_AM, NotificationManager.IMPORTANCE_HIGH);
            channelAmbulancia.setDescription("Ambulancia");

            NotificationChannel channelMedicoAthome = new NotificationChannel(Const.CHANNEL_ID_DAH,
                    Const.CHANNEL_NAME_DH, NotificationManager.IMPORTANCE_HIGH);
            channelMedicoAthome.setDescription("Medico At Home");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channelVoice);
            notificationManager.createNotificationChannel(channelAmbulancia);
            notificationManager.createNotificationChannel(channelMedicoAthome);
        }
    }

    public void getAmbulancia(View view){
        startActivity(new Intent(MainActivity.this, AmbulanciaActivity.class));
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                ActivityCompat.requestPermissions( MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    public void medicoAtHome(View view){
        startActivity(new Intent(MainActivity.this, MedicoActivity.class));
    }







}