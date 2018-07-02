package tegdev.optotypes;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Edgar on 01/06/2018.
 */

public class SoundMediaPlayer extends AppCompatActivity {

    private String imageOptotype;
    private Context context;

    public SoundMediaPlayer() {

    }

    public SoundMediaPlayer(String imageOptotype, Context context) {
        this.imageOptotype = imageOptotype;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getImageOptotype() {
        return imageOptotype;
    }


    public void setImageOptotype(String imageOptotype) {
        this.imageOptotype = imageOptotype;
    }

    /**
     * This method action play sound answer
     */
    public void soundAnswer(){

        //String[] answer = imageOptotype.getTag().toString().split("_");
        MediaPlayer mediaPlayer = null;

        Log.d("edgar", this.imageOptotype);
        switch (this.imageOptotype){

            case "arbol":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.arbol);
                break;
            case "avion":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.avion);
                break;
            case "bandera":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.bandera);
                break;
            case "barco":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.barco);
                break;
            case "bombillo":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.luz);
                break;
            case "botella":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.agua);
                break;
            case "camara":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.camara);
                break;
            case "cambur":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.banana);
                break;
            case "camion":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.camion);
                break;
            case "carro":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.carro);
                break;
            case "carita":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.carita);
                break;
            case "casa":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.casa);
                break;
            case "circulo":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.circulo);
                break;
            case "corazon":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.corazon);
                break;
            case "cuadrado":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.cuadrado);
                break;
            case "estrella":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.estrella);
                break;
            case "flor":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.flor);
                break;
            case "helado":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.helado);
                break;
            case "hueso":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.hueso);
                break;
            case "lapiz":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.lapiz);
                break;
            case "mariposa":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.mariposa);
                break;
            case "pelota":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.pelota);
                break;
            case "pez":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.pez);
                break;
            case "snellen":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.snellen);
                break;
            case "sol":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.sol);
                break;
            case "telefono":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.telefono);
                break;
            case "televisor":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.televisor);
                break;
            case "tetero":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.tetero);
                break;

        }

        try{
            mediaPlayer.start();
        }catch (Exception e){
            Log.d("sonido", this.imageOptotype);
        }
    }

}
