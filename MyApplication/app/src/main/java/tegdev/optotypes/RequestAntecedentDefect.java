package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 30/05/2018.
 */

public class RequestAntecedentDefect {

    private String request;
    private Context context;

    public RequestAntecedentDefect(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public RequestAntecedentDefect(Context context) {
        this.context = context;
    }

    /**
     * This method verify if exist antecendets
     */
    public boolean  findOrCreateTableAntecedent(){

        Boolean value = false;
        Cursor cursor = null;

        AntecedentDefectHelper antecedentDb = new AntecedentDefectHelper(this.context);
        SQLiteDatabase db = antecedentDb.getReadableDatabase();

        try{
            cursor = db.rawQuery(" SELECT antecedentName FROM " + AntecedentDefectContract.AntecedentDefectContractEntry.TABLE_NAME, null);

            if (!cursor.moveToFirst()){
                value = true;
            }else{
                value = false;
            }

        }catch (Exception e){
            e.printStackTrace();
            antecedentDb.onCreate(db);
            value = true;
        }finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }

        return value;
    }

    /**
     * This method verify if exist some Antecedent and send request
     */
    public void findAntecendets(){

        HttpHandlerAntecedent httpHandlerAntecedent = new HttpHandlerAntecedent(this.request, this.context);
        httpHandlerAntecedent.connectToResource((TestFormActivity) context);

    }

    /**
     * This method allow get antecendet
     * @param arrayAntecedent
     */
    public void getAntecendet (ArrayList arrayAntecedent){

        Cursor cursor= null;
        String where = " ORDER BY antecedentName asc ";
        AntecedentDefectHelper antecedentDefectHelper = new AntecedentDefectHelper(this.context);
        SQLiteDatabase db = antecedentDefectHelper.getReadableDatabase();


        try {
            cursor = db.rawQuery("SELECT antecedentName FROM " + AntecedentDefectContract.AntecedentDefectContractEntry.TABLE_NAME + where, null);

            if (cursor.moveToFirst()) {
                do {
                    arrayAntecedent.add(cursor.getString(0));
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            cursor.close();
            db.close();

        }
    }




}
