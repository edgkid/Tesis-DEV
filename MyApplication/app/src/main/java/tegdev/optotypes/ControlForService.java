package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;

/**
 * Created by edgar on 10/07/2018.
 */

public class ControlForService {

    public static Context context = null;
    public static boolean run = false;
    public static BackGroundServiceForUpdate backGroundServiceForUpdate = null;

    public static void runAndStopService (){

        if (!run){
            run = true;
            context.startService(new Intent(context, ServiceForUpdate.class));
        }else{
            run = false;
            context.stopService(new Intent(context, ServiceForUpdate.class));
        }
    }

}
