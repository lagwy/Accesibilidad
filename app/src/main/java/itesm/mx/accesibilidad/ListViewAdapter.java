/*
Accesibilidad - Proyecto de la materia de Desarrollo de Aplicaciones Móviles
        Copyright (C) 2015 - ITESM

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.


        Authors:

        ITESM representatives
        Ing. Martha Sordia Salinas <msordia@itesm.mx>
        Ing. Mario de la Fuente <mario.delafuente@itesm.mx>

        ITESM students
        Luis Angel Martinez Garcia <a00813485@itesm.mx>
        Daniel Garcia Mena <a00813719@itesm.mx>
        Jorge Luis Marquez Sanchez <a01139543@itesm.mx>
*/

package itesm.mx.accesibilidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Renglon> {
    int layoutResourceId;
    List<Renglon> renglones;
    private Context context;

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

/*
 * Copyright (C) 2015 ITESM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
