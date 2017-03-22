package com.teamtools.teacherstool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamtools.teacherstool.adapters.AdapterMaterias;
import com.teamtools.teacherstool.helpers.GruposHelper;
import com.teamtools.teacherstool.helpers.MateriasHelper;
import com.teamtools.teacherstool.models.Grupos;
import com.teamtools.teacherstool.models.Materias;

import java.util.ArrayList;

public class catMaterias extends AppCompatActivity {
    public ListView lstvMaterias;
    //  private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_materias);
        this.initComponents();
    }

    private void initComponents() {

        this.lstvMaterias = (ListView)findViewById(R.id.lstvMaterias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMaterias);
        setSupportActionBar(toolbar);

        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.nuevoMaterias);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getBaseContext(), frmMaterias.class );
                intent.putExtra("clave_materia","0");
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MateriasHelper mh = new MateriasHelper(this);
        mh.open();
        ArrayList<Materias> lstMaterias = new ArrayList<>();
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
                _intent.putExtra("clave_materia", idMateria);
                startActivity(_intent);
            }
        });

        lstvMaterias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popup = new PopupMenu(catMaterias.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_context_materias, popup.getMenu());
                TextView c = (TextView) view.findViewById(R.id.IdcMateria);
                final String idMateria = c.getText().toString();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    //MENU FILA

                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_delete_materia:
                                AlertDialog.Builder builder=new AlertDialog.Builder(catMaterias.this);
                                builder.setMessage("¿Desea eliminar la materia?");
                                builder.setTitle("Confirmación.");
                                builder.setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean eliminado=false;
                                        try {

                                            MateriasHelper mh = new MateriasHelper(catMaterias.this);
                                            mh.open();
                                            Materias materia = new Materias(Integer.parseInt(idMateria),"");
                                            eliminado = mh.eliminaMateria(materia);

                                            mh.close();
                                            if(eliminado)
                                            {
                                                Toast.makeText(getBaseContext(),"La materia ha sido eliminada.", Toast.LENGTH_SHORT).show();
                                                Intent intcriterios=new Intent(catMaterias.this,catMaterias.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intcriterios);
                                            }
                                        }
                                        catch(Exception error){
                                            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog dialog= builder.create();
                                dialog.show();
                                //Toast.makeText(catCriterios.this, "Eliminar Criterio: "+idCriterio, Toast.LENGTH_LONG).show();

                                break;
                            case R.id.menu_item_editar_materia:
                                Intent _intent = new Intent(getBaseContext(), frmMaterias.class);
                                _intent.putExtra("clave_materia", idMateria);
                                startActivity(_intent);
                                break;
                        }
                        return true;
                    }
                    //FIN MENU FILA
                });
                popup.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_context_materias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_materia:
                Toast.makeText(catMaterias.this, "soy eliminar del menu oculto", Toast.LENGTH_LONG).show();
                break;
            case R.id.toolbarMaterias:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


