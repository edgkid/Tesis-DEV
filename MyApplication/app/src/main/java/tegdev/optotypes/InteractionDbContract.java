package tegdev.optotypes;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 29/05/2018.
 */

public class InteractionDbContract {

    public static abstract class InteractionEntry  implements BaseColumns {

        public static final String TABLE_NAME = "interaction_db_app";

        public static final String ID = "idInteraction";
        public static final String IDOPTOTYPE = "idOptotype";
        public static final String IDPATIENT = "idPatient";
        public static final String TESTCODE = "testCode";
        public static final String EYE = "eye";

    }

}
