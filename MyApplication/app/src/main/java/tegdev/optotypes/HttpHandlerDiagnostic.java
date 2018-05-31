package tegdev.optotypes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
            jsonParam.put("action", "0");


            listParam.put(jsonParam);


        }catch (Exception e){

            e.printStackTrace();
            Log.d("message: ", "Problemas generando JSON");
        }

    }

}
