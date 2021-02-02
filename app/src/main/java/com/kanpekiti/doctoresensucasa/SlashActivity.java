package com.kanpekiti.doctoresensucasa;

import android.content.Intent;

import android.os.Bundle;

import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.UserLogged;


import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SlashActivity extends AppCompatActivity {

  private DoctorDB database;

  private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash);
        startAnimations();
        database = new DoctorDB(SlashActivity.this,DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
        splash();


    }



        private void consultaUserLog(){
            UserLogged userTmp = UserLogged.consultarUsuario(database);
            if(userTmp != null ){
                Intent intent = new Intent(SlashActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(SlashActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        }




    /**
     * Metodo para Empezar la animacion
     */
    public void startAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.traslate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);


    }
    public void splash(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                consultaUserLog();
            }
        };
        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }


}