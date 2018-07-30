package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 30/05/2018.
 */

public class RequestDiagnostic {

    private String request;
    private Context context;

    public RequestDiagnostic() {
    }

    public RequestDiagnostic (Context context) {
        this.context = context;
    }


    /**
     * This method send data by form
     * @param diagnostic
     * @param action
     */
    public void sendDataDiagnostic(Diagnostic diagnostic, int action){

        HttpHandlerDiagnostic httpHandlerDiagnostic = new HttpHandlerDiagnostic("diagnostic", this.context);
        httpHandlerDiagnostic.connectToResource((TestFormActivity) context, diagnostic, action);

    }

    /**
     * This method sen reques to find data diagnostic by patient
     * @param objects
     * @param idPatient
     * @param action
     */
    public void requestDataDiagnostic (ArrayList objects, String idPatient, int action){

        HttpHandlerDiagnostic httpHandlerDiagnostic =  new HttpHandlerDiagnostic("diagnostic", this.context);
        httpHandlerDiagnostic.connectToResource((CrudReadAppointmentActivity) context, objects, idPatient, action);
    }

    /**
     * This method sen reques to find data diagnostic by patient
     * @param idPatient
     * @param action
     */
    public void requestAllDataDiagnostic (String idPatient, int action){


        //// aqui comienzo la modificación
        HttpHandlerDiagnostic httpHandlerDiagnostic =  new HttpHandlerDiagnostic("diagnostic", this.context);
        httpHandlerDiagnostic.connectToResource((DiagnosticActivity) this.context, idPatient, action);

    }

    /**
     * This methiod send reques to save diagnostic notes in movil
     * @param diagnostic
     */
    public void saveDataDiagnostic (Diagnostic diagnostic){

        Log.d("printLog", "En request");
        HttpHandlerDiagnostic httpHandlerDiagnostic = new HttpHandlerDiagnostic("diagnostic", ControlForService.context);
        httpHandlerDiagnostic.saveDataDiagnostic(diagnostic);
    }

    /**
     * This method modify local status data
     */
    public void modifyLocalStatus(){

        ContentValues values = new ContentValues();

        FormDataDbHelper formDataDbHelper = new FormDataDbHelper(ControlForService.context);
        SQLiteDatabase db = formDataDbHelper.getWritableDatabase();
        values.put("status", "S");

        db.update(FormDataDbContract.FormDataEntry.TABLE_NAME, values, null,null);
        db.close();
    }

    /**
     * This method get data for views
     * @param idPatient
     */
    public void getDataViews(String idPatient){

        int action = 1;
        HttpHandlerDiagnostic httpHandlerDiagnostic = new HttpHandlerDiagnostic("diagnostic", context);
        httpHandlerDiagnostic.connectToResource((DiagnosticActivity) context, idPatient, action);

    }

}
