package asterios.eGram.app.Student;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class StudentPageFragment extends Fragment {

    public userDataDB upDB;
    public UserProfile up;
	public StudentPageFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        container.removeAllViews();
        upDB = new userDataDB(getActivity());
        up = new UserProfile();

        // Get default profile's ID from DB
        int ProfileID = upDB.getDefaultProfilesID();

        // Get The profile from the DB
        up = upDB.getProfileByID(ProfileID);

        final View rootView = inflater.inflate(R.layout.fragment_student_page, container, false);

        //populate the Student page elements with default profile details

        final ImageView profileImage = (ImageView) rootView.findViewById(R.id.sp_profileImage);//Profile photo path
        final TextView fullName = (TextView) rootView.findViewById(R.id.sp_fullname);          //Fullname
        final TextView AEM = (TextView) rootView.findViewById(R.id.sp_ΑΕΜ);                    //Aem
        final TextView semester = (TextView) rootView.findViewById(R.id.sp_semester);          //Current Semester
        final TextView Subjects = (TextView) rootView.findViewById(R.id.sp_subjects);          //Total subjects passed
        final TextView ECTS = (TextView) rootView.findViewById(R.id.sp_ECTS);                  //Total ECTS points
        final TextView spUpdates = (TextView) rootView.findViewById(R.id.sp_updates);          //To view the updates


        if(!up.getProfilePicturePath().isEmpty())
        profileImage.setImageBitmap(BitmapFactory.decodeFile(up.getProfilePicturePath() + "/" + up.getUserName()+".png"));
        else profileImage.setImageResource(R.drawable.ic_profile);

        fullName.setText(eGramFunctions.properCase(up.getSurName())+" "+eGramFunctions.properCase(up.getName()));
        AEM.setText(up.getAEM());
        semester.setText(eGramFunctions.properCase(String.valueOf(up.getCurSemester()))+getString(R.string.studentAtSemester));   //th Semester
        Subjects.setText(up.getTotalSubjectsPassed()+" "+getString(R.string.studentTotalSubjectsPassed));
        ECTS.setText(up.getTotalECTS()+" "+getString(R.string.studentTotalEcts));

        int generalAnn = upDB.getAnnouncementsSum().get(1);
        int secreteriatAnn = upDB.getAnnouncementsSum().get(0);
        if (generalAnn>0 && secreteriatAnn >0)
        spUpdates.setText(getString(R.string.youHave)+"\n "+
                          String.valueOf(secreteriatAnn)+" "+getString(R.string.SecreteriatAnnouncements)+" "+getString(R.string.and)+"\n"+
                          String.valueOf(generalAnn)+" " + getString(R.string.GeneralAnnouncements)+".");
        else spUpdates.setText(getString(R.string.noAnnouncements));


       final ImageView more = (ImageView) rootView.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MoreFragment More = new MoreFragment();
                eGramFunctions.AddFragment(More,getActivity());


            }
        });

        return rootView;
    }
}