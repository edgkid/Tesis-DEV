package tegdev.optotypes;

import android.content.Context;
import android.util.Log;
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

        Log.d("message", "Rquest");
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
}
