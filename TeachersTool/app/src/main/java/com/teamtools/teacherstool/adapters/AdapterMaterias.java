package com.teamtools.teacherstool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamtools.teacherstool.R;
import com.teamtools.teacherstool.models.Materias;

import java.util.ArrayList;

/**
 * Created by pp on 19/10/2016.
 */

public class AdapterMaterias extends ArrayAdapter<Materias> {
    public AdapterMaterias(Context context, ArrayList<Materias> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;

        if (null == convertView)
        {
            v = inflater.inflate(R.layout.lista_materias, parent, false);
        }

        TextView IdcMateria = (TextView)v.findViewById(R.id.IdcMateria);
        TextView cGrNombre = (TextView)v.findViewById(R.id.cGrNombre);
        TextView cGrTexto = (TextView)v.findViewById(R.id.cGrTexto);
        TextView cGrInicial = (TextView)v.findViewById(R.id.cGrInicial);

        Materias item = getItem(position);
        IdcMateria.setText(Integer.toString(item.getIdcMat()));
        String inicial = item.getcMaNombre().substring(0,1);
        cGrInicial.setText(inicial.toUpperCase());
        cGrNombre.setText(item.getcMaNombre());
        cGrTexto.setText("Alumnos: 0");
        return v;
    }
}
