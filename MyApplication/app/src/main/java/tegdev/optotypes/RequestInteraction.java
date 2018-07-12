package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Iterator;

/**
 * Created by Edgar on 29/05/2018.
 */

public class RequestInteraction {

    private String request;
    private Context context;

    public RequestInteraction() {
    }

    public RequestInteraction(Context context) {
        this.context = context;
    }

    public RequestInteraction(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This method processing data interacion generated
     * @param newData
     * @param patient
     */
    public void processInteraction (Interaction newData, Patient patient){

        int elementsToTest = 0;
        String rTestCode = "";
        String lTestCode = "";
        Iterator<Optotype> iterator = newData.getOptotypes().iterator();
        VisualTest rVisualTest = new VisualTest("R");
        VisualTest lVisualTest = new VisualTest("L");

        rTestCode = patient.getName().substring(0,1) + patient.getLastName().substring(0,1);
        rTestCode = rTestCode + rVisualTest.getTestCodeByInteraction() + rVisualTest.getTestEye();
        rTestCode = rTestCode + patient.getIdPatient();

        lTestCode = patient.getName().substring(0,1) + patient.getLastName().substring(0,1);
        lTestCode = lTestCode + lVisualTest.getTestCodeByInteraction() + lVisualTest.getTestEye();
        lTestCode = lTestCode + patient.getIdPatient();

        rVisualTest.setIdPatient(Integer.parseInt(patient.getIdPatient()));
        rVisualTest.setTestCode(rTestCode);

        lVisualTest.setIdPatient(Integer.parseInt(patient.getIdPatient()));
        lVisualTest.setTestCode(lTestCode);

        while (iterator.hasNext()) {
            if (elementsToTest < newData.getOptotypes().size() / 2){
                rVisualTest.getOptotypes().add(iterator.next());
            }else {
                lVisualTest.getOptotypes().add(iterator.next());
            }

            elementsToTest ++;
        }

        if (rVisualTest.getOptotypes() != null && lVisualTest.getOptotypes() != null){
            rVisualTest.save(this.context);
            lVisualTest.save(this.context);
        }

    }

    /**
     * This method validate if exist data interaction by patient
     * @param idPatient
     * @return
     */
    public boolean validateInteraction (String idPatient){

        Log.d("message: ", "validateInteraction");
        Cursor cursor = null;
        String query = "";
        String id = "";
        boolean value = false;

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        query = "SELECT testCode FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;
        query = query + " WHERE idPatient = " + idPatient;

        Log.d("message: ", query);

        try{

            cursor = db.rawQuery(query,null);

            if (cursor.moveToFirst()) {
                do {
                    Log.d("message: ", cursor.getString(0));
                    value = true;
                } while(cursor.moveToNext());
            }


        }catch (Exception e){
            e.printStackTrace();
            Log.d("error: ", "problema con el cursor");
        }finally{
            if (cursor != null)
                cursor.close();
            db.close();
        }

        Log.d("message: ", String.valueOf(value));

        return value;
    }

    public void modifyLocalStatus(String idPatient){

        Log.d("printLog", "Modifico status de interaccioon para: " + idPatient);

        ContentValues values = new ContentValues();
        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(ControlForService.context);
        SQLiteDatabase db = interactionDbHelper.getWritableDatabase();
        values.put("status", "S");
        db.update(InteractionDbContract.InteractionEntry.TABLE_NAME, values, "idPatient = " + idPatient,null);
        db.close();

        consultaDePrueba();

    }

    private int consultaDePrueba(){

        int value = 0;

        Cursor cursor = null;
        String query = "SELECT  testCode FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME ;

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(ControlForService.context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                Log.d("printLog", "testEdg" + cursor.getString(0));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            if (cursor != null){
                cursor.close();
            }

            db.close();
        }

        return value;
    }


}
