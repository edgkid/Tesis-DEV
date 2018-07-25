package tegdev.optotypes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import java.util.ResourceBundle;

/**
 * Created by edgar on 25/07/2018.
 */

public class CrudMessageDialog {

    private Context context;
    private String title;
    private String message;

    public CrudMessageDialog(Context context) {
        this.context = context;
    }

    public CrudMessageDialog(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This method display a Dialog before dlete an  appointment
     */
    public void alertDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
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



}
