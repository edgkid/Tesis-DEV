package tegdev.optotypes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Edgar on 03/07/2018.
 */

public class BackGroundProcessForUpdate extends AsyncTask<Void, Integer, Boolean> {

    private Context context;


    public BackGroundProcessForUpdate() {
        super();
    }

    public BackGroundProcessForUpdate(Context context) {
        super();
        this.context = context;
    }

    /**
     * This method stop the thread by one second
     */
    private void processPaused(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        while (true){

            Log.d("printLog", "Servicio Corriendo");
            processPaused();

            if (isCancelled()){
                SubProccessControl.processStop = true;
                SubProccessControl.proccessRun = false;
                break;
            }

        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean aVoid) {
        super.onCancelled(aVoid);
        Log.d("PrintLog", "fin del servicio");

    }

}
