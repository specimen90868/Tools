package com.teamtools.teacherstool.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.teamtools.teacherstool.models.Criterios;

import java.util.ArrayList;

/**
 * Created by CECILIA on 09/10/2016.
 */

public class CriteriosHelper {

    protected static final String TAG="DataAdapter";
    private Context ghContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public CriteriosHelper(Context context){
        this.ghContext=context;
        mDbHelper=new DataBaseHelper(ghContext);
    }
    public CriteriosHelper open() throws SQLException{
        try{
            mDbHelper.openDataBase();
            mDb=mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException){
            Log.e(TAG, "open >> " + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    public boolean insertCriterio(Criterios criterios){
        boolean exec=false;
        String cmdText="insert into cCriterios(cCriNombre) values(?)";

        try{
            mDb=mDbHelper.getWritableDatabase();
            SQLiteStatement stm=mDb.compileStatement(cmdText);
            stm.bindString(1,criterios.getcCriNombre());
            stm.execute();
            stm.clearBindings();
            exec=true;
        }
        catch (SQLException err){
            exec=false;
        }

        return exec;
    }

    public boolean actualizaCriterio(Criterios criterios){
        boolean exec = false;
        String cmdText = "update cCriterios set cCriNombre = ? where IdcCri = ?";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement stm = mDb.compileStatement(cmdText);
            stm.bindString(1, criterios.getcCriNombre());
            stm.bindLong(2, criterios.getIdcCri());
            stm.execute();
            stm.clearBindings();
            exec = true;
        }
        catch(SQLException err) {
            exec = false;
        }
        return exec;
    }

    public boolean eliminarCriterio(Criterios criterios){
        boolean exec = false;
        String cmdText = "delete from cCriterios where IdcCri = ?";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement stm = mDb.compileStatement(cmdText);
            stm.bindLong(1, criterios.getIdcCri());
            stm.execute();
            stm.clearBindings();
            exec = true;
        }
        catch(SQLException err) {
            exec = false;
        }
        return exec;
    }

    public ArrayList<Criterios> obtenerCriterios()
    {
        SQLiteDatabase db = null;
        String cmdText = "select * from cCriterios order by cCriNombre";
        Cursor cursor = null;
        ArrayList<Criterios> lstCriterios = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(cmdText, null);

            while(cursor.moveToNext())
            {
                lstCriterios.add(new Criterios(cursor.getInt(0),
                        cursor.getString(1)));
            }
            return lstCriterios;
        }
        catch(SQLException err) {
            lstCriterios = null;
            return lstCriterios;
        }
    }

    public ArrayList<Criterios> obtenerCriterio(Criterios criterios)
    {
        SQLiteDatabase db = null;
        String cmd = "select * from cCriterios where IdcCri = " + criterios.getIdcCri();
        Cursor cursor = null;
        ArrayList<Criterios> lstCriterios = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(cmd, null);

            while(cursor.moveToNext())
            {
                lstCriterios.add(new Criterios(cursor.getInt(0),
                        cursor.getString(1)));
            }
            return lstCriterios;
        }
        catch(SQLException err) {
            lstCriterios = null;
            return lstCriterios;
        }
    }

}
