package com.teamtools.teacherstool.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.teamtools.teacherstool.models.RelacionGrupoMaterias;

import java.util.ArrayList;

/**
 * Created by fabricio.vilchis on 02/12/2016.
 */
public class RelacionGrupoMateriaHelper {

    protected static final String TAG = "DataAdapter";
    private Context ghContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public RelacionGrupoMateriaHelper(Context context) {
        this.ghContext = context;
        mDbHelper = new DataBaseHelper(ghContext);
    }

    public RelacionGrupoMateriaHelper open() throws SQLException
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

    public boolean insertaRelacionGM(RelacionGrupoMaterias rgm){
        boolean exec = false;
        String commandText = "insert into rGrupoMateria (IdcGrupo, IdcMat) values (?,?)";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1, rgm.getIdcGrupo());
            statement.bindLong(2, rgm.getIdcMat());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean actualizaRelacionGM(RelacionGrupoMaterias rgm){
        boolean exec = false;
        String commandText = "update rGrupoMateria set IdcGrupo = ?, IdcMat = ? where IdRGM = ?";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1, rgm.getIdcGrupo());
            statement.bindLong(2, rgm.getIdcMat());
            statement.bindLong(3, rgm.getIdRGM());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public boolean eliminaRelacionGM(RelacionGrupoMaterias rgm){
        boolean exec = false;
        String commandText = "delete from rGrupoMateria where IdRGM = ?";

        try{
            mDb = mDbHelper.getWritableDatabase();
            SQLiteStatement statement = mDb.compileStatement(commandText);
            statement.bindLong(1, rgm.getIdRGM());
            statement.execute();
            statement.clearBindings();
            exec = true;
        }
        catch(SQLiteException err) {
            exec = false;
        }
        return exec;
    }

    public ArrayList<RelacionGrupoMaterias> obtenerGrupoMaterias(RelacionGrupoMaterias rgm)
    {
        SQLiteDatabase db = null;
        String commandText = "select gm.IdRGM, gm.IdcGrupo, gm.IdcMat, m.cMaNombre " +
                "from rGrupoMateria gm inner join cGrupos g on gm.IdcGrupo = g.IdcGrupo inner join cMaterias m on gm.IdcMat = m.IdcMat where gm.IdcGrupo = " + rgm.getIdcGrupo();
        Cursor cursor = null;
        ArrayList<RelacionGrupoMaterias> lstRGM = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(commandText, null);

            while(cursor.moveToNext())
            {
                lstRGM.add(new RelacionGrupoMaterias(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3)));
            }
            return lstRGM;
        }
        catch(SQLiteException err) {
            lstRGM = null;
            return lstRGM;
        }
    }
}
