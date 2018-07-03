package tegdev.optotypes.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Edgar on 03/07/2018.
 */

public class ServiceForUpdate extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
