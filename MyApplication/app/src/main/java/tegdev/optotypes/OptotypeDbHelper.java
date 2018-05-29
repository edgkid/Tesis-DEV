package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 28/05/2018.
 */

public class OptotypeDbHelper extends DbApp {

    public OptotypeDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + OptotypeDbContract.OptotypeEntry.TABLE_NAME + " ( ";
        sql = sql + OptotypeDbContract.OptotypeEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.ID + " TEXT, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.OPTOTYPECODE + " TEXT, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.OPTOTYPENAME + " TEXT, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.OPTOTYPEYEAR + " TEXT, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.IMAGE + " TEXT, ";
        sql = sql + " UNIQUE ( " + OptotypeDbContract.OptotypeEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + OptotypeDbContract.OptotypeEntry.TABLE_NAME + " ( ";
        sql = sql + OptotypeDbContract.OptotypeEntry._ID + " INTEGER PRIARY KEY, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.ID + " TEXT, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.OPTOTYPECODE + " TEXT, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.OPTOTYPENAME + " TEXT, ";
        sql = sql + OptotypeDbContract.OptotypeEntry.IMAGE + " TEXT, ";
        sql = sql + " UNIQUE ( " + OptotypeDbContract.OptotypeEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }


}
