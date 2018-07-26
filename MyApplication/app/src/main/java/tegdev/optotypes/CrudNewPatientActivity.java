package tegdev.optotypes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrudNewPatientActivity extends AppCompatActivity implements ImageView.OnClickListener{

    ImageView imagePatient;

    EditText firstName;
    EditText secondName;
    EditText lastName;
    EditText maidenName;

    Button buttonAcept;
    Button buttonLogOut;
    Button buttonReturnListMenu;

    DatePicker calendar;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    Spinner genderList;

    Patient patient = null;
    Bitmap image;

    private String APP_DIRECTORY = "optotypePictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "optotypeMedia";
    private String TEMPORAL_PICTURE_NAME = "temporalPhoto.png";

    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE= 300;

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
        buttonReturnListMenu = (Button) findViewById(R.id.idbuttonReturnListMenu);
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
        buttonReturnListMenu.setOnClickListener(this);

        imagePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = {"Tomar foto", "Elegir de galeria", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(CrudNewPatientActivity.this);
                builder.setTitle("Elige una Opción");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selection) {

                        if(options[selection] == "Tomar foto"){
                            openCamera();
                        }else if(options[selection] == "Elegir de galeria"){
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Selecciona imagen"), SELECT_PICTURE);
                        }else if(options[selection] == "cancelar"){
                            dialog.dismiss();
                        }

                    }
                });
                builder.show();

            }
        });

    }

    private void openCamera() {

        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdir();

        String path = Environment.getExternalStorageDirectory() + File.separator
                + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                break;
            case R.id.idCrudDButtonAcepted:
                saveNewPatient();
                break;
            case R.id.idbuttonReturnListMenu:
                Intent dashBoard = new Intent(this, DashBoardActivity.class);
                dashBoard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashBoard);
                finish();
                break;

        }

    }

    private void saveNewPatient() {

        String edad = "";
        String encode = "";
        int action = 0;

        String date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth() + 1)
                + "/" + String.valueOf(calendar.getYear());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String appointment = dateFormat.format(today);

        String[] arrayDate = date.split("/");
        String[] arrayToday = appointment.split("/");

        edad = String.valueOf(Integer.parseInt(arrayToday[2]) - Integer.parseInt(arrayDate[2]));

        if (Integer.parseInt(arrayToday[1]) < Integer.parseInt(arrayDate[1]))
            edad = String.valueOf(Integer.parseInt(edad) - 1);

        if (image != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            encode = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        patient = new Patient();
        patient.setName(firstName.getText().toString());
        patient.setMiddleName(secondName.getText().toString());
        patient.setLastName(lastName.getText().toString());
        patient.setMaidenName(maidenName.getText().toString());
        patient.setGender(genderList.getSelectedItem().toString());
        patient.setYearsOld(edad);
        patient.setNextAppointment(appointment);
        patient.setPatientDate(date);
        patient.setFkUser("2");
        patient.setPhoto(encode);

        if (verifyForm(edad)){
            RequestPatient requestPatient = new RequestPatient("patients", this);
            requestPatient.sendDataPatient(patient, action);
        }

    }

    /**
     * This method validate if item name, lastname and calendar are correct
     * @param edad
     * @return
     */
    private boolean verifyForm(String edad) {

        boolean valuePatient = true;
        boolean valueDate = true;
        boolean value = false;

        String title = "Guardando Nuevo Registro";
        String message = "";

        if (TextUtils.isEmpty(firstName.getText()) || TextUtils.isEmpty(lastName.getText()) ){
            valuePatient = false;
        }

        if (Integer.parseInt(edad) <= 0){
            valueDate = false;
        }

        if (!valuePatient && !valueDate){
            message = "Verifique los Campos de; Primer Nombre, Primer Apellido y Fecha de Nacimiento";
        }

        if (valuePatient && !valueDate){
            message = "Verifique Fecha de nacimiento";
        }

        if (!valuePatient && valueDate){
            message = "Verifique; Primer Nombre y Primer Apellido son Obligatorios";
        }

        if (!message.equals("")){
            alertDialog(title,message);
        }

        if (valuePatient && valueDate){
            value = true;
        }

        return value;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){

            case PHOTO_CODE:
                if(resultCode == RESULT_CANCELED){

                    String dir = Environment.getExternalStorageDirectory() + File.separator
                            + MEDIA_DIRECTORY + File.separator
                            + TEMPORAL_PICTURE_NAME;

                    image = BitmapFactory.decodeFile(dir);
                    imagePatient.setImageBitmap(image);

                }
                break;

            case SELECT_PICTURE:
                if(resultCode == RESULT_OK){
                    Uri path = data.getData();
                    imagePatient.setImageURI(path);
                    try {
                        image = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }


    /**
     * This method display a Dialog before dlete an  appointment
     */
    public void alertDialog(String title, String message){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


}
