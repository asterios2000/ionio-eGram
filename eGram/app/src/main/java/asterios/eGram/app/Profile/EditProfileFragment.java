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
import asterios.eGram.app.MainActivity;
import asterios.eGram.app.Photos;
import asterios.eGram.app.Preferences;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class EditProfileFragment extends Fragment {
        public static int RESULT_LOAD_IMAGE = 1;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        public String path;
        public String ProfilePicturePath="";
        public UserProfile up;
        public userDataDB udDB;


    public EditProfileFragment(){}

	@Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        container.removeAllViews();
        udDB = new userDataDB(getActivity());
        // Get The profile's ID from the Bundle
        final int ProfileID = getArguments().getInt("ProfileID");
        up = udDB.getProfileByID(ProfileID);


        final View rootView = inflater.inflate(R.layout.add_profile, container, false);

        final EditText ProfileName = (EditText) rootView.findViewById(R.id.profileScreenName);
        final EditText UserName = (EditText) rootView.findViewById(R.id.profileUserName);
        final EditText Password = (EditText) rootView.findViewById(R.id.passText);
        final EditText picturePathEdit = (EditText) rootView.findViewById(R.id.picturePath);
        final FloatingActionButton UpdateBtn = (FloatingActionButton) rootView.findViewById(R.id.addProfileBtn);
        final ImageView profileImage = (ImageView) rootView.findViewById(R.id.profileImageView);
        Password.setError(getString(R.string.passwordValidation));
        Password.setTypeface(Typeface.DEFAULT);
        Password.setTransformationMethod(new PasswordTransformationMethod());

        UpdateBtn.setEnabled(false);
        UserName.setEnabled(false); //The user name cannot be changed, no duplicates
        ProfileName.setText(up.getScreenName());
        UserName.setText(up.getUserName());
        picturePathEdit.setText(up.getProfilePicturePath());

        if (picturePathEdit.getText().toString().isEmpty()) Log.d("detEmpty","EmptyPath");
        else {profileImage.setImageBitmap(BitmapFactory.decodeFile(up.getProfilePicturePath() +"/"+ up.getUserName() + ".png"));  }



       // Check to enable the send button only when the 3 edittexts are not empty

        TextWatcher tw = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (ProfileName.getText().toString().length() == 0 || UserName.getText().toString().length() == 0 ||
                        Password.getText().toString().length() == 0) {
                    UpdateBtn.setEnabled(false);
                } else {
                    UpdateBtn.setEnabled(true);
                    UpdateBtn.setColorNormal(Color.parseColor("#83d0c9"));

                }
            }
        };
        ProfileName.addTextChangedListener(tw);
        Password.addTextChangedListener(tw);



        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                final String ScreenName = ProfileName.getText().toString();         // Profile name aka Screen Name
                final String LastLoginDate = df.format(cal.getTime());              // Last login date and time
                final String Username = UserName.getText().toString();              // username of gram web and ionian university
                final String Code = Password.getText().toString();                  // gram web code


             // If a picture is already loaded keep its path
                    if (!picturePathEdit.getText().toString().isEmpty()) {

            // or keep the default drawable
                        ProfilePicturePath = picturePathEdit.getText().toString();//path;
                        Photos.renameProfileImageFile(Username, getActivity());

                    } else ProfilePicturePath = "";


                up.setLastLoginDate(LastLoginDate);
                up.setCode(Code);
                up.setScreenName(ScreenName);
                up.setProfilePicturePath(ProfilePicturePath);
                udDB.updateProfileToDB(up);
                eGramFunctions.ShowToast(getActivity().getLayoutInflater(),getActivity(),R.drawable.ic_checked,getString(R.string.profileChanged),"Short");
                getActivity().getFragmentManager().popBackStack();



            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

}
