package itesm.mx.accesibilidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Luis Ángel on 11/11/2015.
 */
public class ListViewAdapter extends ArrayAdapter<Renglon> {
    private Context context;
    int layoutResourceId;
    List<Renglon> renglones;

    public ListViewAdapter(Context context, int idResource, List<Renglon> renglonList){
        super(context, idResource, renglonList);
        this.context = context;
        this.layoutResourceId = idResource;
        this.renglones = renglonList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;

        if (row ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            row = inflater.inflate(layoutResourceId, parent, false);

        }

        TextView renglonTV = (TextView) row.findViewById(R.id.renglonTV);
        ImageView renglonIV = (ImageView) row.findViewById(R.id.renglonIV);

        Renglon renglon = renglones.get(position);
        renglonTV.setText(renglon.getNombre());
        renglonIV.setImageBitmap(renglon.getImagen());

        return row;
    }
}
