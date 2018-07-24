package tegdev.optotypes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by edgar on 24/07/2018.
 */

@SuppressLint("ValidFragment")
public class ListDialog extends AppCompatDialogFragment {

    public String titleMessage;
    public String messageDialog;
    private ListView listOption;
    private ImageView test;
    protected static String year;

   // private MessageDialog.MessageDialogListener listener;*/


    public ListDialog(ImageView test) {
        this.test = test;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }

    public void setMessageDialog(String messageDialog) {
        this.messageDialog = messageDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.list_dialog, null);

        builder.setView(view)
                .setTitle(titleMessage)
                .setMessage(messageDialog)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        listOption = (ListView) view.findViewById(R.id.listOptionLocalCard);
        loadOptions();

        return builder.create();
    }

    /**
     * This method load a list with option
     */
    private void loadOptions() {

        Cursor cursor = null;
        ArrayList<String> arrayList = new ArrayList<String>();
        String query = " SELECT testYear FROM " + ImageTestDbContract.ImageTestEntry.TABLE_NAME;
        ImageTestDbHelper imageTestDbHelper = new ImageTestDbHelper(ControlForService.context);
        SQLiteDatabase db = imageTestDbHelper.getReadableDatabase();

        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                do{
                    arrayList.add("Carta para niños de " + cursor.getString(0) + " años (Ultima utilizada)");
                }while(cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {

            if(cursor != null){
                cursor.close();
            }
            db.close();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ControlForService.context, android.R.layout.simple_expandable_list_item_1, arrayList);
        listOption.setAdapter(arrayAdapter);

        actionOnElement();
    }

    /**
     * This method allow to excute action
     */
    private void actionOnElement() {

        listOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("printLog", adapterView.getAdapter().getItem(i).toString().split(" ")[4]);
                year = adapterView.getAdapter().getItem(i).toString().split(" ")[4];
                buildTestList();
            }
        });


    }

    /**
     * This method build a test fwith local information
     */
    private void buildTestList() {

        Cursor cursor = null;

        String query = " SELECT testCard, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, ";
        query = query + " rowEigth, rowNine, rowTen, rowEleven FROM " + ImageTestDbContract.ImageTestEntry.TABLE_NAME;
        query = query + " WHERE testYear = " + year;

        ImageTestDbHelper imageTestDbHelper = new ImageTestDbHelper(ControlForService.context);
        SQLiteDatabase db = imageTestDbHelper.getReadableDatabase();

        CrudRequestTestActivity.local = true;
        CrudRequestTestActivity.imagesTest.removeAll(CrudRequestTestActivity.imagesTest);

        Log.d("printLog", query);
        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                do{

                    CrudRequestTestActivity.imagesTest.add(cursor.getString(0));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(1));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(2));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(3));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(4));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(5));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(6));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(7));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(8));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(9));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(10));
                    CrudRequestTestActivity.imagesTest.add(cursor.getString(11));

                }while(cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            if (cursor != null){
                cursor.close();
            }
            db.close();
        }


        setImageTest();
        Log.d("printLn", "tamaño de arreglo: "+ CrudRequestTestActivity.imagesTest.size());

    }

    /**
     * This method show the alternative test
     */
    private void setImageTest() {

        byte[] byteCode = Base64.decode(CrudRequestTestActivity.imagesTest.get(0), Base64.DEFAULT);
        test.setTag("local");
        Bitmap image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);

        if (image != null){
            test.setImageBitmap(image);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface ListDialogListener{

        void applyData (String data);
    }

}
