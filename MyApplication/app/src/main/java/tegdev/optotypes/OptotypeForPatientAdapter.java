package tegdev.optotypes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Edgar on 29/05/2018.
 */

public class OptotypeForPatientAdapter extends ArrayAdapter<OptotypeForPatient> {

    Context context;
    int layoutResourceId;
    OptotypeForPatient data[];

    public OptotypeForPatientAdapter (Context context, int layoutResourceId, OptotypeForPatient [] data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    public View getView (int position, View convertView, ViewGroup parent){

        View row = convertView;
        OptotypeForPatientHolder holder = null;


        //verificar entrada de los datos
        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OptotypeForPatientHolder();
            holder.photo = (ImageView) row.findViewById(R.id.imageoptotypeResult);
            holder.name = (TextView) row.findViewById(R.id.optotypeName);

            row.setTag(holder);
        }else{
            holder = (OptotypeForPatientHolder) row.getTag();
        }

        OptotypeForPatient optotypes = data[position];
        if (optotypes.getImage() != null)
            holder.photo.setImageBitmap(optotypes.getImage());
        else
            holder.photo.setImageResource(R.drawable.usuario_icon);

        holder.name.setText(optotypes.getOptotypeCode());

        return row;

    }

    static class OptotypeForPatientHolder{
        ImageView photo;
        TextView name;
    }

}
