package tegdev.optotypes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

            Log.d("printLog", path);

            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/JSON");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonData(listParam, diagnostic, action);

            Log.d("printLog", listParam.toString());

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            responseCode = connection.getResponseCode();

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();

                /// elimino cambio status de datos e ala apliacción

                revisionDeguardado();
                RequestDiagnostic requestDiagnostic = new RequestDiagnostic();
                requestDiagnostic.modifyLocalStatus();

                /*Log.d("pritnLog", "mis nuevos estatus son");
                revisionDeguardado();*/
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
     * This method send data to server
     * @param idPatient
     * @param action
     */
    public String sendRequestPost (String idPatient, int action){

        String line = "";
        String retunrValue = "";
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
            getJsonData(listParam, idPatient, action);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            responseCode = connection.getResponseCode();

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();

                result = new StringBuilder();
                InputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

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

        if (result != null)
            retunrValue = result.toString();
        else
            retunrValue = "[{}]";

        return retunrValue;
    }

    /**
     * This method initialize conect with server
     * @param ctx
     * @param diagnostic
     * @param action
     */
    public void connectToResource (final TestFormActivity ctx, final Diagnostic diagnostic, final int action){

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

        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestPost(idPatient,action);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (result.equals("[{}]")){
                            Log.d("printLog", "No hay datos");
                            Toast.makeText(ctx, "No hay conexion para mostrar datos", Toast.LENGTH_LONG).show();
                        }else{
                            proccessingJson(result, "ReadAppointment");
                            Log.d("printLog", "Hay datos");
                        }
                        interrupt();

                    }
                });

            }
        };
        tr.start();

    }

    /**
     * This method initialize conect with server
     * @param ctx
     * @param action
     */
    public void connectToResource (final DiagnosticActivity ctx, final String idPatient, final int action){

        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestPost(idPatient,action);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            proccessingJson(result,"Diagnostic");
                            //fillData(true, 2);
                            Log.d("PrintLogJSON", result.toString());
                        } else{
                           // fillData(false, 2);
                            Toast.makeText(ctx, "No hay conexion- No se pueden mostrar datos", Toast.LENGTH_LONG).show();
                        }

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
                jsonParam.put("antecedentDad", diagnostic.getExtendDad().substring(14));
            else
                jsonParam.put("antecedentDad", "");
            if(diagnostic.getSignalDefect().length() > 14)
                jsonParam.put("signalDefect", diagnostic.getSignalDefect().substring(14));
            else
                jsonParam.put("signalDefect", "");

            jsonParam.put("typeTest", diagnostic.getTypeTest());
            jsonParam.put("colaboratedGrade", diagnostic.getColaborate());

            jsonParam.put("ortoforia", diagnostic.getOrtoforia());
            jsonParam.put("ortotropia", diagnostic.getOrtotropia());
            jsonParam.put("foria", diagnostic.getForia());
            jsonParam.put("endoforia", diagnostic.getEndoforia());
            jsonParam.put("exoforia", diagnostic.getExoforia());
            jsonParam.put("dvd", diagnostic.getDvd());
            jsonParam.put("caElevada", diagnostic.getCaElevada());
            jsonParam.put("tonometriaOd", diagnostic.getTonometriaOd());
            jsonParam.put("tonometriaOi", diagnostic.getTonometriaOi());

            jsonParam.put("crhomaticOd", diagnostic.getCrhomaticOd() + "/14");
            jsonParam.put("crhomaticOi", diagnostic.getCrhomaticOi() + "/14");

            jsonParam.put("action", action);

            listParam.put(jsonParam);


        }catch (Exception e){
            e.printStackTrace();
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
        }

    }

    /**
     * This method verify a response server
     * @param result
     * @return
     */
    public boolean verifyRespondeServer (String result){

        boolean value = false;

        try{

            JSONArray json = new JSONArray(result);
            if (json.length() > 0)
                value = true;

        }catch (Exception e){}

        return value;
    }

    /**
     * This method processing data on JSon
     * @param result
     */
    public void proccessingJson (String result, String activity){

        JSONArray array = null;
        int sizeList = 0;
        ArrayList<Diagnostic> list = new ArrayList<Diagnostic>();

        Log.d("printLog", "JSON");
        Log.d("printLog", result);

        Diagnostic diagnostic = new Diagnostic();
        try{

            array = new JSONArray(result);

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);

                diagnostic.setTypeTest(jsonObj.getString("typeTest"));
                diagnostic.setAvRigth(jsonObj.getString("eyeRight"));
                diagnostic.setAvLeft(jsonObj.getString("eyeleft"));
                diagnostic.setCenter(jsonObj.getString("center"));
                diagnostic.setSustain(jsonObj.getString("sustain"));
                diagnostic.setMaintain(jsonObj.getString("maintain"));
                diagnostic.setDate(jsonObj.getString("appointmentdate"));
                diagnostic.setTypeTest(jsonObj.getString("typeTest"));
                diagnostic.setDate(jsonObj.getString("appointmentdate"));

                /*CrudReadAppointmentActivity.listData.add(diagnostic);
                sizeList =  CrudReadAppointmentActivity.listData.size();*/
                list.add(diagnostic);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        CrudReadAppointmentActivity.listData.removeAll( CrudReadAppointmentActivity.listData);

        if (list.size() > 0){
            fillActivity(activity, diagnostic);
        }else{
            fillActivity("Empty", diagnostic);
        }


    }



    /**
     * This method fill data on activity
     * @param activity
     */
    private void fillActivity(String activity, Diagnostic diagnostic) {

        if (activity.equals("Empty")){
            CrudReadAppointmentActivity.lastAppointment.setText("Ultima Consulta: N/A");
            CrudReadAppointmentActivity.avLeft.setText("Av Derecho: N/A");
            CrudReadAppointmentActivity.avRight.setText("AV Izquierdo: N/A");
            CrudReadAppointmentActivity.center.setText("Centra: N/A");
            CrudReadAppointmentActivity.sustain.setText("Sostiene: N/A");
            CrudReadAppointmentActivity.maintain.setText("Mantiene: N/A");
        }

        if (activity.equals("ReadAppointment")){

            CrudReadAppointmentActivity.lastAppointment.setText("Ultima Consulta: " + diagnostic.getDate());
            CrudReadAppointmentActivity.avLeft.setText("Av Derecho: " + diagnostic.getAvLeft());
            CrudReadAppointmentActivity.avRight.setText("AV Izquierdo: " + diagnostic.getAvRigth());
            CrudReadAppointmentActivity.center.setText("Centra: " + diagnostic.getCenter());
            CrudReadAppointmentActivity.sustain.setText("Sostiene: " + diagnostic.getSustain());
            CrudReadAppointmentActivity.maintain.setText("Mantiene: " + diagnostic.getMaintain());

        }

        if (activity.equals("Diagnostic")){
            DiagnosticActivity.arryValuesView.get(0).setText(diagnostic.getTypeTest());
            DiagnosticActivity.arryValuesView.get(1).setText("OD: " + diagnostic.getAvRigth());
            DiagnosticActivity.arryValuesView.get(2).setText("OI: " + diagnostic.getAvLeft());
            DiagnosticActivity.arryValuesView.get(3).setText("Center: " + diagnostic.getCenter());
            DiagnosticActivity.arryValuesView.get(4).setText("Sustain: " + diagnostic.getSustain());
            DiagnosticActivity.arryValuesView.get(5).setText("Maintain: " + diagnostic.getMaintain());
        }

    }

    /**
     *
     */
    public void fillData (boolean value, int caseView){

        if (value){

            switch (caseView){
                case 1:
                    getDataCrudReadActivity();
                    break;
                case 2:
                    //getDataDiagnosticActivity();
                    break;
            }

        }else{
            CrudReadAppointmentActivity.avRight.setText("Av Derecho: " + "N/A");
            CrudReadAppointmentActivity.avLeft.setText("Av Izquierdo: " +"N/A");
            CrudReadAppointmentActivity.center.setText("Centra: " + "N/A");
            CrudReadAppointmentActivity.sustain.setText("Sostiene: " + "N/A");
            CrudReadAppointmentActivity.maintain.setText("Mantiene: " + "N/A");
            CrudReadAppointmentActivity.lastAppointment.setText("Ultima Consulta: " + "                 dd/mm/yyyy");
        }

        CrudReadAppointmentActivity.listData.removeAll(CrudReadAppointmentActivity.listData);
    }


    /**
     * This method get data on JSON
     */
    private void getDataCrudReadActivity(){

        if (CrudReadAppointmentActivity.listData.size() == 1){

            CrudReadAppointmentActivity.avLeft.setText("Av Izquierdo: " + CrudReadAppointmentActivity.listData.get(0).getAvLeft());
            CrudReadAppointmentActivity.avRight.setText("Av Derecho: " + CrudReadAppointmentActivity.listData.get(0).getAvRigth());

        }

        if (CrudReadAppointmentActivity.listData.size() == 2){

            CrudReadAppointmentActivity.avLeft.setText("Av Izquierdo: " + CrudReadAppointmentActivity.listData.get(1).getAvLeft());
            CrudReadAppointmentActivity.avRight.setText("Av Derecho: " + CrudReadAppointmentActivity.listData.get(1).getAvLeft());

        }

        CrudReadAppointmentActivity.center.setText("Centra: " + CrudReadAppointmentActivity.listData.get(0).getCenter());
        CrudReadAppointmentActivity.sustain.setText("Sostiene: " + CrudReadAppointmentActivity.listData.get(0).getSustain());
        CrudReadAppointmentActivity.maintain.setText("Mantiene: " + CrudReadAppointmentActivity.listData.get(0).getMaintain());

        CrudReadAppointmentActivity.listData.removeAll(CrudReadAppointmentActivity.listData);

    }

    /**
     * This method save the diagnostic data in local db
     * @param diagnostic
     */
    public void saveDataDiagnostic (Diagnostic diagnostic){

        Log.d("printLog", "en HttpHandler");

        String patientData = diagnostic.getIdPatient() + "-" + diagnostic.getYears() + "-" + diagnostic.getSex();
        //String patientData = diagnostic.getIdPatient() + "-" + diagnostic.getYears() + "-" + "M";

        String avData = diagnostic.getAvRigth() + "-" + diagnostic.getAvLeft() + "-" + diagnostic.getCenter() + "-";
        avData = avData + diagnostic.getSustain() + "-" + diagnostic.getMaintain();

        String otherTestA = diagnostic.getCrhomaticOd() + "-" + diagnostic.getCrhomaticOi() + "-";
        otherTestA = otherTestA + diagnostic.getTonometriaOd() + "-" + diagnostic.getTonometriaOi();

        String otherTestB = diagnostic.getForia() + "-" + diagnostic.getEndoforia() + "-" + diagnostic.getEndoforia() + "-";
        otherTestB = otherTestB + diagnostic.getExoforia() + "-" + diagnostic.getOrtoforia() + "-" + diagnostic.getOrtotropia();
        otherTestB = otherTestB + diagnostic.getDvd() + "-" + diagnostic.getCaElevada();

        String test = diagnostic.getTypeTest() + "-" + diagnostic.getColaborate();
        String status = "N";

        String antecedentDad = diagnostic.getExtendDad();
        String antecedentMon = diagnostic.getExtendsMon();

        FormDataDbHelper formDataDbHelper = new FormDataDbHelper(ControlForService.context);
        SQLiteDatabase db = formDataDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        try{

            values.put(FormDataDbContract.FormDataEntry.PATIENTDATA, patientData);
            values.put(FormDataDbContract.FormDataEntry.AVDATA, avData);
            values.put(FormDataDbContract.FormDataEntry.OTHERTESTA, otherTestA);
            values.put(FormDataDbContract.FormDataEntry.OTHERTESTB, otherTestB);
            values.put(FormDataDbContract.FormDataEntry.TESTUSED, test);
            values.put(FormDataDbContract.FormDataEntry.ANTECEDENTDAD, antecedentDad);
            values.put(FormDataDbContract.FormDataEntry.ANTECEDENTMON, antecedentMon);
            values.put(FormDataDbContract.FormDataEntry.STATUS, status);

            db.insert(FormDataDbContract.FormDataEntry.TABLE_NAME, null, values);

        }catch (Exception e){

            e.printStackTrace();

        }finally {

            db.close();

        }
    }

    private void revisionDeguardado(){

        Cursor cursor = null;
        FormDataDbHelper formDataDbHelper = new FormDataDbHelper(ControlForService.context);
        SQLiteDatabase db = formDataDbHelper.getReadableDatabase();

        String query = " SELECT patientData, avData, otherTestA, otherTestB, testUsed,  status, antecedentDad, antecedentMon FROM " + FormDataDbContract.FormDataEntry.TABLE_NAME;

        try{

            cursor = db.rawQuery( query, null);

            if (cursor.moveToFirst()){
                do{

                    Log.d("pritnLog", "_____________________");
                    Log.d("pritnLog", cursor.getString(0));
                    Log.d("pritnLog", cursor.getString(1));
                    Log.d("pritnLog", cursor.getString(2));
                    Log.d("pritnLog", cursor.getString(3));
                    Log.d("pritnLog", cursor.getString(4));
                    Log.d("pritnLog", cursor.getString(5));
                    Log.d("pritnLog", cursor.getString(6));
                    Log.d("pritnLog", cursor.getString(7));
                    Log.d("pritnLog", "_____________________");

                }while(cursor.moveToNext());
            }

        }catch (Exception e){

        }finally {

            if (cursor != null)
                cursor.close();

            db.close();
        }



    }


}
