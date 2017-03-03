package com.teamtools.teacherstool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.teamtools.teacherstool.helpers.GruposHelper;
import com.teamtools.teacherstool.models.Grupos;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;

public class frmGrupos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_grupos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.initComponents();

        if(idGrupo != 0)
        {
            this.obtenerGrupo();
        }
    }

    private void initComponents() {
        this.txtNombre = (EditText)findViewById(R.id.txtNombre);
        this.txtPeriodos = (EditText)findViewById(R.id.txtPeriodos);
        this.idGrupo = Shared.IdShared;
    }

    public void onClickGuardar(View view){

        String nombre = txtNombre.getText().toString();
        String periodos = txtPeriodos.getText().toString();

        if(nombre.isEmpty()){
            txtNombre.setError("Debes ingresar un nombre.");
            return;
        }

        if (periodos.isEmpty()){
            txtPeriodos.setError("Debes ingresar un periodo.");
            return;
        }

        int nPeriodos = Integer.parseInt(periodos);

        Grupos grupo = null;
        GruposHelper gh = new GruposHelper(this);
        boolean guardado = false;
        try {
            gh.open();
            if(idGrupo != 0) {
                grupo = new Grupos(idGrupo, nombre, nPeriodos);
                guardado = gh.actualizaGrupo(grupo);
            }
            else {
                grupo = new Grupos(0, nombre, nPeriodos);
                guardado = gh.insertaGrupo(grupo);
            }
            gh.close();
            if(guardado)
            {
                Toast.makeText(getBaseContext(),"Tu grupo ha sido guardado.", Toast.LENGTH_SHORT).show();
                this.txtNombre.setText("");
                this.txtPeriodos.setText("");
                Intent intent = new Intent(this, catGrupos.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        catch(Exception error){
            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        //onBackPressed();
    }

    private void obtenerGrupo()
    {
        Grupos grupo = new Grupos(idGrupo,"",0);
        GruposHelper gh = new GruposHelper(this);
        ArrayList<Grupos> lstGrupos = new ArrayList<>();
        gh.open();
        lstGrupos = gh.obtenerGrupo(grupo);
        gh.close();
        for(int i = 0; i < lstGrupos.size(); i++)
        {
            Grupos item = lstGrupos.get(i);
            this.txtNombre.setText(item.getcGrNombre());
            this.txtPeriodos.setText(Integer.toString(item.getcGrPeriodos()));
        }
    }

    private EditText txtNombre;
    private EditText txtPeriodos;
    private Integer idGrupo;
}
