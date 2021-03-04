package com.kanpekiti.doctoresensucasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLogin;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskTknFCM;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.util.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.kanpekiti.doctoresensucasa.util.Const.PARAM_LAT;
import static com.kanpekiti.doctoresensucasa.util.Const.PARAM_LONG;

public class AmbulanciaActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, RoutingListener, LocationListener {

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;

    private Location locationGlobal;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private DoctorDB dataBase;

    private String perfil;
    private View mCustomView;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulancia);
        dataBase = new DoctorDB(AmbulanciaActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        List<Grupos> lstGrupo = Grupos.consultarGrupo(dataBase);
        for (Grupos grp : lstGrupo) {
            if (grp.getGprNombre().equals(Const.ROLE_DOCTOR)) {
                perfil = grp.getGprNombre();
                break;
            } else if (grp.getGprNombre().equals(Const.ROLE_CALL)) {
                perfil = grp.getGprNombre();
                break;
            }
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        startActivity(new Intent(AmbulanciaActivity.this, MainActivity.class));
                        break;
                    case R.id.membre:
                        // Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_2:
                        startActivity(new Intent(AmbulanciaActivity.this, MembresiaActivity.class));
                        break;
                }
                return true;
            }
        });

        if (savedInstanceState == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(AmbulanciaActivity.this);

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(AmbulanciaActivity.this);
        }

        Button button = findViewById(R.id.ambulancia);
        if(Const.ROLE_DOCTOR.equals(perfil) || Const.ROLE_CALL.equals(perfil)){
            button.setText("Enviar Ambulancia");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        if (checkLocationPermission()) {
            mMap = googleMap;
            mMap.setOnMyLocationButtonClickListener(AmbulanciaActivity.this);
            mMap.setOnMyLocationClickListener(AmbulanciaActivity.this);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(AmbulanciaActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                locationGlobal = location;
                                miUbicacion(locationGlobal);
                             }else {

                            }
                        }
                    });
        }
    }

        public void avisaAmbulancia(View view){
        if(locationGlobal != null){
            String titulo = "";
            String mensaje = "";
            if(Const.ROLE_DOCTOR.equals(perfil) || Const.ROLE_CALL.equals(perfil)){
                titulo = Const.TITULO_AMC;
                mensaje = Const.MENSAJE_AMC;
            }else {

                titulo = Const.TITULO_AM;
                mensaje = Const.MENSAJE_AM;
            }
            new AsynTaskTknFCM(AmbulanciaActivity.this).execute(Const.NOTIFICA_DOCTOR,
                    titulo,mensaje,locationGlobal.getLatitude()+"",
                    locationGlobal.getLongitude()+"");
        }

        }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(AmbulanciaActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AmbulanciaActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                ActivityCompat.requestPermissions(AmbulanciaActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(AmbulanciaActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Ubicacion"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    private  void miUbicacion(Location location){
        try {

            setLocationRemote(location.getLatitude(), location.getLongitude(),
                    "Mi Ubicacion", false);


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(AmbulanciaActivity.this, Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            //if(getIntent() != null && getIntent().getStringExtra(PARAM_LAT) != null
           //         && getIntent().getStringExtra(PARAM_LONG) != null){
            setLocationRemote(new Double("37.433024"),
                    new Double("-122.116459"),
                        "Ubicacion Paciente" , true);
           // }
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            StringBuffer strb = new StringBuffer("Mi ubicacion: ").append(address);
            TextView textView = findViewById(R.id.txtUbicacion);
            textView.setText(strb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Marker setLocationRemote(double latitud, double longitud,
                                   String title, boolean remote) {
        LatLng sydney = new LatLng(latitud, longitud);
       Marker maker = mMap.addMarker(new MarkerOptions().position(sydney).title(title));

        if(!remote)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        return maker;
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {

    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public void cerrarSesion(){
        AsynTaskLogin.cerrarSesion( AmbulanciaActivity.this);
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