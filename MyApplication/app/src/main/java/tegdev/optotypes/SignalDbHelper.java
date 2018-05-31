package tegdev.optotypes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 30/05/2018.
 */

public class SignalDbHelper extends  DbApp {

    public SignalDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + SignalDbContract.SignalDbContractEntry.TABLE_NAME + " ( ";
        sql = sql + SignalDbContract.SignalDbContractEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + SignalDbContract.SignalDbContractEntry.ID + " TEXT, ";
        sql = sql + SignalDbContract.SignalDbContractEntry.SIGNALNAME + " TEXT, ";
        sql = sql + " UNIQUE ( " + SignalDbContract.SignalDbContractEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        String sql ="";

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + SignalDbContract.SignalDbContractEntry.TABLE_NAME + " ( ";
        sql = sql + SignalDbContract.SignalDbContractEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + SignalDbContract.SignalDbContractEntry.ID + " TEXT, ";
        sql = sql + SignalDbContract.SignalDbContractEntry.SIGNALNAME + " TEXT, ";
        sql = sql + " UNIQUE ( " + SignalDbContract.SignalDbContractEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }


}
