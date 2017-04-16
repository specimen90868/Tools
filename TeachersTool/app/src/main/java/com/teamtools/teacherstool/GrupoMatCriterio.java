package com.teamtools.teacherstool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.teamtools.teacherstool.adapters.AdapterRGMC;
import com.teamtools.teacherstool.helpers.CriteriosHelper;
import com.teamtools.teacherstool.helpers.RGrupoMateriaCriterioHelper;
import com.teamtools.teacherstool.models.Criterios;
import com.teamtools.teacherstool.models.RelacionGrupoMateriaCriterio;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;

public class GrupoMatCriterio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_mat_criterio);
        this.initComponents();
    }

    private void initComponents(){
        this.idRGruMat=Shared.IdShared;
        this.lstvrGruMatCri = (ListView)findViewById(R.id.lstvGrMatCri);
        this.registerForContextMenu(this.lstvrGruMatCri);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nuevoRGMCrit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), frmGrupMatCrit.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListaRelacionGMC();

    }

    void ListaRelacionGMC()
    {
        RGrupoMateriaCriterioHelper rgmch = new RGrupoMateriaCriterioHelper(this);
        RelacionGrupoMateriaCriterio rgcm = new RelacionGrupoMateriaCriterio(idRGruMat,0,"",0);
        rgmch.open();
        ArrayList<RelacionGrupoMateriaCriterio> lstRGMC = new ArrayList<>();
        lstRGMC = rgmch.obtenerGrupoMateriasCriterios(rgcm);
        rgmch.close();
        ArrayAdapter aRGMC = new AdapterRGMC(getApplicationContext(), lstRGMC);
        this.lstvrGruMatCri.setAdapter(aRGMC);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflate  = this.getMenuInflater();
        inflate.inflate(R.menu.menu_context_gpomat, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuGrupoMateria = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        ListAdapter gpomat = lstvrGruMatCri.getAdapter();
        RelacionGrupoMateriaCriterio rgm = (RelacionGrupoMateriaCriterio) gpomat.getItem(menuGrupoMateria.position);
        final Integer idGrupoMateria = rgm.getIdRGM();
        Log.v("ID", idGrupoMateria.toString());
        final Context context = this;
        switch (item.getItemId()) {
            case R.id.rgmBorrar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Deseas borrar la materia del grupo?");
                builder.setTitle("Confirmación");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        RGrupoMateriaCriterioHelper rgmh = new RGrupoMateriaCriterioHelper(context);
                        RelacionGrupoMateriaCriterio grupomateria = new RelacionGrupoMateriaCriterio(idGrupoMateria,0,"",0);
                        rgmh.open();
                        rgmh.eliminarRGMC(grupomateria);
                        rgmh.close();
                        ListaRelacionGMC();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.rgmCriterio:
                Intent _intent = new Intent(getBaseContext(), GrupoMatCriterio.class);
                Shared.IdShared = idGrupoMateria;
                startActivity(_intent);
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return  true;
    }

    private ListView lstvrGruMatCri;
    private Integer idRGruMat;

}
