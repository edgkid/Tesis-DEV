package tegdev.optotypes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by elandaeta on 16/7/2018.
 */

public class FormDataDbHelper extends DbApp {

    public FormDataDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";

        sql = sql + "CREATE TABLE " + FormDataDbContract.FormDataEntry.TABLE_NAME + " ( ";
        sql = sql + FormDataDbContract.FormDataEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + FormDataDbContract.FormDataEntry.ID + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.PATIENTDATA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.AVDATA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.OTHERTESTA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.OTHERTESTB + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.TESTUSED + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.ANTECEDENTDAD + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.ANTECEDENTMON + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.STATUS + " TEXT, ";

        sql = sql + " UNIQUE ( " + FormDataDbContract.FormDataEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";

        sql = sql + "CREATE TABLE " + FormDataDbContract.FormDataEntry.TABLE_NAME + " ( ";
        sql = sql + FormDataDbContract.FormDataEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + FormDataDbContract.FormDataEntry.ID + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.PATIENTDATA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.AVDATA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.OTHERTESTA + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.OTHERTESTB + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.TESTUSED + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.ANTECEDENTDAD + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.ANTECEDENTMON + " TEXT, ";
        sql = sql + FormDataDbContract.FormDataEntry.STATUS + " TEXT, ";

        sql = sql + " UNIQUE ( " + FormDataDbContract.FormDataEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }
}
