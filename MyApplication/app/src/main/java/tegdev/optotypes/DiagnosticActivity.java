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

}
