package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by edgar on 11/07/2018.
 */

public class LocalDataStructure {


    private Context context;

    public LocalDataStructure(Context context) {
        this.context = context;
    }

    /**
     * This method initialize local table for patient
     */
    public void findOrCreatePatientTable (){

        Log.d("printLog", "Verifico o inicializo tabla para pacientes");
        Cursor cursor = null;
        String query = "SELECT idPatient FROM " + PatientDbContract.PatientEntry.TABLE_NAME;

        PatientDbHelper patientDbHelper = new PatientDbHelper(context);
        SQLiteDatabase db = patientDbHelper.getReadableDatabase();

        try{

            cursor = db.rawQuery(query,null);

            Log.d("printLog", "La tabla existe");
            if (cursor.moveToFirst()){
                Log.d("printLog", "Tabla con datos");
            }else{
                Log.d("printLog", "Tabla sin datos");
            }

        }catch (Exception e){
            Log.d("printLog", "creo la tabla");
            patientDbHelper.onCreate(db);
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
            db.close();
        }

    }

}
