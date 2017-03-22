package com.teamtools.teacherstool.models;

/**
 * Created by pepe on 02/12/2016.
 */

public class Alumnos {

    private int IdcAlumno;
    private int cAlNoLista;
    private String cAlNombre;
    private String cAlApellidos;
    private String cAlMail;
    private String cAlTelefono;
    private String cAlOtro1;
    private String cAlOtro2;
    private int cAlIdRGM;


    public Alumnos(int Id, int numlista, String nombre, String apellidos, String email,
                   String tel, String otro1, String otro2, int idrgm)
    {
        this.IdcAlumno = Id;
        this.cAlNoLista = numlista;
        this.cAlNombre = nombre;
        this.cAlApellidos = apellidos;
        this.cAlMail = email;
        this.cAlTelefono = tel;
        this.cAlOtro1 = otro1;
        this.cAlOtro2 = otro2;
        this.cAlIdRGM = idrgm;
    }

    public int getIdcAlumno() {
        return IdcAlumno;
    }

    public void setIdcAlumno(int idcAlumno) {
        IdcAlumno = idcAlumno;
    }

    public int getcAlNoLista() {
        return cAlNoLista;
    }

    public void setcAlNoLista(int cAlNoLista) {
        this.cAlNoLista = cAlNoLista;
    }

    public String getcAlNombre() {
        return cAlNombre;
    }

    public void setcAlNombre(String cAlNombre) {
        this.cAlNombre = cAlNombre;
    }

    public String getcAlApellidos() {
        return cAlApellidos;
    }

    public void setcAlApellidos(String cAlApellidos) {
        this.cAlApellidos = cAlApellidos;
    }

    public String getcAlMail() {
        return cAlMail;
    }

    public void setcAlMail(String cAlMail) {
        this.cAlMail = cAlMail;
    }

    public String getcAlTelefono() {
        return cAlTelefono;
    }

    public void setcAlTelefono(String cAlTelefono) {
        this.cAlTelefono = cAlTelefono;
    }

    public String getcAlOtro1() {
        return cAlOtro1;
    }

    public void setcAlOtro1(String otro) {
        cAlOtro1 = otro;
    }

    public String getcAlOtro2() {
        return cAlOtro2;
    }

    public void setcAlOtro2(String otro2) {
        cAlOtro2 = otro2;
    }

    public int getcAlIdRGM() {
        return cAlIdRGM;
    }

    public void setcAlIdRGM(int idRGM) {
        this.cAlIdRGM = idRGM;
    }







}
