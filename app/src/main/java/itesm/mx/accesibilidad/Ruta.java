package itesm.mx.accesibilidad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Ruta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);

        final Button backBttn = (Button) findViewById(R.id.back2);
        // Las posiciones se guardan en la posicion 0 del arreglo
        final int[] posicionOrigen = new int[1];
        final int[] posicionDestino = new int[1];

        // SpinnerOri y SpinnerDest
        Spinner spinnerOri = (Spinner) findViewById(R.id.spinnerOri);
        Spinner spinnerDest = (Spinner) findViewById(R.id.spinnerDest);

        // String que contiene la lista de edificios
        String[] edificios = new String[]{"Aulas I", "Aulas II", "Aulas III", "Aulas IV", "Aulas VI",
        "Aulas VII", "CETEC", "Centro Estudiantil", "CIAP", "Cedes", "Rectoría", "Centrales"};
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
                finish();
            }
        };
        backBttn.setOnClickListener(listener);
    }

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
