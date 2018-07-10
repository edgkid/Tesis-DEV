package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONObject;

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
     *
     */
    public void getLocalData(ListView list){

        int count = 0;
        Cursor cursor = null;
        String namePatient = "";
        String query = "SELECT idPatient, firstname, middleName, lastName, maidenName, yearsOld, image ";
        query = query + " FROM " + PatientDbContract.PatientEntry.TABLE_NAME;
        PatientDbHelper patientDbHelper = new PatientDbHelper(SubProccessControl.context);
        SQLiteDatabase db = patientDbHelper.getReadableDatabase();

        try{

            cursor = db.rawQuery(query, null);
            PatientsToday patientsData[] = new PatientsToday[12];

            if (cursor.moveToFirst()) {
                do {

                    Patient patient = new Patient ();

                    patient.setIdPatient(cursor.getString(0));
                    patient.setName(cursor.getString(1));
                    patient.setMiddleName(cursor.getString(2));
                    patient.setLastName(cursor.getString(3));
                    patient.setMiddleName(cursor.getString(4));
                    patient.setYearsOld(cursor.getString(5));
                    patient.setPhoto(cursor.getString(6));

                    if(patient.getMiddleName().equals("") || patient.getMiddleName() == null){
                        patient.setMiddleName("-");
                    }

                    if (patient.getMaidenName().equals("") || patient.getMaidenName() == null ){
                        patient.setMaidenName("-");
                    }

                    namePatient = patient.getName() + " " + patient.getMiddleName() + " " + patient.getLastName() + " " + patient.getMaidenName();

                    byte[] byteCode = Base64.decode(patient.getPhoto(), Base64.DEFAULT);
                    Bitmap image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
                    patientsData[count] = new PatientsToday(namePatient, patient.getYearsOld(),image, Integer.parseInt(patient.getIdPatient()));

                    count ++;

                } while(cursor.moveToNext());
            }

            PatientsTodayAdapter patientsAdapter = new PatientsTodayAdapter(context,R.layout.listview_item_patients_today_row, patientsData);
            list.setAdapter(patientsAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (cursor != null){
                cursor.close();
            }
            db.close();
        }

    }

}
