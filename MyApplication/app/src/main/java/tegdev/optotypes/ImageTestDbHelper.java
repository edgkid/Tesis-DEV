package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by edgar on 21/07/2018.
 */

public class ImageTestDbHelper extends DbApp {


    public ImageTestDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";

        sql = sql + "CREATE TABLE " + ImageTestDbContract.ImageTestEntry.TABLE_NAME + " ( ";
        sql = sql + ImageTestDbContract.ImageTestEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ID + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.TESTCARD + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWONE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWTWO + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWTHREE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWFOUR + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWFIVE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWSIX + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWSEVEN + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWEIGHT + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWNINE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWTEN + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWELEVEN + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.TESTYEAR + " TEXT, ";
        sql = sql + " UNIQUE ( " + ImageTestDbContract.ImageTestEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";

        sql = sql + "CREATE TABLE " + ImageTestDbContract.ImageTestEntry.TABLE_NAME + " ( ";
        sql = sql + ImageTestDbContract.ImageTestEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ID + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.TESTCARD + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWONE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWTWO + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWTHREE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWFOUR + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWFIVE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWSIX + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWSEVEN + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWEIGHT + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWNINE + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWTEN + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.ROWELEVEN + " TEXT, ";
        sql = sql + ImageTestDbContract.ImageTestEntry.TESTYEAR + " TEXT, ";
        sql = sql + " UNIQUE ( " + ImageTestDbContract.ImageTestEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }



}
