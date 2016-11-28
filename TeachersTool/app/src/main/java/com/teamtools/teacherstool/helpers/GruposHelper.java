package com.teamtools.teacherstool.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.teamtools.teacherstool.models.Grupos;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fabricio.vilchis on 26/09/2016.
 */
public class GruposHelper {

    protected static final String TAG = "DataAdapter";
    private Context ghContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public GruposHelper(Context context)
    {
        this.ghContext = context;
        mDbHelper = new DataBaseHelper(ghContext);
    }

    public GruposHelper open() throws SQLException
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

    public boolean insertaGrupo(Grupos grupos){
        boolean exec = false;
        String commandText = "insert into cGrupos (cGrNombre, cGrPeriodos) values (?,?)";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindString(1, grupos.getcGrNombre());
            statement.bindLong(2, grupos.getcGrPeriodos());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean actualizaGrupo(Grupos grupos){
        boolean exec = false;
        String commandText = "update cGrupos set cGrNombre = ?, cGrPeriodos = ? where IdcGrupo = ?";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindString(1, grupos.getcGrNombre());
            statement.bindLong(2, grupos.getcGrPeriodos());
            statement.bindLong(3, grupos.getIdcGrupo());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean eliminaGrupo(Grupos grupos){
        boolean exec = false;
        String commandText = "delete from cGrupos where IdcGrupo = ?";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1, grupos.getIdcGrupo());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public ArrayList<Grupos> obtenerGrupos()
    {
        SQLiteDatabase db = null;
        String commandText = "select * from cGrupos";
        Cursor cursor = null;
        ArrayList<Grupos> lstGrupos = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstGrupos.add(new Grupos(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)));
            }
            return lstGrupos;
        }
        catch(SQLiteException err) {
            lstGrupos = null;
            return lstGrupos;
        }
    }

    public ArrayList<Grupos> obtenerGrupo(Grupos grupos)
    {
        SQLiteDatabase db = null;
        String commandText = "select * from cGrupos where IdcGrupo = " + grupos.getIdcGrupo();
        Cursor cursor = null;
        ArrayList<Grupos> lstGrupos = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstGrupos.add(new Grupos(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)));
            }
            return lstGrupos;
        }
        catch(SQLiteException err) {
            lstGrupos = null;
            return lstGrupos;
        }
    }
}
