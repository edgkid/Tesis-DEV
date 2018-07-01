package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TestControlActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonLogOut;
    Button nextTest;
    Button lastTest;
    Button buttonReturnMenu;
    ImageView imageTest;
    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    TextView avImperial;
    TextView avSnellen;
    TextView avLogMar;
    TextView avDecimal;
    TextView rowPosition;

    ArrayList<String> testList = null;
    ArrayList<String> testListA = null;
    ArrayList<String> testListB = null;

    int positionTestList = -1;
    Context contextActivity;
    Bitmap photo = null;
    Patient patient = null;
    AvScale avScale = new AvScale();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_control);

        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonReturnMenu = (Button) findViewById(R.id.idbuttonReturnListMenu);
        nextTest = (Button) findViewById(R.id.nextImage);
        lastTest = (Button) findViewById(R.id.lastImage);
        imageTest = (ImageView) findViewById(R.id.idActualTest);

        avImperial = (TextView) findViewById(R.id.textImperialValue);
        avSnellen = (TextView) findViewById(R.id.textSnellenValue);
        avLogMar = (TextView) findViewById(R.id.textLogValue);
        avDecimal = (TextView) findViewById(R.id.textDecimalValue);
        rowPosition = (TextView) findViewById(R.id.textRowTest);

        ipWbeService = (TextView) findViewById(R.id.ipWebService);
        ipClient = (TextView) findViewById(R.id.ipProjector);
        port = (TextView) findViewById(R.id.portProjector);

        buttonLogOut.setOnClickListener(this);
        nextTest.setOnClickListener(this);
        lastTest.setOnClickListener(this);
        buttonReturnMenu.setOnClickListener(this);

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

            testList = CrudRequestTestActivity.imagesTest;
            patient.setIdPatient(extras.getString("idPatient"));
            patient.setName(extras.getString("patient"));
            patient.setYearsOld(extras.getString("yearsOld"));
            photo = (Bitmap)extras.get("photo");

            sendTestToClientProjector();

        }

        imageTest.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                newActivity();
            }
        });



    }

    /**
     * This method show a image test and send to client
     */
    public void sendTestToClientProjector(){

        Bitmap image = null;

        if (positionTestList <= -1 || positionTestList == testList.size()){
            positionTestList = 0;
        }

        byte[] byteCode = Base64.decode(testList.get(positionTestList), Base64.DEFAULT);
        image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);

        if (image != null)
            imageTest.setImageBitmap(image);
        else
            imageTest.setImageResource(R.drawable.imagenotfoud);


        avImperial.setText("Imperial: " + avScale.getAvImperial().get(positionTestList));
        avSnellen.setText("Snellen: " + avScale.getAvOriginalMetric().get(positionTestList));
        avLogMar.setText("LogMar: " + avScale.getAvLogmar().get(positionTestList));
        avDecimal.setText("Decimal: " + avScale.getAvDecimal().get(positionTestList));
        rowPosition.setText("Fila: " + avScale.getRow().get(positionTestList));



        ClientProjector clientProjector = new ClientProjector();
        clientProjector.sendMessage(positionTestList + testList.get(positionTestList));

    }

    /**
     * This metohd send request for close a sesion
     */
    public void logOutApp (){

        SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        CloseAndRefresh closeApp = new CloseAndRefresh(this);
        closeApp.logOutApp(loginPreferences);
    }

    /**
     * This method display a new activity
     */
    public void newActivity(){

        testList.removeAll(testList);
        CrudRequestTestActivity.imagesTest.removeAll(CrudRequestTestActivity.imagesTest);

        Intent testForm = new Intent(this, TestFormActivity.class);
        testForm.putExtra("idPatient", String.valueOf(patient.getIdPatient()));
        testForm.putExtra("patient", patient.getName());
        testForm.putExtra("yearsOld", patient.getYearsOld());
        testForm.putExtra("photo", photo);
        startActivity(testForm);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonLogout:
                logOutApp();
                break;
            case R.id.nextImage:
                positionTestList++;
                sendTestToClientProjector();
                break;
            case R.id.lastImage:
                positionTestList--;
                sendTestToClientProjector();
                break;
            case R.id.idbuttonReturnListMenu:
                Intent dashBoard = new Intent (this, DashBoardActivity.class);
                dashBoard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashBoard);
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
