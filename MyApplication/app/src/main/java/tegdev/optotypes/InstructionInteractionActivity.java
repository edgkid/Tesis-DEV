package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InstructionInteractionActivity extends AppCompatActivity {

    TextView textMessageInstruction;
    TextView textNextActivity;
    ImageView imageInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_interaction);

        textMessageInstruction = (TextView) findViewById(R.id.idTextInstruction);
        textNextActivity = (TextView) findViewById(R.id.textNextActivity);
        imageInstruction = (ImageView) findViewById(R.id.idImageInstruction);

        Intent intentData = getIntent();
        Bundle patientExtras = intentData.getExtras();


        if (patientExtras != null){

            String idPatient = patientExtras.getString("IdPatient");
            String patient = patientExtras.getString("patient");
            String yearsOld = patientExtras.getString("patientYear");
            Bitmap photo = (Bitmap) patientExtras.get("photo");

            showInstruction(yearsOld);
            actionOnActivity(yearsOld, patient, idPatient, photo);
        }

    }

    /**
     *
     * This method exuce action touch in this activity
     * @param yearsOld
     * @param patient
     * @param idPatient
     * @param photo
     */
    private void actionOnActivity(String yearsOld, String patient, String idPatient, Bitmap photo) {

        Intent interactionActivity = null;
        Context context = this;

        if (Integer.parseInt(yearsOld.split(" ")[1]) < 3){
            interactionActivity=  new Intent(context, KeyBoardInteractionActivity.class);
        }else if(Integer.parseInt(yearsOld.split(" ")[1]) >= 3){
            interactionActivity = new Intent(context, InteractionActivity.class);
        }

        interactionActivity.putExtra("IdPatient", idPatient);
        interactionActivity.putExtra("patient", patient);
        interactionActivity.putExtra("patientYear", yearsOld);
        interactionActivity.putExtra("photo", photo);


        final Intent finalInteraction = interactionActivity;

        textNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (finalInteraction != null){
                    finalInteraction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(finalInteraction);
                    finish();
                }

            }
        });

        textMessageInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalInteraction != null){
                    finalInteraction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(finalInteraction);
                    finish();
                }
            }
        });

        imageInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalInteraction != null){
                    finalInteraction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(finalInteraction);
                    finish();
                }
            }
        });


    }

    /**
     * This method show interaction instruction
     */
    private void showInstruction(String edad){

        String instruction = "";

        if (Integer.parseInt(edad.split(" ")[1]) <= 2 ){
            instruction = "Por favor ayude a su representado a pulsar las imagenes que identifica y reconoce. Pida a su representado que nombre la imagen que observa.";
            imageInstruction.setImageResource(R.drawable.key_board);
        }else{
            instruction = "Por favor ayude a su representado a arratrar las imagenes que identifica y reconoce sobre su par correspondiente. Pida a su representado que nombre la imagen que observa.";
            imageInstruction.setImageResource(R.drawable.interaction);
        }

        textMessageInstruction.setText(instruction);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
