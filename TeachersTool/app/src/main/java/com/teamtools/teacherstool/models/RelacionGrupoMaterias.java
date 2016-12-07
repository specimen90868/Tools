package com.teamtools.teacherstool.models;

/**
 * Created by fabricio.vilchis on 02/12/2016.
 */
public class RelacionGrupoMaterias {

    private int IdRGM;
    private int IdcGrupo;
    private int IdcMat;
    private String cMaNombre;

    public RelacionGrupoMaterias(int IdRGM, int IdcGrupo, int IdcMat, String cMaNombre) {
        this.IdRGM = IdRGM;
        this.IdcGrupo = IdcGrupo;
        this.IdcMat = IdcMat;
        this.cMaNombre = cMaNombre;
    }

    public int getIdRGM() {
        return IdRGM;
    }

    public void setIdRGM(int idRGM) {
        IdRGM = idRGM;
    }

    public int getIdcGrupo() {
        return IdcGrupo;
    }

    public void setIdcGrupo(int idcGrupo) {
        IdcGrupo = idcGrupo;
    }

    public int getIdcMat() {
        return IdcMat;
    }

    public void setIdcMat(int idcMat) {
        IdcMat = idcMat;
    }

    public String getcMaNombre() {
        return cMaNombre;
    }

    public void setcMaNombre(String cMaNombre) {
        this.cMaNombre = cMaNombre;
    }
}
