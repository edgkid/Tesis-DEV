package tegdev.optotypes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class CrudReadAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listPatients;

    TextView names;
    TextView lastNames;
    TextView yearsOld;
    static TextView lastAppointment;

    static TextView avRight;
    static TextView avLeft;
    static TextView center;
    static TextView sustain;
    static TextView maintain;

    ImageView perfil;

    View separator;

    Context contextActivity;
    Patient patient = null;

    Button buttonLogOut;
    Button buttonUpdate;
    Button buttonDiagnostic;
    Button buttonReturnMenu;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    int action = 3;
    String idPatient = "";
    PatientsToday patientToday = null;
    static public ArrayList<Diagnostic> listData = new ArrayList<Diagnostic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_read_appointment);

        contextActivity = this;

        listPatients = (ListView) findViewById(R.id.listPatienCrudR);
        names = (TextView) findViewById(R.id.idCrudRTextNamesR);
        lastNames = (TextView) findViewById(R.id.idCrudRTextLastNamesR);
        yearsOld = (TextView) findViewById(R.id.idCrudRTextYears);
        lastAppointment = (TextView) findViewById(R.id.idCrudRLastAppointment);
        //nextAppointment = (TextView) findViewById(R.id.idCrudRNexttAppointment);
        avRight = (TextView) findViewById(R.id.idCrudAvRightEstimated);
        avLeft = (TextView) findViewById(R.id.idCrudAvLeftEstimated);
        center = (TextView) findViewById(R.id.idCrudCenterSubjective);
        sustain = (TextView) findViewById(R.id.idCrudSustainSubjective);
        maintain = (TextView) findViewById(R.id.idCrudMaintainSubjective);
        //description = (TextView) findViewById(R.id.idCrudDescription);
        //descriptionTitle = (TextView) findViewById(R.id.idDescriptionTitle);

        perfil = (ImageView) findViewById(R.id.idCrudRImagePeril);
        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDiagnostic = (Button) findViewById(R.id.buttonViewDiagnosticRead);
        buttonReturnMenu = (Button) findViewById(R.id.idbuttonReturnListMenu);

        separator = (View)findViewById(R.id.separatorM);

        buttonLogOut.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDiagnostic.setOnClickListener(this);
        buttonReturnMenu.setOnClickListener(this);

        names.setVisibility(View.INVISIBLE);
        lastNames.setVisibility(View.INVISIBLE);
        yearsOld.setVisibility(View.INVISIBLE);
        lastAppointment.setVisibility(View.INVISIBLE);
        //nextAppointment.setVisibility(View.INVISIBLE);
        avRight.setVisibility(View.INVISIBLE);
        avLeft.setVisibility(View.INVISIBLE);
        center.setVisibility(View.INVISIBLE);
        sustain.setVisibility(View.INVISIBLE);
        maintain.setVisibility(View.INVISIBLE);
        buttonDiagnostic.setVisibility(View.INVISIBLE);
        perfil.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);

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
                //logOutApp();
                break;
            case R.id.buttonUpdate:
                //loadListPatientsToday();
                break;
            case R.id.buttonViewDiagnosticRead:
                displayDiagnosticActivity();
                break;
            case R.id.idbuttonReturnListMenu:
                Intent dashBoard = new Intent (this, DashBoardActivity.class);
                dashBoard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashBoard);
                finish();
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
     * This metho allows select a patin on list
     */
    public void actionOnElement (){

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                patientToday = (PatientsToday)parent.getAdapter().getItem(position);
                idPatient = String.valueOf(patientToday.getIdPatient());
                action = 1;

                RequestDiagnostic requestDiagnostic = new RequestDiagnostic( contextActivity);
                requestDiagnostic.requestDataDiagnostic(listData, idPatient , action);
                showData(patientToday);

            }
        });

    }

    /**
     * This method show the patient data in crud read appointment
     */
    public void showData(PatientsToday patientsToday){

        String[] patient = patientsToday.getName().split(" ");

        if (patientsToday.getPhoto() != null)
            perfil.setImageBitmap(patientsToday.getPhoto());
        else
            perfil.setImageResource(R.drawable.usuario_icon);

        names.setText(patient[0] + " " + patient[1] );
        lastNames.setText(patient[2]+ " " + patient[3]);
        yearsOld.setText(patientsToday.getYearsOld() + " años");

        names.setVisibility(View.VISIBLE);
        lastNames.setVisibility(View.VISIBLE);
        yearsOld.setVisibility(View.VISIBLE);
        lastAppointment.setVisibility(View.VISIBLE);
        avRight.setVisibility(View.VISIBLE);
        avLeft.setVisibility(View.VISIBLE);
        center.setVisibility(View.VISIBLE);
        sustain.setVisibility(View.VISIBLE);
        maintain.setVisibility(View.VISIBLE);
        buttonDiagnostic.setVisibility(View.VISIBLE);
        perfil.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);

    }

    /**
     * This method display the diagnostic activity
     */
    public void displayDiagnosticActivity(){

        String value = "";
        try{

            value = avRight.getText().toString().split(" ")[2];

        }catch (Exception e){
            value = "";
            e.printStackTrace();
        }

        if (value.equals("N/A") || value.equals("")){
            alertDialog();
        }else{
            Intent newActivity = new Intent(this, DiagnosticActivity.class);
            newActivity.putExtra("idPatient", idPatient);
            newActivity.putExtra("yearsOld", patientToday.getYearsOld());
            newActivity.putExtra("patient", patientToday.getName());
            newActivity.putExtra("photo", patientToday.getPhoto());
            newActivity.putExtra("date", lastAppointment.getText().toString());
            startActivity(newActivity);
        }
    }

    /**
     * This method display a Dialog before dlete an  appointment
     */
    public void alertDialog(){

        String name = names.getText().toString() + " " + lastNames.getText().toString();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextActivity);
        alertDialog.setTitle("Resultado de Consulta");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Aun no Existen datos de Consulta para el paciente " + name)
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
