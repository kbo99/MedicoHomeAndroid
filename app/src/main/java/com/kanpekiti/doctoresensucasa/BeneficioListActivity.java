package com.kanpekiti.doctoresensucasa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.vo.Beneficio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeneficioListActivity extends AppCompatActivity {

    private DoctorService doctorService;

    private Call<List<Beneficio>> lstDoctor;

    private DoctorDB database;

    private View mCustomView;

    private LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficio_list);


        database = new DoctorDB(BeneficioListActivity.this,DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
        UserLogged userTmp = UserLogged.consultarUsuario(database);
        getBenefico(userTmp.getUsername());

    }

    private void getBenefico(String user){
        doctorService = DoctorApi.doctorApi(BeneficioListActivity.this).create(DoctorService.class);
        lstDoctor = doctorService.findMyBenefico(user);
        lstDoctor.enqueue(new Callback<List<Beneficio>>() {
            @Override
            public void onResponse(Call<List<Beneficio>> call, Response<List<Beneficio>> response) {
                if(response.code() == 200){
                    List<Beneficio> respuesta = response.body();
                    llenarLista(respuesta, BeneficioListActivity.this);
                } else if(response.code() == 401){
                    UserLogged.deleteUser(database);
                    DoctorApi.setRetrofit(null);
                    DoctorApi.setToken(null);
                    Intent intent = new Intent(BeneficioListActivity.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }else {
                    returnToMain();
                }

            }

            @Override
            public void onFailure(Call<List<Beneficio>> call, Throwable t) {
                returnToMain();
            }
        });
    }
    public static void llenarLista(final List<Beneficio> favoritos, final Activity activity) {

        final ListView listCreditCard = (ListView)activity.findViewById(R.id.listBenefico);

        ArrayList<HashMap<String, Object>> listaParaAdaptador = new ArrayList<>();
        for (Beneficio benefi : favoritos) {

            HashMap<String, Object> info = new HashMap<>();

            info.put("nombreBeneficio", benefi.getBenNombre());
            info.put("beneficio", benefi);

            listaParaAdaptador.add(info);
        }

        final SimpleAdapter adapter = new SimpleAdapter(activity, listaParaAdaptador, R.layout.item_row_beneficio,
                new String[]{"nombreBeneficio"}, new int[]{R.id.tvNumeroTarjeta});

        listCreditCard.setAdapter(adapter);

        //region Click en la lista
        listCreditCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                String[] items = {"Eliminar"};

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Detalle Beneficio");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int item) {

                        HashMap<String, Object> objeto = (HashMap<String, Object>) listCreditCard.getItemAtPosition(position);

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        //endregion

    }


    private void returnToMain(){
        Intent intent = new Intent(BeneficioListActivity.this,
                MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}