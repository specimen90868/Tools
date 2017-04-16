package com.teamtools.teacherstool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamtools.teacherstool.R;
import com.teamtools.teacherstool.models.Criterios;
import com.teamtools.teacherstool.models.RelacionGrupoMateriaCriterio;

import java.util.ArrayList;

/**
 * Created by CECILIA on 20/03/2017.
 */

public class AdapterRGMC extends ArrayAdapter<RelacionGrupoMateriaCriterio> {

    public AdapterRGMC(Context context, ArrayList<RelacionGrupoMateriaCriterio> objects) {
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
            v = inflater.inflate(R.layout.lista_grupo_mat_crit, parent, false);
        }

        TextView IdRGM = (TextView)v.findViewById(R.id.IdrGMC);
        TextView IdcCri = (TextView)v.findViewById(R.id.rGMCcCriID);
        TextView rgmcInicial = (TextView)v.findViewById(R.id.rGMCInical);
        TextView rGMCcCriNombre = (TextView)v.findViewById(R.id.rGMCNombre);
        TextView rgmcPorcen = (TextView)v.findViewById(R.id.rGMCPorcentaje);

        RelacionGrupoMateriaCriterio item = getItem(position);
        IdRGM.setText(Integer.toString(item.getIdRGM()));
        IdcCri.setText(Integer.toString(item.getIdcCri()));
        String inicial = item.getcCriNombre().substring(0,1);
        rgmcInicial.setText(inicial.toUpperCase());
        rGMCcCriNombre.setText(item.getcCriNombre());
        rgmcPorcen.setText(Integer.toString(item.getPorcentaje()));
        return v;
    }
}
