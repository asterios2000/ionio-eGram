package asterios.eGram.app.Applications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import asterios.eGram.app.R;


public class ApplicationsAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> Titles;
    private final ArrayList<String> DateTimes;
    private final ArrayList<String> States;


    public ApplicationsAdapter(Context context,
                               ArrayList<String> Titles,
                               ArrayList<String> DateTimes,
                               ArrayList<String> States) {
        super(context, R.layout.application_list_row, Titles);
        this.context = context;
        this.Titles = Titles;
        this.DateTimes = DateTimes;
        this.States = States;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.application_list_row, parent, false);

        ImageView ApplicationIcon = (ImageView) rowView.findViewById(R.id.applicationicon);
        TextView ApplicationTitle = (TextView) rowView.findViewById(R.id.applicationTitle);
        TextView ApplicationDateTime = (TextView) rowView.findViewById(R.id.appldatetime);


        ApplicationTitle.setText(Titles.get(position)); //Application Title
        ApplicationDateTime.setText(DateTimes.get(position)); //Last Login Date

        /*Log.d("detStates", String.valueOf(States.size()));
        Log.d("detStates", String.valueOf(States.get(0)));*/
        if(States.size()>1) {
            if (States.get(position).contains("Ολοκληρωμένη")) {
                ApplicationIcon.setImageResource(R.drawable.ic_checked);
            } else {
                ApplicationIcon.setImageResource(R.drawable.ic_pending);
            }
        }


        return rowView;
    }
}
