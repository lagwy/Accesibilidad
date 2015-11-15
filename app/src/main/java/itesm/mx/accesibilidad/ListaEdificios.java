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

import static itesm.mx.accesibilidad.R.menu.*;

public class ListaEdificios extends AppCompatActivity {
//dfgvdfg


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
    private boolean listVisible = true;
    private ContextMenu menu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_edificios);



        //set of layouts for the views
        listview = (ViewStub) findViewById(R.id.list);
        gridview = (ViewStub) findViewById(R.id.grid);


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

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                renglon.getImagen().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("imagenEdificio", byteArray);

                startActivity(intent);

                Toast.makeText(ListaEdificios.this, renglon.getNombre() , Toast.LENGTH_SHORT).show();
            }
        };
        listaEdificiosGV.setOnItemClickListener(itemListener);
        listaEdificiosLV.setOnItemClickListener(itemListener);


      
    }

    public void getInfoRenglones(){

        new LoadImage().execute("Aulas 1", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas1.jpg");
        new LoadImage().execute("Aulas 2", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas2.jpg");
        new LoadImage().execute("Aulas 3", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas3.jpg");
        // new LoadImage().execute("Aulas 4", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas4.jpg");
        // new LoadImage().execute("Aulas 6", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas6.jpg");
        new LoadImage().execute("Aulas 7", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas7.jpg");
        new LoadImage().execute("Auditorio", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/auditorio.jpg");
        new LoadImage().execute("Biotecnologia", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/biotecnologia.jpg");
        new LoadImage().execute("Cedes", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/cedes.jpg");
        new LoadImage().execute("Centrales", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/centrales.jpg");
        new LoadImage().execute("Centro Estudiantil", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/cestudiantil.jpg");
        new LoadImage().execute("Cetec", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/cetec.jpg");
        new LoadImage().execute("Domo", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/domo.jpg");
        new LoadImage().execute("E1", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/e1.jpg");
        new LoadImage().execute("Estadio", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/estadio.jpg");
        new LoadImage().execute("Gimnasio", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/gimnasio.jpg");
        new LoadImage().execute("Rectoria", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/rectoria.jpg");
        new LoadImage().execute("Resis 1", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis1.jpg");
        new LoadImage().execute("Resis 2", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis2.jpg");
        new LoadImage().execute("Resis 3", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis3.jpg");
        new LoadImage().execute("Resis 4", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis4.jpg");
//        new LoadImage().execute("Resis 5", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis5.jpg");
//        new LoadImage().execute("Resis 6", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis6.jpg");
        new LoadImage().execute("Resis 7", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis7.jpg");
//        new LoadImage().execute("Resis 8", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis8.jpg");
//        new LoadImage().execute("Resis 9", "http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/resis9.jpg");



        todos = true;
    }

    /**
     * Method to change the current view
     */
    private void changeView() {

        //if the current view is the listview, passes to gridview
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menu_lista_edificios, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Item Exit
        if (id == R.id.exit) {
            System.exit(0);
        }
        //Item Change View
        else if(id == R.id.change_view) {
            changeView();
            return true;
        }
        return super.onOptionsItemSelected(item);

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
}
