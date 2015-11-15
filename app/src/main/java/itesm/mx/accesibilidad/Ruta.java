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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class Ruta extends AppCompatActivity {
    ProgressDialog pDialog;
    Bitmap bitmap;
    ImageView rutaIV;
    final static public String URL_BASE = "http://res.cloudinary.com/brogrammers/image/upload/v1447625535/baston/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);

        final Button backBttn = (Button) findViewById(R.id.back2);
        final Button buttonTraza = (Button) findViewById (R.id.buttonTraza);
        // Las posiciones se guardan en la posicion 0 del arreglo
        final int[] posicionOrigen = new int[1];
        final int[] posicionDestino = new int[1];

        rutaIV = (ImageView) findViewById(R.id.ruta_IV);

        // SpinnerOri y SpinnerDest
        Spinner spinnerOri = (Spinner) findViewById(R.id.spinnerOri);
        Spinner spinnerDest = (Spinner) findViewById(R.id.spinnerDest);

        // String que contiene la lista de edificios
        String[] edificios = new String[]{"Aulas I", "Aulas II", "Aulas III", "Aulas IV", "Aulas VI",
        "Aulas VII", "CETEC", "Centro Estudiantil", "CIAP", "Cedes", "Rectoría", "Centrales"};
        final String[] nombres = new String[]{"aulas1", "aulas2", "aulas3", "aulas4", "aulas6", "aulas7", "cetec",
        "centroestudiantil", "ciap", "cedes", "rectoria", "centrales"};
        // Implementacion del spinner de origen
        ArrayAdapter<String> adaptadorOrigen = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, edificios);
        adaptadorOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOri.setAdapter(adaptadorOrigen);

        // Implementacion del spinner de destino
        ArrayAdapter<String> adaptadorDestino = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, edificios);
        adaptadorDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDest.setAdapter(adaptadorDestino);

        // Obtener las posiciones del edificio seleccionado en los spinners
        spinnerOri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                posicionOrigen[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                posicionDestino[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(backBttn.isPressed()){
                    finish();
                }
                if(buttonTraza.isPressed()){
                    // Stuff
                    if(posicionOrigen[0] == posicionDestino[0]){
                        Toast.makeText(Ruta.this, "El origen y el destino son idénticos.", Toast.LENGTH_SHORT).show();
                    } else {
                        String urlImagen = URL_BASE + nombres[posicionOrigen[0]] + "/" + nombres[posicionDestino[0]] + ".jpg";
                        new LoadImage().execute(urlImagen);//
                        //Toast.makeText(Ruta.this, urlImagen, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        backBttn.setOnClickListener(listener);
        buttonTraza.setOnClickListener(listener);
    }

    // Clase de carga de imagen desde internet
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(Ruta.this);
            pDialog.setMessage("Cargando imagen...");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args){
            try{
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image){
            if (image != null){
                rutaIV.setImageBitmap(image);
                pDialog.dismiss();
            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "La imagen no existe o error de red", Toast.LENGTH_SHORT).show();
            }
        }

    } // Termina clase de carga de imagen

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ruta, menu);



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
