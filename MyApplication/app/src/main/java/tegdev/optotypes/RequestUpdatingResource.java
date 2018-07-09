package tegdev.optotypes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by edgar on 09/07/2018.
 */

public class RequestUpdatingResource {

    private String request;
    private Context context;

    public RequestUpdatingResource(String request) {
        this.request = request;

    }

    public RequestUpdatingResource(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This method allows request data patient to loca uses
     */
    public void requestResourcePatient(){

        HttpHandlerResourceForUpdate handlerResourceForUpdate = new HttpHandlerResourceForUpdate(request, context);
        handlerResourceForUpdate.connectToResource();
    }

    /**
     * This method dlete the local data Patient
     */
    public void deleteLocalPatientResource(){

        Log.d("printLog", "Borro datos viejos");

        String query ="DELETE FROM " + PatientDbContract.PatientEntry.TABLE_NAME;
        PatientDbHelper patientDbHelper = new PatientDbHelper(SubProccessControl.context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();

        db.execSQL(query);

    }

}
