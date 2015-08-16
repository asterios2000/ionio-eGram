package asterios.eGram.app;
import asterios.eGram.app.Applications.ApplicationsFragment;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Grades.GradesFragment;
import asterios.eGram.app.Informations.GeneralInfos;
import asterios.eGram.app.Informations.InformationFragment;
import asterios.eGram.app.NavigationDrawer.NavDrawerItem;
import asterios.eGram.app.Profile.NoProfilesFragment;
import asterios.eGram.app.Profile.ProfilesFragment;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.Student.StudentPageFragment;
import asterios.eGram.app.Subscriptions.SubscriptionsFragment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends Activity {
	public static int RESULT_LOAD_IMAGE = 1;
	final Context eGramContext = this;
	final Activity eGramActivity = this;
	public userDataDB upDB ;
	public static int pos = 1;
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	public boolean firstTime;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	//private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	//private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(eGramContext);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
	    navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		//navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();


		upDB = new userDataDB(eGramContext);


		firstTime = sf.getBoolean("first", true);


		eGramFunctions.drawNavigationDrawer(this,mDrawerList,navDrawerItems);


			mDrawerList.setOnItemClickListener(new SlideMenuClickListener());



			// enabling action bar app icon and behaving it as toggle button
			getActionBar().setDisplayHomeAsUpEnabled(true);
			if (Build.VERSION.SDK_INT > 14) //API 14
				getActionBar().setHomeButtonEnabled(true);

			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
					R.drawable.ic_drawer, //nav menu toggle icon
					R.string.app_name, // nav drawer open - description for accessibility
					R.string.app_name // nav drawer close - description for accessibility
			) {
				public void onDrawerClosed(View view) {
					getActionBar().setTitle(mTitle);
					// calling onPrepareOptionsMenu() to show action bar icons
					invalidateOptionsMenu();
				}

				public void onDrawerOpened(View drawerView) {
					getActionBar().setTitle(mDrawerTitle);
					eGramFunctions.drawNavigationDrawer(eGramActivity, mDrawerList,navDrawerItems);

					mDrawerList.setItemChecked(pos, true);
					mDrawerList.setSelection(pos);
					// calling onPrepareOptionsMenu() to hide action bar icons
					invalidateOptionsMenu();
				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item




			if (firstTime) {

				//New profile creation

				// Create new fragment and transaction
				Fragment NoProfilesFragment = new NoProfilesFragment();
				eGramFunctions.AddFragment(NoProfilesFragment, this);

				if (upDB.getAllInformation().size()==0)
				GeneralInfos.CreateGeneralInfos(eGramContext);

			}
			else {
				//Set last login date of the default profile
				UserProfile up = upDB.getProfileByID(upDB.getDefaultProfilesID());

				eGramFunctions.daysAway(upDB,eGramActivity );
				up.setLastLoginDate(df.format(cal.getTime()));
				upDB.updateProfileToDB(up);

				displayView(1);
			}

		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			pos = position;
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/*@Override
	public void onDestroy() {
		super.onDestroy();
		mDrawerList.setAdapter(null);
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_refresh:
			Dialog dialog = eGramFunctions.onCreateDialogSingleChoice(eGramActivity);
			dialog.show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
		if (firstTime) menu.findItem(R.id.action_refresh).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
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

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			Log.d("position",String.valueOf(position));
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}



}
