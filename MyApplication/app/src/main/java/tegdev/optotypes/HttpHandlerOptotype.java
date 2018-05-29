package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edgar on 28/05/2018.
 */

public class HttpHandlerOptotype {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerOptotype(String request, Context context) {
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

        return result.toString();
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

    public void connectToResource (final InteractionActivity ctx){

        Log.d("message: ", "Genera solicitud de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            Toast.makeText(ctx.getApplicationContext(),"Conexion con Optotypes", Toast.LENGTH_SHORT).show();
                            procesingJson(result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No Optotypes", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }

    public void procesingJson (String result){

        JSONArray array = null;
        String sql = "";
        ContentValues values = new ContentValues();

        Log.d("message: ", "JSON");
        Log.d("message ", result.toString());

       OptotypeDbHelper optotypeDb = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDb.getWritableDatabase();

        try {

            array = new JSONArray(result);

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);

                values.put(OptotypeDbContract.OptotypeEntry._ID, Integer.parseInt(jsonObj.getString("idOptotype")));
                values.put(OptotypeDbContract.OptotypeEntry.ID, jsonObj.getString("idOptotype"));
                values.put(OptotypeDbContract.OptotypeEntry.OPTOTYPECODE, jsonObj.getString("optotypeCode"));
                values.put(OptotypeDbContract.OptotypeEntry.OPTOTYPENAME, jsonObj.getString("optotypeName"));
                values.put(OptotypeDbContract.OptotypeEntry.OPTOTYPEYEAR, jsonObj.getString("optotypeYear"));
                values.put(OptotypeDbContract.OptotypeEntry.IMAGE, jsonObj.getString("image"));

                db.insert(OptotypeDbContract.OptotypeEntry.TABLE_NAME, null, values);

                Log.d("message: ", ("Insert " + Integer.toString(i)) + jsonObj.getString("optotypeCode"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("json: ", "No hay valor para procesar");
        }finally{
            db.close();
        }

    }


}
