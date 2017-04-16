package com.teamtools.teacherstool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamtools.teacherstool.R;
import com.teamtools.teacherstool.models.Grupos;
import com.teamtools.teacherstool.models.RelacionGrupoMaterias;

import java.util.ArrayList;

/**
 * Created by fabricio.vilchis on 02/12/2016.
 */
public class AdapterRelacionGpoMat extends ArrayAdapter<RelacionGrupoMaterias> {

    public AdapterRelacionGpoMat(Context context, ArrayList<RelacionGrupoMaterias> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;

        if (null == convertView)
        {
            v = inflater.inflate(R.layout.lista_grupo_materias, parent, false);
        }

        TextView IdRGM = (TextView)v.findViewById(R.id.IdRGM);
        TextView IdcGrupo = (TextView)v.findViewById(R.id.IdcGrupoRGM);
        TextView IdcMat = (TextView)v.findViewById(R.id.IdcMatRGM);
        TextView cGrInicial = (TextView)v.findViewById(R.id.cGrMInicial);
        TextView cMatNombre = (TextView)v.findViewById(R.id.cMaNombreRGM);

        RelacionGrupoMaterias item = getItem(position);
        IdRGM.setText(Integer.toString(item.getIdRGM()));
        String inicial = item.getcMaNombre().substring(0,1);
        cGrInicial.setText(inicial.toUpperCase());
        IdcGrupo.setText(Integer.toString(item.getIdcGrupo()));
        IdcMat.setText(Integer.toString(item.getIdcMat()));
        cMatNombre.setText(item.getcMaNombre());
        return v;
    }
}
