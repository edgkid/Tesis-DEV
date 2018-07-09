package tegdev.optotypes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Edgar on 27/05/2018.
 */

public class RequestUser {

    private String request;
    private Context context;

    public RequestUser(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This method generate a request to find  user data  for access the app
     * @return boolean
     */
    public boolean findUserOnSystem (){

        boolean value = true;
        String password = "";
        String user = "";
        String roll = "";

        Log.d("printLog", "En request");
        HttpHandlerUser httpRequestUser = new HttpHandlerUser(request, context);
        httpRequestUser.connectToResource((LoginActivity) context, user, password);

        if (user.equals("defaultUser") || password.equals("defaultUser")){
            value = false;
        }

        return value;
    }
}
