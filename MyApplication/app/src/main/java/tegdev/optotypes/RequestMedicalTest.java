package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Edgar on 29/05/2018.
 */

public class RequestMedicalTest {

    private String request;
    private Context context;

    public RequestMedicalTest() {
    }

    public RequestMedicalTest(Context context) {
        this.context = context;
    }

    public RequestMedicalTest(String request, Context context) {
        this.request = request;
        this.context = context;
    }


    /**
     * This method validate  if Table interaction exist
     */
    public void findOrCreateTableInteraction (){

        Cursor cursor = null;
        InteractionDbHelper interactionDb = new InteractionDbHelper(this.context);
        SQLiteDatabase db = interactionDb.getReadableDatabase();

        Log.d("message", "Comprobando existencia de tabla interaction");
        try{
            cursor = db.rawQuery("SELECT idInteraction FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME, null);
            Log.d("message", "existe");
        }catch (Exception e){
            interactionDb.onCreate(db);
            Log.d("message", "no existe la creamos");
        }finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }
    }

    /**
     * This method save data interaction by patient
     * @param visualTest
     * @param context
     */
    public void saveTest (VisualTest visualTest, Context context){

        Log.d("message: ", "llego al metodo que salvara cada test");
        ContentValues values = new ContentValues();
        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(context);
        SQLiteDatabase db = interactionDbHelper.getWritableDatabase();
        Iterator<Optotype> iterator = visualTest.getOptotypes().iterator();

        String idOptotype= "";


        try{

            while (iterator.hasNext()){
                idOptotype = iterator.next().getIdOptotype();
                values.put(InteractionDbContract.InteractionEntry.IDOPTOTYPE, idOptotype );
                values.put(InteractionDbContract.InteractionEntry.IDPATIENT, visualTest.getIdPatient());
                values.put(InteractionDbContract.InteractionEntry.TESTCODE, visualTest.getTestCode());
                values.put(InteractionDbContract.InteractionEntry.EYE, visualTest.getTestEye());

                Log.d("Interaccion", "datp: " + idOptotype);
                Log.d("Interaccion", "datp: " + String.valueOf(visualTest.getIdPatient()));
                Log.d("Interaccion", "datp: " + visualTest.getTestCode());
                Log.d("Interaccion", "datp: " + visualTest.getTestEye());

                db.insert(InteractionDbContract.InteractionEntry.TABLE_NAME, null, values);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.d("Error: ", "Problema al guardar");
        }finally {
            db.close();
        }

    }

    /**
     * This method allow get optotype to interaction
     * @param idPatient
     * @return
     */
    public ArrayList<String> getOptotypes (String idPatient){

        InteractionDbHelper interaction = new InteractionDbHelper(this.context);
        SQLiteDatabase db = interaction.getReadableDatabase();
        ArrayList<String>  list = new ArrayList<String>();
        Cursor cursor = null;
        String query = "SELECT DISTINCT(idOptotype) FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;
        query = query + " WHERE idPatient = " + idPatient;

        Log.d("message: ", query);

        try{
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(0));
                } while(cursor.moveToNext());
            }

        }catch (Exception e){
            Log.d("message: ", "Problema en requesMedicalTest (takeOptotypeByTest)");
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }

        return list;

    }

    /**
     * This method allow get optotype to interaction
     * @param size
     * @param optotypesId
     * @return
     */
    public OptotypeForPatient[] getOptotypes( int size, ArrayList<String> optotypesId){

        int count = 0;
        String query = "";
        Bitmap image = null;
        OptotypeForPatient optotype = null;
        Iterator<String> iteratorOptotypeId = optotypesId.iterator();
        OptotypeForPatient optotypesData[] = new OptotypeForPatient[size];

        OptotypeDbHelper optotypeDbHelper = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDbHelper.getReadableDatabase();
        Cursor cursor = null;

        query = "SELECT idOptotype, optotypeName, image FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME;
        query = query + " WHERE idOptotype IN (";

        while (iteratorOptotypeId.hasNext()){
            query = query + iteratorOptotypeId.next() + ",";
        }

        query = query.substring(0, (query.length()-1)) + ")";

        Log.d("message: ", query);

        try {

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    optotype = new OptotypeForPatient();
                    optotype.setIdOptotype(cursor.getString(0));
                    optotype.setOptotypeCode(cursor.getString(1));

                    byte[] byteCode = Base64.decode(cursor.getString(2), Base64.DEFAULT);
                    image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
                    optotype.setImage(image);
                    optotypesData[count] = optotype;
                    count ++;
                } while(cursor.moveToNext());
            }


        }catch (Exception e){
            e.printStackTrace();
            Log.d("message: ", "Probelmas con el cursor");
        }

        return optotypesData;
    }

    /**
     * This method send request with data intaraction to server
     * @param patient
     * @param action
     */
    public void sendDataInteraction(Patient patient, int action, String yearsOld){

        Log.d("message", "Voy a enviar solicitud de envio de datos");
        HttpHandlerInteraction httpHandlerInteraction = new HttpHandlerInteraction("test",this.context);

        if (Integer.parseInt(yearsOld)<= 2){
            httpHandlerInteraction.connectToResource((KeyBoardInteractionActivity) context, patient, action);
        }else{
            httpHandlerInteraction.connectToResource((InteractionActivity) context, patient, action);
        }

    }

    /**
     * This method send request to generate test
     * @param patient
     * @param distance
     * @param action
     * @param test
     * @param imageTest
     */
    public void requestTest (PatientsToday patient, int distance, int action, ImageView test, ArrayList imageTest){

        Log.d("carta", "request");
        HttpHandlerMedicalTest httpHandlerMedicalTest = new HttpHandlerMedicalTest(this.request, this.context);
        httpHandlerMedicalTest.connectToResource((CrudRequestTestActivity) context, patient, distance, action, test, imageTest);


    }

}
