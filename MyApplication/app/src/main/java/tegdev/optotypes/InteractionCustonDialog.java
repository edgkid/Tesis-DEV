package tegdev.optotypes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
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

    @SuppressLint("ValidFragment")
    public InteractionCustonDialog(Context context, String option) {
        this.context = context;
        this.option = option;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog_interaction_correct, null);

        builder.setView(view)
                .setTitle("Custon")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent dashboardActivity = new Intent(context, DashBoardActivity.class);
                        startActivity(dashboardActivity);
                    }
                });

        imageDialog = (ImageView) view.findViewById(R.id.idMessageDialogGood);

        if (option.equals("ok")){
            imageDialog.setImageResource(R.drawable.ojo_icon);
        }else if (option.equals("back")){
            imageDialog.setImageResource(R.drawable.imagenotfoud);
        }


        imageDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboardActivity = new Intent(context, DashBoardActivity.class);
                startActivity(dashboardActivity);
            }
        });

        return builder.create();
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
