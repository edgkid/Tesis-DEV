package tegdev.optotypes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrudRequestTestActivity extends AppCompatActivity implements View.OnClickListener, MessageDialog.MessageDialogListener {

    ListView listPatients;
    ImageView test;
    Button buttonLogOut;
    Button buttonUpdate;
    Context contextActivity;

    int action = 4;
    int distanceByTest;
    PatientsToday patient = null;
    public static ArrayList<String> imagesTest = new ArrayList<String>();

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_request_test);

        contextActivity = this;
        imagesTest.remove(imagesTest);

        test = (ImageView) findViewById(R.id.idTestForPatient);
        listPatients = (ListView) findViewById(R.id.idListForRequesTest);

        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);

        ipWbeService = (TextView) findViewById(R.id.ipWebService);
        ipClient = (TextView) findViewById(R.id.ipProjector);
        port = (TextView) findViewById(R.id.portProjector);

        try{
            ipWbeService.setText(ipWbeService.getText().toString() + ConfgConnect.getIpWebService());
            ipClient.setText(ipClient.getText().toString() + ConfgConnect.getIpShowTest());
            port.setText(port.getText().toString() + ConfgConnect.getPortConecction());
        }catch(Exception e){
            ipWbeService.setText(ipWbeService.getText().toString() + "no hay conección");
            ipClient.setText(ipClient.getText().toString() + "no hay conexxión");
            port.setText(port.getText().toString() + "no hay conexión");
        }

        test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                nextActivity();
            }
        });

        loadListPatientsToday();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                break;
            case  R.id.buttonUpdate:
                logOutApp();
                break;
        }

    }

    /**
     * This metohd fill menu for Patient
     */
    public void loadListPatientsToday (){

        Patient patient = null;
        RequestPatient reuquestPatient = new RequestPatient("patients", this);
        reuquestPatient.getPatientActualAppointment(listPatients, patient, action);

        actionOnElement();
    }

    /**
     * This method escute an action on list patient
     */
    public void actionOnElement (){

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                patient = (PatientsToday) parent.getAdapter().getItem(position);
                imagesTest.removeAll(imagesTest);
                test.setImageResource(R.drawable.carta);
                setDistanceByTest();

            }
        });

    }

    /**
     * This method display a dialog to get a distance
     */
    public void setDistanceByTest(){
        MessageDialog messageDialog = new MessageDialog();
        messageDialog.setTitleMessage("Distancia de Test");
        messageDialog.show(getSupportFragmentManager(),"Message Dialog");
    }

    @Override
    public void applyData(String data) {

        if (Integer.parseInt(data) >= 2 && Integer.parseInt(data) <= 6){
            distanceByTest = Integer.parseInt(data);
            requestTest();
        }else{
            alertDialog();
        }

    }

    /**
     * This method send request get to summary test
     */
    public void requestTest (){
        RequestMedicalTest requestMedicalTest = new RequestMedicalTest("test",contextActivity);
        requestMedicalTest.requestTest(patient,distanceByTest, action, test, imagesTest);
        displayWaitDialog();
    }

    /**
     * This method display a dialog by validate
     */
    public void alertDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextActivity);
        alertDialog.setTitle("Distancia Errada");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Verifique que ingresa como dato 2 o 6 m")
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

    /**
     * This method display dialog to wait generate test
     */
    public void displayWaitDialog(){

        final ProgressDialog progressDialog = new ProgressDialog(contextActivity);
        progressDialog.setTitle("Generando Carta Optometrica");
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Se estan procesando los optotypos que seran utilizado en el Examen. Por favor espere");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (progressDialog.getProgress() <= progressDialog.getMax()){
                        Thread.sleep(400);
                        progressDialog.incrementProgressBy(3);
                        if (progressDialog.getProgress() == progressDialog.getMax()){
                            progressDialog.dismiss();
                        }
                    }
                }catch(Exception e){

                }
            }
        }).start();

        progressDialog.show();
    }

    /**
     * This method create the test form activity
     */
    public void nextActivity (){

        if (imagesTest.size() != 0){
            Intent testControlActivity = new Intent (this, TestControlActivity.class);
            testControlActivity.putExtra("idPatient", String.valueOf( patient.getIdPatient()));
            testControlActivity.putExtra("patient", patient.getName());
            testControlActivity.putExtra("yearsOld", patient.getYearsOld());
            testControlActivity.putExtra("photo", patient.getPhoto());
            startActivity(testControlActivity);
        }


    }

    /**
     * This metohd send request for close a sesion
     */
    public void logOutApp (){

        SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        CloseAndRefresh closeApp = new CloseAndRefresh(this);
        closeApp.logOutApp(loginPreferences);
    }



}
