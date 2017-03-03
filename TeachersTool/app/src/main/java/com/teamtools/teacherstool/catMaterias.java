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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.teamtools.teacherstool.adapters.AdapterMaterias;
import com.teamtools.teacherstool.helpers.MateriasHelper;
import com.teamtools.teacherstool.models.Materias;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;
import java.util.List;

public class catMaterias extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_materias);
        this.initComponents();
    }

    private void initComponents() {

        this.lstvMaterias = (ListView)findViewById(R.id.lstvMaterias);
        this.registerForContextMenu(this.lstvMaterias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.nuevaMateria);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getBaseContext(), frmMaterias.class );
                Shared.IdShared = 0;
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListaMaterias();
    }

    void ListaMaterias(){
        MateriasHelper mh = new MateriasHelper(this);
        mh.open();
        List<Materias> lstMaterias = new ArrayList<>();
        lstMaterias = mh.obtenerMaterias();
        mh.close();
        ArrayAdapter aMaterias = new AdapterMaterias(getApplicationContext(), lstMaterias);
        this.lstvMaterias.setAdapter(aMaterias);

        lstvMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView c = (TextView) view.findViewById(R.id.IdcMateria);
                String idMateria = c.getText().toString();

                Intent _intent = new Intent(getBaseContext(), frmMaterias.class);
                Shared.IdShared = Integer.parseInt(idMateria);
                startActivity(_intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflate  = this.getMenuInflater();
        inflate.inflate(R.menu.menu_context_materias, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuMateria = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        ListAdapter materia = lstvMaterias.getAdapter();
        Materias m = (Materias)materia.getItem(menuMateria.position);
        final Integer idMateria = m.getIdcMat();
        final Context context = this;
        switch (item.getItemId()) {
            case R.id.mEditar:
                Intent _intent = new Intent(getBaseContext(), frmMaterias.class);
                Shared.IdShared = idMateria;
                startActivity(_intent);
                break;

            case R.id.mBorrar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Deseas borrar la materia?");
                builder.setTitle("Confirmación");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        MateriasHelper mh = new MateriasHelper(context);
                        Materias materia = new Materias(idMateria,"");
                        mh.open();
                        mh.eliminaMateria(materia);
                        mh.close();
                        ListaMaterias();
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

            default:
                return super.onContextItemSelected(item);
        }
        return  true;
    }

    public ListView lstvMaterias;
}


