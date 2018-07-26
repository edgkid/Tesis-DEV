package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Edgar on 27/05/2018.
 */

public class RequestAppointment {

    private String request;
    private Context context;

    public RequestAppointment() {
    }

    public RequestAppointment(String request, Context context) {
        this.request = request;
        this.context = context;
    }


    public void requestActionOnActualAppointment (Patient patient, int option, String date){

        HttpHandlerAppointment httpHandlerAppointment = new HttpHandlerAppointment(request,context);
        switch (option){
            case 0:
                httpHandlerAppointment.connectToResource((CrudSaveAppointmentActivity) context,patient, option, date);
                break;
            case 1:
                httpHandlerAppointment.connectToResource((CrudModifyAppointmentActivity) context, patient, option, date);
                break;
            case 2:
                httpHandlerAppointment.connectToResource((CrudDeleteAppointmentActivity) context, patient, option);
                break;
            default:
                Toast.makeText(context, "acción no valida", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    /**This method delete Local Data for patient list
     * @param patient
     */
    public void deleteLocalAppointment (Patient patient){

        Log.d("printLog", "elimino paciente " + patient.getIdPatient());

        PatientDbHelper patientDbHelper = new PatientDbHelper( ControlForService.context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();

        db.delete(PatientDbContract.PatientEntry.TABLE_NAME, " idPatient = " + patient.getIdPatient(), null);
        db.close();

    }


}
