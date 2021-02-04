package com.kanpekiti.doctoresensucasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLogin;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.vo.MembresiaCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMisDatos extends AppCompatActivity {

    private DoctorService doctorService;

    private Call<MembresiaCliente> membresiaCall;

    private DoctorDB dataBase;
    private View mCustomView;

    private LayoutInflater mInflater;

    private MembresiaCliente membresia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        startActivity(new Intent(ActivityMisDatos.this, MainActivity.class));
                        break;
                    case R.id.membre:
                        // Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_2:
                        startActivity(new Intent(ActivityMisDatos.this, MembresiaActivity.class));
                        break;
                }
                return true;
            }
        });

        dataBase = new DoctorDB(ActivityMisDatos.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);

        UserLogged userTmp = UserLogged.consultarUsuario(dataBase);


        ProgressDialog dialogRec = ProgressDialog.show(ActivityMisDatos.this, "Aviso", "Cargando Informacion...", true);
        doctorService = DoctorApi.doctorApi(ActivityMisDatos.this).create(DoctorService.class);
        membresiaCall = doctorService.findMembresia(userTmp.getUsername());
        membresiaCall.enqueue(new Callback<MembresiaCliente>() {
            @Override
            public void onResponse(Call<MembresiaCliente> call, Response<MembresiaCliente> response) {
                dialogRec.dismiss();
                if(response.code() == 200){
                    membresia = response.body();
                    loadTxt();
                } else if(response.code() == 401){
                    UserLogged.deleteUser(dataBase);
                    DoctorApi.setRetrofit(null);
                    DoctorApi.setToken(null);
                    Intent intent = new Intent(ActivityMisDatos.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }else {
                    returnToMain();
                }

            }

            @Override
            public void onFailure(Call<MembresiaCliente> call, Throwable t) {
                dialogRec.dismiss();
            }
        });
    }


    private void loadTxt() {

    }

    private void returnToMain() {
        Intent intent = new Intent(ActivityMisDatos.this,
                MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void cerrarSesion(){
        AsynTaskLogin.cerrarSesion( ActivityMisDatos.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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


}