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

import java.io.File;

public class CrudNewPatientActivity extends AppCompatActivity implements ImageView.OnClickListener{

    ImageView imagePatient;

    EditText firstName;
    EditText secondName;
    EditText lastName;
    EditText maidenName;

    Button buttonAcept;
    Button buttonLogOut;

    DatePicker calendar;

    TextView ipWbeService;
    TextView ipClient;
    TextView port;

    Spinner genderList;

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

        }

    }

    private void saveNewPatient() {

        String date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth()+1)
                + "/" + String.valueOf(calendar.getYear());

        Log.d("message", firstName.getText().toString());
        Log.d("message", secondName.getText().toString());
        Log.d("message", lastName.getText().toString());
        Log.d("message", maidenName.getText().toString());
        Log.d("message", genderList.getSelectedItem().toString());
        Log.d("message", date);


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

                    Bitmap bitmap = BitmapFactory.decodeFile(dir);
                    imagePatient.setImageBitmap(bitmap);

                }
                break;

            case SELECT_PICTURE:
                if(resultCode == RESULT_OK){
                    Uri path = data.getData();
                    imagePatient.setImageURI(path);
                }
                break;

        }
    }


}
