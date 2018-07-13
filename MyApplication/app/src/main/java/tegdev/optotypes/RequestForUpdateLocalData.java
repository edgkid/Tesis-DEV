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

        Log.d("printLog", "cominicon con el handler para acutlizar datos de pacientes");

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

}
