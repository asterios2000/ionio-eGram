package asterios.eGram.app.Subscriptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import asterios.eGram.app.R;


public class SubscriptionsAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> Codes;
    private final ArrayList<String> Titles;
    private final ArrayList<String> Semesters;
    private final ArrayList<String> Types;
    private final ArrayList<String> DM;
    private final ArrayList<String> Hours;
    private final ArrayList<String> ECTS;


    public SubscriptionsAdapter(Context context,
                                ArrayList<String> Codes,
                                ArrayList<String> Titles,
                                ArrayList<String> Semesters,
                                ArrayList<String> Types,
                                ArrayList<String> DM,
                                ArrayList<String> Hours,
                                ArrayList<String> ECTS) {
        super(context, R.layout.subscription_list_row, Titles);
        this.context = context;
        this.Codes = Codes;
        this.Titles = Titles;
        this.Semesters = Semesters;
        this.Types = Types;
        this.DM = DM;
        this.Hours = Hours;
        this.ECTS = ECTS;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.subscription_list_row, parent, false);

        TextView subjectType = (TextView) rowView.findViewById(R.id.subjectType);
        TextView subjectCode = (TextView) rowView.findViewById(R.id.subjectCode);
        TextView subjectTitle = (TextView) rowView.findViewById(R.id.subjectTitle);
        TextView subjectSemester = (TextView) rowView.findViewById(R.id.subjectSemester);
        TextView subjectDM = (TextView) rowView.findViewById(R.id.subjectDM);
        TextView subjectHours = (TextView) rowView.findViewById(R.id.subjectHours);
        TextView subjectECTS = (TextView) rowView.findViewById(R.id.subjectECTS);


        subjectType.setText(Types.get(position));        //Subscription type  - YE
        subjectCode.setText(Codes.get(position));        //Subscription Code - (H/Y100)
        subjectTitle.setText(Titles.get(position));      //Subscription Title - Pliroforiki
        subjectSemester.setText(Semesters.get(position));//Subscription Semester - ST
        subjectDM.setText(DM.get(position));             //Subscription DM
        subjectHours.setText(Hours.get(position));       //Subscription Hours
        subjectECTS.setText(ECTS.get(position));         //Subscription ECTS

        return rowView;
    }
}
