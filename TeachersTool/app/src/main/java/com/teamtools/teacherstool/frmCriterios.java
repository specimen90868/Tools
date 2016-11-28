package com.teamtools.teacherstool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.teamtools.teacherstool.helpers.CriteriosHelper;
import com.teamtools.teacherstool.models.Criterios;

import java.util.ArrayList;

public class frmCriterios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_criterios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.initComponents();

        if (Integer.parseInt(idCriterio)!=0){
            this.obtenerCriterio();
        }

    }

    private void initComponents(){
        this.txtNombreCriterio = (EditText)findViewById(R.id.txtNombreCriterio);
        this.idCriterio = getIntent().getExtras().getString("clave_criterio");
    }

    public void onClickGuardar(View view){

        String nomcriterio = txtNombreCriterio.getText().toString();

        if(nomcriterio.isEmpty()){
            txtNombreCriterio.setError("Debes ingresar un criterio.");
            return;
        }

        Criterios criterio= null;
        CriteriosHelper gh = new CriteriosHelper(this);
        boolean guardado = false;
        try {
            gh.open();
            if(Integer.parseInt(idCriterio) != 0) {
                criterio = new Criterios(Integer.parseInt(idCriterio), nomcriterio);
                guardado = gh.actualizaCriterio(criterio);
            }
            else {
                criterio = new Criterios(0, nomcriterio);
                guardado = gh.insertCriterio(criterio);
            }
            gh.close();
            if(guardado)
            {
                Toast.makeText(getBaseContext(),"El criterio ha sido guardado.", Toast.LENGTH_SHORT).show();
                Intent intcriterios = new Intent(frmCriterios.this,catCriterios.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intcriterios);
            }
        }
        catch(Exception error){
            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void obtenerCriterio()
    {
        Criterios criterio = new Criterios(Integer.parseInt(idCriterio),"");
        CriteriosHelper gh = new CriteriosHelper(this);
        ArrayList<Criterios> lstCriterios = new ArrayList<>();
        gh.open();
        lstCriterios = gh.obtenerCriterio(criterio);
        gh.close();
        for(int i = 0; i < lstCriterios.size(); i++)
        {
            Criterios item = lstCriterios.get(i);
            this.txtNombreCriterio.setText(item.getcCriNombre());
        }
    }

    private String idCriterio;
    private EditText txtNombreCriterio;

}
