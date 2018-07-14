package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 30/05/2018.
 */

public class RequestSignalDefect {

    private String request;
    private Context context;

    public RequestSignalDefect(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public RequestSignalDefect(Context context) {
        this.context = context;
    }

    /**
     * This method verify and create the existence that signal defect table
     *
     * @return
     */
    public boolean findOrCreateTableSignalDefect() {

        boolean value = false;
        Cursor cursor = null;
        SignalDbHelper signalDb = new SignalDbHelper(this.context);
        SQLiteDatabase db = signalDb.getReadableDatabase();

        try {
            cursor = db.rawQuery(" SELECT idSignal FROM " + SignalDbContract.SignalDbContractEntry.TABLE_NAME, null);
            if (!cursor.moveToFirst()) {
                value = true;
            } else {
                value = false;
            }

        } catch (Exception e) {
            signalDb.onCreate(db);
            value = true;
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }

        return value;
    }

    /**
     * This method verify if exist some signal defect and send request
     */
    public void finSignalDefect(){

        HttpHelperSignal httpHelperSignal = new HttpHelperSignal(this.request, this.context);
        httpHelperSignal.connectToResource((TestFormActivity) this.context);

    }

    /**
     * This method allows get the signal defect
     * @param arraySignal
     */
    public void getSignalDefect (ArrayList arraySignal){

        Cursor cursor =null;
        String where = " ORDER BY signalName asc ";
        SignalDbHelper signalDbHelper = new SignalDbHelper(this.context);
        SQLiteDatabase db = signalDbHelper.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT signalName FROM " + SignalDbContract.SignalDbContractEntry.TABLE_NAME + where, null);

            if (cursor.moveToFirst()) {
                do {
                    arraySignal.add(cursor.getString(0));
                } while(cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {

            cursor.close();
            db.close();

        }

    }


}
