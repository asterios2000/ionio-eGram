package asterios.eGram.app.Grades;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;

public class GradesFragment extends Fragment {
    SparseArray<Group> groups = new SparseArray<Group>();
    GradesAdapter adapter;
    ExpandableListView listView;
    public userDataDB upDB;
    public UserProfile up;
    public List<UserGrades> uGrades;
    public int ProfileID;

    public GradesFragment() { }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


         listView.setAdapter(adapter);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new GradesAdapter(getActivity(), groups);

        upDB = new userDataDB(getActivity());

        // Get default profile's ID from DB
        ProfileID = upDB.getDefaultProfilesID();
        up = upDB.getProfileByID(ProfileID);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        listView = (ExpandableListView) rootView.findViewById(R.id.listView);

        //Header DETAILS
        TextView fullName = (TextView) rootView.findViewById(R.id.NameAtGradesHeader);
        TextView AEM = (TextView) rootView.findViewById(R.id.aemAtGradesHeader);
        TextView totalDetails = (TextView) rootView.findViewById(R.id.totalDetails);

        fullName.setText(eGramFunctions.properCase(up.getSurName())+" "+eGramFunctions.properCase(up.getName()));
        AEM.setText(up.getAEM());
        totalDetails.setText(getString(R.string.MOTitle)+up.getMO()+" "+
                getString(R.string.DMTitle)+upDB.TotalDM(ProfileID)+" "+
                getString(R.string.ECTSTitle)+up.getTotalECTS());

        createData();
        listView.setAdapter(adapter);

        return rootView;
    }


    public void createData() {

        uGrades = upDB.getAllGrades(ProfileID);

        Group group = null;

        if (uGrades.size()==0)
        {
            group = new Group(getString(R.string.NoGradesGroup));
            groups.append(0, group);
        }
        else {

            int i = 0;
            int j = 0;

            group = new Group(getString(R.string.studentSemester) + " " + uGrades.get(i).getSemester(),
                    upDB.TotalSemesterSubjectsPassed(ProfileID, uGrades.get(i).getSemester()) + " " + getString(R.string.TotalSubjectsPassed),  //Total Subjects Passed
                    upDB.TotalSemesterDM(ProfileID, uGrades.get(i).getSemester()) + " " + getString(R.string.dmPoints),               //Total semester SP
                    upDB.TotalSemesterECTS(ProfileID, uGrades.get(i).getSemester()) + " " + getString(R.string.ECTSpoints),                //Total Semester ECTS
                    getString(R.string.semesterAvg) + upDB.SemesterMO(ProfileID, uGrades.get(i).getSemester())                            //Semester's AVG
            );
            while (i < uGrades.size()) {

                if (i > 1 && !uGrades.get(i).getSemester().equals(uGrades.get(i - 1).getSemester())) {
                    group = new Group(getString(R.string.studentSemester) + " " + uGrades.get(i).getSemester(),
                            upDB.TotalSemesterSubjectsPassed(ProfileID, uGrades.get(i).getSemester()) + " " + getString(R.string.TotalSubjectsPassed),  //Total Subjects Passed
                            upDB.TotalSemesterDM(ProfileID, uGrades.get(i).getSemester()) + " " + getString(R.string.dmPoints),               //Total semester SP
                            upDB.TotalSemesterECTS(ProfileID, uGrades.get(i).getSemester()) + " " + getString(R.string.ECTSpoints),                //Total Semester ECTS
                            getString(R.string.semesterAvg) + upDB.SemesterMO(ProfileID, uGrades.get(i).getSemester())                            //Semester's AVG
                    );


                    j++;
                }

                group.subjects.add(uGrades.get(i).getTitle());
                group.grades.add(uGrades.get(i).getGrade());
                group.types.add(uGrades.get(i).getType());
                group.DMs.add(uGrades.get(i).getDM());
                group.ECTS.add(uGrades.get(i).getECTS());
                group.Examinations.add(uGrades.get(i).getExaminationPeriod());


                groups.append(j, group);

                i++;

            }
        }//end of else

    }

}