package com.beytullahzengin.qrbarkodescanner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.print.PrintHelper;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



public class ResultActivity extends AppCompatActivity {


    private ImageView ImageView;
    private Toolbar toolbar;
    private TextView result;
    private TextView type1;
    private TextView date;
    private TextView type2;


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        result = findViewById(R.id.tv_result);
        type1 = findViewById(R.id.type_1);
        type2 = findViewById(R.id.type_2);
        date = findViewById(R.id.time);
        ImageView=findViewById(R.id.QRCodeImageView);






        final Barcode b1 = getIntent().getParcelableExtra("type");

        switch (b1.valueFormat){


            case 1:
                type1.setText("EAN13");
                type2.setText("Ean");
                result.setText(b1.displayValue);
                QRcode();
                break;

            default:
                type1.setText(R.string.QR_Code);
                type2.setText(R.string.QR_Code);
                result.setText(b1.rawValue);
                QRcode();

                break;


        }

    }




    private void QRcode() {

        String textQR = result.getText().toString();
        MultiFormatWriter multiFormWriter = new MultiFormatWriter();
        if (textQR.length()==0){
            Toast.makeText(getApplicationContext(),"Enter Any Text",Toast.LENGTH_SHORT).show();
        }else {
            try {
                BitMatrix bitMatrix = multiFormWriter.encode(textQR, BarcodeFormat.QR_CODE, 360, 360);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap qrcodebitmap = barcodeEncoder.createBitmap(bitMatrix);
                ImageView.setImageBitmap(qrcodebitmap);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.share2);
        ShareActionProvider shareAction = ( ShareActionProvider ) MenuItemCompat . getActionProvider (item);
        shareAction . setShareHistoryFileName ( ShareActionProvider . DEFAULT_SHARE_HISTORY_FILE_NAME );
        shareAction.setShareIntent(createShareIndent());

        return true;

    }

    private Intent createShareIndent() {
        Bitmap bm = ((android.graphics.drawable.BitmapDrawable) ImageView.getDrawable()).getBitmap();
        try {
            java.io.File file = new java.io.File(getExternalCacheDir() + "/image.png");
            java.io.OutputStream out = new java.io.FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) { shareMyMessage(e.toString()); }
        String title = result.getText().toString();
        shareMyMessage(title);

        Intent iten = new Intent(android.content.Intent.ACTION_SEND);
        iten.setType("*/*");
        iten.setType("text/plain");
        iten.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new java.io.File(getExternalCacheDir() + "/image.png")));
        iten.putExtra(Intent.EXTRA_TEXT, title);
        //startActivity(Intent.createChooser(iten, "Share :"));


        return iten;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

           case android.R.id.home:
               onBackPressed();

                return true;


            case R.id.delete:
                delete();

                return true;
            case R.id.write:
                write();

                return true;




            default:

                return super.onOptionsItemSelected(item);
        }

    }

    private void write() {

        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) ImageView.getDrawable()).getBitmap();
        photoPrinter.printBitmap("QR Barcode", bitmap);
    }


    private void delete() {

        //????


    }



    private void shareMyMessage(String message) {

    }

    public void onBackPressed() {
        Intent my = new Intent(ResultActivity.this,MainActivity.class);
        startActivity(my);
        finish();
        super.onBackPressed();
    }








}
