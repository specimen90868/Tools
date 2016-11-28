package com.teamtools.teacherstool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamtools.teacherstool.R;
import com.teamtools.teacherstool.models.Grupos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xpermpento on 25/09/16.
 */
public class AdapterGrupos extends ArrayAdapter<Grupos> {

    public AdapterGrupos(Context context, ArrayList<Grupos> objects) {
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
            v = inflater.inflate(R.layout.lista_grupos, parent, false);
        }

        TextView IdcGrupo = (TextView)v.findViewById(R.id.IdcGrupo);
        TextView cGrNombre = (TextView)v.findViewById(R.id.cGrNombre);
        TextView cGrPeriodos = (TextView)v.findViewById(R.id.cGrPeriodos);
        TextView cGrTexto = (TextView)v.findViewById(R.id.cGrTexto);
        TextView cGrInicial = (TextView)v.findViewById(R.id.cGrInicial);

        Grupos item = getItem(position);
        IdcGrupo.setText(Integer.toString(item.getIdcGrupo()));
        String inicial = item.getcGrNombre().substring(0,1);
        cGrInicial.setText(inicial.toUpperCase());
        cGrNombre.setText(item.getcGrNombre());
        cGrPeriodos.setText("Periodos: " + Integer.toString(item.getcGrPeriodos()));
        cGrTexto.setText("Alumnos: 0");
        return v;
    }
}
