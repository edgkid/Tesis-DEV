package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by edgar on 09/07/2018.
 */

public class PatientDbHelper extends DbApp {


    public PatientDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";
        
        sql = sql + "CREATE TABLE " + PatientDbContract.PatientEntry.TABLE_NAME + " ( ";
        sql = sql + PatientDbContract.PatientEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + PatientDbContract.PatientEntry.ID + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.FISTNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MIDDLENAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.LASTNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MAIDENNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.GENDER + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.YEARSOLD + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.BIRTHDAY + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.IMAGE + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.NEXTAPPOINTMENT + " TEXT, ";
        sql = sql + " UNIQUE ( " + PatientDbContract.PatientEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";

        sql = sql + "CREATE TABLE " + PatientDbContract.PatientEntry.TABLE_NAME + " ( ";
        sql = sql + PatientDbContract.PatientEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + PatientDbContract.PatientEntry.ID + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.FISTNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MIDDLENAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.LASTNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MAIDENNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.GENDER + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.YEARSOLD + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.BIRTHDAY + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.IMAGE + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.NEXTAPPOINTMENT + " TEXT, ";
        sql = sql + " UNIQUE ( " + PatientDbContract.PatientEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

}
