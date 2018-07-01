package tegdev.optotypes;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Edgar on 01/07/2018.
 */

public class BackGroundServiceDataApp extends AsyncTask<Void, Integer, Boolean> {

    private Boolean value;

    public BackGroundServiceDataApp() {
        super();
        value = true;
    }

    /**
     * This method allow pause the thread
     */
    private void sleepThread()  {

        try{
            Thread.sleep(1000);
        }catch (Exception e){
           e.printStackTrace();
        }

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        int x = 0;
        while(x <100){
            Log.d("pirntThread", "Acciones a ejecutar");

            sleepThread();

            if (isCancelled())
                break;

            x++;
        }

        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.d("printTreahd", "fin de actualizaciones");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d("printTreahd", "Servicio finalizado");
        value = false;
    }
}
