package com.kanpekiti.doctoresensucasa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.api.LoginApi;
import com.kanpekiti.doctoresensucasa.api.LoginService;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLogin;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.vo.Beneficio;
import com.kanpekiti.doctoresensucasa.vo.TokenUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void login(View view){

        //Recuperar de la vista
        EditText username=  findViewById(R.id.editTextEmail);
        EditText pass = findViewById(R.id.editTextPassword);
        if(!username.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() ){
            ProgressDialog dialogRec   = ProgressDialog.show(LoginActivity.this, "Doctores en su Casa", "Iniciando Sesion...", true);
            dialogRec.setIcon(R.drawable.favicon1);
            new AsynTaskLogin(this, dialogRec).execute(username.getText().toString(),
                    pass.getText().toString());
        }


    }


}