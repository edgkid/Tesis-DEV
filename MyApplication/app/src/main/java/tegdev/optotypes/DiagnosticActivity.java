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
    TextView AvResultOd;
    TextView AvResultOi;
    TextView subjectiveCenter;
    TextView subjectiveSustain;
    TextView subjectiveMaintain;
    TextView testColorOd;
    TextView testColorOi;
    TextView tonometricOd;
    TextView tonometricOi;
    TextView foria;
    TextView ortotropia;
    TextView ortoforia;
    TextView endoforia;
    TextView exotropia;
    TextView hipertropia;
    TextView dvd;
    TextView caElevada;
    TextView descriptionAv;
    TextView descriptionTestA;
    TextView descriptionTestB;
    TextView descriptionTestC;
    TextView descriptionTestD;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    Button menuList;
    Button logOut;

    Bitmap photo = null;
    Patient patient = null;

    public static ArrayList<TextView> arryValuesView = new ArrayList<TextView>();

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

        AvResultOd = (TextView) findViewById(R.id.idAvRsultOd);
        AvResultOi = (TextView) findViewById(R.id.idAvRsultOi);
        subjectiveCenter = (TextView) findViewById(R.id.idSubjectiveCenter);
        subjectiveSustain = (TextView) findViewById(R.id.idSubjectiveSustain);
        subjectiveMaintain = (TextView) findViewById(R.id.idSubjectiveMaintain);
        testColorOd = (TextView) findViewById(R.id.idTestColorOd);
        testColorOi = (TextView) findViewById(R.id.idTestColorOi);
        tonometricOd = (TextView) findViewById(R.id.idTonometricOd);
        tonometricOi = (TextView) findViewById(R.id.idTonometricOi);
        foria = (TextView) findViewById(R.id.idForia);
        ortotropia = (TextView) findViewById(R.id.idOrtotropia);
        ortoforia = (TextView) findViewById(R.id.idOrtoforia);
        endoforia = (TextView) findViewById(R.id.idEndoforia);
        exotropia = (TextView) findViewById(R.id.idExotropia);
        hipertropia = (TextView) findViewById(R.id.idHipertropia);
        dvd = (TextView) findViewById(R.id.idDvD);
        caElevada = (TextView) findViewById(R.id.idCaElevada);
        descriptionAv = (TextView) findViewById(R.id.idObjectiveTest);
        descriptionTestA = (TextView) findViewById(R.id.idDescripctionTestA);
        descriptionTestB = (TextView) findViewById(R.id.idDescripctionTestB);
        descriptionTestC = (TextView) findViewById(R.id.idDescripctionTestC);
        descriptionTestD = (TextView) findViewById(R.id.idDescripctionTestD);

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

            fillListViewElements();
            getViewData();
            showData();
        }



    }

    /**
     * This method get data for views in activity
     */
    private void getViewData() {

        RequestDiagnostic requestDiagnostic = new RequestDiagnostic(this);
        requestDiagnostic.getDataViews(patient.getIdPatient());
    }

    /**
     * This method fill elements on view
     */
    private void fillListViewElements() {

        arryValuesView.add(typeTest);
        arryValuesView.add(AvResultOd);
        arryValuesView.add(AvResultOi);
        arryValuesView.add(subjectiveCenter);
        arryValuesView.add(subjectiveSustain);
        arryValuesView.add(subjectiveMaintain);
        arryValuesView.add(testColorOd);
        arryValuesView.add(testColorOi);
        arryValuesView.add(tonometricOd);
        arryValuesView.add(tonometricOi);
        arryValuesView.add(foria);
        arryValuesView.add(ortotropia);
        arryValuesView.add(ortoforia);
        arryValuesView.add(endoforia);
        arryValuesView.add(exotropia);
        arryValuesView.add(hipertropia);
        arryValuesView.add(dvd);
        arryValuesView.add(caElevada);
        arryValuesView.add(descriptionAv);
        arryValuesView.add(descriptionTestA);
        arryValuesView.add(descriptionTestB);
        arryValuesView.add(descriptionTestC);
        arryValuesView.add(descriptionTestD);
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

        int action = 1;
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

        RequestDiagnostic requestDiagnostic = new RequestDiagnostic(this);
        requestDiagnostic.requestAllDataDiagnostic(dataPatient.getIdPatient(), action);

    }


}
