package asterios.eGram.app.Profile;


import java.util.ArrayList;

public class UserProfile {

    //private variables
    int _id;                       //database ID
    int _defaultProfile;           //To check whether a profile is default or not
    String _profilePicturePath;    //profile photo path
    String _ScreenName;            //Profile name (not user name)
    String _LastLoginDate;         //when was the last login date
    String _upName;                //User's real name from ionio gram web
    String _upSurName;             //User's real Surname from ionio gram web
    String _upUserName;            //User's username from ionio gram web
    String _upCode;                //User's password from ionio gram web
    String _upAEM;                 //User's AEM
    String _upDepartment;          //User's Department
    int _upCurrentSemester;        //User's current semester
    String _upPrSpoudon;           //Program of studies
    String _upField;               //Field of studies (Kateuthinsi)
    String _upRegistrationYear;    //Registration Year
    String _upRegistrationWay;     //Registration Way
    String _upTotalSubjectsPassed; //Total passed subjects
    String _upTotalECTS;           //Total ECTS points
    String _upMO;                  //Average

    // Empty constructor
    public UserProfile(){}

    // Full constructor
    public UserProfile(int id, int defaultProfile, String ScreenName, String Path, String LastLoginDate, String Name,
                       String SurName, String UserName, String Code, String AEM, String Dep, int CurSemester,
                       String PrSpoudon, String Field, String RegYear, String RegWay, String TotSubjPassed, String TotECTS, String MO){
        this._id = id;
        this._defaultProfile = defaultProfile;
        this._ScreenName = ScreenName;
        this._profilePicturePath = Path;
        this._LastLoginDate = LastLoginDate;
        this._upName = Name;
        this._upSurName = SurName;
        this._upUserName = UserName;
        this._upCode = Code;
        this._upAEM = AEM;
        this._upDepartment = Dep;
        this._upCurrentSemester = CurSemester;
        this._upPrSpoudon = PrSpoudon;
        this._upField = Field;
        this._upRegistrationYear = RegYear;
        this._upRegistrationWay = RegWay;
        this._upTotalSubjectsPassed = TotSubjPassed;
        this._upTotalECTS = TotECTS;
        this._upMO = MO;

    }

    // New Profile constructor - Basic Stuff before fetching details from Ionio Gram Web
    public UserProfile(int id, int defaultProfile, String ScreenName, String Path, String LastLoginDate, String UserName, String Code){
        this._id = id;
        this._defaultProfile = defaultProfile;
        this._ScreenName = ScreenName;
        this._profilePicturePath = Path;
        this._LastLoginDate = LastLoginDate;
        this._upName = "-";
        this._upSurName = "-";
        this._upUserName = UserName;
        this._upCode = Code;
        this._upAEM = "-";
        this._upDepartment = "-";
        this._upCurrentSemester = 0;
        this._upPrSpoudon = "-";
        this._upField = "-";
        this._upRegistrationYear = "-";
        this._upRegistrationWay = "-";
        this._upTotalSubjectsPassed = "-";
        this._upTotalECTS = "-";
        this._upMO = "-";

    }


    // To use in order to make a profile which will update the database
   public void UpdateStudentDetails(ArrayList<String> details)
   {

       //details are parsed data from ionio Gram Web

       this.setName(details.get(5).substring(details.get(5).lastIndexOf(':') + 1));                             //name
       this.setSurName(details.get(4).substring(details.get(4).lastIndexOf(':') + 1));                          //surname
       this.setUserName(details.get(0).substring(details.get(0).lastIndexOf(':') + 1));                         //Username
       this.setAEM(details.get(6).substring(details.get(6).lastIndexOf(':') + 1));                              //AEM
       this.setDepartment(details.get(7).substring(details.get(7).lastIndexOf(':') + 1));                       //Department
       this.setCurSemester(Integer.parseInt(details.get(10).substring(details.get(10).lastIndexOf(':') + 1)));  //Current Semester
       this.setPrSpoudon(details.get(8).substring(details.get(8).lastIndexOf(':') + 1));                        //Program of studies
       this.setField(details.get(9).substring(details.get(9).lastIndexOf(':') + 1));                            //Specialty
       this.setRegistrationWay(details.get(3).substring(details.get(3).lastIndexOf(':') + 1));                  //Registration Way
       this.setRegistrationYear(details.get(1).substring(details.get(1).lastIndexOf(':') + 1));                 //Registration Year

   }



    // getting Profile ID
    public int getID(){  return this._id;   }

    // setting Profile id
    public void setID(int id){ this._id = id;  }

    // get Default Profile Value
    public int getDefaultProfile(){  return this._defaultProfile;   }

    // setting Default Profile Value
    public void setDefaultProfile(int defaultProfile){ this._defaultProfile = defaultProfile;  }

    // getting Profile Picture Path
    public String getProfilePicturePath(){ return this._profilePicturePath;  }

    // setting Profile Picture Path
    public void setProfilePicturePath(String path){ this._profilePicturePath = path;  }

    // getting Profile Screen name
    public String getScreenName(){ return this._ScreenName;  }

    // setting Profile Screen name
    public void setScreenName(String sname){ this._ScreenName = sname;  }

    // getting last Login Date
    public String getLastLoginDate(){ return this._LastLoginDate; }

    // setting last Login Date
    public void setLastLoginDate(String dt){ this._LastLoginDate = dt; }

    // getting User's Real name
    public String getName(){ return this._upName;  }

    // setting User's Real name
    public void setName(String name){ this._upName = name;  }

    // getting User's Real surname
    public String getSurName(){ return this._upSurName;  }

    // setting User's Real surname
    public void setSurName(String surname){ this._upSurName = surname;  }

    // getting User Name
    public String getUserName(){ return this._upUserName;   }

    // setting User Name
    public void setUserName(String usrName){ this._upUserName = usrName;  }

    // getting User's  Code number
    public String getCode(){ return this._upCode;   }

    // setting User's  Code number
    public void setCode(String cd){ this._upCode = cd;  }

    //Getting AEM
    public String getAEM(){ return this._upAEM;  }

    // setting AEM
    public void setAEM(String AEM){ this._upAEM = AEM;  }

    //Getting Department
    public String getDepartment(){ return this._upDepartment;  }

    // setting Department
    public void setDepartment(String dep){ this._upDepartment = dep;  }

    //Getting Current Semester
    public int getCurSemester(){ return this._upCurrentSemester;  }

    // setting Current Semester
    public void setCurSemester(int cursem){ this._upCurrentSemester = cursem;  }

    //Getting programma spoudon
    public String getPrSpoudon(){ return this._upPrSpoudon;  }

    // setting programma spoudon
    public void setPrSpoudon(String prs){ this._upPrSpoudon = prs;  }

    //Getting field of Studies
    public String getField(){ return this._upField;  }

    // setting field of Studies
    public void setField(String fi){ this._upField = fi;  }

    //Getting Year Period Of registration
    public String getRegistrationYear(){ return this._upRegistrationYear;  }

    // setting Year of registration
    public void setRegistrationYear(String regY){ this._upRegistrationYear = regY;  }

    //Getting Way Of registration
    public String getRegistrationWay(){ return this._upRegistrationWay;  }

    // setting Way of registration
    public void setRegistrationWay(String regW){ this._upRegistrationWay = regW;  }

    //Getting Total Subjects Passed
    public String getTotalSubjectsPassed(){ return this._upTotalSubjectsPassed;  }

    // setting Total Subjects Passed
    public void setTotalSubjectspassed(String tSp){ this._upTotalSubjectsPassed = tSp;  }

    //Getting Total ECTS
    public String getTotalECTS(){ return this._upTotalECTS;  }

    //setting Total ECTS
    public void setTotalECTS(String ECTS){ this._upTotalECTS = ECTS; }

    //Getting MO
    public String getMO(){ return this._upMO;  }

    //setting MO
    public void setMO(String MO){ this._upMO = MO; }




}