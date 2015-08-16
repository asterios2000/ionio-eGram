package asterios.eGram.app.Announcements;


import java.util.ArrayList;

import asterios.eGram.app.Database.userDataDB;

public class Announcement {

    //private variables
    int _annid;               //database id
    String _Type;             //type of announcement (secreteriat or general)
    String _URL;              //announcement's url
    String _Title;            //announcement's title
    String _IsRead;           //if it's been read or not

    

   // Empty constructor
    public Announcement(){}

    // constructor
    public Announcement(int id, String Type, String url, String Title, String isRead){
        this._annid = id;
        this._Type = Type;
        this._URL = url;
        this._Title = Title;
        this._IsRead = isRead;


    }


    // To use in order to pass announcements to the DB
    public void UpdateAnnouncements(ArrayList<String> details, userDataDB updb, String type)
    {


        Announcement an = new Announcement();
        an.setID(1);

        for (int i=0;i<details.size();i+=2) {

            an.setType(type);
            an.setURL(details.get(i).toString());
            an.setTitle(details.get(i + 1).toString());
            an.setIsRead("No");
            updb.addAnnouncement(an);
        }



    }


    // getting Announcement ID
    public int getID(){  return this._annid;   }

    // setting Announcement id
    public void setID(int id){ this._annid = id;  }

    // getting Announcement url
    public String getURL(){ return this._URL;  }

    // setting Announcement url
    public void setURL(String url){ this._URL = url;  }

    // getting Announcement's Title
    public String getTitle(){ return this._Title;  }

    // setting Announcement's Title
    public void setTitle(String title){ this._Title = title;  }

    // getting Announcement's Type
    public String getType(){ return this._Type; }

    // setting Announcement's Type
    public void setType(String Type){ this._Type = Type; }

    // getting Announcement's isread
    public String getIsRead(){ return this._IsRead; }

    // setting Announcement's isread
    public void setIsRead(String IsRead){ this._IsRead = IsRead; }

}