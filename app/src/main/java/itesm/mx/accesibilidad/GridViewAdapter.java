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
 * Created by Daniel on 14/11/15.
 */
public class GridViewAdapter extends ArrayAdapter<Renglon> {

    private Context context;
    int layoutResourceId;
    List<Renglon> renglones;

    public GridViewAdapter (Context context, int idResource, List<Renglon> renglonList){
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

        TextView renglonTV = (TextView) row.findViewById(R.id.textViewR);
        ImageView renglonIV = (ImageView) row.findViewById(R.id.imageR);

        Renglon renglon = renglones.get(position);
        renglonTV.setText(renglon.getNombre());
        renglonIV.setImageBitmap(renglon.getImagen());

        return row;
    }
}
