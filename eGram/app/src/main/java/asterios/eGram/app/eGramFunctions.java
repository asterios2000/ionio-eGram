package asterios.eGram.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import asterios.eGram.app.Announcements.AnnouncementWebview;
import asterios.eGram.app.Applications.ApplicationsFragment;
import asterios.eGram.app.Applications.RefreshApplicationsFragment;
import asterios.eGram.app.Applications.SendApplicationFragment;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Grades.GradesFragment;
import asterios.eGram.app.Grades.RefreshGradesFragment;
import asterios.eGram.app.Informations.InformationFragment;
import asterios.eGram.app.NavigationDrawer.NavDrawerItem;
import asterios.eGram.app.NavigationDrawer.NavDrawerListAdapter;
import asterios.eGram.app.Profile.ProfilesFragment;
import asterios.eGram.app.Profile.RefreshProfileFragment;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.Student.RefreshAllFragment;
import asterios.eGram.app.Student.StudentPageFragment;
import asterios.eGram.app.Subscriptions.RefreshSubscriptionsFragment;
import asterios.eGram.app.Subscriptions.SendSubscriptionFragment;
import asterios.eGram.app.Subscriptions.SubscriptionsFragment;


/**
 * Created by asterios on 10/5/2015.
 */
public class eGramFunctions extends Fragment {


//To draw the navigation drawer in certain cases
    public static void drawNavigationDrawer(Activity act, ListView mDrawerList, ArrayList<NavDrawerItem> navDrawerItems)  {

        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(act);
        boolean firstTime = sf.getBoolean("first", true);

       // mDrawerList.removeAllViews();
        String[] navMenuTitles = act.getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        TypedArray navMenuIcons = act.getResources().obtainTypedArray(R.array.nav_drawer_icons);

        navDrawerItems.clear();
        mDrawerList.setAdapter(null);

        //Add a drawer_header above navigation drawer

        View header = act.getLayoutInflater().inflate(R.layout.drawer_header, null);
        header.setTag("header"); //in order to find it afterwards in order to remove it and re arrange it

        if (mDrawerList.getHeaderViewsCount()>0) {

            mDrawerList.removeHeaderView(mDrawerList.findViewWithTag("header"));
        }


        TextView Name = (TextView) header.findViewById(R.id.name);
        TextView email = (TextView) header.findViewById(R.id.email);
        ImageView drawerImage = (ImageView) header.findViewById(R.id.circleView);

        if (!firstTime) {
            userDataDB upDB = new userDataDB(act);
            UserProfile up = upDB.getProfileByID(upDB.getDefaultProfilesID());


            Name.setText(eGramFunctions.properCase(up.getSurName()) + " " + eGramFunctions.properCase(up.getName()));
            email.setText(up.getUserName() + act.getString(R.string.email));
            if (!up.getProfilePicturePath().isEmpty())
                drawerImage.setImageBitmap(BitmapFactory.decodeFile(up.getProfilePicturePath() + "/" + up.getUserName() + ".png"));
            else drawerImage.setImageResource(R.drawable.ic_profile);

           // if (mDrawerList.getHeaderViewsCount()==0)
            mDrawerList.addHeaderView(header);


            // adding nav drawer items to array
            //Drawer 0 belongs to Drawer's header above
            // Student Page
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            // Grades
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            // Subscriptions
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            // Applications
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
            // Profiles
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
            // Information - Communication
            //navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

        }//end of if !firsttime
        else {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(5, -1)));
        }

        // Recycle the typed array
        navMenuIcons.recycle();
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(act, navDrawerItems);
        mDrawerList.setAdapter(adapter);
       // mDrawerList.setItemChecked(mDrawerList.getSelectedItemPosition(), true);
       // mDrawerList.setSelection(mDrawerList.getSelectedItemPosition());
       // mDrawerList.invalidateViews();

    }

//To jump to different pages
    public static void displayView(int position, Activity act) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {

            case 1:
                fragment = new StudentPageFragment();
                break;
            case 2:
                fragment = new GradesFragment();
                break;
            case 3:
                fragment = new SubscriptionsFragment();
                break;
            case 4:
                fragment = new ApplicationsFragment();
                break;
            case 5:
                fragment = new ProfilesFragment();
                break;
            case 6:
                fragment = new InformationFragment();
                break;

            default:
                break;
        }
        ListView mDrawerList = (ListView) act.findViewById(R.id.list_slidermenu);
       // DrawerLayout mDrawerLayout = (DrawerLayout) act.findViewById(R.id.drawer_layout);

        if (fragment != null) {
            FragmentManager fragmentManager = act.getFragmentManager();
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
            fragmentManager.popBackStack();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            act.getActionBar().setTitle((act.getResources().getStringArray(R.array.nav_drawer_items)[position]));
            MainActivity.pos = position;
           // mDrawerLayout.closeDrawer(mDrawerList);
            Log.d("position",String.valueOf(position));
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    //To create new fragments

    public static void AddFragment(Fragment FragmentToAdd, Activity act) {

        String WhichFragment = FragmentToAdd.toString();
        FragmentTransaction transaction = act.getFragmentManager().beginTransaction();
        String Tag = null;
        if (WhichFragment.contains("Subscriptions")) Tag = "Subscriptions";
        if (WhichFragment.contains("Announcement")) Tag = "Announcement";

        transaction.replace(R.id.frame_container, FragmentToAdd,Tag);

        //To avoid getting NoProfile Fragment to the Stack
        if (!WhichFragment.contains("No"))transaction.addToBackStack(null);

        Log.d("detWhichFRG", WhichFragment);

        // Commit the transaction
        transaction.commit();

    }


    public static void RefreshProfile(Activity act, int ProfileID) {

        FragmentTransaction transaction = act.getFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", ProfileID);

        // set Fragmentclass Arguments

        RefreshAllFragment refreshAll = new RefreshAllFragment();
        refreshAll.setArguments(bundle);

        transaction.replace(R.id.frame_container, refreshAll);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public static void SendingApplication(Activity act, ArrayList<String> bun) {


        Bundle bundle = new Bundle();
        bundle.putStringArrayList("ApplicationData", bun);


        // set Fragmentclass Arguments
        SendApplicationFragment webviewOBJ = new SendApplicationFragment();
        webviewOBJ.setArguments(bundle);
        AddFragment(webviewOBJ, act);


    }


    public static void SendingSubscription(Activity act, ArrayList<String> bun) {


        Bundle bundle = new Bundle();
        bundle.putStringArrayList("SubscriptionData", bun);


        // set Fragmentclass Arguments
        SendSubscriptionFragment SendSub = new SendSubscriptionFragment();
        SendSub.setArguments(bundle);
        AddFragment(SendSub, act);


    }

    public static void ShowToast(LayoutInflater inflater, Context ctx,   int imgResId, String msgResId, String Duration){

        final View toastView = inflater.inflate(R.layout.egram_toast, null);

        ImageView imageView = (ImageView)toastView.findViewById(R.id.toastImage);
        TextView textView = (TextView)toastView.findViewById(R.id.toastText);

        Toast toast = new Toast(ctx);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        if (Duration.equals("Long"))
        toast.setDuration(Toast.LENGTH_LONG);
        if (Duration.equals("Short"))
        toast.setDuration(Toast.LENGTH_SHORT);


        imageView.setImageResource(imgResId);
        textView.setText(msgResId);

        toast.setView(toastView);
        toast.show();

    }
    public static void ShowOKDialog (Context ctx, int imgResId, String DialogTitle, String DialogMsg){

        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setTitle(DialogTitle);
        alert.setMessage(DialogMsg);//getString(msgResId));
        alert.setIcon(imgResId);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }
        });
        alert.show();

    }

    //The refresh dialog
    public static Dialog onCreateDialogSingleChoice(final Activity act) {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
//Source of the data in the DIalog
      //  TypedArray strings =  ctx.getResources().obtainTypedArray(R.array.refresh_items);
      //  Log.d("detArray", strings.getString(0));
      //  CharSequence[] array;
        //array.

        userDataDB upDB = new userDataDB(act);
// Set the dialog title
        builder.setTitle(act.getString(R.string.refreshSelection)+" " +upDB.getProfileByID(upDB.getDefaultProfilesID()).getScreenName())
// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(R.array.refresh_items, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

// Set the action buttons
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog
                ListView lw = ((AlertDialog) dialog).getListView();
                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());// Item name e.g. Announcements
                int itemPosition = lw.getCheckedItemPosition();                       //Item Position e.g.4


                switch (itemPosition) {

                    case 0:     ///Profile

                        if (eGramFunctions.isNetAvailable(act)) {
                            RefreshProfileFragment RefreshProfile = new RefreshProfileFragment();
                            eGramFunctions.AddFragment(RefreshProfile, act);
                        }
                        //NO NETWORK
                        else
                            eGramFunctions.ShowOKDialog(act, R.drawable.ic_no_internet, act.getString(R.string.noNetworkTitle), act.getString(R.string.noNetworkMsg));


                        break;
                    case 1:     //Grades

                        if (eGramFunctions.isNetAvailable(act)) {

                            RefreshGradesFragment RefreshGrades = new RefreshGradesFragment();
                            eGramFunctions.AddFragment(RefreshGrades, act);
                        }

                        //NO NETWORK
                        else
                            eGramFunctions.ShowOKDialog(act, R.drawable.ic_no_internet, act.getString(R.string.noNetworkTitle), act.getString(R.string.noNetworkMsg));

                        break;

                    case 2:     //Announcements

                        if (eGramFunctions.isNetAvailable(act)) {
                            AnnouncementWebview RefreshAnnouncements = new AnnouncementWebview();
                            eGramFunctions.AddFragment(RefreshAnnouncements, act);
                            }
                        //NO NETWORK
                        else
                            eGramFunctions.ShowOKDialog(act, R.drawable.ic_no_internet, act.getString(R.string.noNetworkTitle), act.getString(R.string.noNetworkMsg));

                        break;

                    case 3:     //Semester Subscriptions

                        if (eGramFunctions.isNetAvailable(act)) {

                            RefreshSubscriptionsFragment RefreshSubscriptions = new RefreshSubscriptionsFragment();
                            eGramFunctions.AddFragment(RefreshSubscriptions, act);
                        }
                        //NO NETWORK
                        else
                            eGramFunctions.ShowOKDialog(act, R.drawable.ic_no_internet, act.getString(R.string.noNetworkTitle), act.getString(R.string.noNetworkMsg));

                         break;

                    case 4:     //Applications

                        if (eGramFunctions.isNetAvailable(act)) {
                            RefreshApplicationsFragment RefreshApplications = new RefreshApplicationsFragment();
                            eGramFunctions.AddFragment(RefreshApplications, act);
                        }
                        //NO NETWORK
                        else
                            eGramFunctions.ShowOKDialog(act, R.drawable.ic_no_internet, act.getString(R.string.noNetworkTitle), act.getString(R.string.noNetworkMsg));


                        break;

                    case 5:     //ALL
                        if (eGramFunctions.isNetAvailable(act)) {
                            RefreshAllFragment RefreshAll = new RefreshAllFragment();
                            eGramFunctions.AddFragment(RefreshAll, act);
                        }
                        //NO NETWORK
                        else
                            eGramFunctions.ShowOKDialog(act, R.drawable.ic_no_internet, act.getString(R.string.noNetworkTitle), act.getString(R.string.noNetworkMsg));


                        break;

                    default:
                        break;
                }


            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    //Network Connectivity Check
    public static Boolean isNetAvailable(Context ctx)  {

        try{
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo =
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


//To change the strings from uppercase to correct lower Case
    public static String properCase (String inputVal) {
        // Empty strings should be returned as-is.

        if (inputVal.length() == 0) return "";

        // Strings with only one character uppercased.

        if (inputVal.length() == 1) return inputVal.toUpperCase();

        // Otherwise uppercase first letter, lowercase the rest.

        return inputVal.substring(0,1)+inputVal.substring(1).toLowerCase();
    }


    //To create a help overlay
    public static void HelpOverLay(Activity act, View tr, String Title, String Message)
    {

        ViewTarget trg = new ViewTarget(tr);
        new ShowcaseView.Builder(act)
                .setTarget(trg)
                .setStyle(1)
                .setContentTitle(Title)
                .setContentText(Message)
                .hideOnTouchOutside()
                //.singleShot(2)//how many times it will show up
                .build();


    }

    //To check if the default language is in greek

    public static boolean isLangGreek()
    {

        if (Locale.getDefault().getDisplayLanguage().toString().equals("Ελληνικά"))  return true;
        else return false;
    }



   //To check how many days the user hasn't logged in

    public static void daysAway(userDataDB upDB, Activity act){

        UserProfile up = upDB.getProfileByID(upDB.getDefaultProfilesID());

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            Date date1 = cal.getTime();
            Date date2 = df.parse(up.getLastLoginDate());
            long diff = (date1.getTime() - date2.getTime())/86400000;
            Log.d("detDiff", String.valueOf(diff));
            if (diff>10)
                HelpOverLay(act, act.findViewById(R.id.upperRight),act.getString(R.string.refreshIsNeededTitle),
                        act.getString(R.string.daysAway)+" "+String.valueOf(diff)+" "+act.getString(R.string.days)+" " +
                        "("+act.getString(R.string.since)+" " +up.getLastLoginDate()+")\n"+
                        act.getString(R.string.refreshNeededMsg)+" "+upDB.getProfileByID(upDB.getDefaultProfilesID()).getScreenName());

           /* ShowOKDialog(act,R.drawable.ic_warning,act.getString(R.string.refreshIsNeededTitle),
                    act.getString(R.string.daysAway)+" "+String.valueOf(diff)+" "+act.getString(R.string.days)+" " +
                    "("+act.getString(R.string.since)+" " +up.getLastLoginDate()+")\n"+
                    act.getString(R.string.refreshNeededMsg));*/

        } catch (ParseException e) {
            Log.d("det", "Exception", e);
        }

    }

//to get the action Bar
/*
    public static View getActionBarView(Activity act) {
        Window window = act.getWindow();
        View v = window.getDecorView();
        int resId = act.getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }

/*
    public static void SetUserSelections (ArrayList<String> States)
    {UserSelections.addAll(States);  }

    public static void SetUserSelection (int position , String Selection)
    {UserSelections.add(position, Selection);  }

    public static ArrayList<String>  GetUserSelections ()
    { return UserSelections; }
*/


}
