package com.teamtools.teacherstool.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.teamtools.teacherstool.models.Materias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pp on 19/10/2016.
 */

public class MateriasHelper {protected static final String TAG = "DataAdapter";
    private Context ghContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public MateriasHelper(Context context)
    {
        this.ghContext = context;
        mDbHelper = new DataBaseHelper(ghContext);
    }

    public MateriasHelper open() throws SQLException
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

    public boolean insertaMateria(Materias materias){
        boolean exec = false;
        String commandText = "insert into cMaterias (cMaNombre) values (?)";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindString(1, materias.getcMaNombre());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean actualizaMateria(Materias materias){
        boolean exec = false;
        String commandText = "update cMaterias set cMaNombre = ? where IdcMat = ?";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindString(1, materias.getcMaNombre());
            statement.bindLong(2, materias.getIdcMat());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean eliminaMateria(Materias materias){
        boolean exec = false;
        String commandText = "delete from cMaterias Where IdcMat = ?";
        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1,materias.getIdcMat());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public ArrayList<Materias> obtenerMaterias()
    {
        SQLiteDatabase db = null;
        String commandText = "select * from cMaterias";
        Cursor cursor = null;
        ArrayList<Materias> lstMaterias = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstMaterias.add(new Materias(cursor.getInt(0), cursor.getString(1)));
            }
            cursor.close();
            return lstMaterias;
        }
        catch(SQLiteException err) {
            lstMaterias = null;
            return lstMaterias;
        }
    }

    public List<Materias> obtenerMateria(Materias materia)
    {
        SQLiteDatabase db = null;
        String commandText = "select * from cMaterias where IdcMat = " + materia.getIdcMat();
        Cursor cursor = null;
        ArrayList<Materias> lstMaterias = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstMaterias.add(new Materias(cursor.getInt(0),
                        cursor.getString(1)));
            }
            return lstMaterias;
        }
        catch(SQLiteException err) {
            lstMaterias = null;
            return lstMaterias;
        }
    }
}
