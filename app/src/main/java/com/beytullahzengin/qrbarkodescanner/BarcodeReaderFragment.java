package com.beytullahzengin.qrbarkodescanner;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BarcodeReaderFragment extends Fragment {

    Date currentTime;
    SimpleDateFormat df;

    private static View view;
    private static ListView_Adapter adapter;
    private static ListView listView;

    public BarcodeReaderFragment() {
        // Required empty public constructor

    }

    ArrayList<Item_Model> user_list = new ArrayList<>();
    private final Object mCameraLock = new Object();
    private AnimationDrawable anim,anim2,anim3;
    private Camera mCamera;
    private boolean mFlashMode = false;
    private ImageView image,help,flashToggle;


    private TextView t1,t2,t3;
    private SurfaceView cameraSurfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    final int requestCameraPermissionID = 1001;


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(cameraSurfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barcode_reader, container, false);

        cameraSurfaceView = view.findViewById(R.id.cameraSurfaceView);
        barcodeDetector = new BarcodeDetector.Builder(getActivity()).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector).setAutoFocusEnabled(true).build();
        flashToggle = view.findViewById(R.id.flash_torch);

        image=view.findViewById(R.id.image_image);

        help = view.findViewById(R.id.help);
        t1 = view.findViewById(R.id.textView3 );
        t2 = view.findViewById(R.id.textView4 );
        init();
        anim=(AnimationDrawable) flashToggle.getBackground();
        anim.setEnterFadeDuration(2800);
        anim.setExitFadeDuration(2800);

        anim2=(AnimationDrawable) image.getBackground();
        anim2.setEnterFadeDuration(2800);
        anim2.setExitFadeDuration(2800);




        anim3=(AnimationDrawable) help.getBackground();
        anim3.setEnterFadeDuration(2500);
        anim3.setExitFadeDuration(2500);





        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), ImageGallery.class));
                getActivity().finish();


            }
        });




        flashToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCamera = getCamera(cameraSource);
                if (mCamera != null) {
                    try {
                        android.hardware.Camera.Parameters param = mCamera.getParameters();
                        param.setFlashMode(!mFlashMode ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF);
                        mCamera.setParameters(param);
                        mFlashMode = !mFlashMode;
                        if (mFlashMode) {
                            flashToggle.setImageResource(R.drawable.ic_flash_on);

                        } else {
                            flashToggle.setImageResource(R.drawable.ic_flash_of);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });







        /*flashToggle.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Log.d("SCANNER-FRAGMENT", "Got tap on Flash");
                if (cameraSource.setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_OFF : Camera.Parameters.FLASH_MODE_TORCH)) {
                    useFlash = !useFlash;
                    flashToggle.setImageResource(!useFlash ? R.drawable.ic_torch : R.drawable.ic_torch_on);
                }
            }
        });*/

        return view;


    }

    private void init() {



    }


    // get camera from camera source
    private static Camera getCamera(@NonNull CameraSource cameraSource) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;

                    }
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }

        }
        return null;
    }

    public interface AutoFocusCallback {
        /**
         * Called when the camera auto focus completes.  If the camera
         * does not support auto-focus and setAutoFocus is called,
         * onAutoFocus will be called immediately with a fake value of
         * <code>success</code> set to <code>true</code>.
         * <p/>
         * The auto-focus routine does not lock auto-exposure and auto-white
         * balance after it completes.
         *
         * @param success true if focus was successful, false if otherwise
         */
        void onAutoFocus(boolean success);
    }

    private class CameraAutoFocusCallback implements Camera.AutoFocusCallback {
        private AutoFocusCallback mDelegate;

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (mDelegate != null) {
                mDelegate.onAutoFocus(success);
            }
        }
    }
    public void autoFocus(@Nullable AutoFocusCallback cb) {
        synchronized (mCameraLock) {
            if (mCamera != null) {
                CameraAutoFocusCallback autoFocusCallback = null;
                if (cb != null) {
                    autoFocusCallback = new CameraAutoFocusCallback();
                    autoFocusCallback.mDelegate = cb;
                }
                mCamera.autoFocus(autoFocusCallback);
            }
        }
    }




    @Override
    public void onResume() {
        super.onResume();


        cameraSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request Permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, requestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                if (qrcode.size() != 0) {
                    Vibrator vibrator = (Vibrator) getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(150);
                    Intent my = new Intent(getActivity(),ResultActivity.class);
                    my.putExtra("type", qrcode.valueAt(0));

                    currentTime = Calendar.getInstance().getTime();
                    df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(currentTime);
                    String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    System.out.println(formattedDate);

                    //Add QR scan result to listview???
                    //Add current time and date to listview???

                    /*Currently I can save the list view with the code block below.
                     Since I cannot do the features like delete in the current listview,
                     I created a new custom list view. I want to record the detector result there.*/

                    Gson gson = new Gson();
                    String json = gson.toJson(qrcode.valueAt(0));
                    SharedPreferences sp = getActivity().getSharedPreferences("list",Context.MODE_PRIVATE);
                    addhistory(sp,json);
                    startActivity(my);
                    getActivity().finish();
                    barcodeDetector.release();


            }
            }
        });

        if(anim!=null&& !anim.isRunning()){
            anim.start();
        }
        if(anim2!=null&& !anim2.isRunning()){
            anim2.start();
        }


        if(anim3!=null&& !anim3.isRunning()){
            anim3.start();
        }

    }




    public void addhistory(SharedPreferences sp,String json){
        int x = sp.getInt("index",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(String.valueOf(x),json);
        int  y;
        y = x+1;
        editor.putInt("index",y);
        editor.commit();

    }





    @Override
    public void onPause() {
        super.onPause();





        if(anim!=null&& anim.isRunning()){
            anim.stop();
        }
        if(anim2!=null&& anim2.isRunning()){
            anim2.stop();
        }

        if(anim3!=null&& anim3.isRunning()){
            anim3.stop();
        }
    }



    // extra overrides to better understand app lifecycle and assist debugging
    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i(TAG, "onDestroy()");
    }







    @Override
    public void onStop() {
        super.onStop();
        //Log.i(TAG, "onStop()");
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.i(TAG, "onStart()");
    }


   /* public void onBackPressed() {
        Intent my = new Intent(scan.this,MainActivity.class);
        startActivity(my);
        getActivity().finish();
        super.)(onBackPressed;
    }*/











}
