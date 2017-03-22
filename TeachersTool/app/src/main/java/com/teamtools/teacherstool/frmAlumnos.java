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

import com.teamtools.teacherstool.helpers.AlumnosHelper;
import com.teamtools.teacherstool.models.Alumnos;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;

public class frmAlumnos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_alumnos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.initComponents();

        if (Integer.parseInt(idAlumno) != 0) {
            this.obtenerAlumno();
        }
    }

    private void initComponents() {

       // this.idGrupoA = getIntent().getExtras().getString("clave_grupo");
        this.idAlumno = getIntent().getExtras().getString("clave_alumno");
        this.txtNumLista = (EditText) findViewById(R.id.txtNumLista);
        this.txtNombre = (EditText) findViewById(R.id.txtNombre);
        this.txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        this.txtMail = (EditText) findViewById(R.id.txtMail);
        this.txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        this.txtOtro = (EditText) findViewById(R.id.txtOtro);
        this.txtOtro2 = (EditText) findViewById(R.id.txtOtro2);
        this.idRGM = Shared.IdShared;

    }
    public void onClickGuarda(View view) {
       // Toast.makeText(getBaseContext(), "Entro!!.", Toast.LENGTH_SHORT).show();

        int numlista = Integer.parseInt(txtNumLista.getText().toString());
        String nombre = txtNombre.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String mail = txtMail.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String otro = txtOtro.getText().toString();
        String otro2 = txtOtro2.getText().toString();
      //  int idrgm = 1;

//        if ( nombre.isEmpty() || apellidos.isEmpty() || mail.isEmpty() || telefono.isEmpty()
//                || otro.isEmpty() || otro2.isEmpty() ) {
//            txtNombre.setError("Debes ingresar un nombre.");
//            return;
//        }
        if ( nombre.isEmpty() ) {
            txtNombre.setError("Debes ingresar un nombre.");
            return;
        }

        Alumnos alumno = null;
        AlumnosHelper ah = new AlumnosHelper(this);
        boolean guardado = false;
        try {
            ah.open();
            if (Integer.parseInt(idAlumno) != 0) {
                alumno = new Alumnos(Integer.parseInt(idAlumno), numlista, nombre,apellidos,
                        mail,telefono,otro,otro2,idRGM);
                  guardado = ah.actualizaAlumno(alumno);
            }
            else {
                alumno = new Alumnos(0,numlista, nombre,apellidos,mail,telefono,otro,otro2, idRGM);
                guardado = ah.insertaAlumno(alumno);
            }
            ah.close();
            if (guardado) {
                Toast.makeText(getBaseContext(), "El alumno ha sido guardado.", Toast.LENGTH_SHORT).show();
                this.txtNumLista.setText("");
                this.txtNombre.setText("");
                this.txtApellidos.setText("");
                this.txtMail.setText("");
                this.txtTelefono.setText("");
                this.txtOtro.setText("");
                this.txtOtro2.setText("");
                Intent intent = new Intent(this, catAlumnos.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else {
                Toast.makeText(getBaseContext(), "Error al guardar", Toast.LENGTH_LONG).show();}
        } catch (Exception error) {
            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void obtenerAlumno() {
        Alumnos alumno = new Alumnos(Integer.parseInt(idAlumno),0, "", "", "","","","",idRGM);
        AlumnosHelper ah = new AlumnosHelper(this);
        ArrayList<Alumnos> lstAlumnos = new ArrayList<>();

        Integer cantAlum = 0;

        ah.open();
        lstAlumnos = ah.obtenerAlumno(alumno);
        cantAlum = ah.alumnosXGrupo(alumno);
        ah.close();
        for (int i = 0; i < lstAlumnos.size(); i++) {
            Alumnos item = lstAlumnos.get(i);
            this.txtNumLista.setText(Integer.toString(item.getcAlNoLista()));
            this.txtNombre.setText(item.getcAlNombre());
            this.txtApellidos.setText(item.getcAlApellidos());
            this.txtMail.setText(item.getcAlMail());
            this.txtTelefono.setText(item.getcAlTelefono());
            this.txtOtro.setText(item.getcAlOtro1());
            this.txtOtro2.setText(item.getcAlOtro2());
        }
        //Mensajes de prueba
        Toast.makeText(getBaseContext(), "grupo id: " + idRGM, Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), "Cantidad de alumnos: " + cantAlum, Toast.LENGTH_SHORT).show();
    }
    private Integer idRGM;
    private String idGrupoA;
    private String idAlumno;
    private EditText txtNumLista;
    private EditText txtNombre;
    private EditText txtApellidos;
    private EditText txtMail;
    private EditText txtTelefono;
    private EditText txtOtro;
    private EditText txtOtro2;
}