package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultInteractionActivity extends AppCompatActivity {

    ImageView imagePerfil;
    ImageView imageOptotype;

    TextView textNames;
    TextView  textLastNames;
    TextView  textYearsOld;

    ListView interactionResult;

    Bundle patientExtras;
    Context contextActivity;

    SoundMediaPlayer mediaPlayer = new SoundMediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_interaction);

        contextActivity = this;
        imagePerfil = (ImageView) findViewById(R.id.imageViewResultPatient);
        imageOptotype = (ImageView) findViewById(R.id.imageOptometriCard);

        textNames = (TextView) findViewById(R.id.textViewNAmePatient);
        textLastNames = (TextView) findViewById(R.id.textViewLastnamePatient);
        textYearsOld = (TextView) findViewById(R.id.textYearsPatient);
        interactionResult = (ListView) findViewById(R.id.listResult);

        imageOptotype.setVisibility(View.INVISIBLE);

        Intent intentData = getIntent();
        patientExtras = intentData.getExtras();

        if (patientExtras != null){

            Log.d("message", "Existen Datos");
            String idPatient = patientExtras.getString("IdPatient");
            String patient = patientExtras.getString("patient");
            String yearsOld = patientExtras.getString("patientYear");
            Bitmap photo = (Bitmap) patientExtras.get("photo");

            showData(idPatient, patient, yearsOld, photo);
            //loadListOptotypes(idPatient);
        }

    }

    /**
     * This method show a patient data
     * @param idPatient
     * @param patientName
     * @param yearsOld
     * @param photo
     */
    public void showData(String idPatient, String patientName, String yearsOld, Bitmap photo){

        String []completeName = patientName.split(" ");
        String [] years = yearsOld.split(" ");

        textNames.setText( completeName[0] + " " + completeName[1]);
        textLastNames.setText(completeName[2] + " " + completeName[3]);
        textYearsOld.setText("Edad: " + years[1] + " años" );

        if (photo != null)
            imagePerfil.setImageBitmap(photo);
        else
            imagePerfil.setImageResource(R.drawable.usuario_icon);
    }


    /**
     * This method load a list with optotype interaction
     */
    public void loadListOptotypes (String idPatient){

        int countValue = 0;
        ArrayList<String> optotypesId = new ArrayList<String>();

        RequestMedicalTest request = new RequestMedicalTest(this);
        //optotypesId = request.takeOptotypesByTest(idPatient);
        optotypesId = request.getOptotypes(idPatient);

        OptotypeForPatient optotypesData[] = new OptotypeForPatient[optotypesId.size()];
        OptotypeForPatient optotypes[] = request.getOptotypes(optotypesId.size(), optotypesId);

        while (countValue < optotypesId.size() ){

            optotypesData[countValue] = new OptotypeForPatient (optotypes[countValue].getIdOptotype(),optotypes[countValue].getOptotypeCode(),optotypes[countValue].getImage());
            countValue ++;

        }

        OptotypeForPatientAdapter optotypesAdtapter = new OptotypeForPatientAdapter(this, R.layout.listview_item_optotypes_row,optotypesData);
        interactionResult.setAdapter(optotypesAdtapter);

        actionOnElement();
    }

    /**
     * This method excute action on list optotypes
     */
    public void actionOnElement(){

        interactionResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OptotypeForPatient  optotype = (OptotypeForPatient) parent.getAdapter().getItem(position);

                mediaPlayer.setContext(contextActivity);
                mediaPlayer.setImageOptotype(optotype.getOptotypeCode().toString().split("_")[0]);
                mediaPlayer.soundAnswer();

                interactionElement(optotype);
            }
        });

    }

    /**
     * This method allow interaction on Result INteraction Activity
     * @param optotype
     */
    public void interactionElement(OptotypeForPatient  optotype){

        Log.d("message", "lpl-" + optotype.getOptotypeCode());

        if (optotype.getImage() != null){
            imageOptotype.setImageBitmap(optotype.getImage());
        }else{
            imageOptotype.setImageResource(R.drawable.imagenotfoud);
        }

        imageOptotype.setVisibility(View.VISIBLE);

    }
}
