package com.teamtools.teacherstool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.teamtools.teacherstool.adapters.AdapterSpinnerMaterias;
import com.teamtools.teacherstool.helpers.MateriasHelper;
import com.teamtools.teacherstool.models.Materias;

import java.util.ArrayList;

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
        this.spnMaterias = (Spinner)findViewById(R.id.spnMaterias);
        this.idGrupo = getIntent().getExtras().getString("clave_grupomateria");

        MateriasHelper mh = new MateriasHelper(this);
        ArrayList<Materias> lstMaterias = new ArrayList<>();
        mh.open();
        lstMaterias = mh.obtenerMaterias();
        mh.close();
        ArrayAdapter aMaterias = new AdapterSpinnerMaterias(getApplicationContext(), lstMaterias);
        spnMaterias.setAdapter(aMaterias);
    }

    public void onClickGuardar(View view){

    }

    private String idGrupo;
    private Spinner spnMaterias;
}
