package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Edgar on 27/05/2018.
 */

public class CloseAndRefresh extends AppCompatActivity {

    private Context contextActivity;

    public CloseAndRefresh(Context contextActivity) {
        this.contextActivity = contextActivity;
    }

    /**
     * This metohd clos the sesion
     */
    public void logOutApp(SharedPreferences loginPreferences){
        cleanPreferencesLogin(loginPreferences);

        SubProccessControl.runAndStopSubProccess();
        SubProccessControl.backGroundProcessForUpdate.cancel(true);

        Intent loginActivity = new Intent(contextActivity, LoginActivity.class);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        contextActivity.startActivity(loginActivity);
        finish();
    }


    /**
     *This method clean the shared preference to user with login
     */
    public void cleanPreferencesLogin (SharedPreferences loginPreferences){

        //SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = loginPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();

    }

}
