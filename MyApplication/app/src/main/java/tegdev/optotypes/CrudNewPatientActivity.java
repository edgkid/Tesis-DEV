package tegdev.optotypes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class CrudNewPatientActivity extends AppCompatActivity implements ImageView.OnClickListener{

    ImageView imagePatient;

    EditText firstName;
    EditText secondName;
    EditText lastName;
    EditText maidenName;

    Button buttonAcept;
    Button buttonLogOut;

    DatePicker calendar;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    Spinner genderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_new_patient);

        firstName = (EditText) findViewById(R.id.idFirstName);
        secondName = (EditText) findViewById(R.id.idSecondName);
        lastName = (EditText) findViewById(R.id.idFirstLastName);
        maidenName = (EditText) findViewById(R.id.idSecondLastName);
        imagePatient = (ImageView) findViewById(R.id.idImagePatient);
        buttonAcept = (Button) findViewById(R.id.idCrudDButtonAcepted);
        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        calendar = (DatePicker) findViewById(R.id.idDateBrithday);
        genderList = (Spinner) findViewById(R.id.dGenderPatient);

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

        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.text_spiner_resource);
        genderList.setAdapter(adapterGender);

        buttonAcept.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                break;
            case R.id.idCrudDButtonAcepted:
                saveNewPatient();
                break;

        }

    }

    private void saveNewPatient() {

        String date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth()+1)
                + "/" + String.valueOf(calendar.getYear());

        Log.d("message", firstName.getText().toString());
        Log.d("message", secondName.getText().toString());
        Log.d("message", lastName.getText().toString());
        Log.d("message", maidenName.getText().toString());
        Log.d("message", genderList.getSelectedItem().toString());
        Log.d("message", date);


    }


}
