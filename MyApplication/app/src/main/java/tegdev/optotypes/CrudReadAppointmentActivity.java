package tegdev.optotypes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CrudReadAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listPatients;

    TextView names;
    TextView lastNames;
    TextView yearsOld;
    TextView lastAppointment;
    TextView nextAppointment;
    TextView avRight;
    TextView avLeft;
    TextView center;
    TextView sustain;
    TextView maintain;
    TextView descriptionTitle;
    TextView description;

    ImageView perfil;

    View separator;

    Context contextActivity;
    Patient patient = null;

    Button buttonLogOut;
    Button buttonUpdate;
    Button buttonDiagnostic;

    int action = 3;
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
        nextAppointment = (TextView) findViewById(R.id.idCrudRNexttAppointment);
        avRight = (TextView) findViewById(R.id.idCrudAvRightEstimated);
        avLeft = (TextView) findViewById(R.id.idCrudAvLeftEstimated);
        center = (TextView) findViewById(R.id.idCrudCenterSubjective);
        sustain = (TextView) findViewById(R.id.idCrudSustainSubjective);
        maintain = (TextView) findViewById(R.id.idCrudMaintainSubjective);
        description = (TextView) findViewById(R.id.idCrudDescription);
        descriptionTitle = (TextView) findViewById(R.id.idDescriptionTitle);

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
        nextAppointment.setVisibility(View.INVISIBLE);
        avRight.setVisibility(View.INVISIBLE);
        avLeft.setVisibility(View.INVISIBLE);
        center.setVisibility(View.INVISIBLE);
        sustain.setVisibility(View.INVISIBLE);
        maintain.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        buttonDiagnostic.setVisibility(View.INVISIBLE);
        perfil.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);
        descriptionTitle.setVisibility(View.INVISIBLE);

        loadListPatientsToday();

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This metohd fill menu for Patient
     */
    public void loadListPatientsToday (){

        RequestPatient reuquestPatient = new RequestPatient("patients", this);
        reuquestPatient.getPatientActualAppointment(listPatients, patient, action);

        actionOnElement();
    }


    public void actionOnElement (){

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                PatientsToday patient = (PatientsToday)parent.getAdapter().getItem(position);
                showData(patient);

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

        names.setText(patient[0] + " " + patient[1]);
        lastNames.setText(patient[2] + " " + patient[3]);
        yearsOld.setText(patientsToday.getYearsOld() + " años");

        names.setVisibility(View.VISIBLE);
        lastNames.setVisibility(View.VISIBLE);
        yearsOld.setVisibility(View.VISIBLE);
        lastAppointment.setVisibility(View.VISIBLE);
        nextAppointment.setVisibility(View.VISIBLE);
        avRight.setVisibility(View.VISIBLE);
        avLeft.setVisibility(View.VISIBLE);
        center.setVisibility(View.VISIBLE);
        sustain.setVisibility(View.VISIBLE);
        maintain.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        buttonDiagnostic.setVisibility(View.VISIBLE);
        perfil.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);
        descriptionTitle.setVisibility(View.VISIBLE);

    }
}
