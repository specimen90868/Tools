package com.teamtools.teacherstool.models;

/**
 * Created by pp on 19/10/2016.
 */

public class Materias {
    private int IdcMat;
    private String cMaNombre;

    public Materias(int Id, String nombre)
    {
        this.IdcMat = Id;
        this.cMaNombre = nombre;
    }

    public int getIdcMat() {
        return IdcMat;
    }

    public void setIdcMat(int IdcMat) {
        this.IdcMat = IdcMat;
    }

    public String getcMaNombre() {
        return cMaNombre;
    }

    public void setcGrNombre(String cMaNombre) {
        this.cMaNombre = cMaNombre;
    }
}
