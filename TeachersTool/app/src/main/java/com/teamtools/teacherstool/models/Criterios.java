package com.teamtools.teacherstool.models;

/**
 * Created by CECILIA on 09/10/2016.
 */

public class Criterios {
    private int IdcCri;
    private String cCriNombre;

    public Criterios(int Id, String Nombre){
        this.IdcCri=Id;
        this.cCriNombre=Nombre;
    }

    public int getIdcCri(){
        return IdcCri;
    }

    public void setIdcCri(int IdcCri){
        this.IdcCri=IdcCri;
    }

    public String getcCriNombre(){
        return cCriNombre;
    }

    public void setcCriNombre(String cCriNombre){
        this.cCriNombre=cCriNombre;
    }

}

