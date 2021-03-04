package com.kanpekiti.doctoresensucasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.kanpekiti.doctoresensucasa.api.ChannelApi;
import com.kanpekiti.doctoresensucasa.api.ChannelService;
import com.kanpekiti.doctoresensucasa.api.DoctorApi;
import com.kanpekiti.doctoresensucasa.api.DoctorService;
import com.kanpekiti.doctoresensucasa.api.LoginApi;
import com.kanpekiti.doctoresensucasa.api.LoginService;
import com.kanpekiti.doctoresensucasa.asynTask.AsynTaskTknFCM;
import com.kanpekiti.doctoresensucasa.model.DoctorDB;
import com.kanpekiti.doctoresensucasa.model.Grupos;
import com.kanpekiti.doctoresensucasa.model.UserLogged;
import com.kanpekiti.doctoresensucasa.util.Const;
import com.kanpekiti.doctoresensucasa.vo.Doctor;
import com.kanpekiti.doctoresensucasa.vo.LlamadaPendiente;
import com.kanpekiti.doctoresensucasa.vo.LoggerRecyclerView;
import com.kanpekiti.doctoresensucasa.vo.MedicoLlamada;
import com.kanpekiti.doctoresensucasa.vo.NotificacionFcm;
import com.kanpekiti.doctoresensucasa.vo.TokenUser;
import com.kanpekiti.doctoresensucasa.vo.VideoCallChannel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kanpekiti.doctoresensucasa.AmbulanciaActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class VideoCallActivity  extends AppCompatActivity {

    private static final String TAG = VideoCallActivity.class.getSimpleName();

    private static final int PERMISSION_REQ_ID = 22;

    private ChannelService channelService;

    private Call<VideoCallChannel> channelCall;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private RtcEngine mRtcEngine;
    private boolean mCallEnd;
    private boolean mMuted;

    private FrameLayout mLocalContainer;
    private RelativeLayout mRemoteContainer;
    private FrameLayout mRemoteContainerCall;
    private VideoCanvas mLocalVideo;
    private VideoCanvas mRemoteVideo;
    private VideoCanvas mRemoteVideoCall;

    private ImageView mCallBtn;
    private ImageView mMuteBtn;
    private ImageView mSwitchCameraBtn;

    private View mCustomView;

    private LayoutInflater mInflater;

    private ProgressDialog dialogRec;

    private boolean atendio = false;

    private CountDownTimer countDownTimer;


    private DoctorDB dataBase;

    Call<List<Doctor>> listCall;

    private DoctorService doctorService;

    private FusedLocationProviderClient fusedLocationClient;

    private Location locationGlobal;

    Call<LlamadaPendiente> llamadaPendienteCall;

    private boolean isDoctor = false;
    private boolean isOperador = false;

    Call<MedicoLlamada> medicoLlamadaCall;






    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {

        @Override
        public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //mLogView.logI("Join channel success, uid: " + (uid & 0xFFFFFFFFL));

                }
            });
        }


        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //mLogView.logI("First remote video decoded, uid: " + (uid & 0xFFFFFFFFL));
                    if(mRemoteVideo == null){
                        setupRemoteVideo(uid);
                    }else {
                        setupRemoteVideoExtra(uid);
                    }

                }
            });
        }


        @Override
        public void onUserOffline(final int uid, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  mLogView.logI("User offline, uid: " + (uid & 0xFFFFFFFFL));
                    onRemoteUserLeft(uid);
                }
            });
        }
    };

    private void setupRemoteVideo(int uid) {
        ViewGroup parent =   mRemoteContainer;
        if (parent.indexOfChild(mLocalVideo.view) > -1) {
            parent = mLocalContainer;
        }



        if (mRemoteVideo != null) {
            return;
        }

        SurfaceView view = RtcEngine.CreateRendererView(getBaseContext());
        view.setZOrderMediaOverlay(parent == mLocalContainer);
        parent.addView(view);
        mRemoteVideo = new VideoCanvas(view, VideoCanvas.RENDER_MODE_HIDDEN, uid);
        // Initializes the video view of a remote user.
        mRtcEngine.setupRemoteVideo(mRemoteVideo);
        if(getIntent() != null && getIntent().getStringExtra(Const.DOCTOR_PARAM) == null){
            this.atendio = true;
            dialogRec.dismiss();
            countDownTimer.onFinish();
        }



    }



    private void onRemoteUserLeft(int uid) {
        if ((mRemoteVideo != null && mRemoteVideo.uid == uid) ||
                (mRemoteVideoCall != null && mRemoteVideoCall.uid == uid)) {
            removeFromParent(mRemoteVideo);
            removeFromParent(mRemoteVideoCall);
            // Destroys remote view
            mRemoteVideo = null;
            mRemoteVideoCall = null;
            mCallEnd = false;
            changeBotun();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        initUI();

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        mCustomView = mInflater.inflate(R.layout.layout, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(VideoCallActivity.this);

        dataBase = new DoctorDB(VideoCallActivity.this, DoctorDB.databaseName,
                DoctorDB.databaseFactory, DoctorDB.databaseVersion);

        //Recuperar de la vista
        doctorService = DoctorApi.doctorApi(VideoCallActivity.this).create(DoctorService.class);
        List<Grupos> lstGrupo = Grupos.consultarGrupo(dataBase);
        for (Grupos grp : lstGrupo) {
            if (grp.getGprNombre().equals(Const.ROLE_CALL)) {
                ImageView imageView = findViewById(R.id.addDoctor);
                imageView.setVisibility(View.VISIBLE);
                isOperador = true;
                break;
            } else if(grp.getGprNombre().equals(Const.ROLE_DOCTOR)){
                isDoctor = true;
            }
        }

        determinaLlamadaPerfil();
 }

 private  void determinaLlamadaPerfil(){
     //si no es doctor u operador es paciente
     if(!isDoctor && !isOperador){
         if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                 checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                 checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
             initEngineAndJoinChannel();


         }
         splash();
     }else if(isOperador) {
         findLlamadaPendiemte();
     } else if(isDoctor){
         findLlamadaDoctor();
     }

 }

    private void findLlamadaDoctor() {
        NotificacionFcm notificacionFcm = buildLlamadaPendiente();
        medicoLlamadaCall = doctorService.callDoctor(notificacionFcm);
        medicoLlamadaCall.enqueue(new Callback<MedicoLlamada>() {
            @Override
            public void onResponse(Call<MedicoLlamada> call, Response<MedicoLlamada> response) {
                if(response.code() == 200){
                    MedicoLlamada medicoLlamada = response.body();
                    if(medicoLlamada.getMllEstatus().equals(Const.ESTATUS_LLAMADA_ATENDIDA_DOCTOR)){
                        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                                checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
                            initEngineAndJoinChannel();


                        }
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(VideoCallActivity.this);

                        builder.setMessage("Llamada ya fue atendida")
                                .setTitle("Aviso").setIcon(R.drawable.btn_endcall);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        changeBotun();
                    }
                }
            }

            @Override
            public void onFailure(Call<MedicoLlamada> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoCallActivity.this);

                builder.setMessage("Llamada ya fue atendida")
                        .setTitle("Aviso").setIcon(R.drawable.btn_endcall);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                changeBotun();
            }
        });
    }

    private NotificacionFcm buildLlamadaPendiente(){
        UserLogged user = UserLogged.consultarUsuario(dataBase);
        NotificacionFcm notificacionFcm = new NotificacionFcm();
        notificacionFcm.setUsuUsuario(user.getUsername());
        notificacionFcm.setIdLlamada(getIntent().getIntExtra(Const.PARAM_ID_LLAMADA,0));
        return notificacionFcm;
    }
    private void findLlamadaPendiemte() {
        NotificacionFcm notificacionFcm = buildLlamadaPendiente();
        llamadaPendienteCall = doctorService.callOperador(notificacionFcm);
        llamadaPendienteCall.enqueue(new Callback<LlamadaPendiente>() {
            @Override
            public void onResponse(Call<LlamadaPendiente> call, Response<LlamadaPendiente> response) {
                if(response.code() == 200){
                    LlamadaPendiente llamadaPendiente = response.body();
                    if((!llamadaPendiente.getUsuAtiende().equals(notificacionFcm.getUsuUsuario()) &&
                            llamadaPendiente.getLlpEstatus().equals(Const.ESTATUS_LLAMADA_ATENDIDA))
                            || llamadaPendiente.getLlpEstatus().equals(Const.ESTATUS_LLAMADA_ATENDIDA_FIN)
                    || llamadaPendiente.getLlpEstatus().equals(Const.ESTATUS_LLAMADA_NO_ATENDIDA)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(VideoCallActivity.this);

                        builder.setMessage("Llamada ya fue atendida")
                                .setTitle("Aviso").setIcon(R.drawable.btn_endcall);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                    }else {
                        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                                checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
                            initEngineAndJoinChannel();


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LlamadaPendiente> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoCallActivity.this);

                builder.setMessage("Conexion no exitosa")
                        .setTitle("Aviso").setIcon(R.drawable.btn_endcall);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        startActivity(new Intent(VideoCallActivity.this, MembresiaActivity.class));
                    }
                });
            }
        });

    }


    private void initUI() {
        mLocalContainer = findViewById(R.id.local_video_view_container);
        mRemoteContainer = findViewById(R.id.remote_video_view_container);
        mRemoteContainerCall = findViewById(R.id.local_video_view_container_extra);
        mCallBtn = findViewById(R.id.btn_call);
        mMuteBtn = findViewById(R.id.btn_mute);
        mSwitchCameraBtn = findViewById(R.id.btn_switch_camera);


        // Sample logs are optional.
        showSampleLogs();
    }

    private void showSampleLogs() {

    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                showLongToast("Need permissions " + Manifest.permission.RECORD_AUDIO +
                        "/" + Manifest.permission.CAMERA + "/" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
                finish();
                return;
            }

            initEngineAndJoinChannel();
        }
    }

    private void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initEngineAndJoinChannel() {
       if(checkLocationPermission()){
           fusedLocationClient.getLastLocation()
                   .addOnSuccessListener(VideoCallActivity.this, new OnSuccessListener<Location>() {
                       @Override
                       public void onSuccess(Location location) {
                           if (location != null) {
                               locationGlobal = location;
                               initializeEngine();
                               setupVideoConfig();
                               setupLocalVideo();
                               joinChannel();
                           }else {

                           }
                       }
                   });
       }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(VideoCallActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(VideoCallActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                ActivityCompat.requestPermissions(VideoCallActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(VideoCallActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    private void initializeEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private void setupVideoConfig() {
        mRtcEngine.enableVideo();

        // Please go to this page for detailed explanation
        // https://docs.agora.io/en/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_rtc_engine.html#af5f4de754e2c1f493096641c5c5c1d8f
        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x360,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
    }

    private void setupLocalVideo() {

        SurfaceView view = RtcEngine.CreateRendererView(getBaseContext());
        view.setZOrderMediaOverlay(true);
        mLocalContainer.addView(view);
        mLocalVideo = new VideoCanvas(view, VideoCanvas.RENDER_MODE_HIDDEN, 0);
        mRtcEngine.setupLocalVideo(mLocalVideo);
        if(getIntent() != null && getIntent().getStringExtra(Const.DOCTOR_PARAM) == null){
            splash();
        }

    }

    private void setupRemoteVideoExtra(int uid) {
        SurfaceView view = RtcEngine.CreateRendererView(getBaseContext());
        view.setZOrderMediaOverlay(true);
        mRemoteContainerCall.addView(view);
        mRemoteVideoCall = new VideoCanvas(view, VideoCanvas.RENDER_MODE_HIDDEN, uid);
        mRtcEngine.setupRemoteVideo(mRemoteVideoCall);


    }

    private void joinChannel() {
        if(getIntent() != null && getIntent().getStringExtra(Const.DOCTOR_PARAM) == null){
            dialogRec = ProgressDialog.show(VideoCallActivity.this, "Video llamada", "Enlazando Video Llamada...", true);
            dialogRec.setIcon(R.drawable.btn_startcall);

            new AsynTaskTknFCM(VideoCallActivity.this).execute(Const.NOTIFICA_DOCTOR,
                    Const.TITULO_VI,Const.MENSAJE_VI,
                    locationGlobal.getLatitude()+"",locationGlobal.getLongitude()+"");
        }

        if(getIntent() != null && getIntent().getStringExtra(Const.CANAL) != null &&
                !getIntent().getStringExtra(Const.CANAL).isEmpty()){
            ImageView imageView = findViewById(R.id.addDoctor);
            imageView.setVisibility(View.VISIBLE);
        }


        mRtcEngine.joinChannel("de23719d4dd643c4bf17f484ddfdbdfc",
                "channelAgora", "Extra Optional Data", 0);

    }

    public void splash(){
         countDownTimer = new CountDownTimer(60000, 1000) {


            @Override
            public void onTick(long l) {
               // dialogRec = ProgressDialog.show(VideoCallActivity.this, "Video llamada", "Buscando Doctores Disponibles...", true);
            }

            public void onFinish() {

               if(!atendio){
                  if(dialogRec != null)
                      dialogRec.dismiss();

                   mCallEnd = false;
                   changeBotun();
                   AlertDialog.Builder builder = new AlertDialog.Builder(VideoCallActivity.this);

                   builder.setMessage("No hay Doctores Disponibles")
                           .setTitle("Aviso").setIcon(R.drawable.btn_endcall);
                   builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.dismiss();
                       }
                   });
                   AlertDialog dialog = builder.create();
                   dialog.show();
               }


            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mCallEnd) {
            leaveChannel();
        }
        RtcEngine.destroy();
    }

    private void leaveChannel() {
        if(mRtcEngine != null)
            mRtcEngine.leaveChannel();
    }

    public void onLocalAudioMuteClicked(View view) {
        mMuted = !mMuted;
        // Stops/Resumes sending the local audio stream.
        mRtcEngine.muteLocalAudioStream(mMuted);
        int res = mMuted ? R.drawable.btn_mute : R.drawable.btn_unmute;
        mMuteBtn.setImageResource(res);
    }

    public void onSwitchCameraClicked(View view) {
        // Switches between front and rear cameras.
        mRtcEngine.switchCamera();
    }

    public void onCallClicked(View view) {
        changeBotun();
    }

    private void changeBotun(){
        if (mCallEnd) {
            startCall();
            mCallEnd = false;
            mCallBtn.setImageResource(R.drawable.btn_endcall);
        } else {
            endCall();
            mCallEnd = true;
            mCallBtn.setImageResource(R.drawable.btn_startcall);
        }

        showButtons(!mCallEnd);
    }

    private void startCall() {
        determinaLlamadaPerfil();

    }

    private void endCall() {
        removeFromParent(mLocalVideo);
        mLocalVideo = null;
        removeFromParent(mRemoteVideo);
        mRemoteVideo = null;
        removeFromParent(mRemoteVideoCall);
        mRemoteVideoCall = null;
        leaveChannel();
    }

    private void showButtons(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        mMuteBtn.setVisibility(visibility);
        mSwitchCameraBtn.setVisibility(visibility);
    }

    private ViewGroup removeFromParent(VideoCanvas canvas) {
        if (canvas != null) {
            ViewParent parent = canvas.view.getParent();
            if (parent != null) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(canvas.view);
                return group;
            }
        }
        return null;
    }

    private void switchView(VideoCanvas canvas) {
        ViewGroup parent = removeFromParent(canvas);
        if (parent == mLocalContainer) {
            if (canvas.view instanceof SurfaceView) {
                ((SurfaceView) canvas.view).setZOrderMediaOverlay(false);
            }
            mRemoteContainer.addView(canvas.view);
        } else if (parent == mRemoteContainer) {
            if (canvas.view instanceof SurfaceView) {
                ((SurfaceView) canvas.view).setZOrderMediaOverlay(true);
            }
            mLocalContainer.addView(canvas.view);
        }
    }

    public void onLocalContainerClick(View view) {
        switchView(mLocalVideo);
        switchView(mRemoteVideo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onDoctorRequerid(View view){

        listCall = doctorService.findDoctores();
        listCall.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if(response.code() == 200){
                    List<Doctor> respuesta = response.body();
                    DialogFragment newFragment = CallDoctorFragment.newInstance(respuesta);
                    newFragment.show(getSupportFragmentManager(),"frmTarjeta");
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {

            }
        });

    }




}