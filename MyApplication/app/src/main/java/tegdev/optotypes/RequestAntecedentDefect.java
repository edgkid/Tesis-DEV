package tegdev.optotypes;

import android.content.Context;

/**
 * Created by Edgar on 30/05/2018.
 */

public class RequestAntecedentDefect {

    private String request;
    private Context context;

    public RequestAntecedentDefect(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public RequestAntecedentDefect(Context context) {
        this.context = context;
    }


}
