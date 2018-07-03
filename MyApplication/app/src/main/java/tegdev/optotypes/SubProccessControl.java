package tegdev.optotypes;

/**
 * Created by Edgar on 03/07/2018.
 */

public class SubProccessControl {

    public static boolean proccessRun = false;
    public static boolean processStop = true;
    public static BackGroundProcessForUpdate backGroundProcessForUpdate = new BackGroundProcessForUpdate();

    /**
     * This method initialize parameters for subProccess service
     */
    public static void runAndStopSubProccess (){

        if(SubProccessControl.processStop){
            SubProccessControl.processStop = false;
            SubProccessControl.proccessRun = true;
        }else{
            SubProccessControl.processStop = true;
            SubProccessControl.proccessRun = false;
        }

    }


}
