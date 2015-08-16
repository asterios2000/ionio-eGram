package asterios.eGram.app.Database;

/**
 * Created by asterios on 29/4/2015.
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import asterios.eGram.app.Announcements.Announcement;
import asterios.eGram.app.Applications.UserApplications;
import asterios.eGram.app.Grades.UserGrades;
import asterios.eGram.app.Informations.GeneralInfos;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.Subscriptions.UserSubscriptions;

public class userDataDB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userData";

    // -------------------------T A B L E S ---------------------------

    // profiles table name
    private static final String TABLE_PROFILES = "Profiles";
    // grades table name
    private static final String TABLE_GRADES = "Grades";
    // subscriptions table name
    private static final String TABLE_SUBSCRIPTIONS = "Subscriptions";
    // New subscription table name
    private static final String TABLE_NEWSUBSCRIPTION = "NewSubscription";
    // Applications table name
    private static final String TABLE_APPLICATIONS = "Applications";
    // Information table name
    private static final String TABLE_INFORMATION = "Information";
    // Announcements table name
    private static final String TABLE_ANNOUNCEMENTS = "Announcements";



    // profiles Table Columns names
    private static final String KEY_PRID = "id";
    private static final String KEY_DEFAULTPROFILE = "DefaultProfile";
    private static final String KEY_PICTUREPATH = "ProfilePicturePath";
    private static final String KEY_SCREENNAME = "ScreenName";
    private static final String KEY_LASTLOGINDATE = "LastLoginDate";
    private static final String KEY_NAME = "Name";
    private static final String KEY_SURNAME = "SurName";
    private static final String KEY_USERNAME = "UserName";
    private static final String KEY_CODE = "Code";
    private static final String KEY_AEM = "AEM";
    private static final String KEY_DEPARTMENT = "Department";
    private static final String KEY_CURRENTSEMESTER = "CurrentSemester";
    private static final String KEY_PRSPOUDON = "PrSpoudon";
    private static final String KEY_FIELD = "Field";
    private static final String KEY_REGISTRATIONYEAR = "RegistrationYear";
    private static final String KEY_REGISTRATIONWAY = "RegistrationWay";
    private static final String KEY_TOTALSUBJECTSPASSED = "TotalSubjectsPassed";
    private static final String KEY_TOTALECTS = "TotalECTS";
    private static final String KEY_MO = "MO";

    // grades Table Columns names
    private static final String KEY_GRID = "id";
    private static final String KEY_PROFILEID = "userID";
    private static final String KEY_SEMESTER = "Semester";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_DM = "DM";
    private static final String KEY_ECTS = "ECTS";
    private static final String KEY_GRADE = "Grade";
    private static final String KEY_EXAMINATION = "Examination";

    // Subscriptions Table Columns names
    private static final String KEY_SUBID = "id";
    private static final String KEY_SUBPROFILEID = "userID";
    private static final String KEY_SUBPERIOD = "Period";
    private static final String KEY_SUBCODE = "Code";
    private static final String KEY_SUBTITLE = "Title";
    private static final String KEY_SUBSEMESTER = "Semester";
    private static final String KEY_SUBTYPE = "Type";
    private static final String KEY_SUBDM = "DM";
    private static final String KEY_SUBHOURS = "Hours";
    private static final String KEY_SUBECTS = "ECTS";

    // NEW Subscriptions Table Columns names (it borrows some field names from above)

    //private static final String KEY_SUBCODE = "Code";
    //private static final String KEY_SUBTITLE = "Title";
    private static final String KEY_SUBSTATE = "State";
    private static final String KEY_SUBCHECKBOXID = "CheckBoxID";


    // Applications Table Columns names
    private static final String KEY_APPID = "id";
    private static final String KEY_APPPROFILEID = "userID";
    private static final String KEY_APPDATETIMESTAMP = "DateTime";
    private static final String KEY_APPTITLE = "Title";
    private static final String KEY_APPSTATE = "State";


    // Indormation Table Columns names
    private static final String KEY_INFID = "id";
    private static final String KEY_INFTYPE = "Type";
    private static final String KEY_INFNAME = "Name";
    private static final String KEY_INFADDRESS = "Address";
    private static final String KEY_INFPHONE = "Phone";
    private static final String KEY_INFFAX = "Fax";
    private static final String KEY_INFMAIL = "Mail";
    private static final String KEY_INFWEBPAGE = "WebPage";


    // Announcement Table Columns names
    private static final String KEY_ANNID = "id";
    private static final String KEY_ANNTYPE = "Type";
    private static final String KEY_ANNTITLE = "Title";
    private static final String KEY_ANNURL = "URL";
    private static final String KEY_ANNISREAD = "isRead";



    public userDataDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //P R O F I L E S

        String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES + "("
                + KEY_PRID + " INTEGER PRIMARY KEY,"
                + KEY_DEFAULTPROFILE + " INTEGER,"
                + KEY_PICTUREPATH + " TEXT,"
                + KEY_SCREENNAME + " TEXT,"
                + KEY_LASTLOGINDATE + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_USERNAME + " TEXT,"
                + KEY_CODE + " TEXT,"
                + KEY_AEM + " TEXT,"
                + KEY_DEPARTMENT + " TEXT,"
                + KEY_CURRENTSEMESTER + " INTEGER,"
                + KEY_PRSPOUDON + " TEXT,"
                + KEY_FIELD + " TEXT,"
                + KEY_REGISTRATIONYEAR + " TEXT,"
                + KEY_REGISTRATIONWAY + " TEXT,"
                + KEY_TOTALSUBJECTSPASSED + " TEXT,"
                + KEY_TOTALECTS + " TEXT,"
                + KEY_MO + " TEXT" + ")";
        db.execSQL(CREATE_PROFILES_TABLE);


        //G R A D E S

        String CREATE_GRADES_TABLE = "CREATE TABLE " + TABLE_GRADES + "("
                + KEY_GRID + " INTEGER PRIMARY KEY,"
                + KEY_PROFILEID + " INTEGER,"
                + KEY_SEMESTER + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_DM + " TEXT,"
                + KEY_ECTS + " TEXT,"
                + KEY_GRADE + " TEXT,"
                + KEY_EXAMINATION + " TEXT" + ")";
        db.execSQL(CREATE_GRADES_TABLE);

        //S U B S C R I P T I O N S

        String CREATE_SUBSCRIPTIONS_TABLE = "CREATE TABLE " + TABLE_SUBSCRIPTIONS + "("
                + KEY_SUBID + " INTEGER PRIMARY KEY,"
                + KEY_SUBPROFILEID + " INTEGER,"
                + KEY_SUBPERIOD + " TEXT,"
                + KEY_SUBCODE + " TEXT,"
                + KEY_SUBTITLE + " TEXT,"
                + KEY_SUBSEMESTER + " TEXT,"
                + KEY_SUBTYPE + " TEXT,"
                + KEY_SUBDM + " TEXT,"
                + KEY_SUBHOURS + " TEXT,"
                + KEY_SUBECTS + " TEXT"+")";

        db.execSQL(CREATE_SUBSCRIPTIONS_TABLE);

        //NEW S U B S C R I P T I O N

        String CREATE_NEWSUBSCRIPTION_TABLE = "CREATE TABLE " + TABLE_NEWSUBSCRIPTION + "("
                + KEY_SUBID + " INTEGER PRIMARY KEY,"
                + KEY_SUBPROFILEID + " INTEGER,"
                + KEY_SUBCODE + " TEXT,"
                + KEY_SUBTITLE + " TEXT,"
                + KEY_SUBSTATE + " TEXT,"
                + KEY_SUBCHECKBOXID + " TEXT"+")";

        db.execSQL(CREATE_NEWSUBSCRIPTION_TABLE);

        //A P P L I C A T I O N S

        String CREATE_APPLICATIONS_TABLE = "CREATE TABLE " + TABLE_APPLICATIONS + "("
                + KEY_APPID + " INTEGER PRIMARY KEY,"
                + KEY_SUBPROFILEID + " INTEGER,"
                + KEY_APPSTATE + " TEXT,"
                + KEY_APPDATETIMESTAMP + " TEXT,"
                + KEY_APPTITLE + " TEXT"+")";

        db.execSQL(CREATE_APPLICATIONS_TABLE);


       //I N F O R M A T I O N
        String CREATE_INFORMATION_TABLE = "CREATE TABLE " + TABLE_INFORMATION + "("
                + KEY_INFID + " INTEGER PRIMARY KEY,"
                + KEY_INFTYPE + " TEXT,"
                + KEY_INFNAME + " TEXT,"
                + KEY_INFADDRESS + " TEXT,"
                + KEY_INFPHONE + " TEXT,"
                + KEY_INFFAX + " TEXT,"
                + KEY_INFMAIL + " TEXT,"
                + KEY_INFWEBPAGE + " TEXT"+")";

        db.execSQL(CREATE_INFORMATION_TABLE);


          //A N N O U N C E M E N T S
        String CREATE_ANNOUNCEMENT_TABLE = "CREATE TABLE " + TABLE_ANNOUNCEMENTS + "("
                + KEY_ANNID + " INTEGER PRIMARY KEY,"
                + KEY_ANNTYPE + " TEXT,"
                + KEY_ANNURL + " TEXT,"
                + KEY_ANNTITLE + " TEXT,"
                + KEY_ANNISREAD + " TEXT"+")";

        db.execSQL(CREATE_ANNOUNCEMENT_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWSUBSCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFORMATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENTS);

        // Create tables again
        onCreate(db);
    }
    //------------------------------	P R O F I L E S ------------------------------------------//

    //Adding  a new profile entry
    public int addProfileToDB(UserProfile up) {
        SQLiteDatabase db = this.getWritableDatabase();

        long ProfileID;
        ContentValues values = new ContentValues();
        values.put(KEY_DEFAULTPROFILE, up.getDefaultProfile());            // Whether is default or not
        values.put(KEY_PICTUREPATH, up.getProfilePicturePath());           // Profile picture's path
        values.put(KEY_SCREENNAME, up.getScreenName());                    // Profile Name
        values.put(KEY_LASTLOGINDATE, up.getLastLoginDate());              // Last Login date
        values.put(KEY_NAME, up.getName());                                // user's real Name
        values.put(KEY_SURNAME, up.getSurName());                          // user's real Sur Name
        values.put(KEY_USERNAME, up.getUserName());                        // userName
        values.put(KEY_CODE, up.getCode());                                // password
        values.put(KEY_AEM, up.getAEM());                                  // AEM
        values.put(KEY_DEPARTMENT, up.getDepartment());                    // department
        values.put(KEY_CURRENTSEMESTER, up.getCurSemester());              // Semester
        values.put(KEY_PRSPOUDON, up.getPrSpoudon());                      // programma spoudon
        values.put(KEY_FIELD, up.getField());                              // Field
        values.put(KEY_REGISTRATIONYEAR, up.getRegistrationYear());        // registration year
        values.put(KEY_REGISTRATIONWAY, up.getRegistrationWay());          // registration way
        values.put(KEY_TOTALSUBJECTSPASSED, up.getTotalSubjectsPassed());  // total subjects passed
        values.put(KEY_TOTALECTS, up.getTotalECTS());                      // total ECTS
        values.put(KEY_MO, up.getMO());                                    // Mesos Oros
        // Inserting Row
        ProfileID = db.insert(TABLE_PROFILES, null, values);
        db.close(); // Closing database connection
        return (int) ProfileID;
    }



//Profile update or refresh when the profile exists and we need the values from Gram Web

    public int updateProfileToDB(UserProfile up) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PICTUREPATH, up.getProfilePicturePath());           // user's profile picture path
        values.put(KEY_CODE,up.getCode());                                 // user code
        values.put(KEY_SCREENNAME, up.getScreenName());                    // user's profile name aka screen name
        values.put(KEY_NAME, up.getName());                                // user's real Name
        values.put(KEY_SURNAME, up.getSurName());                          // user's real Sur Name
        values.put(KEY_LASTLOGINDATE,up.getLastLoginDate());               // user's last login date
        values.put(KEY_AEM, up.getAEM());                                  // AEM
        values.put(KEY_DEPARTMENT, up.getDepartment());                    // department
        values.put(KEY_CURRENTSEMESTER, up.getCurSemester());              // Semester
        values.put(KEY_PRSPOUDON, up.getPrSpoudon());                      // programma spoudon
        values.put(KEY_FIELD, up.getField());                              // Field
        values.put(KEY_REGISTRATIONYEAR, up.getRegistrationYear());        // registration year
        values.put(KEY_REGISTRATIONWAY, up.getRegistrationWay());          // registration way

        // updating row
        return db.update(TABLE_PROFILES, values, KEY_PRID + " = ?",
                new String[] { String.valueOf(up.getID()) });
    }

    public int updateProfileTotalsToDB(int profileID, String totSubj, String totECTS, String MO) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOTALSUBJECTSPASSED, totSubj);     // user's total subjects passed
        values.put(KEY_TOTALECTS, totECTS);               // user's total ECTS
        values.put(KEY_MO, MO);                           // user's Average


        // updating row
        return db.update(TABLE_PROFILES, values, KEY_PRID + " = ?",
                new String[] { String.valueOf(profileID) });
    }


    public List<UserProfile> getAllProfiles() {
        List<UserProfile> ProfilesList = new ArrayList<UserProfile>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    UserProfile profile = new UserProfile();
                    profile.setID(Integer.parseInt(cursor.getString(0)));             //Table Id
                    profile.setDefaultProfile(Integer.parseInt(cursor.getString(1))); //default profile
                    profile.setProfilePicturePath(cursor.getString(2));               //profile picture path
                    profile.setScreenName(cursor.getString(3));                       //Screen Name (Profile name)
                    profile.setLastLoginDate(cursor.getString(4));                    //LastLoginDate
                    profile.setName(cursor.getString(5));                             //Name
                    profile.setSurName(cursor.getString(6));                          //SurName
                    profile.setUserName(cursor.getString(7));                         //UserName
                    profile.setCode(cursor.getString(8));                             //Code
                    profile.setAEM(cursor.getString(9));                              //AEM
                    profile.setDepartment(cursor.getString(10));                       //Department
                    profile.setCurSemester(Integer.valueOf(cursor.getString(11)));    //Current Semester
                    profile.setPrSpoudon(cursor.getString(12));                       //Programma Spoudon
                    profile.setField(cursor.getString(13));                           //Field
                    profile.setRegistrationYear(cursor.getString(14));                //Registration Year
                    profile.setRegistrationWay(cursor.getString(15));                 //Registration Way
                    profile.setTotalSubjectspassed(cursor.getString(16));             //Total Subjects passed
                    profile.setTotalECTS(cursor.getString(17));                       //Total ECTS
                    profile.setMO(cursor.getString(18));                              //Average


                    // Adding profile to profiles list
                    ProfilesList.add(profile);
                } while (cursor.moveToNext());
                }
        db.close();
        // return profile list
        return ProfilesList;
    }

    public int getDefaultProfilesID() {

        int defProfID = 0;

        // Select All Query
        String selectQuery = "SELECT id FROM " +TABLE_PROFILES +" WHERE DefaultProfile = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        UserProfile profile = new UserProfile();

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                defProfID = Integer.parseInt(cursor.getString(0)); //Default profile id
            }

        db.close();
        // returning the default's profile id

        return defProfID;
    }

    public UserProfile getProfileByID(int ID) {

        UserProfile profile = new UserProfile();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILES +" WHERE id="+ID;
        //Log.d("detSql", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    // Setting the profile
                    profile.setID(Integer.parseInt(cursor.getString(0)));             //Table Id
                    profile.setDefaultProfile(Integer.parseInt(cursor.getString(1))); //default profile
                    profile.setProfilePicturePath(cursor.getString(2));               //profile picture path
                    profile.setScreenName(cursor.getString(3));                       //Screen Name (Profile name)
                    profile.setLastLoginDate(cursor.getString(4));                    //LastLoginDate
                    profile.setName(cursor.getString(5));                             //Name
                    profile.setSurName(cursor.getString(6));                          //SurName
                    profile.setUserName(cursor.getString(7));                         //UserName
                    profile.setCode(cursor.getString(8));                             //Code
                    profile.setAEM(cursor.getString(9));                              //AEM
                    profile.setDepartment(cursor.getString(10));                      //Department
                    profile.setCurSemester(Integer.valueOf(cursor.getString(11)));    //Current Semester
                    profile.setPrSpoudon(cursor.getString(12));                       //Programma Spoudon
                    profile.setField(cursor.getString(13));                           //Field
                    profile.setRegistrationYear(cursor.getString(14));                //Registration Year
                    profile.setRegistrationWay(cursor.getString(15));                 //Registration Way
                    profile.setTotalSubjectspassed(cursor.getString(16));             //Total Subjects passed
                    profile.setTotalECTS(cursor.getString(17));                       //Total ECTS
                    profile.setMO(cursor.getString(18));                              //Average

                } while (cursor.moveToNext());
            }

        db.close();

       // Log.d("detUSER", profile.getUserName());
        return profile;
    }
    public UserProfile getProfileByUserName(String UserName) {

        UserProfile profile = new UserProfile();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILES +" WHERE "+KEY_USERNAME+"='"+UserName+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    // Setting the profile
                    profile.setID(Integer.parseInt(cursor.getString(0)));             //Profile's Id
                    profile.setDefaultProfile(Integer.parseInt(cursor.getString(1))); //default profile
                    profile.setProfilePicturePath(cursor.getString(2));               //profile picture path
                    profile.setScreenName(cursor.getString(3));                       //Screen Name (Profile name)
                    profile.setLastLoginDate(cursor.getString(4));                    //LastLoginDate
                    profile.setName(cursor.getString(5));                             //Name
                    profile.setSurName(cursor.getString(6));                          //SurName
                    profile.setUserName(cursor.getString(7));                         //UserName
                    profile.setCode(cursor.getString(8));                             //Code
                    profile.setAEM(cursor.getString(9));                              //AEM
                    profile.setDepartment(cursor.getString(10));                      //Department
                    profile.setCurSemester(Integer.valueOf(cursor.getString(11)));    //Current Semester
                    profile.setPrSpoudon(cursor.getString(12));                       //Programma Spoudon
                    profile.setField(cursor.getString(13));                           //Field
                    profile.setRegistrationYear(cursor.getString(14));                //Registration Year
                    profile.setRegistrationWay(cursor.getString(15));                 //Registration Way
                    profile.setTotalSubjectspassed(cursor.getString(16));             //Total Subjects passed
                    profile.setTotalECTS(cursor.getString(17));                       //Total ECTS
                    profile.setMO(cursor.getString(18));                              //Average

                } while (cursor.moveToNext());
            }
        db.close();

        return profile;
    }

    //Checks profile existance

    public boolean ProfileExists(String UserName) {

        String selectQuery = "SELECT "+KEY_USERNAME+" FROM " + TABLE_PROFILES +" WHERE "+KEY_USERNAME+"='"+UserName+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.getCount()>0){db.close(); return true;}
        else {db.close();return false;}

    }

//Sets a profile as default

    public void SetProfileAsDefault(int ProfileID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DEFAULTPROFILE, 0);            // Connected Profile id

        // updating row
        db.update(TABLE_PROFILES, values, KEY_DEFAULTPROFILE + " = ?",
                new String[]{String.valueOf(1)});


        values.clear();
        values.put(KEY_DEFAULTPROFILE, 1);            // Connected Profile id
        // updating row
        db.update(TABLE_PROFILES, values, KEY_PRID + " = ?",
                new String[] { String.valueOf(ProfileID) });

        db.close();

    }
    //For profile deletion

    public void DeleteProfile(int ProfileID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //First Delete the profile form the database
        db.delete(TABLE_PROFILES, KEY_PRID + " = ?", new String[]{Integer.toString(ProfileID)});

        //Then delete all the corresponding data from other tables
        db.delete(TABLE_APPLICATIONS, KEY_APPPROFILEID + " = ?", new String[]{Integer.toString(ProfileID)});

        db.delete(TABLE_GRADES, KEY_PROFILEID + " = ?", new String[]{Integer.toString(ProfileID)});

        db.delete(TABLE_SUBSCRIPTIONS, KEY_SUBPROFILEID + " = ?", new String[]{Integer.toString(ProfileID)});

        db.delete(TABLE_NEWSUBSCRIPTION, KEY_SUBPROFILEID + " = ?", new String[]{Integer.toString(ProfileID)});

        db.close();

    }


    //---------------------------------  	G R A D E S    ---------------------------------------//

    //Adding  new subject's details for use in grades
    public void addGrade(UserGrades ug) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROFILEID, ug.getProfileID());            // Connected Profile id
        values.put(KEY_SEMESTER, ug.getSemester());              // Subject's semester
        values.put(KEY_TITLE, ug.getTitle());                    // Subject's title
        values.put(KEY_TYPE, ug.getType());                      // Subject's type
        values.put(KEY_DM, ug.getDM());                          // Subject's Didaktikes monades
        values.put(KEY_ECTS, ug.getECTS());                      // Subject's ECTS points
        values.put(KEY_GRADE, ug.getGrade());                    // Subject's Grade
        values.put(KEY_EXAMINATION, ug.getExaminationPeriod());  // Subject's examination period

        // Inserting Row
        db.insert(TABLE_GRADES, null, values);
        db.close(); // Closing database connection
    }


    //To get all grades based on profile id

    public List<UserGrades> getAllGrades(int ProfileID) {
        List<UserGrades> Grades = new ArrayList<UserGrades>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GRADES +" WHERE userID="+ProfileID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    UserGrades ug = new UserGrades();
                    ug.setID(Integer.parseInt(cursor.getString(0)));                //Table Id
                    ug.setProfileID((Integer.parseInt(cursor.getString(1))));       //default profile
                    ug.setSemester(cursor.getString(2));                            //subject's semester
                    ug.setTitle(cursor.getString(3));                               //Subject's title
                    ug.setType(cursor.getString(4));                                //Subject's Type
                    ug.setDM(cursor.getString(5));                                  //Didaktikes Monades
                    ug.setECTS(cursor.getString(6));                                //ECTS
                    ug.setGrade(cursor.getString(7));                               //Grade
                    ug.setExaminationPeriod(cursor.getString(8));                   //Examination Period


                    // Adding Grades to Grades list
                    Grades.add(ug);
                } while (cursor.moveToNext());
            }
        db.close();
        // return profile list
        return Grades;
    }



    //To get the passed subjects sum for a certain semester

    public String TotalSemesterSubjectsPassed(int ProfileID, String sem) {
        // Select All Query
        String selectQuery = "SELECT COUNT("+KEY_EXAMINATION+") FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID +" AND "+ KEY_EXAMINATION +" != '-' AND "+KEY_SEMESTER+" LIKE '"+sem +"'";

        String st = null;

      // Log.d("detQuery", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    st = String.valueOf(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }


    //Delete a profile's grades from DB

    public void DeleteGrades(int ProfileID)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        //Then delete all the corresponding data from other tables
        db.delete(TABLE_GRADES, KEY_PROFILEID + " = ?", new String[]{Integer.toString(ProfileID)});

        db.close();

    }

    //To get the SP sum of a certain semester

    public String TotalSemesterDM(int ProfileID, String sem) {
        // Select All Query

        String selectQuery = "SELECT SUM("+KEY_DM+") FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID +" AND "+ KEY_GRADE +" != '-' AND "+KEY_SEMESTER+" LIKE '"+sem +"'";
        String st = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    st = String.valueOf(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }

    //To get ECTS point for a given semester

    public String TotalSemesterECTS(int ProfileID, String sem) {
        // Select All Query

        String selectQuery = "SELECT SUM("+KEY_ECTS+") FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID +" AND "+ KEY_GRADE +" != '-' AND "+KEY_SEMESTER+" LIKE '"+sem +"'";
        String st = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    st = String.valueOf(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }

    //To get a profile's ID for the given semester

    public String SemesterMO(int ProfileID, String sem) {
        // Select All Query


        String selectQuery = "SELECT AVG(CAST("+KEY_GRADE+" AS FLOAT)) FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID
                                +" AND "+ KEY_GRADE +" != '0' "
                                +" AND "+ KEY_GRADE +" != '1' "
                                +" AND "+ KEY_GRADE +" != '2' "
                                +" AND "+ KEY_GRADE +" != '3' "
                                +" AND "+ KEY_GRADE +" != '4' "
                                +" AND "+ KEY_GRADE +" != '-' AND "
                                         +KEY_SEMESTER+" LIKE '"+sem +"'";

        String st = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {

                    DecimalFormat df = new DecimalFormat("#.##");
                    st = df.format(cursor.getDouble(0)).toString();

                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }

//To get a profile's passed subjects sum

    public String TotalSubjectsPassed(int ProfileID) {
        // Select All Query
        String selectQuery = "SELECT COUNT("+KEY_EXAMINATION+") FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID +" AND "+ KEY_EXAMINATION +" != '-'";
        String st = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    st = String.valueOf(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }


    //To get ECTS points sum for the given Profile

    public String TotalECTS(int ProfileID) {
        // Select All Query
        String selectQuery = "SELECT SUM("+KEY_ECTS+") FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID +" AND "+ KEY_GRADE +" != '-'";
        String st = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    st = String.valueOf(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }

    //To get SP sum for the given profile
    public String TotalDM(int ProfileID) {
        // Select All Query
        String selectQuery = "SELECT SUM("+KEY_DM+") FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID +" AND "+ KEY_GRADE +" != '-'";
        String st = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    st = String.valueOf(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }


    //To calculate and get the AVG for a given profile
    public String MO(int ProfileID) {
        // Select All Query
        //String selectQuery = "SELECT AVG(CAST("+KEY_GRADE+" AS FLOAT)) FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID
        String selectQuery = "SELECT AVG(CAST("+KEY_GRADE+" AS FLOAT)) FROM " + TABLE_GRADES +" WHERE "+KEY_PROFILEID+"="+ProfileID +" AND "+ KEY_GRADE +" != '-'"
                +" AND "+ KEY_GRADE +" != '0' "
                +" AND "+ KEY_GRADE +" != '1' "
                +" AND "+ KEY_GRADE +" != '2' "
                +" AND "+ KEY_GRADE +" != '3' "
                +" AND "+ KEY_GRADE +" != '4' ";
        Log.d("detQuery", selectQuery);


        String st = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {

                    DecimalFormat df = new DecimalFormat("#.##");
                    st = df.format(cursor.getDouble(0)).toString();
                } while (cursor.moveToNext());
            }
        db.close();

        return st;
    }



    //---------------------------------  	S U B S C R I P T I O N S    ---------------------------------------//

    //Adding new subscription
    public void addSubscription(UserSubscriptions us) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROFILEID, us.getProfileID());            // Connected Profile id
        values.put(KEY_SUBPERIOD, us.getPeriod());               // Subscriptions Period
        values.put(KEY_SUBCODE, us.getCode());                   // Subject's code
        values.put(KEY_SUBTITLE, us.getTitle());                 // Subject's title
        values.put(KEY_SUBSEMESTER, us.getSemester());           // Subject's semester
        values.put(KEY_SUBTYPE, us.getType());                   // Subject's type
        values.put(KEY_SUBDM, us.getDM());                       // Subject's Didaktikes monades
        values.put(KEY_SUBHOURS, us.getHours());                 // Subject's Hours
        values.put(KEY_SUBECTS, us.getECTS());                   // Subject's ECTS points


        // Inserting Row
        db.insert(TABLE_SUBSCRIPTIONS, null, values);
        db.close(); // Closing database connection
    }

    //To get a list of all the subscriptions

    public List<UserSubscriptions> getAllSubscriptions(int profileID) {
        List<UserSubscriptions> SubscriptionList = new ArrayList<UserSubscriptions>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SUBSCRIPTIONS +" WHERE " +KEY_SUBPROFILEID+ "="+profileID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    UserSubscriptions us = new UserSubscriptions();
                    //ua.setID(Integer.parseInt(cursor.getString(0)));             //Table Id
                    us.setCode(cursor.getString(3));                    //Subscription's code - (H/Y100)
                    us.setTitle(cursor.getString(4));                   //Subscription's Subject Title
                    us.setSemester(cursor.getString(5));                //Subscription's  Subject Semester - ST
                    us.setType(cursor.getString(6));                    //Subscription's  Subject Type - YE
                    us.setDM(cursor.getString(7));                      //Subscription's  Subject DM
                    us.setHours(cursor.getString(8));                   //Subscription's  Subject Hours
                    us.setECTS(cursor.getString(9));                    //Subscription's  Subject Semester


                    // Adding profile to profiles list
                    SubscriptionList.add(us);
                } while (cursor.moveToNext());
            }
        db.close();
        // return profile list
        return SubscriptionList;
    }

    //To delete a Subscription

    public void DeleteSubscriptions(int ProfileID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SUBSCRIPTIONS, KEY_SUBPROFILEID + " = ?", new String[] { Integer.toString(ProfileID) });

        db.close();

    }



    //---------------------------------  N E W	S U B S C R I P T I O N    ---------------------------------------//

    //Creating a new subscription for submission
    public void addNewSubscription(UserSubscriptions us) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROFILEID, us.getProfileID());            // Connected Profile id
        values.put(KEY_SUBCODE, us.getCode());                   // subscription's code
        values.put(KEY_SUBTITLE, us.getTitle());                 // subscription's title
        values.put(KEY_SUBSTATE, us.getState());                 // subscription's state enabled/disabled
        values.put(KEY_SUBCHECKBOXID, us.getCheckBoxId());         // subscription's CheckboxID


        // Inserting Row
        db.insert(TABLE_NEWSUBSCRIPTION, null, values);
        db.close(); // Closing database connection
    }


    //To get a new subscription list before submission
    public List<UserSubscriptions> getNewSubscription(int profileID) {
        List<UserSubscriptions> SubscriptionList = new ArrayList<UserSubscriptions>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEWSUBSCRIPTION +" WHERE " +KEY_SUBPROFILEID+ "="+profileID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    UserSubscriptions us = new UserSubscriptions();
                    //ua.setID(Integer.parseInt(cursor.getString(0)));             //Table Id
                    us.setCode(cursor.getString(2));                    //new Subscription's code - (H/Y100)
                    us.setTitle(cursor.getString(3));                   //new Subscription's Subject Title
                    us.setState(cursor.getString(4));                   //new Subscription's  State - Enabled/disabled
                    us.setCheckBoxId(cursor.getString(5));              //new Subscription's  CheckBoxid - PL100


                    // Adding profile to profiles list
                    SubscriptionList.add(us);
                } while (cursor.moveToNext());
            }
        db.close();
        // return profile list
        return SubscriptionList;
    }

    //To delete all new Subscriptions
    public void DeleteNewSubscriptions(int ProfileID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NEWSUBSCRIPTION, KEY_SUBPROFILEID + " = ?", new String[] { Integer.toString(ProfileID) });

        db.close();

    }


    //---------------------------------  	A P P L I C A T I O N S     ---------------------------------------//

    //Adding a new application
    public void addApplication(UserApplications ua) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_APPPROFILEID, ua.getProfileID());             // Connected Profile id
        values.put(KEY_APPSTATE, ua.getState());                     // Applicarion's STATE
        values.put(KEY_APPDATETIMESTAMP, ua.getDateTimeStamp());     // Application's Date and Time stamp
        values.put(KEY_APPTITLE, ua.getTitle());                     // Application's Title


        // Inserting Row
        db.insert(TABLE_APPLICATIONS, null, values);
        db.close(); // Closing database connection
    }


    //To get a list of all applications

    public List<UserApplications> getAllApplications(int profileID) {
        List<UserApplications> ApplicationList = new ArrayList<UserApplications>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_APPLICATIONS +" WHERE " +KEY_APPPROFILEID+ "="+profileID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    UserApplications ua = new UserApplications();
                    //ua.setID(Integer.parseInt(cursor.getString(0)));             //Table Id
                    ua.setState(cursor.getString(2));                            //Application's state
                    ua.setDateTimeStamp(cursor.getString(3));                    //Application's Date Time
                    ua.setTitle(cursor.getString(4));                            //Application's Title


                    // Adding profile to profiles list
                    ApplicationList.add(ua);
                } while (cursor.moveToNext());
            }
        db.close();
        // return profile list
        return ApplicationList;
    }


    //To get if there is already a Study Certificate submitted in applications

    public boolean PendingStudyCertificate(String ProfileID) {

        String selectQuery = "SELECT "+KEY_APPTITLE+" FROM " + TABLE_APPLICATIONS +" WHERE "+KEY_APPPROFILEID+"='"+ProfileID+"' AND "+KEY_APPSTATE+" LIKE 'Εκκρεμής' AND "+KEY_APPTITLE+" LIKE 'Πιστοποιητικό'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount()>0){db.close(); return true;}
        else {db.close();return false;}

    }

    //To get if there is already a Grade Certificate submitted in applications
    public boolean PendingGradeCertificate(String ProfileID) {

        String selectQuery = "SELECT "+KEY_APPTITLE+" FROM " + TABLE_APPLICATIONS +" WHERE "+KEY_APPPROFILEID+"='"+ProfileID+"' AND "+KEY_APPSTATE+" LIKE 'Εκκρεμής' AND "+KEY_APPTITLE+" LIKE 'Πιστοποιητικό Αναλυτικής Βαθμολογίας'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount()>0){db.close(); return true;}
        else {db.close();return false;}

    }

    //To delete an application
    public void DeleteApplications(int ProfileID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_APPLICATIONS, KEY_APPPROFILEID + " = ?", new String[] { Integer.toString(ProfileID) });

        db.close();

    }


    //---------------------------------  	I N F O R M A T I O N     ---------------------------------------//

    //Adding new information in the DB
    public void addInformation(GeneralInfos gi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_INFNAME, gi.getName());             // Title of the contact
        values.put(KEY_INFADDRESS, gi.getAddress());       // Contact's address
        values.put(KEY_INFPHONE, gi.getPhone());           // Contact's phone
        values.put(KEY_INFFAX, gi.getFax());               // Contact's fax
        values.put(KEY_INFMAIL, gi.getEmail());            // Contact's email
        values.put(KEY_INFWEBPAGE, gi.getWebPage());            // Contact's email
        values.put(KEY_INFTYPE, gi.getType());            // Contact's email

        // Inserting Row
        db.insert(TABLE_INFORMATION, null, values);
        db.close(); // Closing database connection
    }

    //To get a list of all DB information

    public List<GeneralInfos> getAllInformation() {
        List<GeneralInfos> InformationList = new ArrayList<GeneralInfos>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFORMATION+"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    GeneralInfos gi = new GeneralInfos();
                    //ua.setID(Integer.parseInt(cursor.getString(0)));
                    gi.setType(cursor.getString(1));                            //Information's Type
                    gi.setName(cursor.getString(2));                            //Information's Name
                    gi.setAddress(cursor.getString(3));                         //Information's Address
                    gi.setPhone(cursor.getString(4));                           //Information's Phone number
                    gi.setFax(cursor.getString(5));                             //Information's Fax number
                    gi.setEmail(cursor.getString(6));                           //Information's e-mail address
                    gi.setWebPage(cursor.getString(7));                         //Information's WebPage


                    // Adding this piece of information to information list
                    InformationList.add(gi);
                } while (cursor.moveToNext());
            }
        db.close();
        // return profile list
        return InformationList;
    }


    //---------------------------------  	A N N O U N C E M E N T S ---------------------------------------//

    //Adding new announcemnt in the DB
    public void addAnnouncement(Announcement an) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ANNTYPE, an.getType());         // type of announcement
        values.put(KEY_ANNTITLE, an.getTitle());       // announcement's title
        values.put(KEY_ANNURL, an.getURL());           // announcement's url
        values.put(KEY_ANNISREAD, an.getIsRead());     // announcement's read state

        // Inserting Row
        db.insert(TABLE_ANNOUNCEMENTS, null, values);
        db.close(); // Closing database connection
    }

    //getting all the announcements

    public List<Announcement> getAllAnnouncements() {
        List<Announcement> AnnouncementList = new ArrayList<Announcement>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ANNOUNCEMENTS+"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount()>0)
            if (cursor.moveToFirst()) {
                do {
                    Announcement an = new Announcement();
                    an.setID(cursor.getInt(0));            //ID
                    an.setType(cursor.getString(1));       //type
                    an.setURL(cursor.getString(2));        //url
                    an.setTitle(cursor.getString(3));      //title
                    an.setIsRead(cursor.getString(4));     //is read or Not

                    // Adding profile to profiles list
                    AnnouncementList.add(an);
                } while (cursor.moveToNext());
            }
        db.close();
        // return profile list
        return AnnouncementList;
    }



    //Deleting a specific announcement aka hiding from app
    public void DeleteAnnouncement(int id) {
        SQLiteDatabase db = this.getWritableDatabase();


        Log.d("detID", String.valueOf(id));
        db.delete(TABLE_ANNOUNCEMENTS, KEY_ANNID + " = ?",
                new String[]{String.valueOf(id)});

        db.close(); // Closing database connection
    }

// To delete all announcements from the DB
    public void DeleteAllAnnouncements()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ANNOUNCEMENTS, null, null);
        db.close();

    }

//To get all the DB Announcements
    public List<Integer> getAnnouncementsSum() {
        List<Integer> Counts = new ArrayList<Integer>();
        // Select Query

        String selectQueryGram = "SELECT COUNT("+KEY_ANNTYPE+") FROM " + TABLE_ANNOUNCEMENTS +" WHERE "+KEY_ANNTYPE+" LIKE 'Γραμματείας'";
        String selectQueryGen = "SELECT COUNT("+KEY_ANNTYPE+") FROM " + TABLE_ANNOUNCEMENTS +" WHERE "+KEY_ANNTYPE+" LIKE 'Γενικές'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorGram = db.rawQuery(selectQueryGram, null);
        Cursor cursorGen = db.rawQuery(selectQueryGen, null);

        // looping through all rows and adding to list

         if (cursorGram.getCount()>0)
            if (cursorGram.moveToFirst()) {
                do {
                    Counts.add(cursorGram.getInt(0));
                } while (cursorGram.moveToNext());
            }
        if (cursorGen.getCount()>0)
            if (cursorGen.moveToFirst()) {
                do {
                    Counts.add(cursorGen.getInt(0));
                } while (cursorGen.moveToNext());
            }


        db.close();

        return Counts;
    }
}