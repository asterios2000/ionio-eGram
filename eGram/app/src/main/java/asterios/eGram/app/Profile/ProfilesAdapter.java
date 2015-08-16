package asterios.eGram.app.Profile;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import asterios.eGram.app.R;


public class ProfilesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> ScreenNames;
    private final ArrayList<String> LastLoginDates;
    private final ArrayList<String> ProfilePicturePaths;
    private final ArrayList<String> UserNames;
    private final ArrayList<Integer> Defaults;
    private final ArrayList<String> AEMS;
   /* private final ArrayList<String> Names;
    private final ArrayList<String> SurNames;
    private final ArrayList<String> Codes;
    private final ArrayList<String> AEMS;
    private final ArrayList<String> Departments;
    private final ArrayList<Integer> CurrentSemesters;
    private final ArrayList<String> PrSpoudon;
    private final ArrayList<String> Fields;
    private final ArrayList<String> RegistrationYears;
    private final ArrayList<String> RegistrationWays;*/

    public ProfilesAdapter(Context context,
                           ArrayList<String> ScreenNames,
                           ArrayList<String> LastLoginDates,
                           ArrayList<String> ProfilePicturePaths,
                           ArrayList<String> UserNames,
                           ArrayList<Integer> Defaults,
                           ArrayList<String> AEMS
                           /*,
                           ArrayList<String> Names,
                           ArrayList<String> SurNames,
                           ArrayList<String> Codes,
                           ArrayList<String> AEMS,
                           ArrayList<String> Departments,
                           ArrayList<Integer> CurrentSemesters,
                           ArrayList<String> PrSpoudon,
                           ArrayList<String> Fields,
                           ArrayList<String> RegistrationYears,
                           ArrayList<String> RegistrationWays  */ ) {
        super(context, R.layout.profile_list_row, ScreenNames);
        this.context = context;
        this.ScreenNames = ScreenNames;
        this.LastLoginDates = LastLoginDates;
        this.ProfilePicturePaths = ProfilePicturePaths;
        this.UserNames = UserNames;
        this.Defaults = Defaults;
        this.AEMS = AEMS;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.profile_list_row, parent, false);

        ImageView ProfileIcon = (ImageView) rowView.findViewById(R.id.profileicon);
        TextView ScreenName = (TextView) rowView.findViewById(R.id.profilename);
        TextView LastLoginDate = (TextView) rowView.findViewById(R.id.lastlogin);
        TextView AEM = (TextView) rowView.findViewById(R.id.AEM);
        FloatingActionButton DefaultIcon = (FloatingActionButton) rowView.findViewById(R.id.ifDefaultImage);

        if (!ProfilePicturePaths.get(position).isEmpty())
        ProfileIcon.setImageBitmap(BitmapFactory.decodeFile(ProfilePicturePaths.get(position) + "/" + UserNames.get(position) + ".png"));

        ScreenName.setText(ScreenNames.get(position));                 //Profile Name
        LastLoginDate.setText(LastLoginDates.get(position));           //Last Login Date
        AEM.setText(AEMS.get(position));                               //AEMS

        if (Defaults.get(position)==1) {
            DefaultIcon.setColorNormal(Color.parseColor("#83d0c9"));  //base color
        }


        return rowView;
    }
}
