package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 29/05/2018.
 */

public class InteractionDbHelper extends DbApp {

    public InteractionDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + InteractionDbContract.InteractionEntry.TABLE_NAME + " ( ";
        sql = sql + InteractionDbContract.InteractionEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + InteractionDbContract.InteractionEntry.ID + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.IDOPTOTYPE + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.IDPATIENT + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.TESTCODE + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.EYE + " TEXT, ";
        sql = sql + " UNIQUE ( " + InteractionDbContract.InteractionEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + InteractionDbContract.InteractionEntry.TABLE_NAME + " ( ";
        sql = sql + InteractionDbContract.InteractionEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + InteractionDbContract.InteractionEntry.ID + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.IDOPTOTYPE + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.IDPATIENT + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.TESTCODE + " TEXT, ";
        sql = sql + InteractionDbContract.InteractionEntry.EYE + " TEXT, ";
        sql = sql + " UNIQUE ( " + InteractionDbContract.InteractionEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }


}
