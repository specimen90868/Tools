package com.teamtools.teacherstool;

import android.app.Dialog;
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
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamtools.teacherstool.adapters.AdapterGrupos;
import com.teamtools.teacherstool.helpers.GruposHelper;
import com.teamtools.teacherstool.models.Grupos;

import java.util.ArrayList;

public class catGrupos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_grupos);
        this.initComponents();
    }

    private void initComponents() {

        this.lstvGrupos = (ListView)findViewById(R.id.lstvGrupos);
        this.registerForContextMenu(this.lstvGrupos);
        /*LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_grupos, lstvGrupos, false);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), frmGrupos.class);
                intent.putExtra("clave_grupo", "0");
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListaGrupos();
    }

    void ListaGrupos()
    {
        GruposHelper gh = new GruposHelper(this);
        gh.open();
        ArrayList<Grupos> lstGrupos = new ArrayList<>();
        lstGrupos = gh.obtenerGrupos();
        gh.close();
        ArrayAdapter aGrupos = new AdapterGrupos(getApplicationContext(), lstGrupos);
//      this.lstvGrupos.addHeaderView(header,null,false);
        this.lstvGrupos.setAdapter(aGrupos);

        lstvGrupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView c = (TextView) view.findViewById(R.id.IdcGrupo);
                String idGrupo = c.getText().toString();

                Intent _intent = new Intent(getBaseContext(), frmGrupos.class);
                _intent.putExtra("clave_grupo", idGrupo);
                startActivity(_intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflate  = this.getMenuInflater();
        inflate.inflate(R.menu.menu_context_grupos, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuGrupo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        ListAdapter grupo = lstvGrupos.getAdapter();
        Grupos g = (Grupos)grupo.getItem(menuGrupo.position);
        final String idGrupo = Integer.toString(g.getIdcGrupo());
        final Context context = this;
        switch (item.getItemId()) {
            case R.id.gEditar:
                Intent _intent = new Intent(getBaseContext(), frmGrupos.class);
                _intent.putExtra("clave_grupo", idGrupo);
                startActivity(_intent);
                break;

            case R.id.gBorrar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Deseas borrar el grupo?");
                builder.setTitle("Confirmación");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        GruposHelper gh = new GruposHelper(context);
                        Grupos grupo = new Grupos(Integer.parseInt(idGrupo),"",0);
                        gh.open();
                        gh.eliminaGrupo(grupo);
                        gh.close();
                        ListaGrupos();
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

    private ListView lstvGrupos;
}