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

}
