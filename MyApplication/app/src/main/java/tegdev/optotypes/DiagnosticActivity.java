package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DiagnosticActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView photoPatient;

    TextView appointmentDate;
    TextView patientName;
    TextView yearsOldPatient;

    TextView typeTest;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    TextView avResult;
    TextView otherTestA;
    TextView otherTestB;
    TextView otherTestC;
    TextView otherTestD;
    TextView description;

    Button menuList;
    Button logOut;

    Bitmap photo = null;
    Patient patient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        photoPatient = (ImageView) findViewById(R.id.idImagePhotoPatient);

        appointmentDate = (TextView) findViewById(R.id.idTextDate);
        patientName = (TextView) findViewById(R.id.idTextPatient);
        yearsOldPatient = (TextView) findViewById(R.id.idTextYears);
        typeTest = (TextView) findViewById(R.id.idTypeTest);
        ipWbeService = (TextView) findViewById(R.id.ipWebService);
        ipClient = (TextView) findViewById(R.id.ipProjector);
        port = (TextView) findViewById(R.id.portProjector);
        avResult = (TextView) findViewById(R.id.idTextAvResult);
        otherTestA = (TextView) findViewById(R.id.idOtherTestA);
        otherTestB = (TextView) findViewById(R.id.idOtherTestB);
        otherTestC = (TextView) findViewById(R.id.idOtherTestC);
        otherTestD = (TextView) findViewById(R.id.idOtherTestD);
        description = (TextView) findViewById(R.id.idDescription);

        logOut = (Button) findViewById(R.id.buttonLogout);
        menuList = (Button) findViewById(R.id.idbuttonReturnListMenu);

        logOut.setOnClickListener(this);
        menuList.setOnClickListener(this);

        try{
            ipWbeService.setText(ipWbeService.getText().toString() + ConfgConnect.getIpWebService());
            ipClient.setText(ipClient.getText().toString() + ConfgConnect.getIpShowTest());
            port.setText(port.getText().toString() + ConfgConnect.getPortConecction());
        }catch(Exception e){
            ipWbeService.setText(ipWbeService.getText().toString() + "no hay conección");
            ipClient.setText(ipClient.getText().toString() + "no hay conexxión");
            port.setText(port.getText().toString() + "no hay conexión");
        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null){
            patient = new Patient();
            photo = (Bitmap) extras.get("photo");
            patient.setIdPatient(extras.getString("idPatient"));
            patient.setYearsOld(extras.getString("yearsOld"));
            patient.setName(extras.getString("patient"));

            showData();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonLogout:
                logOutApp();
                break;
            case R.id.idbuttonReturnListMenu:
                Intent dashBoard = new Intent(this, DashBoardActivity.class);
                dashBoard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashBoard);
                finish();
                break;
        }
    }

    /**
     * This metohd send request for close a sesion
     */
    public void logOutApp (){

        SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        CloseAndRefresh closeApp = new CloseAndRefresh(this);
        closeApp.logOutApp(loginPreferences);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    /**
     * This method display data befor fill the form
     */
    private  void showData (){

        String name = "";
        Patient dataPatient = null;
        if (photo != null)
            photoPatient.setImageBitmap(photo);
        else
            photoPatient.setImageResource(R.drawable.usuario_icon);

        RequestPatient requestPatient = new RequestPatient(this);
        dataPatient = requestPatient.getDataPatientById(patient);

        name = dataPatient.getName() + " " + dataPatient.getLastName();
        Log.d("printLog", name);
        Log.d("printLog", name);

        patientName.setText(patientName.getText().toString() + " " + name);
        appointmentDate.setText(appointmentDate.getText().toString() + dataPatient.getNextAppointment());
        yearsOldPatient.setText(yearsOldPatient.getText().toString() + dataPatient.getYearsOld());

    }


}
