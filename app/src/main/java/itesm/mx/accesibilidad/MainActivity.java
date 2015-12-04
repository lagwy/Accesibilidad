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

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // Revisar la conexión de internet, tiene que ser por wifi, si no se lanza un toast y se cierra la aplicación
        ConnectivityManager networkManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = networkManager.getActiveNetworkInfo();
        NetworkInfo wifi = networkManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isAvailable() && wifi.isConnected()) {
            // La aplicación hacer lo demás
        }else {
            // Desplegar un mensaje de que no hay conexión a internet o no es por wifi
            Toast toast = Toast.makeText(getApplicationContext(), "No estas utilizando una conexión WiFi\n" +
                    "conectate a una y vuelve a intentar.", Toast.LENGTH_SHORT);
            toast.show();
            // Terminar la aplicacion
            finish();
        }

        final Button rutaBttn = (Button) findViewById(R.id.rutaBttn);
        final Button edificioBttn = (Button) findViewById(R.id.ediBttn);
        final Button mapaBttn = (Button) findViewById(R.id.mapaBttn);
        final Button infoBttn = (Button) findViewById(R.id.infoBttn);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (rutaBttn.isPressed()){
                    intent = new Intent(MainActivity.this, ListaDiscapacidades.class);
                    startActivity(intent);
                }
                if (edificioBttn.isPressed()){
                    intent = new Intent(MainActivity.this, ListaEdificios.class);
                    startActivity(intent);
                }
                if (mapaBttn.isPressed()){
                    intent = new Intent(MainActivity.this, MapaCampus.class);
                    startActivity(intent);
                }
                if (infoBttn.isPressed()){
                    intent = new Intent(MainActivity.this, Creditos.class);
                    startActivity(intent);
                }
            }
        };

        rutaBttn.setOnClickListener(listener);
        edificioBttn.setOnClickListener(listener);
        mapaBttn.setOnClickListener(listener);
        infoBttn.setOnClickListener(listener);


    }

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
