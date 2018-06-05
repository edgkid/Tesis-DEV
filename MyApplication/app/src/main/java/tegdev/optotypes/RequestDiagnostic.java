package tegdev.optotypes;

import android.content.Context;
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

        Log.d("message:", "llmando a hhtpHandler");
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
     * @param objects
     * @param idPatient
     * @param action
     */
    public void requestAllDataDiagnostic (ArrayList objects, String idPatient, int action){

        HttpHandlerDiagnostic httpHandlerDiagnostic =  new HttpHandlerDiagnostic("diagnostic", this.context);
        httpHandlerDiagnostic.connectToResource((DiagnosticActivity) this.context, objects, idPatient, action);


    }

}
