package com.teamtools.teacherstool.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.teamtools.teacherstool.models.Alumnos;

import java.util.ArrayList;

/**
 * Created by pepe on 05/12/2016.
 */

public class AlumnosHelper {protected static final String TAG = "DataAdapter";
    private Context ghContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public AlumnosHelper(Context context)
    {
        this.ghContext = context;
        mDbHelper = new DataBaseHelper(ghContext);
    }

    public AlumnosHelper open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >> " + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public boolean insertaAlumno(Alumnos alumnos){
        boolean exec = false;
        String commandText = "insert into cAlumnos (cAlNoLista,cAlNombre,cAlAPellidos,cAlMail,cAlTelefono," +
                "cAlOtro1, cAlOtro2,idRGM) values (?,?,?,?,?,?,?,?)";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1, alumnos.getcAlNoLista());
            statement.bindString(2, alumnos.getcAlNombre());
            statement.bindString(3, alumnos.getcAlApellidos());
            statement.bindString(4, alumnos.getcAlMail());
            statement.bindString(5, alumnos.getcAlTelefono());
            statement.bindString(6, alumnos.getcAlOtro1());
            statement.bindString(7, alumnos.getcAlOtro2());
            statement.bindLong(8, alumnos.getcAlIdRGM());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean actualizaAlumno(Alumnos alumnos){
        boolean exec = false;
        String commandText = "update cAlumnos set cAlNoLista = ?, cAlNombre = ?, cAlAPellidos = ?, " +
               "cAlMail = ?, cAlTelefono = ?, cAlOtro1 = ?, cAlOtro2 = ?, idRGM = ? where IdcAlumno = ?";
       // String commandText = "update cAlumnos set cAlNoLista = ? where IdcAlumno = ?";
        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1, alumnos.getcAlNoLista());
            statement.bindString(2, alumnos.getcAlNombre());
            statement.bindString(3, alumnos.getcAlApellidos());
            statement.bindString(4, alumnos.getcAlMail());
            statement.bindString(5, alumnos.getcAlTelefono());
            statement.bindString(6, alumnos.getcAlOtro1());
            statement.bindString(7, alumnos.getcAlOtro2());
            statement.bindLong(8, alumnos.getcAlIdRGM());
            statement.bindLong(9, alumnos.getIdcAlumno());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean eliminaAlumno(Alumnos alumnos){
        boolean exec = false;
        String commandText = "delete from cAlumnos Where IdcAlumno = ?";
        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1,alumnos.getIdcAlumno());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }
    public int alumnosXGrupo(Alumnos alumnos){
        String commandText = "select count(idrgm) from calumnos where idrgm = " + alumnos.getcAlIdRGM();
        Cursor cursor = null;
        cursor = mDb.rawQuery(commandText, null);
        cursor.moveToFirst();
        Integer count = cursor.getInt(0);
        return  count;
    }

    public ArrayList<Alumnos> obtenerAlumnos()
    {
        SQLiteDatabase db = null;
        String commandText = "select * from cAlumnos";
        Cursor cursor = null;
        ArrayList<Alumnos> lstAlumnos = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstAlumnos.add(new Alumnos(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8)
                ));
            }
            return lstAlumnos;
        }
        catch(SQLiteException err) {
            lstAlumnos = null;
            return lstAlumnos;
        }
    }

    public ArrayList<Alumnos> obtenerAlumno(Alumnos alumnos)
    {
        SQLiteDatabase db = null;
        String commandText = "select * from cAlumnos where IdcAlumno = " + alumnos.getIdcAlumno();
        Cursor cursor = null;
        ArrayList<Alumnos> lstAlumnos = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstAlumnos.add(new Alumnos(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8)
                ));
            }
            return lstAlumnos;
        }
        catch(SQLiteException err) {
            lstAlumnos = null;
            return lstAlumnos;
        }
    }

    public ArrayList<Alumnos> obtenerAlumnosxGrupo(Alumnos alumnos)
    {
        SQLiteDatabase db = null;
        String commandText = "select * from cAlumnos where idRGM = " + alumnos.getcAlIdRGM();
        Cursor cursor = null;
        ArrayList<Alumnos> lstAlumnos = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstAlumnos.add(new Alumnos(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8)
                ));
            }
            return lstAlumnos;
        }
        catch(SQLiteException err) {
            lstAlumnos = null;
            return lstAlumnos;
        }
    }
}