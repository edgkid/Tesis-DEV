package tegdev.optotypes;

import android.content.Context;

/**
 * Created by edgar on 09/07/2018.
 */

public class SubProccessControl {

    public static boolean proccessRun = false;
    public static boolean proccessStop = true;
    public static BackGroundProccessForUpdate backGroundProccessForUpdate = null;
    public static  Context context= null;

    /**
     * This method allows initialice parameters for control the service
     */
    public static void runAndStopSubProceess (){

        if(SubProccessControl.proccessStop){
            SubProccessControl.proccessStop = false;
            SubProccessControl.proccessRun = true;
        }else{
            SubProccessControl.proccessStop = false;
            SubProccessControl.proccessRun = true;
        }
    }





}
