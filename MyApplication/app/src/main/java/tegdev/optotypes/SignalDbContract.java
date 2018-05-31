package tegdev.optotypes;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 30/05/2018.
 */

public class SignalDbContract {

    public static abstract class SignalDbContractEntry  implements BaseColumns {


        public static final String TABLE_NAME = "signal_db_app";
        public static final String ID = "idSignal";
        public static final String SIGNALNAME = "signalName";
    }

}
