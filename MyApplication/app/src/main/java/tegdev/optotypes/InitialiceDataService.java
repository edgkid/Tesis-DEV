package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by edgar on 09/07/2018.
 */

public class InitialiceDataService {

    private Context context;

    public InitialiceDataService(Context context) {

        this.context = context;

    }

    public void initialiceContext (Context context){

        SubProccessControl.context = context;
    }

    /**
     * This method create a Table Patient if this table no exits
     */
    public void initialiceResourcePatient(){

        Log.d("printLog", "Verifico Tabla de Pacientes");
        Cursor cursor = null;
        String query = "SELECT idPatient FROM " + PatientDbContract.PatientEntry.TABLE_NAME;
        PatientDbHelper patientDbHelper = new PatientDbHelper(this.context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();

        try{

            cursor = db.rawQuery(query,null);
            Log.d("printLog", "La tabla existe");

        }catch (Exception e){

            e.printStackTrace();
            patientDbHelper.onCreate(db);
            Log.d("printLog", "Creo la tabla");

        }finally{
            if (cursor != null)
                cursor.close();
            db.close();
        }


    }

}
