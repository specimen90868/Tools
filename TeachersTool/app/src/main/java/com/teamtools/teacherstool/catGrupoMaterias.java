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
import com.teamtools.teacherstool.helpers.GruposHelper;
import com.teamtools.teacherstool.helpers.RelacionGrupoMateriaHelper;
import com.teamtools.teacherstool.models.Grupos;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflate  = this.getMenuInflater();
        inflate.inflate(R.menu.menu_context_gpomat, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuGrupoMateria = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        ListAdapter gpomat = lstvRelacionGM.getAdapter();
        RelacionGrupoMaterias rgm = (RelacionGrupoMaterias)gpomat.getItem(menuGrupoMateria.position);
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
                        RelacionGrupoMateriaHelper rgmh = new RelacionGrupoMateriaHelper(context);
                        RelacionGrupoMaterias grupomateria = new RelacionGrupoMaterias(idGrupoMateria,0,0,"");
                        rgmh.open();
                        rgmh.eliminaRelacionGM(grupomateria);
                        rgmh.close();
                        ListaRelacionGM();
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

    private ListView lstvRelacionGM;
    private Integer idGrupo;
    //private Integer idGrupoMateria;

}
