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
    //Hola mundo
    /*
        Todo: Actualizar la actividad VistaEdificio para que muestre las descripciones correctas.
        Todo: Terminar de subir las imágenes faltantes para las rutas
        Todo: Terminar de subir las imagenes de los mapas
        Todo: Gesto de double tap en la imagen de MapaCampus
        Todo: Permitir únicamente que la aplicación pueda ser utilizada con WiFi

    */


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

    private void setSupportActionBar(Toolbar toolbar) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
