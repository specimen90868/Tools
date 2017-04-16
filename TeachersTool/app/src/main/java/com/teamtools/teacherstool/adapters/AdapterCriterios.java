package com.teamtools.teacherstool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamtools.teacherstool.R;
import com.teamtools.teacherstool.models.Criterios;
import com.teamtools.teacherstool.models.Grupos;

import java.util.ArrayList;
/**
 * Created by CECILIA on 09/10/2016.
 */

public class AdapterCriterios extends ArrayAdapter<Criterios>{

    public AdapterCriterios(Context context, ArrayList<Criterios> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v=convertView;

        if(null==convertView){
            v=inflater.inflate(R.layout.lista_criterios, parent, false);
        }

        TextView cCriInicial=(TextView)v.findViewById(R.id.cCrInicial);
        TextView cCriId=(TextView)v.findViewById(R.id.IdcCriterio);
        TextView cCriNombre=(TextView)v.findViewById(R.id.cCriNombre);

        Criterios item=getItem(position);
        cCriId.setText(Integer.toString(item.getIdcCri()));
        String incial=item.getcCriNombre().substring(0,1);
        cCriInicial.setText(incial.toUpperCase());
        cCriNombre.setText(item.getcCriNombre());
        return v;

    }

}
