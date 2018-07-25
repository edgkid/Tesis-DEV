package tegdev.optotypes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
 * Created by Edgar on 27/05/2018.
 */

public class HttpHandlerAppointment {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerAppointment(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    /**
     * This metohd conect with webervice for save a new appointment
     * @param patient
     * @param option
     * @param date
     * @return
     */
    public boolean sendRequest (Patient patient, int option,String date){

        URL url = null;
        boolean value = false;
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
            connection.setUseCaches(false);

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonData(listParam, patient.getIdPatient(), option, date);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            responseCode = connection.getResponseCode();


            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();
                value = true;
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

        return value;

    }


    /**
     * This method initialize coonection with webservice
     * @param ctx
     * @param patient
     * @param option
     * @param date
     */
    public void connectToResource (final CrudSaveAppointmentActivity ctx, final Patient patient, final int option, final String date){

        /*Thread tr = new Thread(){
            @Override
            public void run() {
                if (sendRequest(patient,option, date)){
                    CrudMessageDialog.positive = true;
                }else{
                    CrudMessageDialog.positive = false;
                }

            }
        };
        tr.start();*/

    }

    /**
     * This method initialize coonection with webservice
     * @param ctx
     * @param patient
     * @param option
     * @param date
     */
    public void connectToResource (final CrudModifyAppointmentActivity ctx, final Patient patient, final int option, final String date){

        Thread tr = new Thread(){
            @Override
            public void run() {

                final boolean value = sendRequest(patient,option,date);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        CrudMessageDialog messageDialog = new CrudMessageDialog(ctx);
                        messageDialog.setTitle("Nuevo registro: " + patient.getLastName());

                        if (value){
                            messageDialog.setMessage("Exito al Modificar Registro");
                        }else{
                            messageDialog.setMessage("Imposible Modificar Registro");
                        }

                        messageDialog.alertDialog();
                    }
                });

            }
        };
        tr.start();

    }

    /**
     * This method initialize coonection with webservice
     * @param ctx
     * @param patient
     * @param option
     */
    public void connectToResource (final CrudDeleteAppointmentActivity ctx, final Patient patient, final int option){

        Thread tr = new Thread(){
            @Override
            public void run() {

                //sendRequestPOST(patient, action);
                final boolean value = sendRequest(patient,option, "25/07/2018");
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        CrudMessageDialog messageDialog = new CrudMessageDialog(ctx);
                        messageDialog.setTitle("Nuevo registro: " + patient.getLastName());

                        if (value){
                            messageDialog.setMessage("Exito al Eliminar Registro");
                        }else{
                            messageDialog.setMessage("Imposible Eliminar Registro");
                        }

                        messageDialog.alertDialog();
                    }
                });

            }
        };
        tr.start();


    }


    /**
     * This method generate a JSON documnet for save new Appointment by patient
     * @param listParam
     * @param idPatient
     * @param option
     * @param date
     */
    public void getJsonData (JSONArray  listParam, String idPatient, int option, String date ){

        JSONObject jsonParam = new JSONObject();

        try {
            jsonParam.put("idPatient", idPatient);
            jsonParam.put("status", "N");
            jsonParam.put("action",option);
            if (option == 1 || option == 0)
                jsonParam.put("appointmentDate", date);
            listParam.put(jsonParam);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
