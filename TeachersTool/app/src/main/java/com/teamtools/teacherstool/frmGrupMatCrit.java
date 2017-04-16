package com.teamtools.teacherstool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.teamtools.teacherstool.adapters.AdapterCriterios;
import com.teamtools.teacherstool.adapters.AdapterMaterias;
import com.teamtools.teacherstool.helpers.CriteriosHelper;
//import com.teamtools.teacherstool.helpers.RelacionGrupoMateriaHelper;
import com.teamtools.teacherstool.models.Criterios;
import com.teamtools.teacherstool.models.RelacionGrupoMateriaCriterio;
import com.teamtools.teacherstool.helpers.RGrupoMateriaCriterioHelper;
import com.teamtools.teacherstool.models.Shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class frmGrupMatCrit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_grup_mat_crit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initComponents();
    }

    private void initComponents() {

        this.spnCriterios = (Spinner) findViewById(R.id.spnCriterios);
        this.spnPorcentaje=(Spinner) findViewById(R.id.spnPorcentaje);
        this.idrGM = Shared.IdShared;

        CriteriosHelper ch = new CriteriosHelper(this);
        ch.open();
        ArrayList<Criterios> lstcriterios = ch.obtenerCriterios();
        ch.close();
        //ArrayList<Criterios> prueba = new ArrayList<>();
        /*for (int i = 0; i < lstcriterios.size(); i++)
        {
            prueba.add(new Criterios(lstcriterios.get(i).getIdcCri(),lstcriterios.get(i).getcCriNombre()));
        }*/

        ArrayAdapter criteriosAdapter = new AdapterCriterios(getApplicationContext(), lstcriterios);
        spnCriterios.setAdapter(criteriosAdapter);

        this.spnCriterios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerAdapter cri = spnCriterios.getAdapter();
                c = (Criterios) cri.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RelacionGrupoMateriaCriterio rgmc = null;
        RGrupoMateriaCriterioHelper rgmch = new RGrupoMateriaCriterioHelper(this);
        rgmc = new RelacionGrupoMateriaCriterio(idrGM, 0, "",0);

        Integer porctotal=0;
        //Integer x=0;

        rgmch.open();
        porctotal = rgmch.porcentajetotal(rgmc);
        rgmch.close();

        int faltapor= 100-porctotal;
        int x = 5;
        int filas=(faltapor/5);

        String[] valores2= new String[filas];

        for (int i = 0; i < filas; i++)
        {
            valores2[i] = Integer.toString(x);
            x = x + 5;
        }

        List<String> listaval = Arrays.asList(valores2);

        //String[] valores = {"5","10","15","20","25","30","35","40","45","50","55","60","65","70","75","80","85","90","95","100"};

        spnPorcentaje.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaval));

        this.spnPorcentaje.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                porcen = Integer.parseInt((String) parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void onClickGuardar(View view){
        //this.txtPorcentaje=(EditText)findViewById(R.id.txtRGMCPorcentaje);
        //String porcentaje = txtPorcentaje.getText().toString();
        //int nporcentaje = Integer.parseInt(porcentaje);
        int nporcentaje=porcen;

        RelacionGrupoMateriaCriterio rgmc = null;
        RGrupoMateriaCriterioHelper rgmch = new RGrupoMateriaCriterioHelper(this);
        boolean guardado = false;
        Integer existeRelacion = 0;
        //Integer portotal=0;
        rgmc = new RelacionGrupoMateriaCriterio(idrGM, c.getIdcCri(), "",nporcentaje);
        try {
            rgmch.open();
            existeRelacion = rgmch.existeRelacionGMC(rgmc);
           // portotal=rgmch.existeRelacionGMC(rgmc);
            rgmch.close();
        }
        catch(Exception error){
            Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        /*
        if ((portotal+nporcentaje)>100){
            Toast.makeText(getBaseContext(),"El porcentaje debe ser menor a .", Toast.LENGTH_SHORT).show();
        }else
        */
        if (existeRelacion == 0){
            try {
                rgmch.open();
                if(idrGM != 0) {
                    guardado = rgmch.insertaRGMC(rgmc);
                }
                rgmch.close();
                if(guardado)
                {
                    Toast.makeText(getBaseContext(),"Criterio asignado.", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception error){
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        }
        else
        {
            Toast.makeText(getBaseContext(),"El criterio ya se encuentra asignado", Toast.LENGTH_LONG).show();
            return;
        }
    }

    class arrPorcentajes
    {
        public int porc;
    }

    private Integer idrGM;
    private Integer porcen;
    //private EditText txtPorcentaje;
    private Spinner spnCriterios;
    private Spinner spnPorcentaje;
    private Criterios c;

}
