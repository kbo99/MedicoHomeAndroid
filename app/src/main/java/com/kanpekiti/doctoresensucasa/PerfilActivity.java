package com.kanpekiti.doctoresensucasa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kanpekiti.doctoresensucasa.vo.Beneficio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        llenarLista(PerfilActivity.this);
    }


    public  void llenarLista( final Activity activity) {

        final ListView listCreditCard = (ListView)activity.findViewById(R.id.listBenefico);

        ArrayList<HashMap<String, Object>> listaParaAdaptador = new ArrayList<>();
           HashMap<String, Object> info = new HashMap<>();

            info.put("perfil", "Mis Datos");
            info.put("action", "1");

        HashMap<String, Object> config = new HashMap<>();

        config.put("perfil", "Configuracion");
        config.put("action", "2");

        HashMap<String, Object> sesion = new HashMap<>();

        sesion.put("perfil", "Cerrar Sesion");

        sesion.put("action", "3");

        listaParaAdaptador.add(info);
        listaParaAdaptador.add(config);
        listaParaAdaptador.add(sesion);


        final SimpleAdapter adapter = new SimpleAdapter(activity, listaParaAdaptador, R.layout.item_row_perfil,
                new String[]{"perfil"}, new int[]{R.id.tvNumeroTarjeta});

        listCreditCard.setAdapter(adapter);

        //region Click en la lista
        listCreditCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                startActivity(new Intent(PerfilActivity.this, ActivityMisDatos.class));

            }
        });
        //endregion

    }
}