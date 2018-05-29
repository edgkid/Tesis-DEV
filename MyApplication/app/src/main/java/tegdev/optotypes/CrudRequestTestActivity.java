package tegdev.optotypes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class CrudRequestTestActivity extends AppCompatActivity implements View.OnClickListener/*, MessageDialog.MessageDialogListener*/ {

    ListView listPatients;
    ImageView test;
    Button buttonLogOut;
    Button buttonUpdate;
    Context contextActivity;

    int action = 4;
    int distanceByTest;
    PatientsToday patient = null;
    ArrayList<String> imagesTest = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_request_test);

        contextActivity = this;
        test = (ImageView) findViewById(R.id.idTestForPatient);
        listPatients = (ListView) findViewById(R.id.idListForRequesTest);

        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);

        /*test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                nextActivity();
            }
        });*/

        loadListPatientsToday();

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This metohd fill menu for Patient
     */
    public void loadListPatientsToday (){

        Patient patient = null;
        RequestPatient reuquestPatient = new RequestPatient("patients", this);
        reuquestPatient.getPatientActualAppointment(listPatients, patient, action);

        //actionOnElement();
    }

}
