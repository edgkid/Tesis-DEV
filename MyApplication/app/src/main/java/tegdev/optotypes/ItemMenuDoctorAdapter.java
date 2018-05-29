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

public class ItemMenuDoctorAdapter extends ArrayAdapter<ItemMenuDoctor> {

    Context context;
    int layoutResourceId;
    ItemMenuDoctor data[];

    public ItemMenuDoctorAdapter(Context context, int layoutResourceId, ItemMenuDoctor  [] data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    public View getView (int position, View convertView, ViewGroup parent){

        View row = convertView;
        ItemMenuDoctorAdapter.ItemMenuDoctorHolder holder = null;

        //verificar entrada de los datos
        if (row == null){

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemMenuDoctorAdapter.ItemMenuDoctorHolder();
            holder.icon = (ImageView) row.findViewById(R.id.crudIconDoctor);
            holder.option = (TextView) row.findViewById(R.id.textOptionDoctor);
            row.setTag(holder);

        }else{
            holder = (ItemMenuDoctorAdapter.ItemMenuDoctorHolder) row.getTag();
        }

        ItemMenuDoctor items = data[position];
        holder.icon.setImageResource(items.getIcon());
        holder.option.setText(items.getOption());

        return row;
    }

    static class ItemMenuDoctorHolder{

        ImageView icon;
        TextView option;
    }


}