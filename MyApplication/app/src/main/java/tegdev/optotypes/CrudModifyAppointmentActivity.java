package tegdev.optotypes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CrudModifyAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    Context contextActivity;
    Patient patient;

    ListView listPatientsM;
    TextView textNames;
    TextView textLastNames;
    TextView textyears;
    TextView lastDate;
    TextView newDate;
    Button updated;
    Button logOut;
    Button buttonModify;
    DatePicker calendar;
    ImageView perfil;

    View line;
    int action = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_modify_appointment);

        contextActivity = this;
        listPatientsM = (ListView) findViewById(R.id.listPatienCrudM);
        perfil = (ImageView) findViewById(R.id.idCrudImagePerfilM);
        textNames = (TextView) findViewById(R.id.idCrudTextNamesM);
        textLastNames = (TextView) findViewById(R.id.idCrudTextLastNamesM);
        textyears = (TextView) findViewById(R.id.idCrudTextYearsM);
        lastDate = (TextView) findViewById(R.id.idCrudLastAppointmentM);
        newDate = (TextView) findViewById(R.id.idCrudNewAppointmentM);
        calendar = (DatePicker) findViewById(R.id.idCrudDatePieckerM);
        updated = (Button) findViewById(R.id.buttonUpdate);
        logOut = (Button) findViewById(R.id.buttonLogout);
        buttonModify = (Button) findViewById(R.id.idCrudButtonAceptedM);
        line = (View) findViewById(R.id.separatorM);

        perfil.setVisibility(View.INVISIBLE);
        textNames.setVisibility(View.INVISIBLE);
        textLastNames.setVisibility(View.INVISIBLE);
        textyears.setVisibility(View.INVISIBLE);
        lastDate.setVisibility(View.INVISIBLE);
        newDate.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        updated.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);
        buttonModify.setVisibility(View.INVISIBLE);

        updated.setOnClickListener(this);
        logOut.setOnClickListener(this);
        buttonModify.setOnClickListener(this);

        loadListPatientsToday();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                logOutApp();
                break;
            case R.id.idCrudButtonAceptedM:
                action = 1;
                alertDialog();
                break;
            case R.id.buttonUpdate:
                break;
        }

    }

    /**
     * This metohd fill menu for Patient
     */
    public void loadListPatientsToday (){

        RequestPatient reuquestPatient = new RequestPatient("patients", this);
        reuquestPatient.getPatientActualAppointment(listPatientsM, patient, action);

        actionOnElement();
    }

    /**
     * This method excute actions on list patients
     */
    public void actionOnElement (){

        listPatientsM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        if (patientsToday.getPhoto() != null)
            perfil.setImageBitmap(patientsToday.getPhoto());
        else
            perfil.setImageResource(R.drawable.usuario_icon);

        textNames.setText(patient[0] + " " + patient[1]);
        textLastNames.setText(patient[2] + " " + patient[3]);
        textyears.setText(patientsToday.getYearsOld() + " años");

        perfil.setVisibility(View.VISIBLE);
        textNames.setVisibility(View.VISIBLE);
        textLastNames.setVisibility(View.VISIBLE);
        textyears.setVisibility(View.VISIBLE);
        lastDate.setVisibility(View.VISIBLE);
        newDate.setVisibility(View.VISIBLE);
        calendar.setVisibility(View.VISIBLE);
        updated.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        buttonModify.setVisibility(View.VISIBLE);

    }

    /**
     * This method display a dialog for modify appointment
     */
    public void alertDialog (){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextActivity);
        alertDialog.setTitle("Se Modificara una Cita");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Seguro que desea mover la cita para otro día")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modifyAppointment();
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
     * This method send request for modify appointment
     */
    public void modifyAppointment(){

        String date = "";
        date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth()+1) + "/" + String.valueOf(calendar.getYear());
        newDate.setText(date);

        RequestAppointment requestAppointment = new RequestAppointment("appointment",this);
        requestAppointment.requestActionOnActualAppointment(patient, action, date);

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
