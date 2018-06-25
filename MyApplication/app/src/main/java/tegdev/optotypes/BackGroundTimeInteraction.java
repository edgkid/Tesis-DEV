package tegdev.optotypes;

import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

/**
 * Created by Edgar on 25/06/2018.
 */

public class BackGroundTimeInteraction extends AsyncTask <Void, Integer, Boolean> {

    TextView textCound;
    InteractionActivity activity;

    public BackGroundTimeInteraction(TextView textCound, InteractionActivity activity) {
        super();
        this.textCound = textCound;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textCound.setText("0");

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int x= 1; x <= 5; x++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("message", "Seg: " + String.valueOf(x));
            if(isCancelled())
                break;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);
        activity.refreshActivity();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        /*textCound.setText(values[0]);
        activity.textBackGround.setText(values[0]);*/
    }

    @Override
    protected void onCancelled(Boolean aVoid) {
        super.onCancelled(aVoid);
        Log.d("message", "hilo cancelado");

    }



}
