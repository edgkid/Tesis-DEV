package tegdev.optotypes;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by edgar on 10/07/2018.
 */

public class ServiceForUpdate extends Service {


    private Context context;
    private BackGroundServiceForUpdate backGroundServiceForUpdate = new BackGroundServiceForUpdate();

    public ServiceForUpdate() {

    }

    public ServiceForUpdate(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        ///Codigo de inico de ejecución del servicio

        Log.d("printLog", "Inicio del Servicio");

        backGroundServiceForUpdate.execute();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        /// Codigo para finalizar el servicio
        backGroundServiceForUpdate.cancel(true);
        Log.d("printLog", "fin del Servicio");

    }
}
