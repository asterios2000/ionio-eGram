package asterios.eGram.app.Applications;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import asterios.eGram.app.Database.userDataDB;

import asterios.eGram.app.Preferences;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;

public class ApplicationsFragment extends ListFragment {

    private userDataDB upDB;
    ArrayList<String> ApplicationStateList = new ArrayList<String>();
    ArrayList<String> ApplicationTitleList = new ArrayList<String>();
    ArrayList<String> ApplicationDateTimeList = new ArrayList<String>();
	
	public ApplicationsFragment(){}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        upDB = new userDataDB(getActivity());
        refreshApplicationList();

        // use custom layout
        ApplicationsAdapter Applications = new ApplicationsAdapter(getActivity(), ApplicationTitleList, ApplicationDateTimeList,ApplicationStateList);
        setListAdapter(Applications);

    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_applications, container, false);
        final FloatingActionButton AddApplicationBtn = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        refreshProfileList();


        AddApplicationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Creation of new APPLICATION


                if (eGramFunctions.isNetAvailable(getActivity())) {
                    //create profile and add it to the DB
                    Fragment NewApplicationFragment = new NewApplicationFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    eGramFunctions.AddFragment(NewApplicationFragment, getActivity());

                }
                //NO NETWORK
                else
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_no_internet, getString(R.string.noNetworkTitle), getString(R.string.noNetworkMsg));




            }
        });
         
        return rootView;
    }

    //----------------Single Click Actions ------------------------------------------//
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        //Toast.makeText(getActivity(), item , Toast.LENGTH_LONG).show();
        eGramFunctions.ShowToast(getActivity().getLayoutInflater(),getActivity(),R.drawable.ic_applications,item,"Short");

        /*
        Intent returnToMsg=new Intent();
        returnToMsg.putExtra("selectedNumber",phonesList.get(position));
        setResult(5, returnToMsg);

        Toast.makeText(this, lv.getItemAtPosition(position).toString()+" "+this.getString(R.string.selected), Toast.LENGTH_SHORT).show();

        finish();
        */


    }

    public void refreshApplicationList() //To refresh the list data

    {

        ApplicationTitleList.clear();
        ApplicationDateTimeList.clear();
        ApplicationStateList.clear();

        List<UserApplications> applications = upDB.getAllApplications(upDB.getDefaultProfilesID());
        for (UserApplications ua : applications) {
            if (!ApplicationTitleList.contains(ua.getTitle())) {
                ApplicationTitleList.add(ua.getTitle());
                ApplicationDateTimeList.add(ua.getDateTimeStamp());
                ApplicationStateList.add(ua.getState());
            }
        }

       /* setListAdapter(new ProfilesAdapter(getActivity(), ScreenNamesList,LastLoginDatesList)); //orizo tis times tou
        lv.setAdapter(getListAdapter());*/
    }

}
