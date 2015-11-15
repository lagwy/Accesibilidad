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
                        intent.putExtra("titulo", "Silla de Ruedas");
                    }else {
                        if (muletaBttn.isPressed()){
                            intent.putExtra("nombre", "usodemuletas");
                            intent.putExtra("titulo", "Uso de muletas");
                        } else {
                            if (bastonBttn.isPressed()){
                                intent.putExtra("nombre", "baston");
                                intent.putExtra("titulo", "baston");
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
//Holaa

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_discapacidades, menu);
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

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nuevo:
                Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");;
                return true;
            case R.id.action_settings:
                Log.i("ActionBar", "Settings!");;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
