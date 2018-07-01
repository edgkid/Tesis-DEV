package tegdev.optotypes;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 28/05/2018.
 */

public class ElementsInteraction {

    private ArrayList<Optotype> elements = new ArrayList<Optotype>();
    private Context context;

    public ElementsInteraction(Context context) {
        this.context = context;
    }

    public ElementsInteraction() {
    }

    public ArrayList<Optotype> getElements() {
        return elements;
    }


    /**
     * This method take Optotypes
     * @param yearsOld
     */
    public void fillInteractionElements (String yearsOld){

        RequestOptotype requestOptotype = new RequestOptotype("optotypes",context);
        requestOptotype.findOptotypes();

        elements = requestOptotype.getOptotypes(yearsOld);
    }

    /**
     * This method validate Optotypes on list
     * @param position
     * @param sizeElements
     * @return
     */
    public int validateElements (int position, int sizeElements){

        if ((position + 1 >= sizeElements)){
            position = 0;
        }

        return position;
    }

    /**
     * This method assing position by even number
     * @param position
     * @return
     */
    public boolean evenNumber (int position){

        boolean value = false;

        if ((position % 2) == 0){
            value = true;
        }

        return value;
    }

    /**
     * This Method assing position by prime number
     * @param position
     * @param size
     * @return
     */
    public boolean primeNumber(int position, int size){

        boolean value = false;
        int results = 0;

        for (int i = 1; i<=size; i++){

            if (position % i == 0)
                results ++;
            if (results > 2){
                value = true;
                break;
            }
        }

        return value;
    }

    /**
     * This methond search a image by optoype code
     * @param code
     * @return
     */
    public String getImageOptotype (String code){

        RequestOptotype requestOptotype = new RequestOptotype("optotypes",context);

        return requestOptotype.getBipmapOptotype(code);
    }


}
