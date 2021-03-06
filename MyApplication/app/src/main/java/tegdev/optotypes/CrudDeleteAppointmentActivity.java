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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CrudDeleteAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listPatients;

    View line;
    ImageView perfil;
    Button actionDelete;
    Button buttonLogOut;
    Button buttonUpdate;
    Button buttonReturnListMenu;

    TextView names;
    TextView lastNames;
    TextView yearsOld;
    TextView lastAppointmentDate;
    TextView nextAppointmentDate;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;


    Patient patient;
    Context contextActivity;

    int action = 2;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_delete_appointment);

        contextActivity = this;
        listPatients = (ListView) findViewById(R.id.listPatienCrudD);

        perfil = (ImageView) findViewById(R.id.idCrudDImagePeril);
        actionDelete = (Button) findViewById(R.id.idCrudDButtonAcepted);
        names = (TextView) findViewById(R.id.idCrudDTextNames);
        lastNames = (TextView) findViewById(R.id.idCrudDTextLastNames);
        yearsOld = (TextView) findViewById(R.id.idCrudDTextYears);
        lastAppointmentDate = (TextView) findViewById(R.id.idCrudDLastAppointment);
        nextAppointmentDate = (TextView) findViewById(R.id.idCrudDNexttAppointment);

        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonReturnListMenu = (Button) findViewById(R.id.idbuttonReturnListMenu);
        line = (View) findViewById(R.id.separatorD);

        perfil.setVisibility(View.INVISIBLE);
        actionDelete.setVisibility(View.INVISIBLE);
        names.setVisibility(View.INVISIBLE);
        lastNames.setVisibility(View.INVISIBLE);
        yearsOld.setVisibility(View.INVISIBLE);
        lastAppointmentDate.setVisibility(View.INVISIBLE);
        nextAppointmentDate.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);

        actionDelete.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonReturnListMenu.setOnClickListener(this);

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

        loadListPatientsToday();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                logOutApp();
                break;
            case R.id.buttonUpdate:
                break;
            case R.id.idCrudDButtonAcepted:
                action = 2;
                alertDialog();
                break;
            case R.id.idbuttonReturnListMenu:

                Intent dashBoard = new Intent(this, DashBoardActivity.class);
                dashBoard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashBoard);
                startActivity(dashBoard);

                break;
        }

    }

    /**
     * This metohd fill menu for Patient
     */
    public void loadListPatientsToday (){

        RequestPatient reuquestPatient = new RequestPatient("patients", this);
        reuquestPatient.getPatientActualAppointment(listPatients, patient, action);

        actionOnElement();
    }

    /**
     * This method excute actions on list patients
     */
    public void actionOnElement (){

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatientsToday patientsToday = (PatientsToday) parent.getAdapter().getItem(position);

                patient = new Patient();
                patient.setIdPatient(String.valueOf(((PatientsToday) parent.getAdapter().getItem(position)).getIdPatient()));

                showData(patientsToday);
            }
        });

    }

    /**
     * This method show the patient data before modify it´s appointment
     */
    public void showData(PatientsToday patientsToday){

        String[] patient = patientsToday.getName().split(" ");
        String[] years = patientsToday.getYearsOld().split(" ");

        if (patientsToday.getPhoto() != null)
            perfil.setImageBitmap(patientsToday.getPhoto());
        else
            perfil.setImageResource(R.drawable.usuario_icon);


        names.setText(patient[0] + " " + patient[1]);
        lastNames.setText(patient[2] + " " + patient[3]);
        yearsOld.setText("Edad: " + years[1] + " años");

        perfil.setVisibility(View.VISIBLE);
        names.setVisibility(View.VISIBLE);
        lastNames.setVisibility(View.VISIBLE);
        yearsOld.setVisibility(View.VISIBLE);
        lastAppointmentDate.setVisibility(View.VISIBLE);
        nextAppointmentDate.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        actionDelete.setVisibility(View.VISIBLE);
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
     * This method display a Dialog before dlete an  appointment
     */
    public void alertDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextActivity);
        alertDialog.setTitle("Se Eliminara una Cita");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("¿Seguro que desea eliminar la cita del paciente seleccionado?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAppointment();
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
     * This method send request delete appointment
     */
    public void deleteAppointment(){

        RequestAppointment requestAppointment = new RequestAppointment("appointment",this);
        requestAppointment.requestActionOnActualAppointment(patient, action, null);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
