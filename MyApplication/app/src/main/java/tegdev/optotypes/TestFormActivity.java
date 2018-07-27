package tegdev.optotypes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    Spinner dropDownTonometricOd;
    Spinner dropDownTonometricOi;

    TextView textChromaticOd;//idTextChromaticOd
    TextView textChromaticOi;//idTextChromaticOi
    //TextView textTonometriaOd; //idTextChromaticOd
    //TextView textTonometriaOi; // idTextChromaticOi

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
    Button buttonReturnMenu;

    Diagnostic diagnosticNotes;
    Context contextActivity;
    Bitmap photo = null;
    Patient patient = null;
    String appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_form);

        contextActivity = this;
        diagnosticNotes = new Diagnostic();
        buttonReturnMenu = (Button) findViewById(R.id.idbuttonReturnListMenu);
        buttonReturnMenu.setOnClickListener(this);

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
        initializeFormDropDowns();

        actionDropDownAvRight();
        actionDropDownAvLeft();
        actionDropDownCenter();
        actionDropDownSustain();
        actionDropDownMaintain();
        actionDropDownTypeTest();
        actionDropDownColaboration();
        actionDropDownAntecendentMon();
        actionDropDownAntecedentDad();
        actionDropDownTonometricOd();
        actionDropDownTonometricOi();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null){
            patient = new Patient();
            diagnosticNotes.setIdPatient(extras.getString("idPatient"));
            photo = (Bitmap) extras.get("photo");
            patient.setIdPatient(extras.getString("idPatient"));
            patient.setYearsOld(extras.getString("yearsOld"));
            patient.setName(extras.getString("patient"));
            showData();
        }

    }

    /**
     * This method display data in this activity
     */
    public void showData(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        appointment = dateFormat.format(date);

        textAppointmentDate.setText("Fecha: " + appointment);
        textPatientName.setText("Paciente: " + patient.getName());
        textPatientYearsOld.setText(patient.getYearsOld());
        getDataPatient();


    }

    private void getDataPatient(){

        Cursor cursor = null;
        String query = " SELECT gender, birthday FROM " + PatientDbContract.PatientEntry.TABLE_NAME;

        PatientDbHelper patientDbHelper = new PatientDbHelper(ControlForService.context);
        SQLiteDatabase db = patientDbHelper.getReadableDatabase();

        try {

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){

                do{

                    diagnosticNotes.setSex(cursor.getString(0));
                    textPatientSex.setText("Sexo: " + cursor.getString(0));
                    textPatientDate.setText("Fecha de Nacimiento: " + cursor.getString(1));

                }while(cursor.moveToNext());

            }

        }catch (Exception e){

            e.printStackTrace();

        }finally {

            if(cursor != null){
                cursor.close();
            }

            db.close();
        }

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
        dropDownTonometricOd = (Spinner) findViewById(R.id.DropDownTonometricOd);
        dropDownTonometricOi = (Spinner) findViewById(R.id.DropDownTonometricOi);

        textChromaticOd = (TextView) findViewById(R.id.idTextChromaticOd);
        textChromaticOi = (TextView) findViewById(R.id.idTextChromaticOi);
        //textTonometriaOd = (TextView) findViewById(R.id.textTometriaOd);
        //textTonometriaOi = (TextView) findViewById(R.id.textTometriaOi);

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

    /**
     * This method initialice drop downs in this activity
     */
    public void initializeFormDropDowns(){

        ArrayAdapter<CharSequence> adapterAv = ArrayAdapter.createFromResource(this, R.array.av, R.layout.text_spiner_resource);
        ArrayAdapter<CharSequence>  adapterSubjective = ArrayAdapter.createFromResource(this, R.array.subjective, R.layout.text_spiner_resource);
        ArrayAdapter<CharSequence>  adapterTest = ArrayAdapter.createFromResource(this, R.array.test, R.layout.text_spiner_resource);
        ArrayAdapter<CharSequence>  adapterColaboration = ArrayAdapter.createFromResource(this, R.array.efectividad, R.layout.text_spiner_resource);
        ArrayAdapter<CharSequence> adapterTonometricOd = ArrayAdapter.createFromResource(this, R.array.tonometric, R.layout.text_spiner_resource);
        ArrayAdapter<CharSequence> adapterTonometricOi = ArrayAdapter.createFromResource(this, R.array.tonometric, R.layout.text_spiner_resource);

        dropDownAvRight.setAdapter(adapterAv);
        dropDownLeft.setAdapter(adapterAv);
        dropDownCenter.setAdapter(adapterSubjective);
        dropDownSustain.setAdapter(adapterSubjective);
        dropDownMaintain.setAdapter(adapterSubjective);
        dropDownTypeTest.setAdapter(adapterTest);
        dropDownColaboration.setAdapter(adapterColaboration);
        dropDownTonometricOd.setAdapter(adapterTonometricOd);
        dropDownTonometricOi.setAdapter(adapterTonometricOi);

        this.initializeAntecedentAdapter();
    }

    /**
     * This method initialize antecendent
     */
    public void initializeAntecedentAdapter(){

        ArrayList<String> arrayAntecedent = new ArrayList<String>();
        arrayAntecedent.add("Antecedentes");
        RequestAntecedentDefect requestAntecedentDefect = new RequestAntecedentDefect("antecedent",this);

        if (requestAntecedentDefect.findOrCreateTableAntecedent())
            requestAntecedentDefect.findAntecendets();

        requestAntecedentDefect.getAntecendet(arrayAntecedent);

        ArrayAdapter<CharSequence> adapterAntecedent = new ArrayAdapter(this, R.layout.text_spiner_resource, arrayAntecedent);
        dropDownPreviusMom.setAdapter(adapterAntecedent);
        dropDownPreviusDad.setAdapter(adapterAntecedent);
    }

    /**
     * This method initialize av list
     */
    public void actionDropDownAvRight(){
        dropDownAvRight.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setAvRigth(dropDownAvRight.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setAvRigth(dropDownAvRight.getSelectedItem().toString());
            }
        });
    }

    /**
     * This method initialize av list
     */
    public void actionDropDownAvLeft(){
        dropDownLeft.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setAvLeft(dropDownLeft.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setAvLeft(dropDownLeft.getSelectedItem().toString());
            }
        });
    }

    /**
     * This method initialize subjective test options
     */
    public void actionDropDownCenter (){
        dropDownCenter.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setCenter(dropDownCenter.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setCenter(dropDownCenter.getSelectedItem().toString());
            }
        });
    }

    /**
     * This method initialize subjective test options
     */
    public void actionDropDownSustain(){
        dropDownSustain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setSustain(dropDownSustain.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setSustain(dropDownSustain.getSelectedItem().toString());
            }
        });
    }

    /**
     * This method initialize subjective test options
     */
    public void actionDropDownMaintain(){
        dropDownMaintain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setMaintain(dropDownMaintain.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setMaintain(dropDownMaintain.getSelectedItem().toString());
            }
        });
    }

    /**
     * This method initialize typetest list
     */
    public void actionDropDownTypeTest(){
        dropDownTypeTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setTypeTest(dropDownTypeTest.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setTypeTest(dropDownTypeTest.getSelectedItem().toString());
            }
        });
    }

    /**
     * This method initialize colaboration grade list
     */
    public void actionDropDownColaboration(){
        dropDownColaboration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setColaborate(dropDownColaboration.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setColaborate(dropDownColaboration.getSelectedItem().toString());
            }
        });
    }


    /**
     * This method initialize Antecedent list
     */
    public void actionDropDownAntecendentMon(){

        dropDownPreviusMom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textParentMon.setText(textParentMon.getText().toString() + "," + dropDownPreviusMom.getSelectedItem().toString() );
                diagnosticNotes.setExtendsMon(textParentMon.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textParentMon.setText(textParentMon.getText().toString() + "," + dropDownPreviusMom.getSelectedItem().toString() );
                diagnosticNotes.setExtendsMon(textParentMon.getText().toString());
            }
        });
    }

    /**
     * This method initialize Antecedent list
     */
    public void actionDropDownAntecedentDad(){

        dropDownPreviusDad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textParentDad.setText(textParentDad.getText().toString() + "," + dropDownPreviusDad.getSelectedItem().toString() );
                diagnosticNotes.setExtendDad(textParentDad.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textParentDad.setText(textParentDad.getText().toString() + "," + dropDownPreviusDad.getSelectedItem().toString());
                diagnosticNotes.setExtendDad(textParentDad.getText().toString());
            }
        });
    }

    /**
     * This method initialize Tonometric Od list
     */
    public void actionDropDownTonometricOd(){

        dropDownTonometricOd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                diagnosticNotes.setTonometriaOd(dropDownTonometricOd.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                diagnosticNotes.setTonometriaOd(dropDownTonometricOd.getSelectedItem().toString());

            }
        });

    }

    /**
     * This method initialize Tonometric Oi List
     */
    public void actionDropDownTonometricOi(){

        dropDownTonometricOi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                diagnosticNotes.setTonometriaOi(dropDownTonometricOi.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                diagnosticNotes.setTonometriaOi(dropDownTonometricOi.getSelectedItem().toString());

            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.AppointmetProcess:
                processTestForm();
                break;
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
     * This method procces data by appointment
     */
    public void processTestForm (){

        int action = 0;

        diagnosticNotes.setSignalDefect("");
        diagnosticNotes.setOrtoforia((ortoforia.isChecked())? "S" : "N");
        diagnosticNotes.setOrtotropia(ortotropia.isChecked()? "S" : "N");
        diagnosticNotes.setForia(foria.isChecked()? "S" : "N");
        diagnosticNotes.setEndoforia(endoForia.isChecked()? "S" : "N");
        diagnosticNotes.setExoforia(exotropia.isChecked()? "S" : "N");
        diagnosticNotes.setHipertropia(hipertropia.isChecked()? "S" : "N");
        diagnosticNotes.setDvd(dvd.isChecked()? "S" : "N");
        diagnosticNotes.setCaElevada(caUpper.isChecked()? "S" : "N");
        diagnosticNotes.setIdPatient(patient.getIdPatient());
        diagnosticNotes.setYears(patient.getYearsOld().split(" ")[1]);

        /*if (textTonometriaOi.getText().toString() != null && textTonometriaOi.getText().toString() != ""){
            diagnosticNotes.setTonometriaOi(textTonometriaOi.getText().toString());
        }else {
            diagnosticNotes.setTonometriaOi("");
        }*/

        /*if (textTonometriaOd.getText().toString() != null && textTonometriaOd.getText().toString() != ""){
            diagnosticNotes.setTonometriaOd(textTonometriaOd.getText().toString());
        }else {
            diagnosticNotes.setTonometriaOd("");
        }*/

        if (textChromaticOd.getText().toString() != null && textChromaticOd.getText().toString() != ""){
            diagnosticNotes.setCrhomaticOd(textChromaticOd.getText().toString());
        }else {
            diagnosticNotes.setCrhomaticOd("");
        }
        if (textChromaticOi.getText().toString() != null && textChromaticOi.getText().toString() != ""){
            diagnosticNotes.setCrhomaticOi(textChromaticOi.getText().toString());
        }else {
            diagnosticNotes.setCrhomaticOi("");
        }

        if (validateForm()){

            RequestDiagnostic requestDiagnostic = new RequestDiagnostic();
            requestDiagnostic.saveDataDiagnostic(diagnosticNotes);
            requestDiagnostic.sendDataDiagnostic(diagnosticNotes, action);
            alertDialog();

        }

    }

    /**
     * This method validate if form are complete
     * @return
     */
    private boolean validateForm() {

        boolean value = true;
        boolean chromatic = true;
        boolean colaborate = true;
        String message = "";
        String title = "Verifique los Datos";

        try{
            int chromaticOd = Integer.parseInt(diagnosticNotes.getCrhomaticOd());
            int chromaticOi = Integer.parseInt(diagnosticNotes.getCrhomaticOi());

            if (chromaticOd < 1 || chromaticOd > 14){
                chromatic = false;
            }

            if (chromaticOi < 1 || chromaticOi > 14){
                chromatic = false;
            }

        }catch (Exception e){
            e.printStackTrace();
            chromatic = false;
        }finally{

            if (diagnosticNotes.getColaborate().equals("0")){
                colaborate = false;
            }


        }

        if (!chromatic){
            message = "Verifique el valor de percepción cromatica, este debe ser entero 1 - 14";
        }

        if (!colaborate){
            message = "Indique valor de estimación de colaboración del niño";
        }

        if (!colaborate && !chromatic){
            message = "Verifique valor de percepción cromatica e indique una vlor de estimación de colaboración";
        }

        if (!colaborate || !chromatic){
            value = false;
            alertDialog(title,message);
        }

        return value;
    }

    /**
     * This method display a dialog to say if data saved
     */
    public void alertDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Información");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Los Datos recavados en la consulta fueron procesados")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Intent newActivity = new Intent(contextActivity, DiagnosticActivity.class);
                        newActivity.putExtra("idPatient",diagnosticNotes.getIdPatient());
                        newActivity.putExtra("photo", photo);
                        newActivity.putExtra("patient", patient.getName());
                        newActivity.putExtra("yearsOld", patient.getYearsOld());
                        newActivity.putExtra("date", appointment);
                        startActivity(newActivity);*/
                        Intent newActivity = new Intent (contextActivity, DashBoardActivity.class);
                        startActivity(newActivity);

                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    /**
     * This method display a dialog to say if data saved
     */
    public void alertDialog(String title, String message){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

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

}
