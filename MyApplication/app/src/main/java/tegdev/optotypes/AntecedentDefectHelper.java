package tegdev.optotypes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 30/05/2018.
 */

public class AntecedentDefectHelper extends DbApp{

    public AntecedentDefectHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + AntecedentDefectContract.AntecedentDefectContractEntry.TABLE_NAME + " ( ";
        sql = sql + AntecedentDefectContract.AntecedentDefectContractEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + AntecedentDefectContract.AntecedentDefectContractEntry.ID + " TEXT, ";
        sql = sql + AntecedentDefectContract.AntecedentDefectContractEntry.ANTECEDENTNAME + " TEXT, ";
        sql = sql + " UNIQUE ( " + AntecedentDefectContract.AntecedentDefectContractEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        String sql ="";

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + AntecedentDefectContract.AntecedentDefectContractEntry.TABLE_NAME + " ( ";
        sql = sql + AntecedentDefectContract.AntecedentDefectContractEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + AntecedentDefectContract.AntecedentDefectContractEntry.ID + " TEXT, ";
        sql = sql + AntecedentDefectContract.AntecedentDefectContractEntry.ANTECEDENTNAME + " TEXT, ";
        sql = sql + " UNIQUE ( " + AntecedentDefectContract.AntecedentDefectContractEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }


}
