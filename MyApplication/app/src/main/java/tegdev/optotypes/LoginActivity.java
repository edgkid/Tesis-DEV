package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ConfgDialog.ConfgDialogListener {

    EditText    editTextUserName;
    EditText    editTextPaswword;
    Button      buttonLogin;
    Button      buttonConfg;
    ImageView   imageViewIcon;
    Context     contextActivity;
    ConfgConnect confgConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences conexionPreferences = this.getSharedPreferences("connectPreferences", Context.MODE_PRIVATE);
        String ipWebService = conexionPreferences.getString("IpWebService", "");
        String ipShowClient = conexionPreferences.getString("IpShowTest", "");
        String portConnection = conexionPreferences.getString("PortConecction", "5000");

        contextActivity = this;
        confgConnect = new ConfgConnect(ipWebService, ipShowClient, portConnection);

        editTextUserName = (EditText) findViewById(R.id.editTextUserNameLogin);
        editTextPaswword = (EditText) findViewById(R.id.editTextViewPassWord);
        imageViewIcon   = (ImageView) findViewById(R.id.imageViewIconEye);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener((View.OnClickListener) contextActivity);

        buttonConfg = (Button) findViewById(R.id.buttonConectionConfg);
        buttonConfg.setOnClickListener((View.OnClickListener) contextActivity);

        verifyPreferencesLogin();

        InitialiceDataService initialiceDataService = new InitialiceDataService(this);
        initialiceDataService.initialiceResourcePatient();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogin:
                actionLogin();
                break;
            case R.id.buttonConectionConfg:
                actionConfg();
                break;
        }
    }

    /**
     * This method display a dialog for configure connection webservice and socket
     */
    public void actionConfg(){
        ConfgDialog confgDialog = new ConfgDialog();
        confgDialog.show(getSupportFragmentManager(),"Message Dialog");

    }

    /**
     * This method excute a petition for Login a user
     */
    public void actionLogin(){

        boolean dataConnection = false;
        boolean dataUser = false;

        String resourceUser = "users/"+editTextUserName.getText().toString() + "," + editTextPaswword.getText().toString();
        RequestUser requestUser = new RequestUser(resourceUser, this);

        if (!ConfgConnect.getIpShowTest().equals("") && !ConfgConnect.getIpShowTest().equals(" ") && ConfgConnect.getIpShowTest() != null){
            if(!ConfgConnect.getIpShowTest().equals("") && !ConfgConnect.getIpShowTest().equals(" ") && ConfgConnect.getIpShowTest() != null){
                if(requestUser.findUserOnSystem()){
                    dataConnection = true;
                }
            }
        }

        if (!editTextUserName.getText().toString().equals("") && !editTextUserName.getText().toString().equals(" ")){
            if (!editTextPaswword.getText().toString().equals("") && !editTextPaswword.getText().toString().equals(" ")){
                dataUser = true;
            }
        }

        if(dataConnection && dataUser){
            callNewActivity();
        }

    }

    /**
     *This method verify if sharedpreferences login file contend the data
     */
    public void verifyPreferencesLogin (){

        String user = "";
        String password = "";
        String roll = "";
        boolean value = false;

        SharedPreferences loginPreferences = this.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);

        user =  loginPreferences.getString("user", "defaultUser");
        password = loginPreferences.getString("password", "defaultUser");

        if (user.equals("defaultUser") && password.equals("defaultUser")){
            ;
        }else{
            callNewActivity();
        }

    }

    /**
     * This metohd call a new window or activity
     */
    public void callNewActivity (){

        /*BackGroundProccessForUpdate backGroundProccessForUpdate = new BackGroundProccessForUpdate();
        backGroundProccessForUpdate.execute();*/

        Intent dashBoardActivity = new Intent(this, DashBoardActivity.class);
        dashBoardActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dashBoardActivity);
        finish();
    }

    @Override
    public void applyData(String ipWebService, String ipShowClient, String portShowClient) {

        confgConnect.setIpWebService(ipWebService);
        confgConnect.setIpShowTest(ipShowClient);
        confgConnect.setPortConecction(portShowClient);

        SharedPreferences conexionPreferences = this.getSharedPreferences("connectPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = conexionPreferences.edit();
        editor.putString("IpWebService", ipWebService);
        editor.putString("IpShowTest", ipShowClient);
        editor.putString("PortConecction", portShowClient);

        editor.commit();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
