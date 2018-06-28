package tegdev.optotypes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Edgar on 22/06/2018.
 */

@SuppressLint("ValidFragment")
public class InteractionCustonDialog extends AppCompatDialogFragment {

    private ImageView imageDialog;
    private Context context;
    private String option;
    private String title;

    private String idPatient;
    private String patient;
    private String yearsOld;
    private Bitmap photo;

    @SuppressLint("ValidFragment")
    public InteractionCustonDialog(Context context, String option, String title) {
        this.context = context;
        this.option = option;
        this.title = title;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog_interaction_correct, null);

        builder.setView(view)
                .setTitle(title)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nextActivity();
                    }
                });

        imageDialog = (ImageView) view.findViewById(R.id.idMessageDialogGood);

        if (option.equals("ok")){
            imageDialog.setImageResource(R.drawable.good_job);
        }else if (option.equals("back")){
            imageDialog.setImageResource(R.drawable.back_job);
        }


        imageDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });

        return builder.create();
    }

    /**
     * this method allow go to dashboard activity or second interaction activity
     */
    private void nextActivity(){

        /*Intent dashboardActivity = new Intent(context, DashBoardActivity.class);
        startActivity(dashboardActivity);*/

        Intent nextActivity = null;

        if (option.equals("ok")){

            nextActivity = new Intent(context, ResultInteractionActivity.class);
        } else if (option.equals("back")){
            nextActivity = new Intent(context, KeyBoardInteractionActivity.class);
        }

        nextActivity.putExtra("IdPatient", idPatient);
        nextActivity.putExtra("patient", patient);
        nextActivity.putExtra("patientYear", "_ " + yearsOld);
        nextActivity.putExtra("photo", photo);

        Log.d("message", "Dialog: "+ patient);

        startActivity(nextActivity);

    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getYearsOld() {
        return yearsOld;
    }

    public void setYearsOld(String yearsOld) {
        this.yearsOld = yearsOld;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
