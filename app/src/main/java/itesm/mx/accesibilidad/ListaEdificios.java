package itesm.mx.accesibilidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class ListaEdificios extends AppCompatActivity {

    private String[] datos = new String[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_edificios);


        final Button backBttn = (Button) findViewById(R.id.back3);

//...
        for(int i=1; i<=50; i++)
            datos[i-1] = "Dato " + i;

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);

        GridView grdOpciones = (GridView) findViewById(R.id.gridView);

        grdOpciones.setAdapter(adaptador);


        grdOpciones.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            android.view.View v, int position, long id) {
                        Toast.makeText(getApplicationContext(), "Opción seleccionada: "
                                + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

//                        lblMensaje.setText("Opción seleccionada: "
//                                + parent.getItemAtPosition(position));
                    }
                });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backBttn.isPressed()){
                    finish();

                }
                Intent intent = new Intent(ListaEdificios.this, VistaEdificio.class);
                startActivity(intent);
            }
        };

        backBttn.setOnClickListener(listener);

    }

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
