package com.teamtools.teacherstool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.teamtools.teacherstool.adapters.AdapterCriterios;
import com.teamtools.teacherstool.helpers.CriteriosHelper;
import com.teamtools.teacherstool.models.Criterios;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;

public class catCriterios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_criterios);
        this.initComponents();
    }

    private void initComponents(){
        this.lstvCriterios=(ListView)findViewById(R.id.lstvCriterios);
        this.registerForContextMenu(this.lstvCriterios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCriterios);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nuevoCriterios);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getBaseContext(),frmCriterios.class );
                Shared.IdShared = 0;
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListaCriterios();
    }
    void ListaCriterios(){
        CriteriosHelper ch=new CriteriosHelper(this);
        ch.open();
        ArrayList<Criterios> lstCriterios = new ArrayList<>();
        lstCriterios = ch.obtenerCriterios();
        ch.close();
        ArrayAdapter aCriterios=new AdapterCriterios(getApplicationContext(),lstCriterios);
        this.lstvCriterios.setAdapter(aCriterios);

        lstvCriterios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView c = (TextView) view.findViewById(R.id.IdcCriterio);
                String idCriterio = c.getText().toString();
                Intent _intent = new Intent(getBaseContext(), frmCriterios.class);
                Shared.IdShared = Integer.parseInt(idCriterio);
                startActivity(_intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflate  = this.getMenuInflater();
        inflate.inflate(R.menu.menu_context_criterios, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuCriterio = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        ListAdapter criterio = lstvCriterios.getAdapter();
        Criterios c = (Criterios)criterio.getItem(menuCriterio.position);
        final Integer idCriterio = c.getIdcCri();
        final Context context = this;
        switch (item.getItemId()) {
            case R.id.cEditar:
                Intent _intent = new Intent(getBaseContext(), frmCriterios.class);
                Shared.IdShared = idCriterio;
                startActivity(_intent);
                break;

            case R.id.cBorrar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Deseas borrar el criterio?");
                builder.setTitle("Confirmación");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        CriteriosHelper ch = new CriteriosHelper(context);
                        Criterios criterio = new Criterios(idCriterio,"");
                        ch.open();
                        ch.eliminarCriterio(criterio);
                        ch.close();
                        ListaCriterios();
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

    private ListView lstvCriterios;
}
