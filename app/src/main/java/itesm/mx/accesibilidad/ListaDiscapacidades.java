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

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ListaDiscapacidades extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_discapacidades);

        Button backButton = (Button) findViewById(R.id.back1);



            // 4 al
        final Button sillaBttn = (Button) findViewById(R.id.sillaBttn);
        final Button muletaBttn = (Button) findViewById(R.id.muletaBttn);
        final Button bastonBttn = (Button) findViewById(R.id.bastonBttn);
        final Button embaraBttn = (Button) findViewById(R.id.embaraBttn);
        final Button backBttn = (Button) findViewById(R.id.back1);


        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (backBttn.isPressed()){
                    finish();

                } else {
                    Intent intent = new Intent(ListaDiscapacidades.this, Ruta.class);
                    if (sillaBttn.isPressed()){
                        intent.putExtra("nombre", "silladeruedas");
                        intent.putExtra("titulo", "Silla de ruedas");
                    }else {
                        if (muletaBttn.isPressed()){
                            intent.putExtra("nombre", "usodemuletas");
                            intent.putExtra("titulo", "Uso de muletas");
                        } else {
                            if (bastonBttn.isPressed()){
                                intent.putExtra("nombre", "baston");
                                intent.putExtra("titulo", "Bastón");
                            } else {
                                if(embaraBttn.isPressed()){
                                    intent.putExtra("nombre", "embarazada");
                                    intent.putExtra("titulo", "Embarazada");
                                }
                            }
                        }
                    } // Terminan los ifs de los extras
                    startActivity(intent);
                }
            }
        };

        sillaBttn.setOnClickListener(listener);
        muletaBttn.setOnClickListener(listener);
        bastonBttn.setOnClickListener(listener);
        embaraBttn.setOnClickListener(listener);
        backBttn.setOnClickListener(listener);

    }


}
