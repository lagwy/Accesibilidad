package itesm.mx.accesibilidad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    //Hola mundo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button boton1 = (Button) findViewById(R.id.rutaBttn);
        final Button boton2 = (Button) findViewById(R.id.ediBttn);
        final Button boton3 = (Button) findViewById(R.id.mapaBttn);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (boton1.isPressed()){
                    intent = new Intent(MainActivity.this, ListaDiscapacidades.class);
                    startActivity(intent);
                }
                if (boton2.isPressed()){
                    intent = new Intent(MainActivity.this, ListaEdificios.class);
                    startActivity(intent);
                }
                if (boton3.isPressed()){
                    intent = new Intent(MainActivity.this, MapaCampus.class);
                    startActivity(intent);
                }
            }
        };

        boton1.setOnClickListener(listener);
        boton2.setOnClickListener(listener);
        boton3.setOnClickListener(listener);

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
