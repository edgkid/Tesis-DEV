package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
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

                            if (imageTest.size() > 0){
                                saveOrReplaceTest(imageTest, patient.getYearsOld());
                            }else if (imageTest.size() <= 0){
                                alertDialog(patient);
                            }

                        } else{
                            alertDialog(patient);
                        }
                        interrupt();
                    }
                });

            }
        };
        tr.start();
    }

    private void saveOrReplaceTest(ArrayList imageTest, String yearsOld) {

        Log.d("printLog", yearsOld.split(" ")[1]);
        ContentValues values = new ContentValues();
        String where = " testYear = " + yearsOld.split(" ")[1];
        ImageTestDbHelper imageTestDbHelper = new ImageTestDbHelper(ControlForService.context);
        SQLiteDatabase db = imageTestDbHelper.getWritableDatabase();

        values.put("testCard", imageTest.get(0).toString());
        values.put("rowOne", imageTest.get(1).toString());
        values.put("rowTwo", imageTest.get(2).toString());
        values.put("rowThree", imageTest.get(3).toString());
        values.put("rowFour", imageTest.get(4).toString());
        values.put("rowFive", imageTest.get(5).toString());
        values.put("rowSix", imageTest.get(6).toString());
        values.put("rowSeven", imageTest.get(7).toString());
        values.put("rowEigth", imageTest.get(8).toString());
        values.put("rowNine", imageTest.get(9).toString());
        values.put("rowTen", imageTest.get(10).toString());
        values.put("rowEleven", imageTest.get(11).toString());
        values.put("testYear", yearsOld.split(" ")[1]);

        if (existTest(yearsOld)){
            Log.d("printLog", "Actualizo");
            db.update(ImageTestDbContract.ImageTestEntry.TABLE_NAME, values, where, null);
        }else{
            Log.d("printLog", "Guardo");
            db.insert(ImageTestDbContract.ImageTestEntry.TABLE_NAME, null, values);
        }

        db.close();
    }

    private boolean existTest(String yearsOld) {

        Boolean value = false;
        Cursor cursor = null;
        String query = " SELECT testYear FROM " + ImageTestDbContract.ImageTestEntry.TABLE_NAME;

        Log.d("printLog", "condicion:" + yearsOld.split(" ")[1] );
        query = query + " WHERE testYear = '" + yearsOld.split(" ")[1] + "'";
        Log.d("printLog", "condicion:" + query);
        ImageTestDbHelper imageTestDbHelper = new ImageTestDbHelper(ControlForService.context);
        SQLiteDatabase db = imageTestDbHelper.getReadableDatabase();

        try{

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                Log.d("printLog", "registros");
                value = true;
            }else{
                Log.d("printLog", "No registros");
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
                imageTest.add(jsonObj.getString("imageTest"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if (image != null){
            test.setImageBitmap(image);
            test.setTag("test");
        } else{
            test.setImageResource(R.drawable.imagenotfoud);
            test.setTag("NotFoud");
        }
    }

    /**
     * This method display a dialog by validate
     */
    public void alertDialog(PatientsToday patient){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Problemas con la Carta");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Hay problemas de conexion o no existen datos de interacción para el paciente " + patient.getName())
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

}
