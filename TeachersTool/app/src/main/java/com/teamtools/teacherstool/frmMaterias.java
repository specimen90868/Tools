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

import com.teamtools.teacherstool.helpers.MateriasHelper;
import com.teamtools.teacherstool.models.Materias;

import java.util.ArrayList;

public class frmMaterias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_materias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.initComponents();

        if(Integer.parseInt(idMateria) != 0)
        {
            this.obtenerMateria();
        }
    }

    private void initComponents() {
        this.txtNombre = (EditText)findViewById(R.id.txtNombre);
        this.idMateria = getIntent().getExtras().getString("clave_materia");
    }

    public void onClickGuardar(View view){

        String nombre = txtNombre.getText().toString();

        if(nombre.isEmpty()){
            txtNombre.setError("Debes ingresar un nombre.");
            return;
        }

        Materias materia = null;
        MateriasHelper mh = new MateriasHelper(this);
        boolean guardado = false;
        try {
            mh.open();
            if(Integer.parseInt(idMateria) != 0) {
                materia = new Materias(Integer.parseInt(idMateria), nombre);
                guardado = mh.actualizaMateria(materia);
            }
            else {
                materia = new Materias(0, nombre);
                guardado = mh.insertaMateria(materia);
            }
            mh.close();
            if(guardado)
            {
                Toast.makeText(getBaseContext(),"Tu materia ha sido guardada.", Toast.LENGTH_SHORT).show();
                Intent intMaterias = new Intent(frmMaterias.this,catMaterias.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intMaterias);
            }
        }
        catch(Exception error){
            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void obtenerMateria()
    {
        Materias materia = new Materias(Integer.parseInt(idMateria),"");
        MateriasHelper mh = new MateriasHelper(this);
        ArrayList<Materias> lstMaterias = new ArrayList<>();
        mh.open();
        lstMaterias = mh.obtenerMateria(materia);
        mh.close();
        for(int i = 0; i < lstMaterias.size(); i++)
        {
            Materias item = lstMaterias.get(i);
            this.txtNombre.setText(item.getcMaNombre());
        }
    }

    private EditText txtNombre;
    private String idMateria;
}
