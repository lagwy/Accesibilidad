package itesm.mx.accesibilidad;

import android.app.ProgressDialog;
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
    public GestureDetectorCompat myGestureDetector;
    ProgressDialog pDialog;
    Bitmap bitmap;
    Bitmap[] imagenes;
    ImageView mapaIV;
    TextView nombreCampusTV;
    int actual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_campus);
        imagenes = new Bitmap[3]; // Arreglo de bitmaps que contiene los 3 dibujos

        nombreCampusTV = (TextView) findViewById(R.id.nombreMapaTV);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa_campus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            nombreCampusTV.setText("Mapa del campus");
        } else if(actual == 1){
            nombreCampusTV.setText("Rampas");
        } else if(actual == 2){
            nombreCampusTV.setText("Baños accesibles");
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
                nombreCampusTV.setText("Mapa del campus");
                pDialog.dismiss();
            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "La imagen no existe o error de red", Toast.LENGTH_SHORT).show();
            }
        }

    } // Termina clase de carga de imagen
}

