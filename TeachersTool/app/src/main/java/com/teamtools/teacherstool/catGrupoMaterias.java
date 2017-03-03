package com.teamtools.teacherstool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.teamtools.teacherstool.adapters.AdapterRelacionGpoMat;
import com.teamtools.teacherstool.helpers.RelacionGrupoMateriaHelper;
import com.teamtools.teacherstool.models.RelacionGrupoMaterias;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;


public class catGrupoMaterias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_grupo_materias);
        this.initComponents();
    }

    private void initComponents() {

        this.idGrupo = Shared.IdShared;
        this.lstvRelacionGM = (ListView)findViewById(R.id.lstvGrupoMaterias);
        this.registerForContextMenu(this.lstvRelacionGM);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nuevoRelacionGM);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), frmGrupoMaterias.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListaRelacionGM();
    }

    void ListaRelacionGM()
    {
        RelacionGrupoMateriaHelper rgmh = new RelacionGrupoMateriaHelper(this);
        RelacionGrupoMaterias rgm = new RelacionGrupoMaterias(0,idGrupo,0,"");
        rgmh.open();
        ArrayList<RelacionGrupoMaterias> lstRGM = new ArrayList<>();
        lstRGM = rgmh.obtenerGrupoMaterias(rgm);
        rgmh.close();
        ArrayAdapter aRGM = new AdapterRelacionGpoMat(getApplicationContext(), lstRGM);
        this.lstvRelacionGM.setAdapter(aRGM);
    }

    private ListView lstvRelacionGM;
    private Integer idGrupo;
}
