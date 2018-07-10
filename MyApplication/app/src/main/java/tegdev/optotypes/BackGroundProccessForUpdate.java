package tegdev.optotypes;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by edgar on 09/07/2018.
 */

public class BackGroundProccessForUpdate extends AsyncTask<Void, Integer, Boolean > {

    public BackGroundProccessForUpdate () {
        super();

    }

    /**
     * This method stop the thread by one second
     */
    private void proccessPause(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method allows execute the update on LocalResource
     */
    private void excuteTheUpdates(){

        String requets = "patients";
        RequestUpdatingResource requestUpdatingResource = new RequestUpdatingResource(requets, SubProccessControl.context);
        requestUpdatingResource.requestResourcePatient();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        while (true){

            Log.d("printLog", "Service is Running");
            //excuteTheUpdates();
            proccessPause();

            if (isCancelled()){
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

    }


}
