package tegdev.optotypes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

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
import java.util.ArrayList;

/**
 * Created by Edgar on 30/05/2018.
 */

public class HttpHandlerDiagnostic {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerDiagnostic(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This method send data to server
     * @param diagnostic
     * @param action
     */
    public void sendRequestPOST (Diagnostic diagnostic, int action){

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
            getJsonData(listParam, diagnostic, action);

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
            Log.d("message: ", "Error no estoy haciendo conexion");
        }

    }

    /**
     * This method send data to server
     * @param idPatient
     * @param action
     */
    public String sendRequestPost (String idPatient, int action){

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
            getJsonData(listParam, idPatient, action);

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
     * This method initialize conect with server
     * @param ctx
     * @param diagnostic
     * @param action
     */
    public void connectToResource (final TestFormActivity ctx, final Diagnostic diagnostic, final int action){

        Log.d("message: ", "Genera solicitud de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {
                sendRequestPOST(diagnostic, action);
            }
        };
        tr.start();

    }

    /**
     * This method initialize conect with server
     * @param ctx
     * @param diagnostics
     * @param action
     */
    public void connectToResource (final CrudReadAppointmentActivity ctx, final ArrayList diagnostics, final String idPatient, final int action){

        Log.d("message: ", "Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestPost(idPatient,action);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            //Toast.makeText(ctx.getApplicationContext(),"Conexion con patients", Toast.LENGTH_SHORT).show();
                            proccessingJson(diagnostics, result);
                            Log.d("message", "el servidor trjo resultados");
                        } else
                            Log.d("message", "el servidor trjo resultados");
                            //Toast.makeText(ctx.getApplicationContext(),"Conexion No patients", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }

    /**
     * This method generate a JSON For save Data
     * @param listParam
     * @param diagnostic
     * @param action
     */
    public void getJsonData (JSONArray  listParam, Diagnostic diagnostic, int action ){

        JSONObject jsonParam = null;

        try{

            jsonParam = new JSONObject();
            jsonParam.put("idPatient", diagnostic.getIdPatient());
            jsonParam.put("yearsOld", diagnostic.getYears());
            jsonParam.put("gender", diagnostic.getSex());
            jsonParam.put("center", diagnostic.getCenter());
            jsonParam.put("sustain", diagnostic.getSustain());
            jsonParam.put("maintain", diagnostic.getMaintain());
            jsonParam.put("avRigth", diagnostic.getAvRigth());
            jsonParam.put("avLeft", diagnostic.getAvLeft());
            if (diagnostic.getExtendsMon().length() > 14)
                jsonParam.put("antecedentMon", diagnostic.getExtendsMon().substring(14));
            else
                jsonParam.put("antecedentMon", "");
            if (diagnostic.getExtendDad().length() > 14)
                jsonParam.put("antacedentDad", diagnostic.getExtendDad().substring(14));
            else
                jsonParam.put("antacedentDad", "");
            if(diagnostic.getSignalDefect().length() > 14)
                jsonParam.put("signalDefect", diagnostic.getSignalDefect().substring(14));
            else
                jsonParam.put("signalDefect", "");
            jsonParam.put("typeTest", diagnostic.getTypeTest());
            jsonParam.put("colaboratedGrade", diagnostic.getColaborate());
            jsonParam.put("action", action);


            listParam.put(jsonParam);


        }catch (Exception e){

            e.printStackTrace();
            Log.d("message: ", "Problemas generando JSON");
        }

    }

    /**
     * This method generate a JSON For save Data
     * @param listParam
     * @param idPatient
     * @param action
     */
    public void getJsonData (JSONArray  listParam, String idPatient, int action ){

        JSONObject jsonParam = null;

        try{

            jsonParam = new JSONObject();
            jsonParam.put("idPatient", idPatient);
            jsonParam.put("action", action);


            listParam.put(jsonParam);


        }catch (Exception e){

            e.printStackTrace();
            Log.d("message: ", "Problemas generando JSON");
        }

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
     * This method processing data on JSon
     * @param list
     * @param result
     */
    public void proccessingJson (ArrayList list, String result){

        Log.d("message", "lpl");
        Log.d("message:", result.toString());

        JSONArray array = null;

        try{

            array = new JSONArray(result);

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);

                Diagnostic diagnostic = new Diagnostic();
                diagnostic.setAvRigth(jsonObj.getString("eyeRight"));
                diagnostic.setAvLeft(jsonObj.getString("eyeleft"));
                diagnostic.setCenter(jsonObj.getString("center"));
                diagnostic.setSustain(jsonObj.getString("sustain"));
                diagnostic.setMaintain(jsonObj.getString("maintain"));
                diagnostic.setDate(jsonObj.getString("appointmentdate"));
                diagnostic.setTypeTest(jsonObj.getString("typeTest"));
                diagnostic.setSex(jsonObj.getString("gender"));

                //list.add(diagnostic);
                CrudReadAppointmentActivity.listData.add(diagnostic);
                //Log.d("message", "http-" + String.valueOf(CrudReadAppointmentActivity.listData.size()));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }


}
