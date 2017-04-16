package com.teamtools.teacherstool.models;

/**
 * Created by CECILIA on 19/03/2017.
 */

public class RelacionGrupoMateriaCriterio {
    private int IdRGM;
    private int IdRGMC;
    private int IdcCri;
    private int porcentaje;
    private String cCriNombre;

    public RelacionGrupoMateriaCriterio(int IdRGM, int IdcCri, String cCriNombre, int porcentaje){
        this.IdcCri=IdcCri;
        this.IdRGM=IdRGM;
        this.cCriNombre=cCriNombre;
        this.porcentaje=porcentaje;

    }

    public int getIdRGM(){
        return IdRGM;
    }

    public void setIdRGM(int idRGM) {
        IdRGM = idRGM;
    }

    public int getIdcCri() {
        return IdcCri;
    }

    public void setIdcCri(int idcCri) {
        IdcCri = idcCri;
    }

    public int getIdRGMC() {
        return IdRGMC;
    }

    public void setIdRGMC(int idRGMC) {
        IdRGMC = idRGMC;
    }

    public String getcCriNombre() {
        return cCriNombre;
    }

    public void setcCriNombre(String cCriNombre) {
        this.cCriNombre = cCriNombre;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
}
