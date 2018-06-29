package tegdev.optotypes;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrudSaveAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    Context contextActivity;

    ListView listPatientsC;

    TextView textNames;
    TextView textLastNames;
    TextView textyears;
    TextView newDate;
    EditText textShared;

    Button save;
    ImageButton shared;
    Button buttonLogOut;
    Button buttonReturnMenu;

    DatePicker calendar;
    ImageView perfil;
    View line;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    boolean saveValue = false;
    int action = 0;
    Patient patient = new Patient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_save_appointment);

        contextActivity = this;
        listPatientsC = (ListView) findViewById(R.id.listPatienCrudC);
        perfil = (ImageView) findViewById(R.id.idCrudImagePerfilC);
        textNames = (TextView) findViewById(R.id.idCrudTextNamesC);
        textLastNames = (TextView) findViewById(R.id.idCrudTextLastNamesC);
        textyears = (TextView) findViewById(R.id.idCrudTextYearsC);
        newDate = (TextView) findViewById(R.id.idCrudNewAppointmentC);
        calendar = (DatePicker) findViewById(R.id.idCrudDatePieckerC);
        save= (Button) findViewById(R.id.idCrudButtonAceptedC);
        line = (View) findViewById(R.id.separatorC);

        shared = (ImageButton) findViewById(R.id.idCrudButtonShareC);
        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonLogOut.setOnClickListener(this);
        buttonReturnMenu = (Button) findViewById(R.id.idbuttonReturnListMenu);
        textShared = (EditText) findViewById(R.id.idCrudShareSaveC);


        perfil.setVisibility(View.INVISIBLE);
        textNames.setVisibility(View.INVISIBLE);
        textLastNames.setVisibility(View.INVISIBLE);
        textyears.setVisibility(View.INVISIBLE);
        newDate.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);

        save.setOnClickListener(this);
        shared.setOnClickListener(this);
        buttonReturnMenu.setOnClickListener(this);

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


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.idCrudButtonShareC:
                action = 3;
                patient.setName(textShared.getText().toString());
                RequestPatient requestPatient = new RequestPatient("patients",contextActivity);
                requestPatient.getSomePatientForNewAppointment(listPatientsC, patient, action);
                actionOnElement();
                break;
            case R.id.idCrudButtonAceptedC:
                action = 0;
                alertDialog();
                break;
            case R.id.buttonLogout:
                logOutApp();
                break;
            case R.id.idbuttonReturnListMenu:
                Intent dashboard = new Intent(this, DashBoardActivity.class);
                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashboard);
                finish();
                break;
        }

        if (saveValue)
            Toast.makeText(contextActivity, "registro procesado con Exito", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(contextActivity, "No se pudo procesar el registro", Toast.LENGTH_SHORT).show();

    }

    /**
     * This metohd send request for close a sesion
     */
    public void logOutApp (){

        SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        CloseAndRefresh closeApp = new CloseAndRefresh(this);
        closeApp.logOutApp(loginPreferences);
    }


    /**
     * This method excecute a Onclieck metodo in List
     */
    public void actionOnElement (){

        listPatientsC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PatientsToday patientsToday = (PatientsToday) parent.getAdapter().getItem(position);
                showData(patientsToday);
            }
        });

    }

    /**
     * This method show data by patient selected
     * @param patientOnList
     */
    public void showData(PatientsToday patientOnList){

        String[] name = patientOnList.getName().split(" ");
        String[] years = patientOnList.getYearsOld().split(" ");


        if (patientOnList.getPhoto() != null)
            perfil.setImageBitmap(patientOnList.getPhoto());
        else
            perfil.setImageResource(R.drawable.usuario_icon);

        patient.setName(name[0]);
        patient.setMiddleName(name[1]);
        patient.setLastName(name[2]);
        patient.setMaidenName(name[3]);
        patient.setYearsOld(years[1]);
        patient.setIdPatient(String.valueOf(patientOnList.getIdPatient()));

        textNames.setText(patient.getName() + " " + patient.getMiddleName());
        textLastNames.setText(patient.getLastName() + " " + patient.getMaidenName());
        textyears.setText("Edad: " + patient.getYearsOld());

        perfil.setVisibility(View.VISIBLE);
        textNames.setVisibility(View.VISIBLE);
        textLastNames.setVisibility(View.VISIBLE);
        textyears.setVisibility(View.VISIBLE);
        newDate.setVisibility(View.VISIBLE);
        calendar.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);

    }

    /**
     * This method display a Dialog before save a new appointment date
     */
    public void alertDialog (){

        String date = "";
        date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth()+1) + "/" + String.valueOf(calendar.getYear());
        newDate.setText("Nueva Fecha de Consulta: " + date);
        String message = "Seguro que desea generar una cita para el paciente " +
                patient.getName() + " " + patient.getLastName() +
                " El dia "+ newDate.getText().toString();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextActivity);
        alertDialog.setTitle("Nueva Cita Medica");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processNewDate();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    /**
     * This method send a new appointment date
     */
    public void processNewDate (){

        String date = "";

        date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth()+1) + "/" + String.valueOf(calendar.getYear());
        newDate.setText("Nueva Fecha de Consulta: " + date);

        RequestAppointment requestAppointment = new RequestAppointment("appointment",contextActivity);
        requestAppointment.requestActionOnActualAppointment(patient, action, date);

    }


}
