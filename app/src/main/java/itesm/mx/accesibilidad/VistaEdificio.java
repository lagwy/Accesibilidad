package itesm.mx.accesibilidad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.ActionBar;
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
    String nombreEdificio;
    TextView nombreEdificioTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_edificio);
        //setTitle(R.string.title_activity_vista_edificio);


        edificioIV = (ImageView) findViewById(R.id.ediImg);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            nombreEdificio = extras.getString("nombreEdificio");
            nombreEdificioTV = (TextView) findViewById(R.id.nombreEdificio);
            nombreEdificioTV.setText(nombreEdificio);

            // if(getIntent().hasExtra("byteArray"))
            byte[] byteArray = getIntent().getByteArrayExtra("imagenEdificio");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            edificioIV.setImageBitmap(bmp);


        }

        final Button backBttn = (Button) findViewById(R.id.back4);
        mapa = (ImageView) findViewById(R.id.imageView3);
        new LoadImage().execute("http://res.cloudinary.com/brogrammers/image/upload/v1447304278/edificios/aulas2.jpg");//
        // new LoadImage().execute("http://g-forward.com/wp-content/uploads/2012/03/approved2.png");


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        backBttn.setOnClickListener(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vista_edificio, menu);
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

    // Clase de carga de imagen desde internet
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(VistaEdificio.this);
            pDialog.setMessage("Cargando imagen...");
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
