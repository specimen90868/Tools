package com.teamtools.teacherstool.helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by fabricio.vilchis on 26/09/2016.
 */
public class IniciaDataBaseHelper {

    protected static final String TAG = "DataAdapter";
    private final Context mContext;
    private DataBaseHelper mDbHelper;

    public IniciaDataBaseHelper(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public IniciaDataBaseHelper createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

}
