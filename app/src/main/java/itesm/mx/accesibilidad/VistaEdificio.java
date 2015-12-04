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

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class VistaEdificio extends AppCompatActivity {
    Bitmap bitmap;
    ImageView mapa, edificioIV;
    ProgressDialog pDialog;
    String nombreEdificio, sUrl;
    String []descripciones;
    TextView nombreEdificioTV;
    int posicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_edificio);
        //setTitle(R.string.title_activity_vista_edificio);

        descripciones = getResources().getStringArray(R.array.descripciones);
        edificioIV = (ImageView) findViewById(R.id.ediImg);
        TextView descripcion = (TextView) findViewById(R.id.descripcion);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            nombreEdificio = extras.getString("nombreEdificio");
            nombreEdificioTV = (TextView) findViewById(R.id.nombreEdificioVis);
            nombreEdificioTV.setText(nombreEdificio);
            posicion = extras.getInt("posicion");
            descripcion.setText(descripciones[posicion]);

            if(getIntent().hasExtra("imagenEdificio")) {
                sUrl = extras.getString("urls");
                byte[] byteArray = getIntent().getByteArrayExtra("imagenEdificio");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                edificioIV.setImageBitmap(bmp);

            }


        }

        final Button backBttn = (Button) findViewById(R.id.back4);
        mapa = (ImageView) findViewById(R.id.imageView3);


        new LoadImage().execute("http://res.cloudinary.com/brogrammers/image/upload/v1447304278/maps/"+ sUrl+ ".jpg");//
        // new LoadImage().execute("http://g-forward.com/wp-content/uploads/2012/03/approved2.png");


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        backBttn.setOnClickListener(listener);

    }


    /**
     * Clase para la carga de imagenes desde el servidor
     */
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(VistaEdificio.this);
            pDialog.setMessage("Cargando...");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args){
            try{
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
            } catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image){
            if (image != null){
                mapa.setImageBitmap(image);
                pDialog.dismiss();
            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "La imagen no existe o error de red", Toast.LENGTH_SHORT).show();
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
