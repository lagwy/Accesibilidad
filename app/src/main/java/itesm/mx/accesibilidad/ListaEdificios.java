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

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListaEdificios extends AppCompatActivity {
    int van;
    ProgressDialog pDialog;
    List<Renglon> listaRenglones = new ArrayList<Renglon>();
    Bitmap bitmap;
    ListView listaEdificiosLV;
    GridView listaEdificiosGV;

    ListViewAdapter miAdaptador;
    GridViewAdapter gridAdapter;
    boolean todos = false;
    boolean unaVez = false;
    Button backBttn, lBttn, gBttn, backBttn2;

    ViewStub listview, gridview, gb, lb, bb;
    String[] vistaEdificios;
    private boolean listVisible = true;
    private ContextMenu menu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_edificios);

        van = 0;
        //set of layouts for the views
        listview = (ViewStub) findViewById(R.id.list);
        gridview = (ViewStub) findViewById(R.id.grid);

        vistaEdificios = getResources().getStringArray(R.array.vista_edificios);
        //inflate the layouts
        listview.inflate();
        gridview.inflate();

        //set of layouts for the list/grid
        listaEdificiosLV = (ListView) findViewById(R.id.listaEdificiosLV);
        listaEdificiosGV = (GridView) findViewById(R.id.listaEdificiosGV);


        lBttn = (Button) findViewById(R.id.Bttn);
        gBttn = (Button) findViewById(R.id.Bttn2);
        backBttn = (Button) findViewById(R.id.back3);
        backBttn2 = (Button) findViewById(R.id.back33);


        gridview.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);

        // Lista de los renglones
        getInfoRenglones();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backBttn.isPressed() || backBttn2.isPressed()) {
                    finish();
                }
                else
                    changeView();
            }
        };
        backBttn.setOnClickListener(listener);
        backBttn2.setOnClickListener(listener);
        lBttn.setOnClickListener(listener);
        gBttn.setOnClickListener(listener);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Renglon renglon;
                if(listVisible)
                    renglon = (Renglon) miAdaptador.getItem(position);
                else
                    renglon = (Renglon) gridAdapter.getItem(position);


                Intent intent = new Intent(ListaEdificios.this, VistaEdificio.class);
                intent.putExtra("nombreEdificio", renglon.getNombre());

                if (renglon.getImagen() != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    renglon.getImagen().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("imagenEdificio", byteArray);
                    intent.putExtra("posicion", position);
                    intent.putExtra("urls", vistaEdificios[position]);
                    // Aquí hay que añadir lo de la carga de imagen del edificio
                }
                startActivity(intent);
            }
        };
        listaEdificiosGV.setOnItemClickListener(itemListener);
        listaEdificiosLV.setOnItemClickListener(itemListener);
    }

    public void getInfoRenglones(){
        // 26 edificios
        new LoadImage().execute("Aulas 1",              "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas1.jpg");
        new LoadImage().execute("Aulas 2",              "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas2.jpg");
        new LoadImage().execute("Aulas 3",              "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas3.jpg");
        new LoadImage().execute("Aulas 4",              "http://res.cloudinary.com/brogrammers/image/upload/c_scale,q_31,w_362/v1447654247/edificios/aulas4.png");
        new LoadImage().execute("Aulas 6", "http://res.cloudinary.com/brogrammers/image/upload/c_scale,q_52,w_502/v1447629345/edificios/aulas6.jpg");
        new LoadImage().execute("Aulas 7",              "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas7_nuevo.jpg");
        new LoadImage().execute("Auditorio",            "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/auditorio.jpg");
        new LoadImage().execute("Biotecnología",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/biotecnologia.jpg");
        new LoadImage().execute("CEDES",                "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/cedes.jpg");
        new LoadImage().execute("Centrales",            "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/centrales.jpg");
        new LoadImage().execute("Centro Estudiantil",   "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/cestudiantil.jpg");
        new LoadImage().execute("CETEC",                "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/cetec.jpg");
        new LoadImage().execute("CIAP",                 "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/ciap.jpg");
        new LoadImage().execute("Domo Acuático",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/domo.jpg");
        new LoadImage().execute("E1",                   "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/e1.jpg");
        new LoadImage().execute("Estadio",              "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/estadio.jpg");
        new LoadImage().execute("Gimnasio",             "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/gimnasio.jpg");
        new LoadImage().execute("Rectoría",             "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/rectoria_nuevo.jpg");
        new LoadImage().execute("Residencias 1",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis1.jpg");
        new LoadImage().execute("Residencias 2",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis2.jpg");
        new LoadImage().execute("Residencias 3",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis3.jpg");
        new LoadImage().execute("Residencias 4",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis4.jpg");
        new LoadImage().execute("Residencias 5",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis5.jpg");
        new LoadImage().execute("Residencias 6",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis6.jpg");
        new LoadImage().execute("Residencias 7",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis7.jpg");
        new LoadImage().execute("Residencias 8",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis8.jpg");
        new LoadImage().execute("Residencias 9",        "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis9.jpg");
        // Todos las imágenes han sido cargadas
        todos = true;
    }

    /**
     * Switch para cambiar la vista actual
     */
    private void changeView() {
        // Cambia de ListView a GridView
        if(listVisible) {
            listview.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            listVisible = false;
            setAdapters();
        }
        else {
            gridview.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            listVisible = true;
            setAdapters();
        }
    }

    /**
     * Establecer el adaptador que se vaya a utilizar
     */
    private void setAdapters() {
        if(listVisible) {
            miAdaptador = new ListViewAdapter(this,R.layout.row, listaRenglones);
            listaEdificiosLV.setAdapter(miAdaptador);
        }
        else {
            gridAdapter = new GridViewAdapter(this,R.layout.frame, listaRenglones);
            listaEdificiosGV.setAdapter(gridAdapter);
        }

    }

    /**
     * Clase de carga de imagen desde internet
     */
    private class LoadImage extends AsyncTask<String, String, Renglon> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            while(!unaVez){
                pDialog = new ProgressDialog(ListaEdificios.this);
                pDialog.setMessage("Cargando...");
                pDialog.show();
                unaVez = true;
            }
        }

        protected Renglon doInBackground(String... args){
            Bitmap bmp = null;

            try{
                bmp = BitmapFactory.decodeStream((InputStream) new URL(args[1]).getContent());
            } catch (Exception e){
                e.printStackTrace();
            }
            Renglon renglon = new Renglon(args[0], bmp);
            return renglon;
        }

        protected void onPostExecute(Renglon renglon){
            if (renglon != null){
                // renglon = new Renglon("Aulas 1", image);
                van++;
                listaRenglones.add(renglon);
                if(todos){
                    miAdaptador = new ListViewAdapter(getApplication(), R.layout.row, listaRenglones);
                    listaEdificiosLV.setAdapter(miAdaptador);
                }
                if(van >= 25){
                    pDialog.dismiss();
                }
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