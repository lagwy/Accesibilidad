/*
Accesibilidad - Proyecto de la materia de Desarrollo de Aplicaciones Móviles
        Copyright (C) 2015 - ITESM

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.


        Authors:

        ITESM representatives
        Ing. Martha Sordia Salinas <msordia@itesm.mx>
        Ing. Mario de la Fuente <mario.delafuente@itesm.mx>

        ITESM students
        Luis Angel Martinez Garcia <a00813485@itesm.mx>
        Daniel Garcia Mena <a00813719@itesm.mx>
        Jorge Luis Marquez Sanchez <a01139543@itesm.mx>
*/

package itesm.mx.accesibilidad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    boolean hayInternet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_campus);
        imagenes = new Bitmap[3]; // Arreglo de bitmaps que contiene los 3 dibujos

        // Revisar la conexión a internet
        ConnectivityManager networkManager = (ConnectivityManager) this.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = networkManager.getActiveNetworkInfo();
        NetworkInfo wifi = networkManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isAvailable() && wifi.isConnected()) {
            hayInternet = true;
        }else {
            // Desplegar un mensaje de que no hay conexión a internet o no es por wifi
            Toast toast = Toast.makeText(getApplicationContext(), "No estas utilizando una conexión WiFi", Toast.LENGTH_SHORT);
            toast.show();
        }


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

        new LoadImage().execute("http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/juntos_nuevos.png",
                "http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/rampas_nuevos.png",
                "http://res.cloudinary.com/brogrammers/image/upload/v1448563552/campus/banos_nuevos.png");
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

    /**
     * Metodo que evalua el gesto de hacer fling sobre la imagen del campus
     * @param e1 Comienzo del evento de fling
     * @param e2 Final del evento de fling
     * @param velocityX Velocidad en x
     * @param velocityY Velocidad en y
     * @return true
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(hayInternet){

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

        }
        mapaIV.setImageBitmap(imagenes[actual]);
        return true;
    }

    @Override
    public boolean onTouchEvent (MotionEvent e){
        this.myGestureDetector.onTouchEvent(e);

        return  super.onTouchEvent(e);
    }

    /**
     * Clase para la carga de imagenes desde el servidor
     */
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        /**
         * Establecer un dialogo para avisar al usuario que se estan cargando las imagenes
         */
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(MapaCampus.this);
            pDialog.setMessage("Cargando...");
            pDialog.show();
        }

        /**
         * Obtener las imagenes desde el servidor
         * @param args Strings que contienen las direcciones de las imagenes en la red
         * @return Bitmap con la imagen default
         */
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
                // Que aparezca solo una imagen incluso con el fling cuando no se pueden obtener las imagenes de red
                imagenes[0] = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.ma);
                imagenes[1] = imagenes[2] = imagenes[0];
            }
            // Manda la imagen del primer bitmap al onPostExecute()
            return imagenes[0];
        }

        /**
         * Establece la imagen default en el ImageView
         * @param image La imagen default cargada desde el servidor
         */
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


/*
 * Copyright (C) 2015 ITESM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


