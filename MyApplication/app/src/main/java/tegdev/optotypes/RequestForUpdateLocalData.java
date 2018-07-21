package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by edgar on 11/07/2018.
 */

public class RequestForUpdateLocalData {

    private String request;
    private Context context;

    public RequestForUpdateLocalData(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * This method request
     */
    public void startUpdatePatients (){

        HttpHandlerForUpdate httpHandlerForUpdate = new HttpHandlerForUpdate(request,context);
        httpHandlerForUpdate.connectToResource(context, 0);
    }

    public void startUpdateInteraction(){


        Cursor cursor = null;
        String query = "SELECT  testCode FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME ;

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(ControlForService.context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){

                HttpHandlerForUpdate httpHandlerForUpdate = new HttpHandlerForUpdate(request, context);
                httpHandlerForUpdate.connectToResource(context, 1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            if (cursor != null){
                cursor.close();
            }

            db.close();
        }
    }

    public void startUpdateFormaData(){

        Cursor cursor = null;
        String query = "SELECT  status FROM " + FormDataDbContract.FormDataEntry.TABLE_NAME ;
        query = query + " WHERE status = 'N' ";

        FormDataDbHelper formDataDbHelper = new FormDataDbHelper(ControlForService.context);
        SQLiteDatabase db = formDataDbHelper.getReadableDatabase();

        try{

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){

                HttpHandlerForUpdate httpHandlerForUpdate = new HttpHandlerForUpdate(request, context);
                httpHandlerForUpdate.connectToResource(context, 2);

            }

        }catch (Exception e){

            e.printStackTrace();

        }finally {

            if (cursor != null){
                cursor.close();
            }
            db.close();
        }

    }

    public void startUpdateImageTest(){

        Cursor cursor = null;
        String query = " SELECT idImageTest FROM " + ImageTestDbContract.ImageTestEntry.TABLE_NAME;
        ImageTestDbHelper imageTestDbHelper = new ImageTestDbHelper(ControlForService.context);
        SQLiteDatabase db = imageTestDbHelper.getReadableDatabase();

        try{
            cursor = db.rawQuery( query, null);
            Log.d("printLog", "Existe tabla de test");
        }catch (Exception e){
            Log.d("printLog", "Creo tabla de test");
            imageTestDbHelper.onCreate(db);
            e.printStackTrace();
        }finally {

            if(cursor != null){
                cursor.close();
            }
            db.close();
        }


    }

}
