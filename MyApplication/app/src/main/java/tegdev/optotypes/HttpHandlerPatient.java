package tegdev.optotypes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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
 * Created by Edgar on 27/05/2018.
 */

public class HttpHandlerPatient {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerPatient(String request, Context context) {
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
            Log.d("path: ", path);

            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();// en caso de que halla respuesta el valor es 200

            Log.d("code paciente: ", Integer.toString(responseCode));
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
     * This method connect the App with webservice by Post
     * @param patient
     * @param action
     * @return
     */
    public String sendRequestPost (Patient patient, int action){

        String line = "";
        URL url = null;
        int responseCode;
        StringBuilder result = null;
        DataOutputStream printout;
        InputStream inputStreamResponse = null;
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ this.request;

        try{

            url = new URL (path);
            Log.d("message: ", path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/JSON");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonData(listParam, patient.getName(), action);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            Log.d("message: ", listParam.toString() );

            responseCode = connection.getResponseCode();

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();
                Log.d("message code:", String.valueOf(responseCode));

                result = new StringBuilder();
                InputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                while ((line = reader.readLine()) !=null ){
                    result.append(line);
                }
            }else
                Log.d("message: ", "Como que no conecto");

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
        }

        return result.toString();
    }


    /**
     * This method initialize connection with webservice, this method belongs to SaveAppointment
     * @param ctx
     * @param list
     * @param patient
     * @param action
     */
    public void connectToResource (final CrudRequestTestActivity ctx, final ListView list, final Patient patient, final int action){

        Log.d("message: ", "Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            Toast.makeText(ctx.getApplicationContext(),"Conexion con patients", Toast.LENGTH_SHORT).show();
                            fillList(list, result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No patients", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();
    }

    /**
     * This method initialize connection with webservice, this method belongs to SaveAppointment
     * @param ctx
     * @param list
     * @param patient
     * @param action
     */
    public void connectToResource (final CrudReadAppointmentActivity ctx, final ListView list, final Patient patient, final int action){

        Log.d("message: ", "Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            Toast.makeText(ctx.getApplicationContext(),"Conexion con patients", Toast.LENGTH_SHORT).show();
                            fillList(list, result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No patients", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();
    }

    /**
     * This method initialize connection with webservice, this method belongs to SaveAppointment
     * @param ctx
     * @param list
     * @param patient
     * @param action
     */
    public void connectToResource (final CrudSaveAppointmentActivity ctx, final ListView list, final Patient patient, final int action){

        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestPost(patient, action);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            fillList(list, result);
                            Log.d("message: ", "datos");
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"problema para cargar lista", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();
    }

    /**
     * This method initialize connection with webservice, this method belongs to modifyAppointment
     * @param ctx
     * @param list
     * @param patient
     * @param action
     */
    public void connectToResource (final CrudModifyAppointmentActivity ctx, final ListView list, final Patient patient, final int action){

        Log.d("message: ", "Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            fillList(list, result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No patients", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }

    /**
     * This method initialize connection with webservice, this method belongs to deleteAppointment
     * @param ctx
     * @param list
     * @param patient
     * @param action
     */
    public void connectToResource (final CrudDeleteAppointmentActivity ctx, final ListView list, final Patient patient, final int action){

        Log.d("message: ", "Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            fillList(list, result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No patients", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }

    /**
     * This method initialize connection with webservice, this method belongs to DashBoardActivity
     * @param ctx
     * @param list
     * @param patient
     * @param action
     */
    public void connectToResource (final DashBoardActivity ctx, final ListView list, final Patient patient, final int action){

        Log.d("message: ", "Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            fillList(list, result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No patients", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();

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
     * This method fill a lsit view with concidence patient
     * @param list
     * @param result
     */
    public void fillList(ListView list, String result){

        Log.d("message:", result);

        JSONArray array = null;

        String namePatient;

        try{

            array = new JSONArray(result);

            PatientsToday patientsData[] = new PatientsToday[array.length()];

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);
                Patient patient = new Patient ( jsonObj.getString("idPatient"),
                        jsonObj.getString("firstName"),
                        jsonObj.getString("lastName"),
                        jsonObj.getString("middleName"),
                        jsonObj.getString("maidenName"),
                        "Edad: " + jsonObj.getString("yearsOld"),
                        jsonObj.getString("image"),null);

                namePatient = patient.getName() + " " + patient.getMiddleName() + " " + patient.getLastName() + " " + patient.getMaidenName();


                byte[] byteCode = Base64.decode(patient.getPhoto(), Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
                patientsData[i] = new PatientsToday(namePatient, patient.getYearsOld(),image, Integer.parseInt(patient.getIdPatient()));
            }

            PatientsTodayAdapter patientsAdapter = new PatientsTodayAdapter(context,R.layout.listview_item_patients_today_row, patientsData);
            list.setAdapter(patientsAdapter);

        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    /**
     * This method generate a JSON to get  patients
     * @param listParam
     * @param value
     * @param action
     */
    public void getJsonData (JSONArray listParam, String value, int action){

        JSONObject jsonParam = null;

        try{

            jsonParam = new JSONObject();
            jsonParam.put("action", action);
            jsonParam.put("patient", value);
            listParam.put(jsonParam);

        }catch (Exception e){
            e.printStackTrace();
            Log.d("message: ", "Exception cursor o DB");
        }

    }
}
