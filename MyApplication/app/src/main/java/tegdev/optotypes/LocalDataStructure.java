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

        Cursor cursor = null;
        String query = "SELECT idPatient FROM " + PatientDbContract.PatientEntry.TABLE_NAME;

        PatientDbHelper patientDbHelper = new PatientDbHelper(context);
        SQLiteDatabase db = patientDbHelper.getReadableDatabase();

        try{

            cursor = db.rawQuery(query,null);

        }catch (Exception e){
            patientDbHelper.onCreate(db);
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
            db.close();
        }

    }


    /**
     * This method initialize local table for data diagnostic
     */
    public void findOrCreateDiagnosticTable (){

        Cursor cursor = null;
        String query = " SELECT idForm FROM " + FormDataDbContract.FormDataEntry.TABLE_NAME;

        FormDataDbHelper formDataDbHelper = new FormDataDbHelper(context);
        SQLiteDatabase db = formDataDbHelper.getReadableDatabase();

        try{

            cursor = db.rawQuery(query, null);
            Log.d("printLog","tengo tabla de diagnosticos");
        }catch (Exception e){

            formDataDbHelper.onCreate(db);
            e.printStackTrace();
            Log.d("printLog","Creo tabla de Diagnostico");

        }finally {

            if (cursor != null ){
                cursor.close();
            }
            db.close();
        }

    }

    /**
     * This method initialize local table for data interacion
     */
    public void findOrCreateInteractionTable (){

        Cursor cursor = null;
        String query = " SELECT idInteraction FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(ControlForService.context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        try{

            cursor = db.rawQuery(query, null);
            Log.d("printLog","tengo tabla de nteraccion");
        }catch (Exception e){

            interactionDbHelper.onCreate(db);
            e.printStackTrace();
            Log.d("printLog","Creo tabla de Interaccion");

        }finally {

            if (cursor != null ){
                cursor.close();
            }
            db.close();
        }

    }

}
