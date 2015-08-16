package asterios.eGram.app.Applications;


import android.util.Log;

import java.util.ArrayList;

import asterios.eGram.app.Database.userDataDB;

public class UserApplications {

    //private variables
    int _appid;                //database id
    int _profileID;            //user profile id
    String _State;             //Application's state Pending or Fullfilled
    String _DateTimeStamp;     //Application's Date and Time Stamp
    String _Title;             //Application's title
    




    // Empty constructor
    public UserApplications(){}

    // constructor
    public UserApplications(int id, int profileID, String DateTimeStamp, String Title, String State){
        this._appid = id;
        this._profileID = profileID;
        this._State = State;
        this._DateTimeStamp = DateTimeStamp;
        this._Title = Title;
        
    }


    // To use in order to pass the user's Applications to the DB
    public void UpdateStudentApplications(ArrayList<String> details, int prID, userDataDB updb)
    {

        UserApplications ua = new UserApplications();
        ua.setID(1);
        ua.setProfileID(prID);
        updb.DeleteApplications(prID); //Clear everything up before refresh

        if (details.get(0).contains("Δεν")){
            ua.setDateTimeStamp("-");
            ua.setTitle(details.get(0));
            updb.addApplication(ua);

        }

        else {//This runs in case there is even one application
            ArrayList<String> Ekkremeis = new ArrayList<String>();
            ArrayList<String> Olokliromenes = new ArrayList<String>();
            Ekkremeis.clear();
            Olokliromenes.clear();

            int i = 0;
            while (i < details.size() && !details.get(i).contains("Ολοκληρωμένες")) {
                if (details.get(i).contains("Εκκρεμείς")) i++;
                Ekkremeis.add(details.get(i));
                i++;
            }

            while (i < details.size()) {
                if (details.get(i).contains("Ολοκληρωμένες")) i++;
                Olokliromenes.add(details.get(i));
                i++;
            }

            int EkkStep = Ekkremeis.size() / 2;
            int OlStep = Olokliromenes.size() / 2;

            ua.setState("Εκκρεμής");
            for (int y = 0; y < Ekkremeis.size() - EkkStep; y++) {

                ua.setDateTimeStamp(Ekkremeis.get(y));
                ua.setTitle(Ekkremeis.get(y + EkkStep));
                updb.addApplication(ua);
            }
            ua.setState("Ολοκληρωμένη");
            for (int y = 0; y < Olokliromenes.size() - OlStep; y++) {

                ua.setDateTimeStamp(Olokliromenes.get(y));
                ua.setTitle(Olokliromenes.get(y + OlStep));
                updb.addApplication(ua);
            }
        }//only if there is even one application

    }


    // getting Subscriptions ID
    public int getID(){  return this._appid;   }

    // setting Subscriptions id
    public void setID(int id){ this._appid = id;  }

    // get connected user's id
    public int getProfileID(){  return this._profileID;   }

    // setting connected user's id
    public void setProfileID(int profileID){ this._profileID = profileID;}

    // getting application's DateTimeStamp
    public String getDateTimeStamp(){ return this._DateTimeStamp;  }

    // setting application's DateTimeStamp
    public void setDateTimeStamp(String DateTimeStamp){ this._DateTimeStamp = DateTimeStamp;  }

    // getting application's Title
    public String getTitle(){ return this._Title;  }

    // setting application's Title
    public void setTitle(String title){ this._Title = title;  }

    // getting application's State
    public String getState(){ return this._State; }

    // setting application's State
    public void setState(String State){ this._State = State; }

}