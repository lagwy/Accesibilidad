package itesm.mx.accesibilidad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Creditos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        final Button backBttn = (Button) findViewById(R.id.bbttn);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        };
        backBttn.setOnClickListener(listener);


    }

}
