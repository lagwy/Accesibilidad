package itesm.mx.accesibilidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ListaEdificios extends AppCompatActivity {
//dfgvdfg
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_edificios);

        Button boton9 = (Button) findViewById(R.id.button9);
        Button boton10 = (Button ) findViewById(R.id.button10);
        Button boton11 = (Button) findViewById(R.id.button11);
        Button boton12 = (Button) findViewById(R.id.button12);
        final Button backBttn = (Button) findViewById(R.id.back3);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backBttn.isPressed()){
                    finish();

                } else {
                    Intent intent = new Intent(ListaEdificios.this, VistaEdificio.class);
                    startActivity(intent);
                }
            }
        };

        boton9.setOnClickListener(listener);
        boton10.setOnClickListener(listener);
        boton11.setOnClickListener(listener);
        boton12.setOnClickListener(listener);
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
