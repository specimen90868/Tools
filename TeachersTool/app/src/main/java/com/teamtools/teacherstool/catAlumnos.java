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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamtools.teacherstool.adapters.AdapterAlumnos;
import com.teamtools.teacherstool.helpers.AlumnosHelper;
import com.teamtools.teacherstool.models.Alumnos;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;

import static com.teamtools.teacherstool.R.id.lstvAlumnos;

public class catAlumnos extends AppCompatActivity {
    public ListView lstvAlumnos;
    //private EditText txtNombreGrupo;
    private Integer idRGM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_alumnos);

        this.initComponents();
    }

    private void initComponents() {
      //  this.txtNombreGrupo = (EditText) findViewById(R.id.txtNumLista);
      //  idGrupo = getIntent().getExtras().getString("clave_grupo");
        //this.setTitle(getIntent().getExtras().getString("nombre_grupo"));
        this.setTitle(Shared.cadena);
        this.lstvAlumnos = (ListView)findViewById(R.id.lstvAlumnos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAlumnos);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nuevoAlumnos);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getBaseContext(), frmAlumnos.class );
               // Shared.IdShared = Integer.parseInt(idGrupo);
               // intent.putExtra("clave_grupo",idGrupo);
                intent.putExtra("clave_alumno","0");
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListaAlumnos();
    }
    private void ListaAlumnos(){

        AlumnosHelper ah = new AlumnosHelper(this);
        ah.open();
        ArrayList<Alumnos> lstAlumnos = new ArrayList<>();

        Alumnos alumno = new Alumnos(0,0, "", "", "","","","",Shared.IdShared);
        lstAlumnos = ah.obtenerAlumnosxGrupo(alumno);
        //lstAlumnos = ah.obtenerAlumnos();
        ah.close();
        ArrayAdapter aAlumnos = new AdapterAlumnos(getApplicationContext(), lstAlumnos);
        this.lstvAlumnos.setAdapter(aAlumnos);

        lstvAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView c = (TextView) view.findViewById(R.id.IdcAlumno);
                String idAlumno = c.getText().toString();

                Intent _intent = new Intent(getBaseContext(), frmAlumnos.class);
                _intent.putExtra("clave_alumno", idAlumno);
                startActivity(_intent);
            }
        });

        lstvAlumnos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popup = new PopupMenu(catAlumnos.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_context_alumnos, popup.getMenu());
                TextView c = (TextView) view.findViewById(R.id.IdcAlumno);

                final String idAlumno = c.getText().toString();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    //MENU FILA

                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_delete_alumno:
                                AlertDialog.Builder builder=new AlertDialog.Builder(catAlumnos.this);
                                builder.setMessage("¿Desea eliminar el alumno?");
                                builder.setTitle("Confirmación.");
                                builder.setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean eliminado=false;
                                        try {

                                            AlumnosHelper ah = new AlumnosHelper(catAlumnos.this);
                                            ah.open();
                                            Alumnos alumno = new Alumnos(Integer.parseInt(idAlumno),0,"","","","","","",0);
                                            eliminado = ah.eliminaAlumno(alumno);

                                            ah.close();
                                            if(eliminado)
                                            {
                                                Toast.makeText(getBaseContext(),"El alumno ha sido eliminado.", Toast.LENGTH_SHORT).show();
                                                Intent intAlumnos = new Intent(catAlumnos.this,catAlumnos.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intAlumnos);
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

                                break;
                            case R.id.menu_item_editar_alumno:
                                Intent _intent = new Intent(getBaseContext(), frmAlumnos.class);
                                _intent.putExtra("clave_alumno", idAlumno);
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
        getMenuInflater().inflate(R.menu.menu_context_alumnos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_alumno:
                Toast.makeText(catAlumnos.this, "soy eliminar del menu oculto", Toast.LENGTH_LONG).show();
                break;
            case R.id.toolbarAlumnos:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
