package tegdev.optotypes;

import android.provider.BaseColumns;

/**
 * Created by edgar on 21/07/2018.
 */

public class ImageTestDbContract {


    public static abstract class ImageTestEntry  implements BaseColumns {

        public static final String TABLE_NAME = "image_test_db_app";

        public static final String ID = "idImageTest";
        public static final String TESTCARD = "testCard";
        public static final String ROWONE= "rowOne";
        public static final String ROWTWO = "rowTwo";
        public static final String ROWTHREE = "rowThree";
        public static final String ROWFOUR = "rowFour";
        public static final String ROWFIVE = "rowFive";
        public static final String ROWSIX = "rowSix";
        public static final String ROWSEVEN = "rowSeven";
        public static final String ROWEIGHT = "rowEigth";
        public static final String ROWNINE = "rowNine";
        public static final String ROWTEN = "rowTen";
        public static final String ROWELEVEN = "rowEleven";
        public static final String TESTYEAR = "testYear";

    }

}
