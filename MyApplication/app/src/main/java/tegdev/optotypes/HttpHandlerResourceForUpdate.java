package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by edgar on 09/07/2018.
 */

public class HttpHandlerResourceForUpdate {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerResourceForUpdate(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This method connect the App with webservice by Get
     * @return
     */
    public String sendRequestGet (){

        URL url = null;
        String line = "";
        int responseCode;
        StringBuilder result = null;
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ this.request;
        String retunrValue = "";

        try{
            Log.d("printLog: ", path);

            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();// en caso de que halla respuesta el valor es 200

            Log.d("printLog: ", Integer.toString(responseCode));
            // equivalente a preguntar si la respuesta es igual a 200
            if (responseCode == HttpURLConnection.HTTP_OK){

                result = new StringBuilder();
                InputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                while ((line = reader.readLine()) !=null ){
                    result.append(line);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if (result == null)
            retunrValue = "[{}]";
        else
            retunrValue = result.toString();

        Log.d("printLog : ",retunrValue);

        return retunrValue;
    }

    /**
     * This method verify a response server
     * @param result
     * @return
     */
    public boolean verifyRespondeServer (String result){

        boolean value = false;
        Log.d("message: ", "Metodo para verificar");

        try{

            JSONArray json = new JSONArray(result);
            if (json.length() > 0)
                value = true;

        }catch (Exception e){}

        return value;
    }


    /**
     * This method initialize connection with webservice, this method belongs to DashBoardActivity
     */
    public void connectToResource (){

        Thread tr = new Thread(){
            @Override
            public void run() {

                String result = sendRequestGet();
                Log.d("printLog", "Preparado para solicitar y actualizar Pacientes");
                Log.d("printLog", result);
                if (verifyRespondeServer(result)){
                    manageLocalData(result, 0);
                } else{
                    Log.d("printLog", "No Existen Datos para actualizar");
                }
            }
        };
        tr.start();
    }

    /**
     * This method save Local Date
     */
    public void manageLocalData(String result, int localData){

        RequestUpdatingResource requestUpdatingResource = new RequestUpdatingResource("patients");

        switch (localData){
            case 0:
                Log.d("printLog", "Datos de Paciente");
                requestUpdatingResource.deleteLocalPatientResource();
                savePatientLocalData(result);
                break;
            case 1:
                Log.d("printLog", "Datos de Interaccion");
                break;
            default:
                Log.d("printLog", "No hay datos para actualizar");
                break;
        }

    }

    /**
     * This method save Local Patient Data
     */
    private void savePatientLocalData (String result){

        JSONArray array = null;
        ContentValues values = new ContentValues();
        PatientDbHelper patientDbHelper = new PatientDbHelper(context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();

        try{

            array = new JSONArray(result);

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);

                values.put(PatientDbContract.PatientEntry.ID, jsonObj.getString("idPatient"));
                values.put(PatientDbContract.PatientEntry.FISTNAME, jsonObj.getString("firstName"));
                values.put(PatientDbContract.PatientEntry.MIDDLENAME, jsonObj.getString("middleName"));
                values.put(PatientDbContract.PatientEntry.LASTNAME, jsonObj.getString("lastName"));
                values.put(PatientDbContract.PatientEntry.MAIDENNAME, jsonObj.getString("maidenName"));
                values.put(PatientDbContract.PatientEntry.GENDER, jsonObj.getString("gender"));
                values.put(PatientDbContract.PatientEntry.YEARSOLD, jsonObj.getString("yearsOld"));
                values.put(PatientDbContract.PatientEntry.BIRTHDAY, jsonObj.getString("birthday"));
                values.put(PatientDbContract.PatientEntry.NEXTAPPOINTMENT, jsonObj.getString("nextAppointmentDate"));
                values.put(PatientDbContract.PatientEntry.IMAGE, jsonObj.getString("image"));

                db.insert(PatientDbContract.PatientEntry.TABLE_NAME, null, values);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.close();
        }
    }
}
