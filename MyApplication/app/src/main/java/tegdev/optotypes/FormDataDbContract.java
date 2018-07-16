package tegdev.optotypes;

import android.provider.BaseColumns;

/**
 * Created by edgar on 15/07/2018.
 */

public class FormDataDbContract {

    public static abstract class FormDataEntry implements BaseColumns {

        public static final String TABLE_NAME = "form_db_app";

        public static final String ID = "idform";
        public static final String IDPATIENT = "idPatient";
        public static final String YEARSOLD = "yearsOld";
        public static final String GENDER = "gender";

        public static final String AVRIGTH = "avRigth";
        public static final String AVLEFT = "avLeft";

        public static final String CENTER = "center";
        public static final String SUSTAIN = "sustain";
        public static final String MAINTAIN = "maintain";

        public static final String CHRONOMATICOD = "chronomaticOd";
        public static final String CHRONOMATICOI = "chronomaticOi";

        public static final String TONOMETRIAOD = "tonometriaOd";
        public static final String TONOMETRIAOI = "tonometria Oi";

        public static final String FORIA = "foria";
        public static final String ENDOFORIA = "endoforia";
        public static final String EXOFORIA = "exoforia";
        public static final String ORTOFORIA = "ortoforia";
        public static final String ORTOTROPIA = "ortotropia";
        public static final String DVD = "dvd";
        public static final String CAELEVADA = "caElevada";

        public static final String ANTECEDENTDAD = "antecedentDad";
        public static final String ANTECEDENTMON = "antecedentMon";
        public static final String SIGNALDEFECT = "signalDefect";

        public static final String TYPETEST = "typeTest";
        public static final String COLABORATEGRADE = "colaborateGrade";

        public static final String STATUS = "status";

    }

}
