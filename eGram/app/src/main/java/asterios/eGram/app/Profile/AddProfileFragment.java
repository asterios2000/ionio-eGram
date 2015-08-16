package asterios.eGram.app.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Photos;
import asterios.eGram.app.Preferences;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class AddProfileFragment extends Fragment {
        public static int RESULT_LOAD_IMAGE = 1;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        public String gotPath = "";
        public String path="";
        public String ProfilePicturePath="";
        public int DefaultProfile = 0;                                       //all new profiles are 0 by default
        public ImageView profileImage = null;


    public AddProfileFragment(){}

	@Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {

        container.removeAllViews(); //To clean everything in the container in order to have clear fragments
        final View rootView = inflater.inflate(R.layout.add_profile, container, false);

        final SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final userDataDB udDB = new userDataDB(getActivity());

        final EditText ProfileName = (EditText) rootView.findViewById(R.id.profileScreenName);
        final EditText UserName = (EditText) rootView.findViewById(R.id.profileUserName);
        final EditText Password = (EditText) rootView.findViewById(R.id.passText);
        final EditText picturePathEdit = (EditText) rootView.findViewById(R.id.picturePath);
        final FloatingActionButton AddProfileBtn = (FloatingActionButton) rootView.findViewById(R.id.addProfileBtn);
        profileImage = (ImageView) rootView.findViewById(R.id.profileImageView);


        AddProfileBtn.setEnabled(false); //Disable add button on create, enable on filled form
        UserName.setError(getString(R.string.userNameValidation));
        Password.setError(getString(R.string.passwordValidation));


       //Fix the typeface of password area
        Password.setTypeface(Typeface.DEFAULT);
        Password.setTransformationMethod(new PasswordTransformationMethod());

       // Add button will be enabled after all 3 editexts are filled

        TextWatcher tw = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (ProfileName.getText().toString().length() == 0 || UserName.getText().toString().length() == 0 ||
                        Password.getText().toString().length() == 0) {
                    AddProfileBtn.setEnabled(false);
                } else {
                    AddProfileBtn.setEnabled(true);
                    AddProfileBtn.setColorNormal(Color.parseColor("#83d0c9"));  //base color

                }
            }
        };
        ProfileName.addTextChangedListener(tw);
        UserName.addTextChangedListener(tw);
        Password.addTextChangedListener(tw);



        AddProfileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean firstTime = sf.getBoolean("first", true);             // To check whether its the first time application starts or not

                // if it's the app's firsttime we have our first profile
                if (firstTime) DefaultProfile = 1;


                final String ScreenName = ProfileName.getText().toString();         // Profile name
                final String LastLoginDate = df.format(cal.getTime());              // Prfile date time
                final String Username = UserName.getText().toString();              // gram web and ionian username
                final String Code = Password.getText().toString();                  // gram web and ionian pass


                // if (bitmapLoaded) {                                                 // if he has selected a new photo
                if (!picturePathEdit.getText().toString().isEmpty()) {

                    ProfilePicturePath = picturePathEdit.getText().toString();//path;
                    Photos.renameProfileImageFile(Username, getActivity());         //it's for renaming the profile image as the username

                    gotPath = ProfilePicturePath + "/" + Username + ".png";                              //it's used on resume
                } else ProfilePicturePath = "";




                if (!udDB.ProfileExists(Username)) { //NO DUPLICATES - THE PROFILE IS ADDED ONLY IF THE USERNAME DOES NOT EXIST


                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(R.string.profileLoadTitle);
                    alert.setMessage(R.string.profileLoadMsg);
                    alert.setIcon(R.drawable.ic_warning);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            //THE PROFILE IS ADDED ON IF THERE IS AN ACTIVE (WIFI OR 3G) INTERNET CONNECTION

                            if (eGramFunctions.isNetAvailable(getActivity())) {
                                //create profile and add it to the DB
                                UserProfile NewUserProfile = new UserProfile(1, DefaultProfile, ScreenName, ProfilePicturePath, LastLoginDate, Username, Code);
                                int newID = udDB.addProfileToDB(NewUserProfile);

                                //TODO: Update tin database
                                Preferences.setPreferences(udDB.getDefaultProfilesID(), false, getActivity());
                                eGramFunctions.RefreshProfile(getActivity(), newID);


                            }
                            //NO NETWORK, NO PROFILE
                            else
                                eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_no_internet, getString(R.string.noNetworkTitle), getString(R.string.noNetworkMsg));


                        }
                    });

                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Log.d("det", "Cancel selected");
                            //CANCEL BUTTON AFTER NO NETWORK

                        }
                    });
                    alert.show();

                } else { //EXISTING PROFILE - STOP

                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning, getString(R.string.profileExistsTitle), getString(R.string.profileExistsMsg) + " " + Username);
                }
            }
        });

       //IF THE PROFILE ICON IS PRESSED
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });



        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            File SelectedImage = new File(picturePath);
            Bitmap yourSelectedImage = Photos.decodeFile(SelectedImage);
            cursor.close();


            final ImageView profileImage = (ImageView) getView().findViewById(R.id.profileImageView);
            final EditText picturePathEdit = (EditText) getView().findViewById(R.id.picturePath);

            path = Photos.saveToInternalStorage(Photos.getCroppedBitmap(yourSelectedImage), getActivity());
            picturePathEdit.setText(path);

            profileImage.setImageBitmap(BitmapFactory.decodeFile(path+"/profile.png"));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //do something
        final EditText picturePathEdit = (EditText) getView().findViewById(R.id.picturePath);

        if (!picturePathEdit.getText().toString().isEmpty())
            profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePathEdit.getText().toString()+"/profile.png"));
        if (!gotPath.isEmpty())
            profileImage.setImageBitmap(BitmapFactory.decodeFile(gotPath));
    }


}
