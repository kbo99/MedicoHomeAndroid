package com.kanpekiti.doctoresensucasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLogin;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.util.Const;

import java.util.List;

public class AsesoriaMedicaActivity extends AppCompatActivity {

    private View mCustomView;

    private LayoutInflater mInflater;

    private DoctorDB dataBase;

    private boolean isDoctor = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asesoria_medica);
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
                        startActivity(new Intent(AsesoriaMedicaActivity.this, MainActivity.class));
                        break;
                    case R.id.membre:
                        // Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_2:
                        startActivity(new Intent(AsesoriaMedicaActivity.this, MembresiaActivity.class));
                        break;
                }
                return true;
            }
        });

        dataBase = new DoctorDB(AsesoriaMedicaActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);

        List<Grupos> lstGrupo = Grupos.consultarGrupo(dataBase);
        for (Grupos grp : lstGrupo) {
            if (grp.getGprNombre().equals(Const.ROLE_DOCTOR)) {
                this.isDoctor = true;
                break;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public void getChatView(View view){
        if(!this.isDoctor){
            startActivity(new Intent(AsesoriaMedicaActivity.this, ChatActivity.class));
        }

    }

    public void getCallView(View view){
        if(!this.isDoctor){
            startActivity(new Intent(AsesoriaMedicaActivity.this, LlamadaVozActivity.class));
        }

    }

    public void getVideoCall(View view){

        if(!this.isDoctor){
            startActivity(new Intent(AsesoriaMedicaActivity.this, VideoCallActivity.class));
        }

    }


    public void cerrarSesion(){
        AsynTaskLogin.cerrarSesion( AsesoriaMedicaActivity.this);
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