package tegdev.optotypes;

import android.graphics.Bitmap;

/**
 * Created by Edgar on 29/05/2018.
 */

public class OptotypeForPatient {

    private String idOptotype;
    private String optotypeCode;
    private Bitmap image;

    public OptotypeForPatient() {
    }

    public OptotypeForPatient(String idOptotype, String optotypeCode, Bitmap image) {
        this.idOptotype = idOptotype;
        this.optotypeCode = optotypeCode;
        this.image = image;
    }

    public String getIdOptotype() {
        return idOptotype;
    }

    public void setIdOptotype(String idOptotype) {
        this.idOptotype = idOptotype;
    }

    public String getOptotypeCode() {
        return optotypeCode;
    }

    public void setOptotypeCode(String optotypeCode) {
        this.optotypeCode = optotypeCode;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
