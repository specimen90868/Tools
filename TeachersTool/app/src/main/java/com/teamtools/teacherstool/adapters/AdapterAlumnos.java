package com.teamtools.teacherstool.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamtools.teacherstool.R;
import com.teamtools.teacherstool.models.Alumnos;
import com.teamtools.teacherstool.models.Materias;

import java.util.ArrayList;

/**
 * Created by pepe on 02/12/2016.
 */

public class AdapterAlumnos extends ArrayAdapter<Alumnos> {
    public AdapterAlumnos(Context context, ArrayList<Alumnos> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;

        if (null == convertView)
        {
            v = inflater.inflate(R.layout.lista_alumnos, parent, false);
        }

        TextView IdcAlumno = (TextView)v.findViewById(R.id.IdcAlumno);
        TextView cAlNombre = (TextView)v.findViewById(R.id.cAlNombre);
        TextView cAlInicial = (TextView)v.findViewById(R.id.cAlInicial);

        Alumnos item = getItem(position);
        IdcAlumno.setText(Integer.toString(item.getIdcAlumno()));
        String inicial = item.getcAlNombre().substring(0,1);
        cAlInicial.setText(inicial.toUpperCase());
        cAlNombre.setText(item.getcAlNombre());
        return v;
    }
}
