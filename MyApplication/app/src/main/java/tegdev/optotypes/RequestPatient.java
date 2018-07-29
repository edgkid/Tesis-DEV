package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by Edgar on 27/05/2018.
 */

public class RequestPatient {

    private String request;
    private Context context;

    public RequestPatient(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public RequestPatient(Context context) {

        this.context = context;
    }

    /**
     * This method send request to getting some patient
     * @param list
     * @param patient
     * @param action
     */
    public void getSomePatientForNewAppointment (ListView list, Patient patient, int action){

        HttpHandlerPatient httpHandlerPatient = new HttpHandlerPatient(request,context);
        httpHandlerPatient.connectToResource((CrudSaveAppointmentActivity) context, list, patient, action);

    }

    /**
     * This method send request to getting some patient
     * @param list
     * @param patient
     * @param action
     */
    public void getPatientActualAppointment (ListView list, Patient patient, int action){

        HttpHandlerPatient httpRequestPatient = new HttpHandlerPatient(request, context);

        switch (action){
            case 0:
                httpRequestPatient.connectToResource((DashBoardActivity) context, list, patient, action);
                break;
            case 1:
                httpRequestPatient.connectToResource((CrudModifyAppointmentActivity) context, list, patient, action);
                break;
            case 2:
                httpRequestPatient.connectToResource((CrudDeleteAppointmentActivity) context, list, patient, action);
                break;
            case 3:
                httpRequestPatient.connectToResource((CrudReadAppointmentActivity) context, list, patient, action);
                break;
            case 4:
                httpRequestPatient.connectToResource((CrudRequestTestActivity) context, list, patient, action);
                break;

        }
    }

    /**
     * This method send data patient to server
     */
    public void sendDataPatient(Patient patient, int action){

        HttpHandlerPatient httpHandlerPatient = new HttpHandlerPatient(this.request, this.context);
        httpHandlerPatient.connectToResource((CrudNewPatientActivity) context, patient, action);

    }

    /**
     * This method find a patient by id
     */
    public Patient getDataPatientById (Patient patient){

        Cursor cursor = null;
        Patient newPatient = new Patient();
        PatientDbHelper patientDbHelper = new PatientDbHelper(ControlForService.context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();
        String query = " SELECT idPatient, firstName, middleName, lastName, maidenName, yearsOld, nextAppointment FROM ";
        query = query + PatientDbContract.PatientEntry.TABLE_NAME + " WHERE idPatient = " + patient.getIdPatient();

        Log.d("printLog", query);

        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                do{
                    newPatient.setIdPatient(cursor.getString(0));
                    newPatient.setName(cursor.getString(1));
                    newPatient.setMiddleName(cursor.getString(2));
                    newPatient.setLastName(cursor.getString(3));
                    newPatient.setMaidenName(cursor.getString(4));
                    newPatient.setYearsOld(cursor.getString(5));
                    newPatient.setNextAppointment(cursor.getString(6));

                }while(cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            if (cursor != null){
                cursor.close();
            }
            db.close();
        }

        return newPatient;
    }

}
