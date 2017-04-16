package com.teamtools.teacherstool.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.teamtools.teacherstool.models.RelacionGrupoMateriaCriterio;

import java.util.ArrayList;

/**
 * Created by CECILIA on 19/03/2017.
 */

public class RGrupoMateriaCriterioHelper {

    protected static final String TAG="DataAdapter";
    private Context ghContext;
    private SQLiteDatabase mDB;
    private DataBaseHelper mDbHelper;

    public RGrupoMateriaCriterioHelper(Context context){
        this.ghContext=context;
        mDbHelper=new DataBaseHelper(ghContext);
    }

    public RGrupoMateriaCriterioHelper open() throws SQLException{
        try {
            mDbHelper.openDataBase();
            mDB=mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException){
            Log.e(TAG,"open >> "+mSQLException.toString());
            throw  mSQLException;
        }
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    public boolean insertaRGMC(RelacionGrupoMateriaCriterio rgmc){
        boolean exec=false;
        String commRGMC="insert into rPonderaciones (IdRGM,IdcCri ,rPonPorcentaje) values(?,?,?)";

        try {
            mDB=mDbHelper.getWritableDatabase();
            SQLiteStatement stmRGMC=mDB.compileStatement(commRGMC);
            stmRGMC.bindLong(1,rgmc.getIdRGM());
            stmRGMC.bindLong(2,rgmc.getIdcCri());
            stmRGMC.bindLong(3,rgmc.getPorcentaje());
            stmRGMC.execute();
            stmRGMC.clearBindings();
            exec=true;
        }
        catch (SQLException err){
            exec=false;
        }

        return exec;
    }

    public boolean actualizaRGMC(RelacionGrupoMateriaCriterio rgmc){
        boolean exec=false;
        String commRGMC="update rPonderaciones set rPonPorcentaje=? where IdRGM=? and IdcCri=? ";
        try {
            mDB=mDbHelper.getWritableDatabase();
            SQLiteStatement stmRGMC=mDB.compileStatement(commRGMC);
            stmRGMC.bindLong(1,rgmc.getPorcentaje());
            stmRGMC.bindLong(2,rgmc.getIdRGM());
            stmRGMC.bindLong(3,rgmc.getIdcCri());
            stmRGMC.execute();
            stmRGMC.clearBindings();
            exec=true;
        }
        catch (SQLException err){
            exec=false;
        }

        return exec;
    }

    public boolean eliminarRGMC(RelacionGrupoMateriaCriterio rgmc){
        boolean exec=false;
        String commRGMC="Delete from rPonderaciones where IdRGM=? and IdcCri=? ";
        try {
            mDB=mDbHelper.getWritableDatabase();
            SQLiteStatement stmRGMC=mDB.compileStatement(commRGMC);
            stmRGMC.bindLong(1,rgmc.getIdRGM());
            stmRGMC.bindLong(2,rgmc.getIdcCri());
            stmRGMC.execute();
            stmRGMC.clearBindings();
            exec=true;
        }
        catch (SQLException err){
            exec=false;
        }

        return exec;
    }

    public ArrayList<RelacionGrupoMateriaCriterio> obtenerGrupoMateriasCriterios(RelacionGrupoMateriaCriterio rgmc)
    {
        String commandText = "select r.IdRGM, c.IdcCri, c.cCriNombre, (cast(p.rPonPorcentaje as char(3))+'%') as Porcenta " +
                "from rPonderaciones p " +
                "inner join rGrupoMateria r on p.IdRGM=r.IdRGM " +
                "inner join cCriterios c on p.IdcCri=c.IdcCri " +
                "WHERE r.IdRGM = " + rgmc.getIdRGM();
        Cursor cursor = null;
        ArrayList<RelacionGrupoMateriaCriterio> lstRGMC = new ArrayList<>();
        try {
            cursor = mDB.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstRGMC.add(new RelacionGrupoMateriaCriterio(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3)));
            }
            return lstRGMC;
        }
        catch(SQLiteException err) {
            lstRGMC = null;
            return lstRGMC;
        }
    }

    public Integer existeRelacionGMC(RelacionGrupoMateriaCriterio rgmc)
    {
        String commandText = "select count(*) from rPonderaciones where IdRGM = " + rgmc.getIdRGM() + " and IdcCri = " + rgmc.getIdcCri();
        Cursor cursor = null;
        cursor = mDB.rawQuery(commandText, null);
        cursor.moveToFirst();
        Integer count = cursor.getInt(0);
        return  count;
    }

    public Integer porcentajetotal(RelacionGrupoMateriaCriterio rgmc)
    {
        String commandText = "select sum(rPonPorcentaje) as totalpon from rPonderaciones where IdRGM = " + rgmc.getIdRGM();
        Cursor cursor = null;
        cursor = mDB.rawQuery(commandText, null);
        cursor.moveToFirst();
        Integer porctotal = cursor.getInt(0);
        return  porctotal;
    }


}
