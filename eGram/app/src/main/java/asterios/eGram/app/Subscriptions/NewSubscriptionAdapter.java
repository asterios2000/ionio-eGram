package asterios.eGram.app.Subscriptions;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class NewSubscriptionAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> Codes;
    private final ArrayList<String> Titles;
    private final ArrayList<String> States;
    private final ArrayList<String> CheckBoxIds;
    public static ArrayList<String> UserSelections = new ArrayList<String>();



    public NewSubscriptionAdapter(Context context,
                                  ArrayList<String> Codes,
                                  ArrayList<String> Titles,
                                  ArrayList<String> States,
                                  ArrayList<String> CheckBoxIds) {
        super(context, R.layout.add_subscription_list_row, Titles);
        this.context = context;
        this.Codes = Codes;
        this.Titles = Titles;
        this.States = States;
        this.CheckBoxIds = CheckBoxIds;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.add_subscription_list_row, parent, false);

        TextView subjectTitle = (TextView) rowView.findViewById(R.id.subjectToAdd);
        TextView subjectCode = (TextView) rowView.findViewById(R.id.subjectCode);
        final ToggleButton inOrOut = (ToggleButton) rowView.findViewById(R.id.inorout);

        UserSelections.clear();
        UserSelections.addAll(States);

        subjectTitle.setText(Titles.get(position)); //Subscription Title - Pliroforiki
        subjectCode.setText(Codes.get(position));   //Subscription Code - (H/Y100)

        if (States.get(position).equals("1"))
            inOrOut.setChecked(true);
        else
        {
            inOrOut.setChecked(false);
            inOrOut.setEnabled(false);
            rowView.setBackgroundColor(Color.LTGRAY);
        }


        inOrOut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        if (inOrOut.isChecked()) {
                            UserSelections.set(position, "1");
                                    eGramFunctions.ShowToast(inflater, context, R.drawable.ic_subscriptions, "'"+Titles.get(position) +"' "+context.getString(R.string.insertedInTheList),"Short");
                        }
                        if (!inOrOut.isChecked()){
                            UserSelections.set(position, "0");
                            eGramFunctions.ShowToast(inflater, context, R.drawable.ic_subscriptions, "'"+Titles.get(position) +"' "+context.getString(R.string.removedFromTheList),"Short");
                        }
                    }
                });


        return rowView;
    }
}
