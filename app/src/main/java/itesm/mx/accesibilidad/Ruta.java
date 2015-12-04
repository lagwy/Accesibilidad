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

package itesm.mx.accesibilidad;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Object;


import java.io.InputStream;
import java.net.URL;

public class Ruta extends AppCompatActivity {
    final static public String URL_BASE = "http://res.cloudinary.com/brogrammers/image/upload/v1447625535/";
    public double latitud, longitud;
    ProgressDialog pDialog;
    Bitmap bitmap;
    ImageView rutaIV;
    String titulo, nombre;
    boolean gpsActivo;
    Location location;
    int posicionOrigen;
    int posicionDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);
        TextView tituloRutaTV = (TextView) findViewById(R.id.titulo_rutaTV);


        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        final LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        if ( Build.VERSION.SDK_INT >= 23 &&
                checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000*60*60, 10, locationListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titulo = tituloRutaTV.getText().toString() + " - " + extras.getString("titulo");
            nombre = extras.getString("nombre");
            tituloRutaTV.setText(titulo);
        }

        final Button backBttn = (Button) findViewById(R.id.back2);
        final Button buttonTraza = (Button) findViewById(R.id.buttonTraza);


        // Las posiciones se guardan en la posicion 0 del arreglo

        rutaIV = (ImageView) findViewById(R.id.ruta_IV);

        // SpinnerOri y SpinnerDest
        Spinner spinnerOri = (Spinner) findViewById(R.id.spinnerOri);
        Spinner spinnerDest = (Spinner) findViewById(R.id.spinnerDest);

        final String[] nombres = getResources().getStringArray(R.array.nombres);
        final String[] edificiosOri = getResources().getStringArray(R.array.edificiosOrigen);
        final String[] edificiosDest = getResources().getStringArray(R.array.edificiosDestino);

        // Implementacion del spinner de origen
        ArrayAdapter<String> adaptadorOrigen = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, edificiosOri);
        adaptadorOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOri.setAdapter(adaptadorOrigen);

        // Implementacion del spinner de destino
        ArrayAdapter<String> adaptadorDestino = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, edificiosDest);
        adaptadorDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDest.setAdapter(adaptadorDestino);

        // Obtener las posiciones del edificio seleccionado en los spinners
        spinnerOri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (position == 12 && gpsActivo) {
                    if ( Build.VERSION.SDK_INT >= 23 &&
                            checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return  ;
                    }
                    location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    localPosition();



                }else if (position == 12 && !gpsActivo) {
                    Toast.makeText(Ruta.this, "Activa tu GPS para localizar tu ubicación", Toast.LENGTH_SHORT).show();

                }else
                    posicionOrigen = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerDest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                    posicionDestino = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (backBttn.isPressed()) {
                    finish();
                }

                if (buttonTraza.isPressed()) {

                     if (posicionOrigen == posicionDestino) {
                        Toast.makeText(Ruta.this, "El origen y el destino son idénticos.", Toast.LENGTH_SHORT).show();


                     }else if (posicionOrigen == 12) {
                         Toast.makeText(Ruta.this, "Acercate al edificio más cercano", Toast.LENGTH_SHORT).show();


                     }else if(posicionOrigen == 13){
                         Toast.makeText(Ruta.this, "Estas fuera del campus", Toast.LENGTH_SHORT).show();

                     } else {

                        String urlImagen = URL_BASE + nombre + "/" + nombres[posicionOrigen] + "/" + nombres[posicionDestino] + ".jpg";
                        new LoadImage().execute(urlImagen);//
                        //Toast.makeText(Ruta.this, urlImagen, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        backBttn.setOnClickListener(listener);
        buttonTraza.setOnClickListener(listener);
    }

    /**
     * Obtener la posicion en la que se encuentra dentro del campus
     */
    public void localPosition(){
        if(latitud <= 25.652008 && latitud >= 25.651813 && longitud >=  -100.290838 && longitud <= -100.289460){
            posicionOrigen = 0; //Aulas 1

        } else if(latitud <= 25.651093 && latitud >= 25.650818 && longitud >=  -100.290868 && longitud <= -100.289467){
            posicionOrigen = 1; //Aulas 2

        }else if(latitud <= 25.650024  && latitud >= 25.649857 && longitud >=  -100.290835 && longitud <= -100.289394){
            posicionOrigen = 2; //Aulas 3

        }else if(latitud <= 25.650070 && latitud >= 25.649194 && longitud >=  -100.289387 && longitud <= -100.288568){
            posicionOrigen = 3;//Aulas 4

        }else if(latitud <= 25.651806 && latitud >= 25.651400 && longitud >=  -100.288453 && longitud <= -100.287592){
            posicionOrigen = 4;//Aulas 6

        }else if(latitud <= 25.650070 && latitud >= 25.649194 && longitud >=  -100.288550 && longitud <= -100.287766){
            posicionOrigen = 5;//Aulas 7

        }else if(latitud <= 25.652218  && latitud >= 25.652246 && longitud >=  -100.289667 && longitud <= -100.289663){
            posicionOrigen = 6;//Biotecnologia

        }else if(latitud <= 25.651772 && latitud >= 25.651132 && longitud >=  -100.289620 && longitud <= -100.288684){
            posicionOrigen = 7;//centrales

        }else if(latitud <= 25.648934 && latitud >= 25.648458 && longitud >=  -100.290374 && longitud <= -100.289219){
            posicionOrigen = 8;//centro estudiantil

        }else if(latitud <= 25.650732  && latitud >= 25.650198 && longitud >=  -100.291363 && longitud <= -100.290616){
            posicionOrigen = 9;//cetec

        }else if(latitud <= 25.652953 && latitud >= 25.652529 && longitud >=  -100.290737 && longitud <= -100.288816){
            posicionOrigen = 10;// ciap

        }else if(latitud <= 25.651616 && latitud >= 25.651295 && longitud >=  -100.291096 && longitud <= -100.290595){
            posicionOrigen = 11;//rectoria

        }
        else if(latitud <= 25.653495 && latitud >= 25.648022 && longitud >=  -100.292131 && longitud <= -100.286485){
            posicionOrigen= 12;

        }
        else
            posicionOrigen= 13;
    }

    /**
     * Clase para la carga de imagenes desde el servidor
     */
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ruta.this);
            pDialog.setMessage("Cargando imagen...");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                rutaIV.setImageBitmap(image);
                pDialog.dismiss();
            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        }

    } // Termina clase de carga de imagen

}

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