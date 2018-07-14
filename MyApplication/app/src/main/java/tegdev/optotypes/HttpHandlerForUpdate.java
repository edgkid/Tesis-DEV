package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by edgar on 11/07/2018.
 */

public class HttpHandlerForUpdate {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerForUpdate(String request, Context context) {
        this.request = request;
        this.context = context;
    }


    public String sendRequestGet (){

        URL url = null;
        String line = "";
        int responseCode;
        StringBuilder result = null;
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ this.request;
        String retunrValue = "";

        try{
            Log.d("message: ", path);

            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();// en caso de que halla respuesta el valor es 200

            Log.d("message: ", Integer.toString(responseCode));
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

        Log.d("result : ",retunrValue);

        return retunrValue;
    }

    /**
     * This method send rquest by post
     */
    public void sendRequestPOST (){

        URL url = null;
        int responseCode;
        StringBuilder result = null;
        DataOutputStream printout;
        InputStream inputStreamResponse = null;
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ this.request;

        try{
            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/JSON");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonInteraction(listParam);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            Log.d("message: ", listParam.toString() );

            responseCode = connection.getResponseCode();

            Log.d("message: ", String.valueOf(responseCode));

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();
                Log.d("message code:", String.valueOf(responseCode));

                /// Aqui solicito cambiar estatus a S
                RequestInteraction requestInteraction = new RequestInteraction(ControlForService.context);
                requestInteraction.modifyLocalStatus();

            }

            if (inputStreamResponse != null){
                try{
                    inputStreamResponse.close();
                }
                catch(Exception ex){
                    Log.d(this.getClass().toString(), "Error cerrando InputStream", ex);
                }
            }


        }catch (IOException e){
            e.printStackTrace();
            Log.d("message: ", "Error no estoy haciendo conexion");
        }

    }


    public boolean verifyRespondeServer (String result){

        boolean value = false;
        Log.d("message: ", "Verificando espuesta del servidor");

        try{

            JSONArray json = new JSONArray(result);
            if (json.length() > 0)
                value = true;

        }catch (Exception e){}

        return value;
    }


    public void connectToResource (final Context ctx, final int action){

        Thread tr = new Thread(){
            @Override
            public void run() {

                switch (action){
                    case 0 :
                        String result= sendRequestGet();
                        if (verifyRespondeServer(result)){
                            Log.d("Resultado", result);
                            deleteDataPatient();
                            saveDataPatient(result);
                        }
                        break;
                    case 1 :
                        sendRequestPOST();
                        break;
                }

            }
        };
        tr.start();

    }


    private void saveDataPatient (String result){

        Log.d("printLog", "Salvo datos");
        JSONArray array = null;
        ContentValues values = new ContentValues();

        PatientDbHelper patientDbHelper = new PatientDbHelper(this.context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();

        try {

            array = new JSONArray(result);

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);

                values.put(PatientDbContract.PatientEntry._ID, Integer.parseInt(jsonObj.getString("idPatient")));
                values.put(PatientDbContract.PatientEntry.ID, jsonObj.getString("idPatient"));
                values.put(PatientDbContract.PatientEntry.FIRSTNAME, jsonObj.getString("firstName"));
                values.put(PatientDbContract.PatientEntry.MIDDLENAME, jsonObj.getString("middleName"));
                values.put(PatientDbContract.PatientEntry.LASTNAME, jsonObj.getString("lastName"));
                values.put(PatientDbContract.PatientEntry.MAIDENNAME, jsonObj.getString("maidenName"));
                values.put(PatientDbContract.PatientEntry.GENDER, jsonObj.getString("gender"));
                values.put(PatientDbContract.PatientEntry.BIRTHDAY, jsonObj.getString("birthday"));
                values.put(PatientDbContract.PatientEntry.YEARSOLD, jsonObj.getString("yearsOld"));
                values.put(PatientDbContract.PatientEntry.IMAGE, jsonObj.getString("image"));
                values.put(PatientDbContract.PatientEntry.NEXTAPPOINTMENT, jsonObj.getString("nextAppointmentDate"));

                db.insert(PatientDbContract.PatientEntry.TABLE_NAME, null, values);

                Log.d("printLog: ", ("Insert " + Integer.toString(i)) + jsonObj.getString("firstName"));

            }

        }catch (Exception e){
            e.printStackTrace();
            Log.d("json: ", "No hay valor para procesar");
        }finally {
            db.close();
        }


    }

    private void deleteDataPatient (){

        Log.d("printLog", "Elimino datos");
        PatientDbHelper patientDbHelper = new PatientDbHelper(this.context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();

        db.delete(PatientDbContract.PatientEntry.TABLE_NAME, null,null);
    }

    /**
     * This method get the all interaction data in status N and send
     */
    private void getJsonInteraction(JSONArray  listParam){

        Cursor cursor = null;
        JSONObject jsonParam = null;
        String query = "SELECT idInteraction, idOptotype, idPatient, testCode, eye, status FROM ";
        query = query + InteractionDbContract.InteractionEntry.TABLE_NAME ;
        query = query + " WHERE status = 'N'";

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(ControlForService.context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                do {
                    jsonParam = new JSONObject();
                    jsonParam.put("idPatient", cursor.getString(2));
                    jsonParam.put("idOptotype", cursor.getString(1));
                    jsonParam.put("testCode", cursor.getString(3));
                    jsonParam.put("eye", cursor.getString(4));
                    jsonParam.put("action", 0);
                    listParam.put(jsonParam);

                }while(cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
            db.close();
        }

    }



}
