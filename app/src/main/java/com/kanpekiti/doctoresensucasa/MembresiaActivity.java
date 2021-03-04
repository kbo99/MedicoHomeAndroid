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
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLogin;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.vo.Beneficio;
import com.kanpekiti.doctoresensucasa.vo.Membresia;
import com.kanpekiti.doctoresensucasa.vo.MembresiaCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembresiaActivity extends AppCompatActivity {

    private DoctorService doctorService;

    private Call<MembresiaCliente> membresiaCall;

    private DoctorDB dataBase;

    private MembresiaCliente membresia;

    private View mCustomView;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membresia);

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
                        startActivity(new Intent(MembresiaActivity.this, MainActivity.class));
                        break;
                    case R.id.membre:
                        // Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_2:
                        startActivity(new Intent(MembresiaActivity.this, MembresiaActivity.class));
                        break;
                }
                return true;
            }
        });

        dataBase = new DoctorDB(MembresiaActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);

        UserLogged userTmp = UserLogged.consultarUsuario(dataBase);


        ProgressDialog dialogRec = ProgressDialog.show(MembresiaActivity.this, "Aviso", "Cargando Informacion...", true);
        doctorService = DoctorApi.doctorApi(MembresiaActivity.this).create(DoctorService.class);
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
                    Intent intent = new Intent(MembresiaActivity.this,
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
        TextView textView = (TextView) findViewById(R.id.txtMemberId);
        TextView textEstatus = (TextView) findViewById(R.id.txtMemEstatus);
        TextView textSa = (TextView) findViewById(R.id.txtSaldo);
        TextView textFecha = (TextView) findViewById(R.id.txtFechaCorte);
        textEstatus.setText(membresia.getMecEstatus());
        textView.setText(membresia.getMecFolio());
        textSa.setText("$1,200.00");
        textFecha.setText("20/02/2021");


    }

    private void returnToMain() {
        Intent intent = new Intent(MembresiaActivity.this,
                MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void cerrarSesion(){
        AsynTaskLogin.cerrarSesion( MembresiaActivity.this);
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