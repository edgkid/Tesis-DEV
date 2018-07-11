package tegdev.optotypes;

import android.content.Context;
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

        Log.d("printLog", "cominicon con el handler para acutlizar datos");

        HttpHandlerForUpdate httpHandlerForUpdate = new HttpHandlerForUpdate(request,context);
        httpHandlerForUpdate.connectToResource(context);
    }

}
