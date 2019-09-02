package com.beytullahzengin.qrbarkodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.print.PrintHelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class imageScan extends AppCompatActivity {



    private ShareActionProvider shareAction;
    private ImageView ImageView,img;
    private Toolbar toolbar;
    private TextView t1,t2,t3,s1,sb,bd,tp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_scan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//geri butonu
        getSupportActionBar().setDisplayShowTitleEnabled(false);//action bar text hide
        img = findViewById(R.id.imgview);
        t1 =findViewById(R.id.type1);
        t2 =findViewById(R.id.type2);
        t3 =findViewById(R.id.toplam2);

        s1 =findViewById(R.id.sonuc);
        tp =findViewById(R.id.toplam);
        tp.setVisibility(View.GONE);

        s1.setText(getIntent().getExtras().getString("sonuc"));
        t1.setText(getIntent().getExtras().getString("type"));
        tp.setText(getIntent().getExtras().getString("toplam"));



        QRcode();



    }



    private void QRcode() {

        String textQR = tp.getText().toString();


        MultiFormatWriter multiFormWriter = new MultiFormatWriter();
        if (textQR.length()==0){
            Toast.makeText(getApplicationContext(),"Enter Any Text",Toast.LENGTH_SHORT).show();
        }else {
            try {
                BitMatrix bitMatrix = multiFormWriter.encode(textQR, BarcodeFormat.QR_CODE, 560, 560);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap qrcodebitmap = barcodeEncoder.createBitmap(bitMatrix);
                img.setImageBitmap(qrcodebitmap);
                try {

                    File cachePath = new File(getApplicationContext().getCacheDir(), "images");
                    cachePath.mkdirs(); // don't forget to make the directory
                    FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
                    qrcodebitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        MenuItem item = menu.findItem(R.id.share6);
        ShareActionProvider shareAction = ( ShareActionProvider ) MenuItemCompat. getActionProvider (item);
        shareAction . setShareHistoryFileName ( ShareActionProvider . DEFAULT_SHARE_HISTORY_FILE_NAME );
        shareAction.setShareIntent(createShareIndent());

        return true;

    }

    private Intent createShareIndent() {
        Bitmap bm = ((android.graphics.drawable.BitmapDrawable) img.getDrawable()).getBitmap();
        try {
            java.io.File file = new java.io.File(getExternalCacheDir() + "/image.png");
            java.io.OutputStream out = new java.io.FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) { shareMyMessage(e.toString()); }
        String title = s1.getText().toString();
        shareMyMessage(title);

        Intent iten = new Intent(android.content.Intent.ACTION_SEND);
        iten.setType("*/*");
        iten.setType("text/plain");
        iten.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new java.io.File(getExternalCacheDir() + "/image.png")));
        iten.putExtra(Intent.EXTRA_TEXT, title);
        //startActivity(Intent.createChooser(iten, "Share :"));


        return iten;
    }

    private void shareMyMessage(String toString) {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;


            case R.id.writ:
                write();

                return true;



            default:

                return super.onOptionsItemSelected(item);
        }

    }

    private void write() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) img.getDrawable()).getBitmap();
        photoPrinter.printBitmap("QR Barcode", bitmap);
    }



}
