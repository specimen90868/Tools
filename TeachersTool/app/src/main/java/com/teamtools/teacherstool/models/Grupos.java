package com.teamtools.teacherstool.models;

/**
 * Created by Xpermpento on 25/09/16.
 */
public class Grupos {

    private int IdcGrupo;
    private String cGrNombre;
    private int cGrPeriodos;

    public Grupos (int Id, String nombre, int periodos)
    {
        this.IdcGrupo = Id;
        this.cGrNombre = nombre;
        this.cGrPeriodos = periodos;
    }

    public int getIdcGrupo() {
        return IdcGrupo;
    }

    public void setIdcGrupo(int IdcGrupo) {
        this.IdcGrupo = IdcGrupo;
    }

    public String getcGrNombre() {
        return cGrNombre;
    }

    public void setcGrNombre(String cGrNombre) {
        this.cGrNombre = cGrNombre;
    }

    public int getcGrPeriodos() {
        return cGrPeriodos;
    }

    public void setcGrPeriodos(int cGrPeriodos) {
        this.cGrPeriodos = cGrPeriodos;
    }
}
