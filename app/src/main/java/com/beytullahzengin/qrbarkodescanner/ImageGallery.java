package com.beytullahzengin.qrbarkodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;


public class ImageGallery extends AppCompatActivity {


    private static final int READ_REQUEST_CODE = 42;

    public Uri QRImgURI;
    public String QRcodeText;
    public static Menu globalMenuItem;

    private ImageView image2;
    private Toolbar toolbar;
    private TextView t1,t3,s1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);
        performFileSearch();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//geri butonu
        getSupportActionBar().setDisplayShowTitleEnabled(false);//action bar text hide

        image2 = findViewById(R.id.imgview);
        t1 =findViewById(R.id.type1);
        t3 =findViewById(R.id.toplam2);
        s1 =findViewById(R.id.sonuc);





    }



    private void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (resultData != null) {
                QRImgURI = resultData.getData();
                setimg();
            }

        }

    }

    @SuppressLint("ResourceAsColor")
    private void setimg() {


        Uri imgUri = QRImgURI;
        image2.setImageURI(null);
        image2.setImageURI(imgUri);


        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.ALL_FORMATS )
                        .build();
        if (!detector.isOperational()) {
            s1.setText("Could not set up the detector!");
            return;
        }

        Bitmap myQRbitmap = ((BitmapDrawable) image2.getDrawable()).getBitmap();
        Frame frame = new Frame.Builder().setBitmap(myQRbitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);

        int totalCodes = barcodes.size();
        if (totalCodes > 0) {
            Barcode thisCode = barcodes.valueAt(0);
            String QRtext = thisCode.rawValue.toString();
            if (QRtext != null) {
                QRcodeText = QRtext;
                s1.setText(QRtext);


                Intent my = new Intent(ImageGallery.this,ResultActivity.class);
                my.putExtra("type", barcodes.valueAt(0));
                Gson gson = new Gson();
                String json = gson.toJson(barcodes.valueAt(0));
                SharedPreferences sp = this.getSharedPreferences("list", Context.MODE_PRIVATE);
                addhistory(sp,json);
                startActivity(my);
                finish();
                detector.release();

            }

        } else {
            s1.setText("Error");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main1, menu);





        return true;

    }



    private void shareMyMessage(String toString) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;


            case R.id.intentcek:
                performFileSearch();

                return true;



            default:

                return super.onOptionsItemSelected(item);
        }

    }



    public void onBackPressed(){

        startActivity(new Intent(ImageGallery.this, MainActivity.class));
        finish();

        super.onBackPressed();
    }


}
