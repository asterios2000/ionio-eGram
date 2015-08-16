package asterios.eGram.app.Subscriptions;


import android.util.Log;
import java.util.ArrayList;
import asterios.eGram.app.Database.userDataDB;

public class UserSubscriptions {

    //private variables
    int _subid;                //database ID
    int _profileID;            //The profile id where the subscriptions are connected
    String _Period;            //Subscription's period (eg Winter 2012)
    String _Code;              //Subject's Code (e.g. HY345)
    String _Title;             //Subject's Full Tilte
    String _Semester;          //Semester that the Subject belongs e.g ST
    String _Type;              //Subject's type e.g. YE
    String _DM;                //Subject's SP
    String _Hours;             //Subject's Hours
    String _ECTS;              //Subject's ECTS
    String _State;             //If we have a new subscription case
    String _CheckBoxId;        //To keep the check boxes ids in order to pass them to ionio gram WEB


    // Empty constructor
    public UserSubscriptions(){}

    // constructor
    public UserSubscriptions(int id, int profileID, String Period,String Code, String Title, String Semester , String Type, String DM, String Hours, String ECTS){
        this._subid = id;
        this._profileID = profileID;
        this._Period = Period;
        this._Code = Code;
        this._Title = Title;
        this._Semester = Semester;
        this._Type = Type;
        this._DM = DM;
        this._Hours = Hours;
        this._ECTS = ECTS;
    }


    // constructor for new Subscription
    public UserSubscriptions(String Code, String Title, String State, String CheckBoxId){

        this._Code = Code;
        this._Title = Title;
        this._State = State;
        this._CheckBoxId = CheckBoxId;
    }


    // To use in order to pass the user's subscriptions to the DB
    public void UpdateStudentSubscriptions(ArrayList<String> details, int prID, userDataDB updb)
    {
        String sem = null;
        UserSubscriptions us = new UserSubscriptions();
        us.setID(1);
        us.setProfileID(prID);
        us.setPeriod("current");
        int SubjectsCount = details.size()/7;
        ArrayList<String> finals = new ArrayList<String>();
        finals.clear();
        updb.DeleteSubscriptions(prID); //It deletes everything in that profile before proceeding

        for (int m = 0; m<SubjectsCount ;m++ ) {


            for (int r = m; r <= details.size() - 1; r += SubjectsCount) {

                finals.add(details.get(r));

            }
        }


            for (int k = 0; k < finals.size(); k+=7 ) {

                us.setCode(finals.get(k));
                us.setTitle(finals.get(k + 1));
                us.setSemester(finals.get(k + 2));
                us.setType(finals.get(k + 3));
                us.setDM(finals.get(k + 4));
                us.setHours(finals.get(k + 5));
                us.setECTS(finals.get(k + 6));

                updb.addSubscription(us);
            }
    }


    public void UpdateNewSubscription(ArrayList<String> details, int prID, userDataDB updb)
    {
        UserSubscriptions us = new UserSubscriptions();
        us.setID(1);
        us.setProfileID(prID);

        int SubjectsCount = details.size()/4;
        ArrayList<String> finals = new ArrayList<String>();
        finals.clear();
        updb.DeleteNewSubscriptions(prID); //It deletes everything in that profile before proceeding

        for (int m = 0; m<SubjectsCount; m++ ) {

            for (int r = m; r <= details.size() - 1; r += SubjectsCount) {

                Log.d("detSubscr", details.get(r));
                finals.add(details.get(r));

            }
        }



        for (int k = 0; k < finals.size(); k+=4 ) {

            Log.d("detFinal_State", finals.get(k));
            Log.d("detFinal_Id", finals.get(k+1));

            us.setState(finals.get(k));
            us.setCheckBoxId(finals.get(k + 1));
            us.setCode(finals.get(k + 2));
            us.setTitle(finals.get(k + 3));

            updb.addNewSubscription(us);

        }
    }


    // getting Subscriptions ID
    public int getID(){  return this._subid;   }

    // setting Subscriptions id
    public void setID(int id){ this._subid = id;  }

    // get connected user's id
    public int getProfileID(){  return this._profileID;   }

    // setting connected user's id
    public void setProfileID(int profileID){ this._profileID = profileID;}

    // getting subject's Semester
    public String getSemester(){ return this._Semester;  }

    // setting subject's Semester
    public void setSemester(String semester){ this._Semester = semester;  }

    // getting subject's Title
    public String getTitle(){ return this._Title;  }

    // setting subject's Title
    public void setTitle(String title){ this._Title = title;  }

    // getting subject's Type
    public String getType(){ return this._Type; }

    // setting subject's Type
    public void setType(String type){ this._Type = type; }

    // getting Didaktikes monades
    public String getDM(){ return this._DM;  }

    // setting Didaktikes monades
    public void setDM(String dm){ this._DM = dm;  }

    // getting ECTS points
    public String getECTS(){ return this._ECTS;  }

    // setting ECTS points
    public void setECTS(String ects){ this._ECTS = ects;  }

    // getting Subject's Hours
    public String getHours(){ return this._Hours;   }

    // setting Subject's Hours
    public void setHours(String hour){ this._Hours = hour;  }

    // getting Subject's Code
    public String getCode(){ return this._Code;   }

    // setting Subject's Code
    public void setCode(String code){ this._Code = code;  }

    // getting Subscriptions Period
    public String getPeriod(){ return this._Period;   }

    // setting Subscriptions Period
    public void setPeriod(String period){ this._Period = period;  }

    // getting Subscriptions State (for new subscription)
    public String getState(){ return this._State;   }

    // setting Subscriptions State (for new subscription)
    public void setState(String state){ this._State = state;  }

    // getting Subscriptions CheckBoxId (for new subscription)
    public String getCheckBoxId(){ return this._CheckBoxId;   }

    // setting Subscriptions State (for new subscription)
    public void setCheckBoxId(String id){ this._CheckBoxId = id;  }

}