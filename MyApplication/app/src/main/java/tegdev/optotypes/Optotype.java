package tegdev.optotypes;

/**
 * Created by Edgar on 28/05/2018.
 */

public class Optotype {

    private String idOptotype;
    private String optotypeCode;
    private String optotypeName;
    private String image;


    public Optotype() {
    }

    public Optotype(String idOptotype, String optotypeCode, String optotypeName, String image) {
        this.idOptotype = idOptotype;
        this.optotypeCode = optotypeCode;
        this.optotypeName = optotypeName;
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

    public String getOptotypeName() {
        return optotypeName;
    }

    public void setOptotypeName(String optotypeName) {
        this.optotypeName = optotypeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
