package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    Button logOut;
    Button update;
    ImageView imageUser;

    ListView listViewMenu;
    Context contextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        contextActivity = this;
        logOut = (Button) findViewById(R.id.buttonLogout);
        update = (Button) findViewById(R.id.buttonUpdate);
        imageUser = (ImageView) findViewById(R.id.imageViewLoginUser);
        logOut.setOnClickListener((View.OnClickListener) contextActivity);
        update.setOnClickListener((View.OnClickListener) contextActivity);
        listViewMenu = (ListView) findViewById(R.id.listViewDashBoardMenu);

        loadMenu();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                logOutDashBoard();
                break;
            case R.id.buttonUpdate:
                loadMenu();
                break;
        }

    }

    /**
     * This metohd send request for close a sesion
     */
    public void logOutDashBoard(){

        SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        CloseAndRefresh closeApp = new CloseAndRefresh(contextActivity);
        closeApp.logOutApp(loginPreferences);
    }

    /**
     * This method show a menu for doctor or a list patients for other users
     */
    public void loadMenu(){

        SharedPreferences preferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);

        if (preferences.getString("roll", "defaultroll").equals("Doctor")){
            loadListMenuDoctor();
        }else if(preferences.getString("roll", "defaultroll").equals("Paciente Infantil")){
            loadListPatientsToday();
        }

        // aqui cargo los datos de ultimos resultados sobre le día
        //loadLastAvResultByToDay();

    }

    /**
     * This method show a menu for Doctor
     */
    public void loadListMenuDoctor(){

        ItemMenuDoctor itemsData[] = new ItemMenuDoctor[]{
                new ItemMenuDoctor(R.drawable.icon_appoinment, "Nueva Consulta"),
                new ItemMenuDoctor(R.drawable.icon_modify, "Modificar Consulta"),
                new ItemMenuDoctor(R.drawable.icon_garbage, "Eliminar Consulta"),
                new ItemMenuDoctor(R.drawable.icon_calendar, "Consulta del Día"),
                new ItemMenuDoctor(R.drawable.icon_test, "Test de Consulta")
        };

        ItemMenuDoctorAdapter itemMenuDoctorAdapter = new ItemMenuDoctorAdapter(this, R.layout.listview_item_doctor_row, itemsData);
        listViewMenu.setAdapter(itemMenuDoctorAdapter);

        /// aqui inicializo los sintomas y defectos
        /*RequestSignalDefect requestSignalDefect = new RequestSignalDefect("signalDefect",contextActivity);
        requestSignalDefect.findSignalDefect();

        RequestAntecedentDefect requestAntecedentDefect = new RequestAntecedentDefect("antecedent",contextActivity);
        requestAntecedentDefect.findAntecedentDefect();*/

        callActivitiesByDoctor();

    }

    /**
     * This method show a lsit Patient
     */
    public void loadListPatientsToday (){

        Patient patient = new Patient();
        RequestPatient reuquestPatient = new RequestPatient("patients", this);
        reuquestPatient.getPatientActualAppointment(listViewMenu, patient, 0);

        callInteractionActivity();

    }

    /**
     * this metohd fill a list type menu for doctor
     */
    public void callActivitiesByDoctor (){

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = null;
                switch (position){

                    case 0:
                        newActivity = new Intent(contextActivity, CrudSaveAppointmentActivity.class);
                        break;
                    case 1:
                        newActivity = new Intent(contextActivity,CrudModifyAppointmentActivity.class);
                        break;
                    case 2:
                        newActivity = new Intent(contextActivity, CrudDeleteAppointmentActivity.class);
                        break;
                    case 3:
                        //newActivity = new Intent(contextActivity, CrudReadAppointmentActivity.class);
                        break;
                    case 4:
                        //newActivity = new Intent(contextActivity, CrudRequestTestActivity.class);
                        break;
                }

                if (newActivity != null)
                    startActivity(newActivity);
            }
        });


    }

    /**
     * This metohd fill a list for Patients
     */
    public void callInteractionActivity (){

        final Intent interactionActivity = new Intent(this, InteractionActivity.class);

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PatientsToday patient = (PatientsToday)parent.getAdapter().getItem(position);
                callActivityByPatient(patient, interactionActivity);

            }
        });

    }

    /**
     * This method call a new activity for interaction patient
     * @param patient
     * @param activity
     */
    public void callActivityByPatient (PatientsToday patient, Intent activity){


        RequestInteraction interaction = new RequestInteraction(this.contextActivity);

        if(interaction.validateInteraction(String.valueOf(patient.getIdPatient()))){

            Intent resultInteraction = new Intent(this, ResultInteractionActivity.class);
            resultInteraction.putExtra("IdPatient", String.valueOf(patient.getIdPatient()));
            resultInteraction.putExtra("patient", patient.getName());
            resultInteraction.putExtra("patientYear", patient.getYearsOld());
            resultInteraction.putExtra("photo", patient.getPhoto());
            startActivity(resultInteraction);
        } else{
            activity.putExtra("IdPatient", String.valueOf(patient.getIdPatient()));
            activity.putExtra("patient", patient.getName());
            activity.putExtra("patientYear", patient.getYearsOld());
            activity.putExtra("photo", patient.getPhoto());
            startActivity(activity);
        }

    }

}
