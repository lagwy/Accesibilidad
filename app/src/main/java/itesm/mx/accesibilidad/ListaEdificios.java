package itesm.mx.accesibilidad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListaEdificios extends AppCompatActivity {
//dfgvdfg
    ProgressDialog pDialog;
    List<Renglon> listaRenglones = new ArrayList<Renglon>();
    Bitmap bitmap;
    ListView listaEdificiosLV;
    ListViewAdapter miAdaptador;
    boolean todos = false;
    boolean unaVez = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_edificios);

        /*
        final Button boton9 = (Button) findViewById(R.id.button9);
        final Button boton10 = (Button ) findViewById(R.id.button10);
        final Button boton11 = (Button) findViewById(R.id.button11);
        final Button boton12 = (Button) findViewById(R.id.button12);*/
        final Button backBttn = (Button) findViewById(R.id.back3);
        listaEdificiosLV = (ListView) findViewById(R.id.listaEdificiosLV);

        // Lista de los renglones
        getInfoRenglones();



        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Renglon renglon = (Renglon) miAdaptador.getItem(position);
                Intent intent = new Intent(ListaEdificios.this, VistaEdificio.class);
                intent.putExtra("nombreEdificio", renglon.getNombre());
                startActivity(intent);

                Toast.makeText(ListaEdificios.this, renglon.getNombre() , Toast.LENGTH_SHORT).show();
            }
        };
        listaEdificiosLV.setOnItemClickListener(itemListener);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backBttn.isPressed()){
                    finish();

                }
                //Intent intent = new Intent(ListaEdificios.this, VistaEdificio.class);
                //startActivity(intent);
            }
        };

        /*boton9.setOnClickListener(listener);
        boton10.setOnClickListener(listener);
        boton11.setOnClickListener(listener);
        boton12.setOnClickListener(listener);*/
        backBttn.setOnClickListener(listener);

    }

    public void getInfoRenglones(){

        new LoadImage().execute("Aulas 1", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas1.jpg");
        new LoadImage().execute("Aulas 2", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas2.jpg");
        new LoadImage().execute("Aulas 3", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas3.jpg");
        todos = true;
    }

    // Clase de carga de imagen desde internet
    private class LoadImage extends AsyncTask<String, String, Renglon> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            while(!unaVez){
                pDialog = new ProgressDialog(ListaEdificios.this);
                pDialog.setMessage("Cargando imagen...");
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
                listaRenglones.add(renglon);
                if(todos){
                    miAdaptador = new ListViewAdapter(getApplication(), R.layout.row, listaRenglones);
                    listaEdificiosLV.setAdapter(miAdaptador);
                    pDialog.dismiss();
                }

            } else {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "La imagen no existe o error de red", Toast.LENGTH_SHORT).show();
            }
        }

    } // Termina clase de carga de imagen

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_edificios, menu);
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
