package com.kanpekiti.doctoresensucasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;

import com.google.maps.PendingResult;
import com.google.maps.RoadsApi;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.SnappedPoint;
import com.google.maps.model.SpeedLimit;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskLogin;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskTknFCM;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.util.Const;
import com.kanpekiti.doctoresensucasa.vo.Doctor;

import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MedicoActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;

    private Location locationGlobal;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private View mCustomView;

    private LayoutInflater mInflater;

    private DoctorDB dataBase;

    private String perfil;

    Spinner spinner;


    /**
     * The API context used for the Roads and Geocoding web service APIs.
     */
    public GeoApiContext mContext;

    /**
     * The number of points allowed per API request. This is a fixed value.
     */
    private static final int PAGE_SIZE_LIMIT = 100;

    /**
     * Define the number of data points to re-send at the start of subsequent requests. This helps
     * to influence the API with prior data, so that paths can be inferred across multiple requests.
     * You should experiment with this value for your use-case.
     */
    private static final int PAGINATION_OVERLAP = 5;



    private ProgressBar mProgressBar;

    List<com.google.maps.model.LatLng> mCapturedLocations;

    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    PolylineOptions polyline = new PolylineOptions();
    List<SnappedPoint> mSnappedPoints;

    AsyncTask<Void, Void, List<SnappedPoint>> mTaskSnapToRoads =
            new AsyncTask<Void, Void, List<SnappedPoint>>() {
                @Override
                protected void onPreExecute() {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setIndeterminate(true);
                }

                @Override
                protected List<SnappedPoint> doInBackground(Void... params) {
                    try {
                        return snapToRoads(mContext);
                    } catch (final Exception ex) {

                        ex.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(List<SnappedPoint> snappedPoints) {
                    mSnappedPoints = snappedPoints;
                    mProgressBar.setVisibility(View.INVISIBLE);


                    com.google.android.gms.maps.model.LatLng[] mapPoints =
                            new com.google.android.gms.maps.model.LatLng[mSnappedPoints.size()];
                    int i = 0;
                    LatLngBounds.Builder bounds = new LatLngBounds.Builder();
                    for (SnappedPoint point : mSnappedPoints) {
                        mapPoints[i] = new com.google.android.gms.maps.model.LatLng(point.location.lat,
                                point.location.lng);
                        bounds.include(mapPoints[i]);
                        i += 1;
                    }

                    mMap.addPolyline(new PolylineOptions().add(mapPoints).color(Color.BLUE));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 0));
                }
            };

    private Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        dataBase = new DoctorDB(MedicoActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);

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
                        startActivity(new Intent(MedicoActivity.this, MainActivity.class));
                        break;
                    case R.id.membre:
                        // Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_2:
                        startActivity(new Intent(MedicoActivity.this, MembresiaActivity.class));
                        break;
                }
                return true;
            }
        });
        mContext = new GeoApiContext.Builder()
                .apiKey(("AIzaSyD2EHNlBLalssfak0mH6KLW4J0QeUBSFck")).build();
        if (savedInstanceState == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(MedicoActivity.this);

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(MedicoActivity.this);
        }

        Button button = findViewById(R.id.medicath);
        if(Const.ROLE_DOCTOR.equals(perfil) || Const.ROLE_CALL.equals(perfil)){
            button.setText("Enviar Medico");
        }

        spinner = findViewById(R.id.rout_spinner);
        spinner.setOnItemSelectedListener(MedicoActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        HashMap<String, Object> selectedItem =
                (HashMap<String, Object>) adapterView.getSelectedItem();
        DirectionsRoute ruta = (DirectionsRoute) selectedItem.get("ruta");
        builder = new LatLngBounds.Builder();
        polyline = new PolylineOptions();
        for(com.google.maps.model.LatLng leg : ruta.overviewPolyline.decodePath()) {
            com.google.android.gms.maps.model.LatLng mapPoint =
                    new com.google.android.gms.maps.model.LatLng(leg.lat, leg.lng);

            builder.include(mapPoint);
            polyline.add(mapPoint);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /**
     * Snaps the points to their most likely position on roads using the Roads API.
     */
    private List<SnappedPoint> snapToRoads(GeoApiContext context) throws Exception {
        List<SnappedPoint> snappedPoints = new ArrayList<>();

        int offset = 0;
        while (offset < mCapturedLocations.size()) {
            // Calculate which points to include in this request. We can't exceed the APIs
            // maximum and we want to ensure some overlap so the API can infer a good location for
            // the first few points in each request.
            if (offset > 0) {
                offset -= PAGINATION_OVERLAP;   // Rewind to include some previous points
            }
            int lowerBound = offset;
            int upperBound = Math.min(offset + PAGE_SIZE_LIMIT, mCapturedLocations.size());

            // Grab the data we need for this page.
            com.google.maps.model.LatLng[] page = mCapturedLocations
                    .subList(lowerBound, upperBound)
                    .toArray(new com.google.maps.model.LatLng[upperBound - lowerBound]);

            // Perform the request. Because we have interpolate=true, we will get extra data points
            // between our originally requested path. To ensure we can concatenate these points, we
            // only start adding once we've hit the first new point (i.e. skip the overlap).
            SnappedPoint[] points = RoadsApi.snapToRoads(context, true, page).await();
            boolean passedOverlap = false;
            for (SnappedPoint point : points) {
                if (offset == 0 || point.originalIndex >= PAGINATION_OVERLAP) {
                    passedOverlap = true;
                }
                if (passedOverlap) {
                    snappedPoints.add(point);
                }
            }

            offset = upperBound;
        }

        return snappedPoints;
    }

    /**
     * Handles the Snap button-click event, running the demo snippet {@link #snapToRoads}.
     */
    public void onSnapToRoadsButtonClick(View view) {
        mMap.addPolyline(polyline.color(Color.RED));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
    }



   @Override
    public void onMapReady(GoogleMap googleMap) {


        if (checkLocationPermission()) {
            mMap = googleMap;
            mMap.setOnMyLocationButtonClickListener(MedicoActivity.this);
            mMap.setOnMyLocationClickListener(MedicoActivity.this);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(MedicoActivity.this, new OnSuccessListener<Location>() {
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
            new AsynTaskTknFCM(MedicoActivity.this).execute(Const.NOTIFICA_DOCTOR,
                    Const.TITULO_AM,Const.MENSAJE_AM,locationGlobal.getLatitude()+"",
                    locationGlobal.getLongitude()+"");
        }

    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MedicoActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MedicoActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                ActivityCompat.requestPermissions(MedicoActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MedicoActivity.this,
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

            marker = setLocationRemote(location.getLatitude(), location.getLongitude(),
                    "Mi Ubicacion", false);


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(MedicoActivity.this, Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


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

   public void cerrarSesion(){
        AsynTaskLogin.cerrarSesion( MedicoActivity.this);
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

    public void avisaMedico(View view){
        calculateDirections();
        if(locationGlobal != null){
            String titulo = "";
            String mensaje = "";
            if(Const.ROLE_DOCTOR.equals(perfil) || Const.ROLE_CALL.equals(perfil)){
                titulo = Const.TITULO_AMC;
                mensaje = Const.MENSAJE_AM;
             }else {

                titulo = Const.TITULO_DOC;
                mensaje = Const.MENSAJE_DC;
            }
            new AsynTaskTknFCM(MedicoActivity.this).execute(Const.NOTIFICA_DOCTOR,
                    titulo,mensaje,locationGlobal.getLatitude()+"",
                    locationGlobal.getLongitude()+"");
        }

    }

    private void calculateDirections(){

    //if(getIntent() != null && getIntent().getStringExtra(Const.PARAM_LAT) != null
          //      && getIntent().getStringExtra(Const.PARAM_LONG) != null){
             Marker paciente =  setLocationRemote(new Double("37.433024"),
                     new Double("-122.116459"),
                     "Ubicacion Paciente" , true);
             com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                     paciente.getPosition().latitude,
                     paciente.getPosition().longitude
             );
             DirectionsApiRequest directions = new DirectionsApiRequest(mContext);

             directions.alternatives(true);
             directions.origin(
                     new com.google.maps.model.LatLng(
                             marker.getPosition().latitude,
                             marker.getPosition().longitude
                     )
             );

             directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
                 @Override
                 public void onResult(DirectionsResult result) {
                     llenarLista(result.routes);


                  }

                 @Override
                 public void onFailure(Throwable e) {
                     Log.e("TAG", "onFailure: " + e.getMessage() );

                 }
             });
       //   }


    }

    @Override
    public void onLocationChanged(Location location) {
    if(mCapturedLocations == null){
       mCapturedLocations = new ArrayList<>();
     }
        mCapturedLocations.add(new com.google.maps.model.LatLng(location.getLatitude(),location.getLongitude()));
        mTaskSnapToRoads.execute();
    }

    public  void llenarLista(final DirectionsRoute [] favoritos) {

        ArrayList<HashMap<String, Object>> listaParaAdaptador = new ArrayList<>();
        for(DirectionsRoute ruta : favoritos){


            HashMap<String, Object> info = new HashMap<>();

            info.put("rutaSummary", ruta.summary);
            info.put("ruta", ruta);

            listaParaAdaptador.add(info);
        }

        final SimpleAdapter adapter = new SimpleAdapter(MedicoActivity.this, listaParaAdaptador, R.layout.item_row_doctor,
                new String[]{"rutaSummary"}, new int[]{R.id.doctor});
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setAdapter(adapter);
            }
        });



        //endregion

    }
}