package asterios.eGram.app.Student;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import java.util.List;
import asterios.eGram.app.Announcements.Announcement;
import asterios.eGram.app.Announcements.AnnouncementAdapter;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Announcements.AnnouncementGroup;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;

public class MoreFragment extends Fragment {

    SparseArray<AnnouncementGroup> groups = new SparseArray<AnnouncementGroup>();
    AnnouncementAdapter adapter;
    ExpandableListView listView;
    public userDataDB upDB;
    public List<Announcement> Ann;
    public UserProfile up;
    public MoreFragment() { }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setAdapter(adapter);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new AnnouncementAdapter(getActivity(), groups);
        upDB = new userDataDB(getActivity());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_student_page_more, container, false);
        listView = (ExpandableListView) rootView.findViewById(R.id.listView);
        registerForContextMenu(listView);
        createData();
        listView.setAdapter(adapter);

        int ProfileID = upDB.getDefaultProfilesID();

        // Get The profile from the DB
        up = upDB.getProfileByID(ProfileID);

        final TextView fullName = (TextView) rootView.findViewById(R.id.stFullName);                               //Name and Last Name
        final TextView AEMSemester = (TextView) rootView.findViewById(R.id.stAEMSemester);                         //AEM & Current Semester
        final TextView Department = (TextView) rootView.findViewById(R.id.stDepartment);                           //Department
        final TextView Specialisation = (TextView) rootView.findViewById(R.id.stSpecialisation);                   //Specialty
        final TextView RegistrationWayYear = (TextView) rootView.findViewById(R.id.stRegistrationWayYear);         //Year And way of registration
        final TextView PrSpoudon = (TextView) rootView.findViewById(R.id.stPrSpoudon);                             //Program of studies


        fullName.setText(eGramFunctions.properCase(up.getSurName())+" "+eGramFunctions.properCase(up.getName()));
        AEMSemester.setText(up.getAEM()+" "+eGramFunctions.properCase(String.valueOf(up.getCurSemester()))+getString(R.string.studentAtSemester));
        Department.setText(eGramFunctions.properCase(up.getDepartment()));
        Specialisation.setText(eGramFunctions.properCase(up.getField()));
        RegistrationWayYear.setText(eGramFunctions.properCase(up.getRegistrationWay())+" "+up.getRegistrationYear());
        PrSpoudon.setText(eGramFunctions.properCase(up.getPrSpoudon()));

        return rootView;
    }


    public void createData() {

        Ann = upDB.getAllAnnouncements();
        AnnouncementGroup group = null;

        int i = 0;
        int j = 0;


        int secreteriatSum = 0;
        int generalSum = 0;
        int k = 0;
        while (k< Ann.size())
        {
            if (Ann.get(k).getType().equals("Γενικές"))generalSum++;
            if (Ann.get(k).getType().equals("Γραμματείας"))secreteriatSum++;

            k++;
        }

       if (Ann.size()!=0) //Only if there isn't anything in the list create it
        {

            if(secreteriatSum>0)  group = new AnnouncementGroup(getString(R.string.SecreteriatAnnouncements), "(" + String.valueOf(secreteriatSum) + ")");
            else
            if(generalSum>0)  group = new AnnouncementGroup(getString(R.string.GeneralAnnouncements), "(" + String.valueOf(generalSum) + ")");
            while (i < Ann.size()) {

                if(generalSum>0) {
                    if (Ann.get(i).getType().equals("Γενικές")) {
                        if (i > 2 && !Ann.get(i - 1).getType().equals("Γενικές")) {
                            group = new AnnouncementGroup(getString(R.string.GeneralAnnouncements), "(" + String.valueOf(generalSum) + ")");
                            j++;
                        }

                    }
                }//end of if generalSum>0
                else groups.delete(1);

                if(secreteriatSum>0) {
                    if (Ann.get(i).getIsRead().contains("No")) {
                        group.Titles.add(Ann.get(i).getTitle());
                        group.Urls.add(Ann.get(i).getURL());
                        groups.append(j, group);
                    }
                }//end of if secreteriat sum>0
                else groups.delete(0);

                    i++;


            }
        }//end of if Ann.size==0
        else {
           groups.delete(0);
           Log.d("detEmpty","Empty List");

       }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        int type =
                ExpandableListView.getPackedPositionType(info.packedPosition);
        int group =
                ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int child =
                ExpandableListView.getPackedPositionChild(info.packedPosition);

//Only create a context menu for child items
        if (type == 1) {

            menu.setHeaderIcon(R.drawable.ic_announcement);
            menu.setHeaderTitle(getString(R.string.annActions));
            menu.add(getString(R.string.deleteAnnouncement));

        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuItem.getMenuInfo();
        int groupPos = 0, childPos = 0;
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
        }

        switch (menuItem.getItemId()) {
            case 0:
                int announcement = 0;
                if (groupPos==0) announcement = childPos;
                if (groupPos==1) announcement = groups.get(0).Titles.size() +  childPos;

                String titleBeforeDelete = Ann.get(announcement).getTitle();
                upDB.DeleteAnnouncement(Ann.get(announcement).getID());

                //Update the list
                createData();
                adapter.notifyDataSetChanged();

                eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_checked, titleBeforeDelete, getString(R.string.AnnouncementRemoved));

                return true;

            default:
                return super.onContextItemSelected(menuItem);
        }
    }

}