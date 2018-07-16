package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by edgar on 15/07/2018.
 */

public class FormDataDbHelper extends DbApp {
    public FormDataDbHelper(Context context) {
        super(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + FormDataDbContract.FormDataEntry.TABLE_NAME + " ( ";
        sql = sql + FormDataDbContract.FormDataEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + FormDataDbContract.FormDataEntry.ID + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.IDPATIENT + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.YEARSOLD + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.GENDER + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.AVRIGTH + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.AVLEFT + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.CENTER + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.SUSTAIN + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.MAINTAIN + " TEXT, ";

        sql = sql + FormDataDbContract.FormDataEntry.CHRONOMATICOD + " TEXT,";
        sql = sql + FormDataDbContract.FormDataEntry.CHRONOMATICOI + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.TONOMETRIAOD + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.TONOMETRIAOI + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.FORIA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.ENDOFORIA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.EXOFORIA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.ORTOFORIA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.ORTOTROPIA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.DVD + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry


        sql = sql + " UNIQUE ( " + FormDataDbContract.FormDataEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + FormDataDbContract.FormDataEntry.TABLE_NAME + " ( ";
        sql = sql + FormDataDbContract.FormDataEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + FormDataDbContract.FormDataEntry.ID + " TEXT, ";

        sql = sql + " UNIQUE ( " + FormDataDbContract.FormDataEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

}
