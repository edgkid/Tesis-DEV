package tegdev.optotypes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
 * Created by Edgar on 29/05/2018.
 */

public class HttpHandlerInteraction {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerInteraction(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This method send rquest by post
     * @param patient
     * @param action
     */
    public void sendRequestPOST (Patient patient, int action){

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
            getJsonData(listParam, patient.getIdPatient(), action);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            responseCode = connection.getResponseCode();

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();
            }

            if (inputStreamResponse != null){
                try{
                    inputStreamResponse.close();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * This method initialize connection with webservice
     * @param ctx
     * @param patient
     * @param action
     */
    public void connectToResource (final InteractionActivity ctx, final Patient patient, final int action){

        Thread tr = new Thread(){
            @Override
            public void run() {
                sendRequestPOST(patient, action);
            }
        };
        tr.start();

    }

    /**
     * This method initialize connection with webservice
     * @param ctx
     * @param patient
     * @param action
     */
    public void connectToResource (final KeyBoardInteractionActivity ctx, final Patient patient, final int action){

        Thread tr = new Thread(){
            @Override
            public void run() {
                sendRequestPOST(patient, action);
            }
        };
        tr.start();

    }

    /**
     * This method genarate JSON to send
     * @param listParam
     * @param idPatient
     * @param action
     */
    public void getJsonData (JSONArray  listParam, String idPatient, int action ){

        JSONObject jsonParam = null;
        Cursor cursor = null;
        String query = "";

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(this.context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        query = "SELECT idOptotype, idPatient, testCode, eye FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;
        query = query + " WHERE idPatient = " + idPatient;

        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    jsonParam = new JSONObject();
                    jsonParam.put("idPatient", cursor.getString(1));
                    jsonParam.put("idOptotype", cursor.getString(0));
                    jsonParam.put("testCode", cursor.getString(2));
                    jsonParam.put("eye", cursor.getString(3));
                    jsonParam.put("action", action);
                    listParam.put(jsonParam);

                } while (cursor.moveToNext());
            }
        }catch (Exception e){

            e.printStackTrace();
        }finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }

    }


}
