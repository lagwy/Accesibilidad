package itesm.mx.accesibilidad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class MapaCampus extends AppCompatActivity implements GestureDetector.OnGestureListener {
    final static String tituloBase = "Mapa ";
    public GestureDetectorCompat myGestureDetector;
    ProgressDialog pDialog;
    Bitmap bitmap;
    Bitmap[] imagenes;
    ImageView mapaIV;
    TextView mapaTV;
    int actual = 0;
    Button infoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_campus);
        imagenes = new Bitmap[3]; // Arreglo de bitmaps que contiene los 3 dibujos

        infoBtn = (Button) findViewById(R.id.infoButton);

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MapaCampus.this).create();
                alertDialog.setTitle("Mapa del Campus");
                alertDialog.setMessage("Desliza hacia la izquierda o derecha para cambiar de mapa.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        mapaTV = (TextView) findViewById(R.id.mapa);
        final Button backBttn = (Button) findViewById(R.id.back5);
        mapaIV = (ImageView) findViewById(R.id.mapaCampusIV);
        /*
            Links de las imagenes del campus
            Juntos: http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/juntos.png
            Rampas: http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/rampas.png
            BaÃ±os: http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/banos.png

         */
        new LoadImage().execute("http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/juntos_nuevo.png",
                "http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/rampas_nuevo.png",
                "http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/banos_nuevo.png");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backBttn.isPressed()) {
                    finish();
                }
            }
        };
        backBttn.setOnClickListener(listener);

        // Gestos de flings
        myGestureDetector = new GestureDetectorCompat(getApplicationContext(), this);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() < e2.getX()){
            // Esto es a la derecha
            actual--;
            if(actual<0){
                actual = 2;
            }
        } else if (e1.getX() > e2.getX()){
            // Esto es a la izquierda
            actual++;
            if(actual > 2){
                actual = 0;
            }
        }

        if (actual == 0){
            mapaTV.setText(tituloBase + "del campus");
        } else if(actual == 1){
            mapaTV.setText(tituloBase +"- Rampas");
        } else if(actual == 2){
            mapaTV.setText(tituloBase + "- Baños");
        }

        mapaIV.setImageBitmap(imagenes[actual]);
        return true;
    }

    @Override
    public boolean onTouchEvent (MotionEvent e){
        this.myGestureDetector.onTouchEvent(e);

        return  super.onTouchEvent(e);
    }

    // Clase de carga de imagen desde internet
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(MapaCampus.this);
            pDialog.setMessage("Cargando...");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args){
            try{
                // Obtener bitmap de todos los elementos
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
                imagenes[0] = bitmap;
                // Obtener bitmap de las rampas
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[1]).getContent());
                imagenes[1] = bitmap;
                // Obtener bitmap de los baÃ±os
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[2]).getContent());
                imagenes[2] = bitmap;
            } catch (Exception e){
                e.printStackTrace();
            }
            // Manda la imagen del primer bitmap al onPostExecute()
            return imagenes[0];
        }

        protected void onPostExecute(Bitmap image){
            if (image != null){
                mapaIV.setImageBitmap(image);
                //nombreCampusTV.setText("Mapa del campus");
                pDialog.dismiss();
            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "La imagen no existe o error de red", Toast.LENGTH_SHORT).show();
            }
        }

    } // Termina clase de carga de imagen
}

