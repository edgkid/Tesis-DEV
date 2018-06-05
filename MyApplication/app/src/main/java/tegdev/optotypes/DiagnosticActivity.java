package tegdev.optotypes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiagnosticActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imagePhoto;

    TextView textDate;
    TextView textPatient;
    TextView textYears;
    static TextView textAVRE;
    static TextView textAVLE;
    static TextView textAVRP;
    static TextView textAVLP;
    static TextView textCenter;
    static TextView textSustain;
    static TextView textMaintain;

    Button comeBackButton;
    Bundle patientExtras;

    PatientsToday patientsToday = new PatientsToday();
    static public ArrayList<Diagnostic> listData = new ArrayList<Diagnostic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        imagePhoto = (ImageView) findViewById(R.id.diagnosticPhoto);
        textDate = (TextView) findViewById(R.id.diagnosticAppointment);
        textPatient = (TextView) findViewById(R.id.diagnosticPatient);
        textYears = (TextView) findViewById(R.id.diagnosticYear);
        textAVRE = (TextView) findViewById(R.id.diagnosticAvRigthE);
        textAVLE = (TextView) findViewById(R.id.diagnosticAvLeftE);
        textAVRP = (TextView) findViewById(R.id.diagnosticAvRigthP);
        textAVLP = (TextView) findViewById(R.id.diagnosticAvLeftP);
        textCenter = (TextView) findViewById(R.id.diagnosticCenter);
        textSustain = (TextView) findViewById(R.id.diagnosticSustain);
        textMaintain = (TextView) findViewById(R.id.diagnosticMaintain);
        comeBackButton = (Button) findViewById(R.id.diagnosticButton);
        comeBackButton.setOnClickListener(this);

        Intent intentData = getIntent();
        patientExtras = intentData.getExtras();


        if (patientExtras != null){

            patientsToday.setIdPatient(Integer.parseInt(patientExtras.getString("idPatient")));
            Bitmap photo = (Bitmap) patientExtras.get("photo");

            showData(photo);

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.diagnosticButton:
                Intent newActivity = new Intent(this, DashBoardActivity.class);
                startActivity(newActivity);
                break;
            case R.id.buttonLogout:
                //Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonUpdate:
                //Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * This method show the patient data in crud read appointment
     */
    public void showData(Bitmap photo){

        if (photo != null){
           imagePhoto.setImageBitmap(photo);
        }else{
            imagePhoto.setImageResource(R.drawable.usuario_icon);
        }
        fillData();

    }

    /**
     * This method fill data in activity
     */
    public void fillData (){

        int action = 1;

        RequestDiagnostic requestDiagnostic = new RequestDiagnostic( this);
        requestDiagnostic.requestAllDataDiagnostic(listData, String.valueOf(patientsToday.getIdPatient()), action);

    }



}
