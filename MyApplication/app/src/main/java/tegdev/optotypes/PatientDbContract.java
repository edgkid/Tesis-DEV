package tegdev.optotypes;

import android.provider.BaseColumns;

/**
 * Created by edgar on 09/07/2018.
 */

public class PatientDbContract {

    public static abstract class PatientEntry  implements BaseColumns {

        public static final String TABLE_NAME = "patient_db_app";

        public static final String ID = "idPatient";
        public static final String FISTNAME = "firstName";
        public static final String MIDDLENAME = "middleName";
        public static final String LASTNAME = "lastNam";
        public static final String MAIDENNAME = "maidenName";
        public static final String GENDER = "gender";
        public static final String BIRTHDAY = "birthday";
        public static final String YEARSOLD = "yearsOld";
        public static final String IMAGE = "image";
        public static final String NEXTAPPOINTMENT = "nextAppointment";

    }

}
