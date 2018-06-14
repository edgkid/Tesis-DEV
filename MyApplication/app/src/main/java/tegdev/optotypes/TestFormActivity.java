package tegdev.optotypes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TestFormActivity extends AppCompatActivity implements View.OnClickListener {

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    TextView textAppointmentDate; //TextAppointmentDate
    TextView textPatientName; //TextPatient
    TextView textPatientDate; //TextPatientDate
    TextView textPatientYearsOld; // TextPatientYearsOld
    TextView textPatientSex; //TextPatientSex

    TextView textParentMon;//TextParentMon
    TextView textParentDad; //TextParentDad

    Spinner dropDownPreviusDad; //dropDownPreviusDad
    Spinner dropDownPreviusMom; // dropDownPreviusMon
    Spinner dropDownAvRight; //DropDownRight
    Spinner dropDownLeft; //DropDownLeft
    Spinner dropDownTypeTest; //DropDownTypeTest
    Spinner dropDownCenter; //DropDowCenter
    Spinner dropDownSustain; //DropDowSustain
    Spinner dropDownMaintain; //DropDowMaintain

    TextView textChromaticOd;//idTextChromaticOd
    TextView textChromaticOi;//idTextChromaticOi

    CheckBox ortoforia; //idOrtoforia
    CheckBox ortotropia; //idOrtotropia
    CheckBox foria; // idForia
    CheckBox endoForia;// idEndoforia
    CheckBox exotropia; // idExotropia
    CheckBox hipertropia; //idHipertropia
    CheckBox dvd;// idDvd
    CheckBox caUpper; // idCaUp

    Spinner dropDownColaboration; //DropDownCoperation

    Button buttonProcess; //AppointmetProcess
    Button logOut;//buttonLogout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_form);

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

        initializeFormViews();

    }

    /**
     * This method the viewa in this activity
     */
    public void initializeFormViews (){

        textAppointmentDate = (TextView) findViewById(R.id.TextAppointmentDate);
        textPatientName = (TextView) findViewById(R.id.TextPatient);
        textPatientDate = (TextView) findViewById(R.id.TextPatientDate);
        textPatientYearsOld = (TextView) findViewById(R.id.TextPatientYearsOld);
        textPatientSex = (TextView) findViewById(R.id.TextPatientSex);

        dropDownAvRight = (Spinner) findViewById(R.id.DropDownRight);
        dropDownLeft = (Spinner) findViewById(R.id.DropDownLeft);
        dropDownCenter = (Spinner) findViewById(R.id.DropDowCenter);
        dropDownSustain = (Spinner) findViewById(R.id.DropDowSustain);
        dropDownMaintain = (Spinner) findViewById(R.id.DropDowMaintain);
        dropDownTypeTest = (Spinner) findViewById(R.id.DropDownTypeTest);
        dropDownColaboration = (Spinner) findViewById(R.id.DropDownCoperation);
        dropDownPreviusDad = (Spinner) findViewById(R.id.dropDownPreviusDad);
        dropDownPreviusMom = (Spinner) findViewById(R.id.dropDownPreviusMon);

        textChromaticOd = (TextView) findViewById(R.id.idTextChromaticOd);
        textChromaticOi = (TextView) findViewById(R.id.idTextChromaticOi);
        ortoforia = (CheckBox) findViewById(R.id.idOrtoforia);
        ortotropia = (CheckBox) findViewById(R.id.idOrtotropia);
        foria = (CheckBox) findViewById(R.id.idForia);
        endoForia = (CheckBox) findViewById(R.id.idEndoforia);
        exotropia = (CheckBox) findViewById(R.id.idExotropia);
        hipertropia = (CheckBox) findViewById(R.id.idHipertropia);
        dvd = (CheckBox) findViewById(R.id.idDvD);
        caUpper = (CheckBox) findViewById(R.id.idCaUP);
        textParentDad = (TextView) findViewById(R.id.TextParentDad);
        textParentMon = (TextView) findViewById(R.id.TextParentMon);

        buttonProcess = (Button) findViewById(R.id.AppointmetProcess);
        buttonProcess.setOnClickListener(this);

        logOut = (Button) findViewById(R.id.buttonLogout);
        logOut.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }
}
