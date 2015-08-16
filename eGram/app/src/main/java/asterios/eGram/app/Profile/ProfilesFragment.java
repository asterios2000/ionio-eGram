package asterios.eGram.app.Profile;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.melnykov.fab.FloatingActionButton;

import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Photos;
import asterios.eGram.app.Preferences;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class ProfilesFragment extends ListFragment {



    private userDataDB upDB;
    ArrayList<String> ScreenNamesList = new ArrayList<String>();
    ArrayList<String> LastLoginDatesList = new ArrayList<String>();
    ArrayList<Integer> profileIDs = new ArrayList<Integer>();
    ArrayList<String> ProfilePicturePaths = new ArrayList<String>();
    ArrayList<String> UserNames = new ArrayList<String>();
    ArrayList<String> AEMS = new ArrayList<String>();
    ArrayList<Integer> Defaults = new ArrayList<Integer>();
    public ActionMode mActionMode;
    public ListView list;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");




    public ProfilesFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        upDB = new userDataDB(getActivity());

        list = getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        refreshProfileList();
        registerForContextMenu(list);



        ProfilesAdapter Profiles = new ProfilesAdapter(getActivity(), ScreenNamesList, LastLoginDatesList,ProfilePicturePaths,UserNames,Defaults,AEMS);
        setListAdapter(Profiles);


        //----------------------- Long Click Actions on a profile -----------------------------------//


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {


                list.setItemChecked(position,true);

                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                view.setSelected(true);

                return true;

            }
        });



    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
           MenuInflater inflater = mode.getMenuInflater();
           inflater.inflate(R.menu.profile_context_menu, menu);

         //   menu.add(getString(R.string.deleteAnnouncement));

            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int pos = list.getCheckedItemPosition();//To get the selevted object
            final int profileID = profileIDs.get(pos);
            switch (item.getItemId()) {
                case R.id.editProf:

                    list.setItemChecked(pos, false);
                    Fragment EditProfile = new EditProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ProfileID",profileID );
                    EditProfile.setArguments(bundle);

                    eGramFunctions.AddFragment(EditProfile,getActivity());

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.delProf:
                    list.setItemChecked(pos, false);//Checks the list's selected item


                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(getString(R.string.deleteConfirmationMsg));
                    alert.setMessage(getString(R.string.deleteConfirmation) +" "+ ScreenNamesList.get(pos)+" ("+UserNames.get(pos)+")");
                    alert.setIcon(R.drawable.ic_warning);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            DeleteCheckSetProfile(profileID);
                            eGramFunctions.ShowToast(getActivity().getLayoutInflater(), getActivity(), R.drawable.ic_checked, getString(R.string.profileDeleted), "Short");
                            }
                    });

                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Log.d("det", "Cancel selected");
                            //CANCEL BUTTON

                        }
                    });
                    alert.show();






                    mode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.setDefault:
                    list.setItemChecked(pos, false);

                    UserProfile up = upDB.getProfileByID(profileID);
                    up.setLastLoginDate(df.format(cal.getTime()));
                    upDB.updateProfileToDB(up);
                    upDB.SetProfileAsDefault(profileID);
                    eGramFunctions.ShowToast(getActivity().getLayoutInflater(), getActivity(), R.drawable.ic_checked, list.getItemAtPosition(pos).toString()+"\n"+getString(R.string.defaultprofileChanged),"Short");
                    refreshProfileList();
                    list.invalidateViews();
                    eGramFunctions.displayView(1,getActivity());

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();

        final View rootView = inflater.inflate(R.layout.fragment_profiles, container, false);
        final FloatingActionButton AddProfileBtn = (FloatingActionButton) rootView.findViewById(R.id.fab);



        AddProfileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Create New profile Button

                Fragment AddProfile = new AddProfileFragment();
                eGramFunctions.AddFragment(AddProfile, getActivity());


            }
        });

        return rootView;
    }



    //------------------------- Profile Single Click Actions -------------------------------//
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        //Toast.makeText(getActivity(), item + " selected", Toast.LENGTH_LONG).show();

        /*
        Intent returnToMsg=new Intent();
        returnToMsg.putExtra("selectedNumber",phonesList.get(position));
        setResult(5, returnToMsg);

        */


    }

    public void refreshProfileList() //To refresh the List View
    {

        ScreenNamesList.clear();
        LastLoginDatesList.clear();
        ProfilePicturePaths.clear();
        Defaults.clear();
        profileIDs.clear();

        List<UserProfile> profiles = upDB.getAllProfiles();
        for (UserProfile up : profiles) {
            if (!ScreenNamesList.contains(up.getScreenName())) {
                ScreenNamesList.add(up.getScreenName());
                LastLoginDatesList.add(up.getLastLoginDate());
                profileIDs.add(up.getID());
                ProfilePicturePaths.add(up.getProfilePicturePath());
                UserNames.add(up.getUserName());
                Defaults.add(up.getDefaultProfile());
                AEMS.add(up.getAEM());
            }
        }

    }

    public void DeleteCheckSetProfile(int profileIDtoDelete) //To delete the selected profile and Set another default

    {
        final UserProfile upd = upDB.getProfileByID(profileIDtoDelete);
        Log.d("detPath", upd.getProfilePicturePath() + "/" + upd.getUserName() + ".png");
        Photos.DeleteProfilePicture(upd.getProfilePicturePath() + "/" + upd.getUserName() + ".png");
        upDB.DeleteProfile(profileIDtoDelete);

        refreshProfileList();
        list.invalidateViews();


        List<UserProfile> AllProfiles =  upDB.getAllProfiles();
        if (AllProfiles.size()==0)
        {
            Fragment noProfile = new NoProfilesFragment();
            eGramFunctions.AddFragment(noProfile, getActivity());
            Preferences.setPreferences(0, true, getActivity());
        }
        else
        {

            upDB.SetProfileAsDefault(AllProfiles.get(0).getID());
            eGramFunctions.displayView(5, getActivity());

        }

    }


    /*

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        menu.setHeaderIcon(R.drawable.ic_announcement);
        menu.setHeaderTitle(getString(R.string.annActions));
        menu.add(getString(R.string.deleteAnnouncement));
        inflater.inflate(R.menu.profile_context_menu, menu);
    }




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            /*case R.id.edit:
                editNote(info.id);
                return true;
            case R.id.delete:
                deleteNote(info.id);
                return true;*/
 /*           default:
                return super.onContextItemSelected(item);
        }
    }

            */
}

