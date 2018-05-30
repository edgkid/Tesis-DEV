package tegdev.optotypes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Edgar on 29/05/2018.
 */

public class MessageDialog extends AppCompatDialogFragment {

    private EditText editText;
    private MessageDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog, null);

        builder.setView(view)
                .setTitle("Dialogo personalizado")
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getActivity().getBaseContext(),"Cancelo", Toast.LENGTH_SHORT).show();

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int value = 0;
                        String data = editText.getText().toString();

                        try{
                            value = Integer.parseInt(data);
                        }catch(Exception e){
                            value = 0;
                            data = "0";
                        }finally{
                            listener.applyData(data);
                        }

                    }
                });

        editText = (EditText) view.findViewById(R.id.idData);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try{
            listener = (MessageDialogListener) context;
        }catch (ClassCastException e){

            throw  new ClassCastException(context.toString() + "must implement CustomDialogoExceptio");

        }
    }

    public interface MessageDialogListener{

        void applyData (String data);
    }

}
