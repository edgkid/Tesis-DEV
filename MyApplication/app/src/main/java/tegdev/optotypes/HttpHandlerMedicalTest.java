package tegdev.optotypes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
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
import java.util.ArrayList;

/**
 * Created by Edgar on 29/05/2018.
 */

public class HttpHandlerMedicalTest {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerMedicalTest(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public String sendRequestPost (PatientsToday patient, int distance, int action){

        URL url = null;
        int responseCode;
        StringBuilder result = null;
        String line;
        DataOutputStream printout;
        String retunrValue;
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
            getJsonData(listParam, patient.getIdPatient(), distance, action);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            responseCode = connection.getResponseCode();

            if( responseCode == HttpURLConnection.HTTP_OK){

                result = new StringBuilder();
                inputStreamResponse = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamResponse));

                while ((line = reader.readLine()) !=null ){
                    result.append(line);
                }

            }

            if (inputStreamResponse != null){
                try{
                    inputStreamResponse.close();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    Log.d("printLog", "No hay conexion para solicitar test");
                }
            }


        }catch (IOException e){
            e.printStackTrace();
        }

        if (result == null)
            retunrValue = "[{}]";
        else
            retunrValue = result.toString();

        return retunrValue;
    }

    public boolean verifyRespondeServer (String result){

        boolean value = false;

        try{

            JSONArray json = new JSONArray(result);
            if (json.length() > 0)
                value = true;

        }catch (Exception e){}

        return value;
    }

    public void connectToResource (final CrudRequestTestActivity ctx, final PatientsToday patient, final int distance, final int action, final ImageView test, final ArrayList imageTest){

        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestPost(patient, distance, action);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            procesingJson(result,test, imageTest);
                        } else
                            Log.d("printLog", "no hay datos de carta");

                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }


    public void getJsonData (JSONArray  listParam, int idPatient, int distance, int action){

        JSONObject jsonParam = null;

        try{

            jsonParam = new JSONObject();
            jsonParam.put("distance", distance);
            jsonParam.put("patientId", idPatient);
            jsonParam.put("action", action);
            listParam.put(jsonParam);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void procesingJson(String result, ImageView test, ArrayList imageTest){

        JSONArray array = null;
        Bitmap image = null;

        try{

            array = new JSONArray(result);
            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);
                imageTest.add(jsonObj.getString("imageTest"));
                byte[] byteCode = Base64.decode(jsonObj.getString("imageTest"), Base64.DEFAULT);
                test.setTag(jsonObj.getString("imageTest"));
                image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
                break;
            }

            for(int i=1; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if (image != null)
            test.setImageBitmap(image);
        else
            test.setImageResource(R.drawable.imagenotfoud);

    }

}
