package com.teamtools.teacherstool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.teamtools.teacherstool.adapters.AdapterMaterias;
import com.teamtools.teacherstool.helpers.GruposHelper;
import com.teamtools.teacherstool.helpers.MateriasHelper;
import com.teamtools.teacherstool.helpers.RelacionGrupoMateriaHelper;
import com.teamtools.teacherstool.models.Grupos;
import com.teamtools.teacherstool.models.Materias;
import com.teamtools.teacherstool.models.RelacionGrupoMaterias;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;
import java.util.List;

public class frmGrupoMaterias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_grupo_materias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initComponents();
    }

    private void initComponents() {
        this.spnMaterias = (Spinner) findViewById(R.id.spnMaterias);
        this.idGrupo = Shared.IdShared;

        MateriasHelper mh = new MateriasHelper(this);
        mh.open();
        ArrayList<Materias> materias = mh.obtenerMaterias();
        mh.close();
        List<Materias> prueba = new ArrayList<>();
        for (int i = 0; i < materias.size(); i++)
        {
            prueba.add(new Materias(materias.get(i).getIdcMat(),materias.get(i).getcMaNombre()));
        }
        ArrayAdapter materiasAdapter = new AdapterMaterias(getApplicationContext(), prueba);
        spnMaterias.setAdapter(materiasAdapter);

        this.spnMaterias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerAdapter mat = spnMaterias.getAdapter();
                m = (Materias) mat.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClickGuardar(View view){

        RelacionGrupoMaterias rgm = null;
        RelacionGrupoMateriaHelper rgmh = new RelacionGrupoMateriaHelper(this);
        boolean guardado = false;
        Integer existeRelacion = 0;
        rgm = new RelacionGrupoMaterias(0, idGrupo, m.getIdcMat(), "");
        try {
            rgmh.open();
            existeRelacion = rgmh.existeAsignacion(rgm);
            rgmh.close();
        }
        catch(Exception error){
            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        if (existeRelacion == 0){
            try {
                rgmh.open();
                if(idGrupo != 0) {
                    guardado = rgmh.insertaRelacionGM(rgm);
                }
                rgmh.close();
                if(guardado)
                {
                    Toast.makeText(getBaseContext(),"Materia asignada.", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception error){
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        }
        else
        {
            Toast.makeText(getBaseContext(),"La materia ya se encuentra asignada", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private Integer idGrupo;
    private Spinner spnMaterias;
    private Materias m;
}
