package tegdev.optotypes;

import android.graphics.Bitmap;

/**
 * Created by Edgar on 27/05/2018.
 */

public class PatientsToday {

    private int idPatient;
    private String name;
    private String yearsOld;
    private Bitmap photo;


    public PatientsToday() {
        super();
    }

    public PatientsToday(String name, String yearsOld, Bitmap photo) {
        super();
        this.name = name;
        this.yearsOld = yearsOld;
        this.photo = photo;
    }

    public PatientsToday(String name, String yearsOld, Bitmap photo, int idPatient) {
        super();
        this.name = name;
        this.yearsOld = yearsOld;
        this.photo = photo;
        this.idPatient = idPatient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

}
