package tegdev.optotypes;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class InteractionActivity extends AppCompatActivity {

    TextView textNames;
    TextView textLastNames;
    TextView textYearsOld;

    ImageView imageOptotype;
    ImageView imagePerfil;
    ImageView imageAnimation;

    ImageView imageOptotypeA;
    ImageView imageOptotypeB;
    ImageView imageOptotypeC;

    ImageView imageProgress;

    TextView textDebug;
    TextView textDebugB;

    ElementsInteraction elements;
    Interaction controlInteraction;

    Context contextActivity;
    Patient patient = new Patient();
    Bundle patientExtras;
    boolean flag = true;
    int action = 0;

    SoundMediaPlayer mediaPlayer = new SoundMediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);

        contextActivity = this;
        controlInteraction = new Interaction();
        elements = new ElementsInteraction(this);

        textDebug = (TextView) findViewById(R.id.textDebug);
        textDebugB = (TextView) findViewById(R.id.textDebug2);

        textNames = (TextView) findViewById(R.id.textViewNAmePatient);
        textLastNames = (TextView) findViewById(R.id.textViewLastnamePatient);
        textYearsOld = (TextView) findViewById(R.id.textYearsPatient);

        imageOptotype = (ImageView) findViewById(R.id.imageViewOptotype);
        imageAnimation = (ImageView) findViewById(R.id.imageInteractionEmotion);
        imagePerfil = (ImageView) findViewById(R.id.imageViewInteractionPatient);

        imageProgress = (ImageView) findViewById(R.id.progressBar);

        imageOptotypeA = (ImageView) findViewById(R.id.imageOptotypeOptionA);
        imageOptotypeB = (ImageView) findViewById(R.id.imageOptotypeOptionB);
        imageOptotypeC = (ImageView) findViewById(R.id.imageOptotypeOptionC);


        imageOptotype.setOnLongClickListener(logClickListener);

        imageOptotypeA.setOnDragListener(dragListenerA);
        imageOptotypeB.setOnDragListener(dragListenerB);
        imageOptotypeC.setOnDragListener(dragListenerC);

        Intent intentData = getIntent();
        patientExtras = intentData.getExtras();


        if (patientExtras != null){

            String idPatient = patientExtras.getString("IdPatient");
            String patient = patientExtras.getString("patient");
            String yearsOld = patientExtras.getString("patientYear");
            Bitmap photo = (Bitmap) patientExtras.get("photo");

            showData(idPatient, patient, yearsOld, photo);

        }

        loadOptotypes();
        refreshActivity();

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

        textNames.setText( completeName[0] + " " + completeName[1]);
        textLastNames.setText(completeName[2] + " " + completeName[3]);
        textYearsOld.setText("Edad: " + years[1] + " años" );

        patient.setIdPatient(idPatient);
        patient.setName(completeName[0]);
        patient.setLastName(completeName[2]);
        patient.setMiddleName(completeName[1]);
        patient.setMaidenName(completeName[3]);
        patient.setYearsOld(years[1]);

        if (photo != null)
            imagePerfil.setImageBitmap(photo);
        else
            imagePerfil.setImageResource(R.drawable.usuario_icon);


    }

    /**
     * This method initialize a procees fill List with optotypes
     */
    public void loadOptotypes(){

        elements.fillInteractionElements(patient.getYearsOld());
    }

    /**
     * This method reload this activity
     */
    public void refreshActivity(){

        String image = "";
        int position = 0;
        int sizeElements = 0;

        Double number = Math.floor(Math.random() * elements.getElements().size());
        position = number.intValue();
        sizeElements = elements.getElements().size();

        Log.d("message: ", String.valueOf(position));

        try{
            image = elements.getElements().get(position).getOptotypeCode();
            Log.d("message: ", image);
            assingBipmapImage(image, imageOptotype);
            assignOptotypeOptions(position, sizeElements, image);
        }catch (Exception e){
            Log.d("message: ", "problemas con el llenado de la lista (Vacia)");
        }

    }

    /**
     * This method orders elements
     * @param position
     * @param size
     * @param image
     */
    public void assignOptotypeOptions(int position, int size, String image){

        if (elements.primeNumber(position, size) && elements.evenNumber(position)){

            assingBipmapImage(image, imageOptotypeA);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeB);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeC);

        }else if (elements.primeNumber(position, size) || ! elements.evenNumber(position)){

            assingBipmapImage(image, imageOptotypeC);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeB);

            position ++;

            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeA);

        }else{
            assingBipmapImage(image, imageOptotypeB);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeA);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeC);
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

    ///////////////////////////////Drag Section////////////////////////////////////////////////////
    View.OnLongClickListener  logClickListener = new View.OnLongClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onLongClick(View v) {

            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                v.startDragAndDrop(data,myShadowBuilder,v,0);
            } else {
                v.startDrag(data,myShadowBuilder,v,0);
            }

            return true;
        }
    };

    View.OnDragListener dragListenerA = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            actionDrag(dragEvent, view, 1);

            return true;
        }
    };

    View.OnDragListener dragListenerB = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            actionDrag(dragEvent, view, 2);

            return true;
        }
    };

    View.OnDragListener dragListenerC = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            actionDrag(dragEvent, view, 3);

            return true;
        }
    };

    /**
     * This method controla las acciones del Drag and Drop
     * @param dragEvent
     * @param view
     * @param option
     */
    public void actionDrag (int dragEvent, View view, int option){

        MediaPlayer soundDrag = null;

        switch (dragEvent){
            //Accion para mover elemento
            case DragEvent.ACTION_DRAG_ENTERED:
                //identificar si es correcta la selección
                if (option == 1 ) {
                    if (imageOptotypeA.getTag().equals(imageOptotype.getTag())) {
                        imageOptotypeA.setBackgroundColor(Color.rgb(0, 255, 102));
                        soundDrag = MediaPlayer.create(this, R.raw.ding);
                        soundDrag.start();
                    }else{
                        imageOptotypeA.setBackgroundColor(Color.rgb(183,28,28));
                        soundDrag = MediaPlayer.create(this, R.raw.wrong);
                        soundDrag.start();
                    }
                }else if(option == 2){
                    if (imageOptotypeB.getTag().equals(imageOptotype.getTag())){
                        imageOptotypeB.setBackgroundColor(Color.rgb(0, 255, 102));
                        soundDrag = MediaPlayer.create(this, R.raw.ding);
                        soundDrag.start();
                    }else{
                        imageOptotypeB.setBackgroundColor(Color.rgb(183,28,28));
                        soundDrag = MediaPlayer.create(this, R.raw.wrong);
                        soundDrag.start();
                    }
                }else if (option == 3){
                    if (imageOptotypeC.getTag().equals(imageOptotype.getTag())){
                        imageOptotypeC.setBackgroundColor(Color.rgb(0, 255, 102));
                        soundDrag = MediaPlayer.create(this, R.raw.ding);
                        soundDrag.start();
                    }else{
                        imageOptotypeC.setBackgroundColor(Color.rgb(183,28,28));
                        soundDrag = MediaPlayer.create(this, R.raw.wrong);
                        soundDrag.start();
                    }

                }
                break;

            case DragEvent.ACTION_DRAG_EXITED:

                if (option == 1 ) {
                    imageOptotypeA.setBackgroundColor(Color.rgb(255,255,255));
                }else if(option == 2){
                    imageOptotypeB.setBackgroundColor(Color.rgb(255,255,255));
                }else if (option == 3){
                    imageOptotypeC.setBackgroundColor(Color.rgb(255,255,255));
                }

                break;
            case DragEvent.ACTION_DROP:

                //accionar sobre el elemeno seleccionado

                if (option == 1 ) {
                    if (imageOptotypeA.getTag().equals(imageOptotype.getTag()))
                        workWithRealOption(imageOptotype, imageOptotypeA);
                    else
                        workWithBackOption(imageOptotype, imageOptotypeA);
                }else if(option == 2){
                    if (imageOptotypeB.getTag().equals(imageOptotype.getTag()))
                        workWithRealOption(imageOptotype, imageOptotypeB);
                    else
                        workWithBackOption(imageOptotype, imageOptotypeB);
                }else if (option == 3){
                    if (imageOptotypeC.getTag().equals(imageOptotype.getTag()))
                        workWithRealOption(imageOptotype, imageOptotypeC);
                    else
                        workWithBackOption(imageOptotype, imageOptotypeC);
                }

                refreshActivity();
                break;
        }
    }

    /**
     * Este metodo permite operar la opcion correcta en el Drag and Drop
     * @param optotype
     * @param option
     */
    public void workWithRealOption (ImageView optotype, ImageView option){

        int sizeElements =0;
        int total = 0;
        option.setBackgroundColor(Color.rgb(255, 255, 255));

        ArrayList<Optotype> optotypes = elements.getElements();
        Iterator<Optotype> iterator = optotypes.iterator();

        total = controlInteraction.getTotalOptotypes();

        while (iterator.hasNext()){

            if (iterator.next().getOptotypeCode().equals(optotype.getTag())){
                total ++;
                controlInteraction.setTotalOptotypes(total);
                textDebugB.setText(Integer.toString(controlInteraction.getTotalOptotypes()));
                controlInteraction.getOptotypes().add(optotypes.get(sizeElements));
                if (sizeElements != 0 ) {
                    controlInteraction.setTotalOptotypes(total);
                    elements.getElements().remove(sizeElements);
                }
                break;
            }
            sizeElements ++;

            if (controlInteraction.getTotalOptotypes() >= 12)
                break;
        }

        if (controlInteraction.getTotalOptotypes() >= 12){
            Toast.makeText(this, "fin de la interacción", Toast.LENGTH_SHORT).show();
            RequestInteraction requestInteraction = new RequestInteraction(this);
            requestInteraction.processInteraction(controlInteraction, patient);

            RequestMedicalTest requestMedicalTest = new RequestMedicalTest(this);
            requestMedicalTest.sendDataInteraction(patient, action, patient.getYearsOld());

            //alertDialog();
            InteractionCustonDialog dialog = new InteractionCustonDialog(this, "ok");
            dialog.show(getSupportFragmentManager(),"dialog");

        }

        imageAnimation.setImageResource(R.drawable.emotion_example);
        fillProgressBar();
        mediaPlayer.setImageOptotype(imageOptotype.getTag().toString().split("_")[0]);
        mediaPlayer.setContext(this);
        mediaPlayer.soundAnswer();

    }

    /**
     * este metodo permite procesor las opciones incorrectas en el drag and drop
     * @param optotype
     * @param option
     */
    public void workWithBackOption (ImageView optotype, ImageView option){
        option.setBackgroundColor(Color.rgb(255, 255, 255));
        imageAnimation.setImageResource(R.drawable.triste);
        //// Agregar sonido de oh! no
        interactionSound(false);
    }

    /**
     * This method fill a progress bar on interaction activity
     */
    public void fillProgressBar(){

        switch (controlInteraction.getTotalOptotypes()){
            case 2:
                imageProgress.setImageResource(R.drawable.completebar_2);
                break;
            case 4:
                imageProgress.setImageResource(R.drawable.completebar_4);
                break;
            case 6:
                imageProgress.setImageResource(R.drawable.completebar_6);
                break;
            case 8:
                imageProgress.setImageResource(R.drawable.completebar_8);
                break;
            case 10:
                imageProgress.setImageResource(R.drawable.completebar_10);
                break;
            case 12:
                imageProgress.setImageResource(R.drawable.completebar_12);
                break;
            case 14:
                imageProgress.setImageResource(R.drawable.completebar_14);
                break;
            case 16:
                imageProgress.setImageResource(R.drawable.completebar_16);
                break;
        }
    }

    /**
     * This method allow sound interaction
     * @param value
     */
    public void interactionSound(boolean value){

        MediaPlayer interactionSound = null;
        int sound = (int) (Math.random() * 3) + 1;

        //Log.d("sonido: ", Integer.toString(sound));
        if (value){
            switch (sound){
                case 1:
                    interactionSound = MediaPlayer.create(this, R.raw.yes);
                    break;
                case 2:
                    interactionSound = MediaPlayer.create(this, R.raw.woohoo);
                    break;
                case 3:
                    interactionSound = MediaPlayer.create(this, R.raw.bienhecho);
                    break;
            }
        }else{

            switch (sound){
                case 1:
                    interactionSound = MediaPlayer.create(this, R.raw.incorrect);
                    break;
                case 2:
                    interactionSound = MediaPlayer.create(this, R.raw.heno);
                    break;
                case 3:
                    interactionSound = MediaPlayer.create(this, R.raw.ooh);
                    break;
            }
        }

        interactionSound.start();

    }

}
