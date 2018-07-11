package tegdev.optotypes;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by edgar on 10/07/2018.
 */

public class BackGroundServiceForUpdate extends AsyncTask<Void, Integer, Boolean> {


    public BackGroundServiceForUpdate() {
    }

    /**
     * This method stop the thread by one second
     */
    private void serviceThreadPause(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initialice update the nesesary local data
     */
    private void updateData (){

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        while(true){

            Log.d("printLog", "Run Service");
            serviceThreadPause();

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
        Log.d("message", "value:" + values[0]);

    }

    @Override
    protected void onCancelled(Boolean aVoid) {
        super.onCancelled(aVoid);
        Log.d("message", "hilo cancelado");

    }

}
