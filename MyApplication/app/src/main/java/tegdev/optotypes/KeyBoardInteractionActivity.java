package tegdev.optotypes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class KeyBoardInteractionActivity extends AppCompatActivity {


    ImageView optotypePosAa;
    ImageView optotypePosAb;
    ImageView optotypePosAc;
    ImageView optotypePosAd;
    ImageView optotypePosBa;
    ImageView optotypePosBb;
    ImageView optotypePosBc;
    ImageView optotypePosBd;
    ImageView optotypePosCa;
    ImageView optotypePosCb;
    ImageView optotypePosCc;
    ImageView optotypePosCd;
    ImageView optotypePosDa;
    ImageView optotypePosDb;
    ImageView optotypePosDc;
    ImageView optotypePosDd;

    ImageView opSelectedAa;
    ImageView opSelectedAb;
    ImageView opSelectedAc;
    ImageView opSelectedAd;
    ImageView opSelectedBa;
    ImageView opSelectedBb;
    ImageView opSelectedBc;
    ImageView opSelectedBd;
    ImageView opSelectedCa;
    ImageView opSelectedCb;
    ImageView opSelectedCc;
    ImageView opSelectedCd;

    TextView textNamePatient;
    TextView textLastNamePatient;
    TextView yearsOldPatient;

    ImageView perfilPatient;

    Patient patient = new Patient();
    ElementsInteraction elements;
    Bundle patientExtras;

    ArrayList<ImageView> arrayImage = new ArrayList<ImageView>();
    ArrayList<ImageView> arrayImageSelected = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board_interaction);


        optotypePosAa = (ImageView) findViewById(R.id.optotypeAa);
        optotypePosAb = (ImageView) findViewById(R.id.optotypeAb);
        optotypePosAc = (ImageView) findViewById(R.id.optotypeAc);
        optotypePosAd = (ImageView) findViewById(R.id.optotypeAd);
        optotypePosBa = (ImageView) findViewById(R.id.optotypeBa);
        optotypePosBb = (ImageView) findViewById(R.id.optotypeBb);
        optotypePosBc = (ImageView) findViewById(R.id.optotypeBc);
        optotypePosBd = (ImageView) findViewById(R.id.optotypeBd);
        optotypePosCa = (ImageView) findViewById(R.id.optotypeCa);
        optotypePosCb = (ImageView) findViewById(R.id.optotypeCb);
        optotypePosCc = (ImageView) findViewById(R.id.optotypeCc);
        optotypePosCd = (ImageView) findViewById(R.id.optotypeCd);
        optotypePosDa = (ImageView) findViewById(R.id.optotypeDa);
        optotypePosDb = (ImageView) findViewById(R.id.optotypeDb);
        optotypePosDc = (ImageView) findViewById(R.id.optotypeDc);
        optotypePosDd = (ImageView) findViewById(R.id.optotypeDd);

        opSelectedAa = (ImageView) findViewById(R.id.opSelectedAa);
        opSelectedAb = (ImageView) findViewById(R.id.opSelectedAb);
        opSelectedAc = (ImageView) findViewById(R.id.opSelectedAc);
        opSelectedAd = (ImageView) findViewById(R.id.opSelectedAd);
        opSelectedBa = (ImageView) findViewById(R.id.opSelectedBa);
        opSelectedBb = (ImageView) findViewById(R.id.opSelectedBb);
        opSelectedBc = (ImageView) findViewById(R.id.opSelectedBc);
        opSelectedBd = (ImageView) findViewById(R.id.opSelectedBd);
        opSelectedCa = (ImageView) findViewById(R.id.opSelectedCa);
        opSelectedCb = (ImageView) findViewById(R.id.opSelectedCb);
        opSelectedCc = (ImageView) findViewById(R.id.opSelectedCc);
        opSelectedCd = (ImageView) findViewById(R.id.opSelectedCd);

        perfilPatient = (ImageView) findViewById(R.id.imageViewInteractionPatient);

        textNamePatient = (TextView) findViewById(R.id.textViewNAmePatient);
        textLastNamePatient = (TextView) findViewById(R.id.textViewLastnamePatient);
        yearsOldPatient = (TextView) findViewById(R.id.textYearsPatient);

        Intent intentData = getIntent();
        patientExtras = intentData.getExtras();


        if (patientExtras != null){

            patient.setIdPatient(patientExtras.getString("IdPatient"));
            patient.setName(patientExtras.getString("patient"));
            patient.setYearsOld(patientExtras.getString("patientYear"));

            Bitmap photo = (Bitmap) patientExtras.get("photo");
            elements = new ElementsInteraction(this);
            elements.fillInteractionElements(patient.getYearsOld());

            initializeImage();
            showData(patient.getIdPatient(), patient.getName(), patient.getYearsOld(), photo);
            refreshActivity();

        }

    }

    /**
     * This method prepare optotypes array list
     */
    private void initializeImage() {

        arrayImage.add(optotypePosAa);
        arrayImage.add(optotypePosAb);
        arrayImage.add(optotypePosAc);
        arrayImage.add(optotypePosAd);
        arrayImage.add(optotypePosBa);
        arrayImage.add(optotypePosBb);
        arrayImage.add(optotypePosBc);
        arrayImage.add(optotypePosBd);
        arrayImage.add(optotypePosCa);
        arrayImage.add(optotypePosCb);
        arrayImage.add(optotypePosCc);
        arrayImage.add(optotypePosCd);
        arrayImage.add(optotypePosDa);
        arrayImage.add(optotypePosDb);
        arrayImage.add(optotypePosDc);
        arrayImage.add(optotypePosDd);

        arrayImageSelected.add(opSelectedAa);
        arrayImageSelected.add(opSelectedAb);
        arrayImageSelected.add(opSelectedAc);
        arrayImageSelected.add(opSelectedAd);
        arrayImageSelected.add(opSelectedBa);
        arrayImageSelected.add(opSelectedBb);
        arrayImageSelected.add(opSelectedBc);
        arrayImageSelected.add(opSelectedBd);
        arrayImageSelected.add(opSelectedCa);
        arrayImageSelected.add(opSelectedCb);
        arrayImageSelected.add(opSelectedCc);
        arrayImageSelected.add(opSelectedCd);
    }

    /**
     * This method Show patient Data in interacton activity
     * @param idPatient
     * @param patientName
     * @param yearsOld
     * @param photo
     */
    public void showData(String idPatient, String patientName, String yearsOld, Bitmap photo){

        String []completeName = patientName.split(" ");
        String [] years = yearsOld.split(" ");

        textNamePatient.setText( completeName[0] + " " + completeName[1]);
        textLastNamePatient.setText(completeName[2] + " " + completeName[3]);
        yearsOldPatient.setText("Edad: " + years[1] + " años" );

        patient.setIdPatient(idPatient);
        patient.setName(completeName[0]);
        patient.setLastName(completeName[2]);
        patient.setMiddleName(completeName[1]);
        patient.setMaidenName(completeName[3]);
        patient.setYearsOld(years[1]);

        if (photo != null)
            perfilPatient.setImageBitmap(photo);
        else
            perfilPatient.setImageResource(R.drawable.usuario_icon);
    }

    /**
     * This method reload this activity
     */
    public void refreshActivity(){

        String image = "";
        int position = 0;
        int sizeElements = 0;



        for (int x=0; x < arrayImage.size(); x++ ){

            Double number = Math.floor(Math.random() * elements.getElements().size());
            position = number.intValue();
            sizeElements = elements.getElements().size();

            try{
                image = elements.getElements().get(position).getOptotypeCode();
                Log.d("message: ", image);
                assingBipmapImage(image, arrayImage.get(x));
            }catch (Exception e){
                Log.d("message: ", "problemas con el llenado de la lista (Vacia)");
            }

        }

    }

    /**
     * This methid set a image optotypes Option by interaction
     * @param image
     * @param optotypeOption
     */
    public void assingBipmapImage (String image, ImageView optotypeOption) {

        byte[] byteCode = Base64.decode(elements.getImageOptotype(image),Base64.DEFAULT);
        Bitmap imageCode = null;

        try{
            imageCode = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
        }catch (Exception e){
            Log.d("message: ","Erro al convertir imagen");
            imageCode = null;
        }
        if (imageCode != null)
            optotypeOption.setImageBitmap(imageCode);
        else
            optotypeOption.setImageResource(R.drawable.usuario_icon);

        optotypeOption.setTag(image);
    }

}