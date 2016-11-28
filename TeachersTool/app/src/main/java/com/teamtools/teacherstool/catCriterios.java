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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.teamtools.teacherstool.adapters.AdapterCriterios;
import com.teamtools.teacherstool.helpers.CriteriosHelper;
import com.teamtools.teacherstool.models.Criterios;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class catCriterios extends AppCompatActivity {

    private PopupMenu popupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_criterios);
        this.initComponents();
    }

    private void initComponents(){
        this.lstvCriterios=(ListView)findViewById(R.id.lstvCriterios);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.nuevoCriterios);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getBaseContext(),frmCriterios.class );
                intent.putExtra("clave_criterio","0");
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                _intent.putExtra("clave_criterio", idCriterio);
                startActivity(_intent);
            }
        });

        lstvCriterios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popup = new PopupMenu(catCriterios.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_context_criterios, popup.getMenu());
                TextView c = (TextView) view.findViewById(R.id.IdcCriterio);
                final String idCriterio = c.getText().toString();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    //MENU FILA

                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_delete_criterio:
                                AlertDialog.Builder builder=new AlertDialog.Builder(catCriterios.this);
                                builder.setMessage("¿Desea borrar el criterio?");
                                builder.setTitle("Confirmación.");
                                builder.setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean eliminado=false;
                                        try {

                                            CriteriosHelper gh = new CriteriosHelper(catCriterios.this);
                                            gh.open();
                                            Criterios criterio = new Criterios(Integer.parseInt(idCriterio),"");
                                            eliminado = gh.eliminarCriterio(criterio);

                                            gh.close();
                                            if(eliminado)
                                            {
                                                Toast.makeText(getBaseContext(),"El criterio ha sido borrado.", Toast.LENGTH_SHORT).show();
                                                Intent intcriterios = new Intent(catCriterios.this,catCriterios.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                            case R.id.menu_item_editar_criterio:
                                Intent _intent = new Intent(getBaseContext(), frmCriterios.class);
                                _intent.putExtra("clave_criterio", idCriterio);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_context_criterios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_criterio:
                Toast.makeText(catCriterios.this, "soy eliminar del menu oculto", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


    private ListView lstvCriterios;
}
