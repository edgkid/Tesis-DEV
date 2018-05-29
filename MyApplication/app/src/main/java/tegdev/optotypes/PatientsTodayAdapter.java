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
 * Created by Edgar on 27/05/2018.
 */

public class PatientsTodayAdapter extends ArrayAdapter<PatientsToday> {

    Context context;
    int layoutResourceId;
    PatientsToday data[];

    public PatientsTodayAdapter (Context context, int layoutResourceId, PatientsToday [] data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    public View getView (int position, View convertView, ViewGroup parent){

        View row = convertView;
        PatientsTodayHolder holder = null;

        //verificar entrada de los datos
        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PatientsTodayHolder();
            holder.photo = (ImageView) row.findViewById(R.id.photoPatientToday);
            holder.name = (TextView) row.findViewById(R.id.namePatientToday);
            holder.yearsOld = (TextView) row.findViewById(R.id.yearsOldPatientToday);
            holder.code = (TextView) row.findViewById(R.id.codePatient);

            row.setTag(holder);
        }else{
            holder = (PatientsTodayHolder) row.getTag();
        }

        PatientsToday patients = data[position];
        if (patients.getPhoto() != null)
            holder.photo.setImageBitmap(patients.getPhoto());
        else
            holder.photo.setImageResource(R.drawable.usuario_icon);

        holder.name.setText(patients.getName());
        holder.yearsOld.setText(patients.getYearsOld());
        holder.code.setText(String.valueOf(patients.getIdPatient()));

        return row;

    }

    static class PatientsTodayHolder{
        ImageView photo;
        TextView name;
        TextView yearsOld;
        TextView code;
    }

}
