package com.kanpekiti.doctoresensucasa;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLlamada;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.util.Const;
import com.kanpekiti.doctoresensucasa.vo.Beneficio;
import com.kanpekiti.doctoresensucasa.vo.Doctor;
import com.kanpekiti.doctoresensucasa.vo.MedicoLlamada;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import static com.kanpekiti.doctoresensucasa.AmbulanciaActivity.MY_PERMISSIONS_REQUEST_LOCATION;
import static com.kanpekiti.doctoresensucasa.util.Const.PARAM_LAT;
import static com.kanpekiti.doctoresensucasa.util.Const.PARAM_LONG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallDoctorFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, RoutingListener, LocationListener {


    Spinner spinner;

    EditText editText;

    List<Doctor> listDoctor;

    String userTarget = null;

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;

    private Location locationGlobal;



    public CallDoctorFragment() {
        // Required empty public constructor
    }


    public static CallDoctorFragment newInstance(List<Doctor> listDoctor) {
        CallDoctorFragment fragment = new CallDoctorFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        fragment.setListDoctor(listDoctor);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {


            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        }

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_call_doctor, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapCall);

        Button bt_yes = (Button)rootView.findViewById(R.id.btnAsignar);
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onClickAsigna();
            }
        });
        mapFragment.onCreate(savedInstanceState);
        mapFragment.getMapAsync(this);

return rootView;
    }


    public  void llenarLista(final List<Doctor> favoritos) {

        ArrayList<HashMap<String, Object>> listaParaAdaptador = new ArrayList<>();
        for (Doctor benefi : favoritos) {

            HashMap<String, Object> info = new HashMap<>();

            info.put("nombreDoctor", benefi.getPersona().getPerNombre());
            info.put("docUser", benefi.getPersona().getPerTelefono());

            listaParaAdaptador.add(info);
        }

        final SimpleAdapter adapter = new SimpleAdapter(getActivity(), listaParaAdaptador, R.layout.item_row_doctor,
                new String[]{"nombreDoctor"}, new int[]{R.id.doctor});

        spinner.setAdapter(adapter);


        //endregion

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.planets_spinner);
        spinner.setOnItemSelectedListener(this);
        editText = view.findViewById(R.id.txtSintomas);
        llenarLista(listDoctor);

    }

    public List<Doctor> getListDoctor() {
        return listDoctor;
    }

    public void setListDoctor(List<Doctor> listDoctor) {
        this.listDoctor = listDoctor;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        HashMap<String, Object> selectedItem =
                (HashMap<String, Object>) adapterView.getSelectedItem();
        userTarget = (String) selectedItem.get("docUser");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onClickAsigna(){
        if(userTarget != null){
            MedicoLlamada medicoLlamada = new MedicoLlamada();
            medicoLlamada.setUserMedico(userTarget);
            medicoLlamada.setMllEstatus(Const.ESTATUS_LLAMADA_X_ATENDER);
            medicoLlamada.setLlpId( this.getActivity().getIntent().
                    getIntExtra(Const.PARAM_ID_LLAMADA, 0));
            medicoLlamada.setMllObservaciones(editText.getText().toString());
            new AsynTaskLlamada(this.getActivity(),medicoLlamada).execute("");
        }
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this.getActivity());
        if (checkLocationPermission()) {
            mMap = googleMap;
            mMap.setOnMyLocationButtonClickListener(CallDoctorFragment.this);
            mMap.setOnMyLocationClickListener(CallDoctorFragment.this);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
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
    public boolean checkLocationPermission() {
       if (ContextCompat.checkSelfPermission(this.getActivity(),
    Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {


            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        return false;
    } else {
        return true;
    }
}
    private  void miUbicacion(Location location){
        try {

            setLocationRemote(location.getLatitude(), location.getLongitude(),
                    "Mi Ubicacion", false);


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this.getActivity(), Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if(this.getActivity().getIntent() != null &&
                    this.getActivity().getIntent().getStringExtra(PARAM_LAT) != null
                    && this.getActivity().getIntent().getStringExtra(PARAM_LONG) != null){
                setLocationRemote(new Double(this.getActivity().getIntent().getStringExtra(PARAM_LAT)),
                        new Double(this.getActivity().getIntent().getStringExtra(PARAM_LONG)),
                        "Ubicacion Paciente", true);
            }

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
}