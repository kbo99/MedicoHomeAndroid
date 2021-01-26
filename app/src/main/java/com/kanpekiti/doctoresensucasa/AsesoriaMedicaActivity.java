package com.kanpekiti.doctoresensucasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class AsesoriaMedicaActivity extends AppCompatActivity {

    private View mCustomView;

    private LayoutInflater mInflater;

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public void getChatView(View view){
        startActivity(new Intent(AsesoriaMedicaActivity.this, ChatActivity.class));
    }

    public void getCallView(View view){
        startActivity(new Intent(AsesoriaMedicaActivity.this, LlamadaVozActivity.class));
    }

    public void getVideoCall(View view){
        startActivity(new Intent(AsesoriaMedicaActivity.this, VideoCallActivity.class));
    }
}