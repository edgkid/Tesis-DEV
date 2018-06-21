package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 28/05/2018.
 */

public class RequestOptotype {

    private String request;
    private Context context;

    public RequestOptotype(Context context) {

    }

    public RequestOptotype(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This method find the Optotypes
     */
    public void findOptotypes(){

        if (!findOrCreateTableOptotypes()){

            HttpHandlerOptotype httpRequestOptotype = new HttpHandlerOptotype(request,context);
            httpRequestOptotype.connectToResource((InteractionActivity)context);

        }
    }

    /**
     * This method get the Optotypes
     * @return
     */
    public ArrayList<Optotype> getOptotypes (String yearsOld){

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper( this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;
        Optotype optotype = null;
        ArrayList<Optotype> optotypes = new ArrayList<Optotype>();

        String query = "SELECT optotypeCode, idOptotype FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME;

        Log.d("query", yearsOld);

        if (yearsOld.equals("3") || Integer.parseInt(yearsOld) <= 2 ){
            query = query + " WHERE OptotypeYear <= 3 ORDER BY random() LIMIT 16";
        }
        if (yearsOld.equals("4")){
            query = query + " WHERE OptotypeYear <= 4 ORDER BY random() LIMIT 16";
        }
        if (yearsOld.equals("5") || Integer.parseInt(yearsOld) > 5){
            query = query + " WHERE OptotypeYear <= 5 ORDER BY random() LIMIT 16";
        }

        Log.d("query", query);

        try{

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    optotype = new Optotype();
                    optotype.setIdOptotype(cursor.getString(1));
                    optotype.setOptotypeCode(cursor.getString(0));
                    optotypes.add(optotype);
                } while(cursor.moveToNext());
            }else {
                Log.d("message: ", "NO se lee registro");
            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");

        }finally {
            cursor.close();
            db.close();
        }

        return optotypes;
    }


    /**
     * This method verify optotype existences
     * @return
     */
    private boolean  findOrCreateTableOptotypes(){

        Cursor cursor = null;
        boolean value = false;

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT idOptotype FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()){
                value = true;
            }else{
                value = false;
            }
        }catch (Exception e){
            Log.d("message", "catch");
            optotypeDb.onCreate(db);
            value = true;
        }finally{
            if (cursor != null)
                cursor.close();
            db.close();
        }

        return value;

    }

    /**
     * This method find a image Optotype by code
     * @param code
     * @return
     */
    public String getBipmapOptotype (String code){
        Log.d("message: ", "acedi a la busqueda del Bitmap");
        String value = "";
        OptotypeDbHelper optotypeDb = new OptotypeDbHelper( this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;
        String sql = "SELECT image FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME + " WHERE optotypeCode = '" + code +"'";


        try{

            cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                value = cursor.getString(0);
            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");
        }finally{
            cursor.close();
            db.close();
        }
        return value;
    }



}
