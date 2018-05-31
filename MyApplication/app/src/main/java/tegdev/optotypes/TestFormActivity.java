package tegdev.optotypes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestFormActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textAppointmentDate;
    TextView textPatientName;
    TextView textPatientDate;
    TextView textPatientYearsOld;
    TextView textPatientSex;
    TextView textParentMon;
    TextView textParentDad;

    Spinner dropDownCenter;
    Spinner dropDownSustain;
    Spinner dropDownMaintain;
    Spinner dropDownAvRight;
    Spinner dropDownLeft;
    Spinner dropDownTypeTest;
    Spinner dropDownColaboration;
    Spinner dropDownPreviusSignal;
    Spinner dropDownPreviusDad;
    Spinner dropDownPreviusMom;

    TextView textSignal;
    ImageView imageViewControl;

    Button buttonProcess;
    Button logOut;
    Button updated;
    Button nexttest;
    Button lastTest;

    Diagnostic diagnosticNotes;
    ArrayList<String> testList = null;
    int positionTestList = -1;
    Context contextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_form);

        contextActivity = this;
        diagnosticNotes = new Diagnostic();

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
        actionDropDownSignalDefect();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // se considera una buena practica y debe hacerse
        if (extras != null){
            Patient patient = new Patient();
            testList = (ArrayList<String>) getIntent().getStringArrayListExtra("listTest");
            diagnosticNotes.setIdPatient(extras.getString("idPatient"));
            Log.d("message: ", testList.get(0));
            //setPatientData();
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
        dropDownPreviusSignal = (Spinner) findViewById(R.id.DropDownSignal);
        dropDownPreviusDad = (Spinner) findViewById(R.id.dropDownPreviusDad);
        dropDownPreviusMom = (Spinner) findViewById(R.id.dropDownPreviusMon);

        textParentDad = (TextView) findViewById(R.id.TextParentDad);
        textParentMon = (TextView) findViewById(R.id.TextParentMon);
        textSignal = (TextView) findViewById(R.id.TextAllSignals);

        buttonProcess = (Button) findViewById(R.id.AppointmetProcess);
        buttonProcess.setOnClickListener(this);

        imageViewControl = (ImageView) findViewById(R.id.ImageTestControlTest);

        logOut = (Button) findViewById(R.id.buttonLogout);
        logOut.setOnClickListener(this);
        updated = (Button) findViewById(R.id.buttonUpdate);
        updated.setOnClickListener(this);

        nexttest = (Button) findViewById(R.id.nextImage);
        nexttest.setOnClickListener(this);
        lastTest = (Button) findViewById(R.id.lastImage);
        lastTest.setOnClickListener(this);
    }

    /**
     * This method initialice drop downs in this activity
     */
    public void initializeFormDropDowns(){

        ArrayAdapter<CharSequence> adapterAv = ArrayAdapter.createFromResource(this, R.array.av, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>  adapterSubjective = ArrayAdapter.createFromResource(this, R.array.subjective, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>  adapterTest = ArrayAdapter.createFromResource(this, R.array.test, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>  adapterColaboration = ArrayAdapter.createFromResource(this, R.array.efectividad, android.R.layout.simple_spinner_item);

        dropDownAvRight.setAdapter(adapterAv);
        dropDownLeft.setAdapter(adapterAv);
        dropDownCenter.setAdapter(adapterSubjective);
        dropDownSustain.setAdapter(adapterSubjective);
        dropDownMaintain.setAdapter(adapterSubjective);
        dropDownTypeTest.setAdapter(adapterTest);
        dropDownColaboration.setAdapter(adapterColaboration);

        this.initializeAntecedentAdapter();
        this.initializeSignalAdapter();

    }

    /**
     * This method initialize antecendent
     */
    public void initializeAntecedentAdapter(){

        Log.d("message", "inicalizando anteceedentes");
        ArrayList<String> arrayAntecedent = new ArrayList<String>();
        arrayAntecedent.add("Antecedentes");
        RequestAntecedentDefect requestAntecedentDefect = new RequestAntecedentDefect("antecedent",this);

        if (requestAntecedentDefect.findOrCreateTableAntecedent())
            requestAntecedentDefect.findAntecendets();

        requestAntecedentDefect.getAntecendet(arrayAntecedent);

        ArrayAdapter<CharSequence> adapterAntecedent = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayAntecedent);
        dropDownPreviusMom.setAdapter(adapterAntecedent);
        dropDownPreviusDad.setAdapter(adapterAntecedent);
    }

    /**
     * This method initialize antecendent
     */
    public void initializeSignalAdapter(){

        ArrayList<String> arraySignal = new ArrayList<String>();
        arraySignal.add("Sintomas");

        RequestSignalDefect requestSignalDefect = new RequestSignalDefect("signalDefect", this);
        if(requestSignalDefect.findOrCreateTableSignalDefect()){
            requestSignalDefect.finSignalDefect();
        }

        requestSignalDefect.getSignalDefect(arraySignal);

        ArrayAdapter<CharSequence> adapterSignal = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySignal);
        dropDownPreviusSignal.setAdapter(adapterSignal);

    }

    /**
     * This method initialize av list
     */
    public void actionDropDownAvRight(){
        dropDownAvRight.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diagnosticNotes.setAvRigth(dropDownAvRight.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getAvRigth());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setAvRigth(dropDownAvRight.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getAvRigth());
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
                Log.d("Datos: ", diagnosticNotes.getAvLeft());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setAvLeft(dropDownLeft.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getAvLeft());
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
                Log.d("Datos: ", diagnosticNotes.getCenter());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setCenter(dropDownCenter.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getCenter());
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
                Log.d("Datos: ", diagnosticNotes.getSustain());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setSustain(dropDownSustain.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getSustain());
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
                Log.d("Datos: ", diagnosticNotes.getMaintain());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setMaintain(dropDownMaintain.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getMaintain());
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
                Log.d("Datos: ", diagnosticNotes.getTypeTest());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setTypeTest(dropDownTypeTest.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getTypeTest());
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
                Log.d("Datos: ", diagnosticNotes.getColaborate());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diagnosticNotes.setColaborate(dropDownColaboration.getSelectedItem().toString());
                Log.d("Datos: ", diagnosticNotes.getColaborate());
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
                Log.d("Datos:", diagnosticNotes.getExtendsMon().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textParentMon.setText(textParentMon.getText().toString() + "," + dropDownPreviusMom.getSelectedItem().toString() );
                diagnosticNotes.setExtendsMon(textParentMon.getText().toString());
                Log.d("Datos:", diagnosticNotes.getExtendsMon().toString());
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
                Log.d("Datos:", diagnosticNotes.getExtendDad().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textParentDad.setText(textParentDad.getText().toString() + "," + dropDownPreviusDad.getSelectedItem().toString());
                diagnosticNotes.setExtendDad(textParentDad.getText().toString());
                Log.d("Datos:", diagnosticNotes.getExtendDad().toString());
            }
        });
    }

    /**
     * This method initialize signal defect List
     */
    public void actionDropDownSignalDefect(){

        dropDownPreviusSignal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textSignal.setText(textSignal.getText().toString() + "," + dropDownPreviusSignal.getSelectedItem().toString());
                diagnosticNotes.setSignalDefect(textSignal.getText().toString());
                Log.d("Datos:", diagnosticNotes.getSignalDefect());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textSignal.setText(textSignal.getText().toString() + "," + dropDownPreviusSignal.getSelectedItem().toString());
                diagnosticNotes.setSignalDefect(textSignal.getText().toString());
                Log.d("Datos:", diagnosticNotes.getSignalDefect());
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.AppointmetProcess:
                //processTestForm();
                break;
            case R.id.buttonLogout:
                //Toast.makeText(this, "salir de la seción", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonUpdate:
                //Toast.makeText(this, "Actualizar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nextImage:
                positionTestList++;
                sendTestToClientProjector();
                break;
            case R.id.lastImage:
                positionTestList--;
                sendTestToClientProjector();
                break;
        }
    }

    /**
     * This method send optotypes to projector
     */
    public void sendTestToClientProjector(){

        Bitmap image = null;

        if (positionTestList <= -1 || positionTestList == testList.size()){
            positionTestList = 0;
        }else if (positionTestList == testList.size()){
            Log.d("message: ", testList.get(positionTestList));
        }

        Log.d("message: ", positionTestList + testList.get(positionTestList));

        byte[] byteCode = Base64.decode(testList.get(positionTestList), Base64.DEFAULT);
        image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);

        if (image != null)
            imageViewControl.setImageBitmap(image);
        else
            imageViewControl.setImageResource(R.drawable.imagenotfoud);

        ClientProjector clientProjector = new ClientProjector();
        clientProjector.sendMessage(positionTestList + testList.get(positionTestList));

    }


    /**
     * This method procces data by appointment
     */
    public void processTestForm (){

        int action = 0;
        RequestDiagnostic requestDiagnostic = new RequestDiagnostic();
        requestDiagnostic.sendDataDiagnostic(diagnosticNotes, action);
        alertDialog();

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
                        Intent newActivity = new Intent(contextActivity, DiagnosticActivity.class);
                        newActivity.putExtra("idPatient",diagnosticNotes.getIdPatient());
                        newActivity.putExtra("patientName", diagnosticNotes.getPatient());
                        newActivity.putExtra("year",diagnosticNotes.getYears());
                        startActivity(newActivity);
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

}
