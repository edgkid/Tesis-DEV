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
 * Created by Edgar on 27/05/2018.
 */

public class ConfgDialog extends AppCompatDialogFragment {

    private EditText ipWebService;
    private EditText ipShowClient;
    private EditText portShowClient;
    private ConfgDialogListener listenerConfg;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.confg_connection, null);

        builder.setView(view)
                .setTitle("Parametros de Connexión")
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

                        String ipWeb = ipWebService.getText().toString();
                        String ipShow = ipShowClient.getText().toString();
                        String portShow = portShowClient.getText().toString();
                        listenerConfg.applyData(ipWeb, ipShow, portShow);

                    }
                });

        ipWebService = (EditText) view.findViewById(R.id.idIpWebservice);
        ipWebService.setText(ConfgConnect.getIpWebService());
        ipShowClient = (EditText) view.findViewById(R.id.idIpShowClient);
        ipShowClient.setText(ConfgConnect.getIpShowTest());
        portShowClient = (EditText) view.findViewById(R.id.idPortShowClient);
        portShowClient.setText(ConfgConnect.getPortConecction());

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try{
            listenerConfg = (ConfgDialogListener) context;
        }catch (ClassCastException e){

            throw  new ClassCastException(context.toString() + "must implement CustomDialogoExceptio");

        }
    }


    public interface ConfgDialogListener{

        void applyData(String ipWebService, String ipShowClient, String portShowClient);

    }

}