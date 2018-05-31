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
    TextView lastAppointment;
    //TextView nextAppointment;
    TextView avRight;
    TextView avLeft;
    TextView center;
    TextView sustain;
    TextView maintain;
    //TextView descriptionTitle;
    //TextView description;

    ImageView perfil;

    View separator;

    Context contextActivity;
    Patient patient = null;

    Button buttonLogOut;
    Button buttonUpdate;
    Button buttonDiagnostic;

    int action = 3;
    String idPatient = "";
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

        separator = (View)findViewById(R.id.separatorM);

        buttonLogOut.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDiagnostic.setOnClickListener(this);

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
        //description.setVisibility(View.INVISIBLE);
        buttonDiagnostic.setVisibility(View.INVISIBLE);
        perfil.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);
        //descriptionTitle.setVisibility(View.INVISIBLE);

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

                Log.d("message","acción");
                PatientsToday patient = (PatientsToday)parent.getAdapter().getItem(position);
                idPatient = String.valueOf(patient.getIdPatient());
                action = 1;
                RequestDiagnostic requestDiagnostic = new RequestDiagnostic( contextActivity);
                requestDiagnostic.requestDataDiagnostic(listData, idPatient , action);
                showData(patient);

            }
        });

    }

    /**
     * This method show the patient data before modify it´s appointment
     */
    public void showData(PatientsToday patientsToday){

        //String idPatient = String.valueOf(patientsToday.getIdPatient());

        String[] patient = patientsToday.getName().split(" ");

        if (patientsToday.getPhoto() != null)
            perfil.setImageBitmap(patientsToday.getPhoto());
        else
            perfil.setImageResource(R.drawable.usuario_icon);

        names.setText(patient[0] + " " + patient[1]);
        lastNames.setText(patient[2] + " " + patient[3]);
        yearsOld.setText(patientsToday.getYearsOld() + " años");


        Log.d("message", "size: " + String.valueOf(listData.size()));

        fillData();


        names.setVisibility(View.VISIBLE);
        lastNames.setVisibility(View.VISIBLE);
        yearsOld.setVisibility(View.VISIBLE);
        lastAppointment.setVisibility(View.VISIBLE);
        //nextAppointment.setVisibility(View.VISIBLE);
        avRight.setVisibility(View.VISIBLE);
        avLeft.setVisibility(View.VISIBLE);
        center.setVisibility(View.VISIBLE);
        sustain.setVisibility(View.VISIBLE);
        maintain.setVisibility(View.VISIBLE);
        //description.setVisibility(View.VISIBLE);
        buttonDiagnostic.setVisibility(View.VISIBLE);
        perfil.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);
        //descriptionTitle.setVisibility(View.VISIBLE);

        listData.removeAll(listData);

    }

    /**
     * This method fill data in activity
     */
    public void fillData(){

        Diagnostic diagnostic = null;

        if(listData.size() > 0){
            for(int x=0;x<listData.size();x++) {
                if(listData.get(x).getTypeTest().equals("Test Personalizado")){
                    diagnostic = new Diagnostic();
                    diagnostic = listData.get(x);
                    break;
                }
            }

            if (diagnostic == null && listData.get(0) != null){
                diagnostic = new Diagnostic();
                diagnostic = listData.get(0);
            }

            avRight.setText("Av Derecho: " + diagnostic.getAvRigth());
            avLeft.setText("Av Izquierdo: " + diagnostic.getAvLeft());
            center.setText("Centra: " + diagnostic.getCenter());
            sustain.setText("Sostiene: " + diagnostic.getSustain());
            maintain.setText("Mantiene: " + diagnostic.getMaintain());
            lastAppointment.setText("Ultima Consulta: " + diagnostic.getDate());
        }else{

            avRight.setText("Av Derecho: " + "N/A");
            avLeft.setText("Av Izquierdo: " +"N/A");
            center.setText("Centra: " + "N/A");
            sustain.setText("Sostiene: " + "N/A");
            maintain.setText("Mantiene: " + "N/A");
            lastAppointment.setText("Ultima Consulta: " + "                 dd/mm/yyyy");

        }
    }

    /**
     * This method display the diagnostic activity
     */
    public void displayDiagnosticActivity(){

        Log.d("message", avLeft.getText().toString());

        if (avRight.getText().toString().equals("Av Derecho: N/A")){
            alertDialog();
        }else{
            Intent newActivity = new Intent(this, DiagnosticActivity.class);
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

}
