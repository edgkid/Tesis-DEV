package tegdev.optotypes;

import android.provider.BaseColumns;

/**
 * Created by edgar on 15/07/2018.
 */

public class FormDataDbContract {

    public static abstract class FormDataEntry implements BaseColumns {

        public static final String TABLE_NAME = "form_db_app";

        public static final String ID = "idForm";
        public static final String PATIENTDATA = "patientData";
        public static final String AVDATA = "avData";
        public static final String OTHERTESTA = "otherTestA";
        public static final String OTHERTESTB = "otherTestB";
        public static final String TESTUSED = "testUsed";
        public static final String ANTECEDENTDAD = "antecedentDad";
        public static final String ANTECEDENTMON = "antecedentMon";
        public static final String STATUS = "status";

    }

}
