package tegdev.optotypes;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Edgar on 29/05/2018.
 */

public class VisualTest {

    private int id;
    private int idPatient;
    private ArrayList<Optotype> optotypes = new ArrayList<Optotype>();
    private String testCode;
    private String testEye;

    public VisualTest() {
    }

    public VisualTest(int id, int idPatient, ArrayList<Optotype> optotypes, String testCode, String testEye) {
        this.id = id;
        this.idPatient = idPatient;
        this.optotypes = optotypes;
        this.testCode = testCode;
        this.testEye = testEye;
    }

    public VisualTest(String testEye) {
        this.testEye = testEye;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestEye() {
        return testEye;
    }

    public void setTestEye(String testEye) {
        this.testEye = testEye;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public ArrayList<Optotype> getOptotypes() {
        return optotypes;
    }

    public void setOptotypes(ArrayList<Optotype> optotypes) {
        this.optotypes = optotypes;
    }


    /**
     * This method generate a code for  visual test
     * @return
     */
    public String getTestCodeByInteraction (){

        Calendar date = new GregorianCalendar();
        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(date.get(Calendar.MONTH));
        String year = String.valueOf(date.get(Calendar.YEAR));

        return day+month+year;
    }

    /**
     * This method send petition for save data interaction test
     * @param context
     */
    public void save (Context context){

        RequestMedicalTest requestMedicalTest = new RequestMedicalTest(context);
        requestMedicalTest.findOrCreateTableInteraction();
        requestMedicalTest.saveTest(this, context);
    }

}
