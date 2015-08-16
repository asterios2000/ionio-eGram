package asterios.eGram.app.Grades;


import android.util.Log;

import java.util.ArrayList;

import asterios.eGram.app.Database.userDataDB;

public class UserGrades {

    //private variables
    int _grid;                 //database ID
    int _profileID;            //The profile id where the grades are connected
    String _Semester;          //Subject's semester
    String _Title;             //Subject's full title aka name
    String _Type;              //Subject's type e.g Obligatory
    String _DM;                //Subject's SP
    String _ECTS;              //Subject's ECTS
    String _Grade;             //Subject's Grade
    String _ExaminationPeriod; //Subject's examination period


    // Empty constructor
    public UserGrades(){}

    // constructor
    public UserGrades(int id, int profileID, String Semester, String Title, String Type, String DM, String ECTS, String Grade, String ExaminationPeriod){
        this._grid = id;
        this._profileID = profileID;
        this._Semester = Semester;
        this._Title = Title;
        this._Type = Type;
        this._DM = DM;
        this._ECTS = ECTS;
        this._Grade = Grade;
        this._ExaminationPeriod = ExaminationPeriod;


    }

    // To use in order to update the database with the new grades
    public void UpdateStudentGrades(ArrayList<String> details, int prID, userDataDB updb)
    {
        String sem = null;
        UserGrades ug = new UserGrades();
        ug.setID(1);
        ug.setProfileID(prID);
        updb.DeleteGrades(prID); //First it deletes everything before creation


        int i = 0;
        while (i<=details.size()-7)

        {

           if (details.get(i).contains("Εξάμηνο")) {sem = details.get(i).substring(7); i++;}

           ug.setSemester(sem);
           ug.setTitle(details.get(i));
           ug.setType(details.get(i + 1));
           ug.setDM(details.get(i + 2));
            //i + 3 is the Hours that we don't want here
           ug.setECTS(details.get(i + 4));
           ug.setGrade(details.get(i + 5).replaceAll(",",".")); //With correction from , to . in order to get the AVERAGE correct
           ug.setExaminationPeriod(details.get(i + 6));

           updb.addGrade(ug);
           i = i + 7;
        }
    }


    // getting grades ID
    public int getID(){  return this._grid;   }

    // setting grades id
    public void setID(int id){ this._grid = id;  }

    // get connected user's id
    public int getProfileID(){  return this._profileID;   }

    // setting connected user's id
    public void setProfileID(int profileIDID){ this._profileID = profileIDID;  }

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

    // getting Subject's Grade
    public String getGrade(){ return this._Grade;   }

    // setting Subject's Grade
    public void setGrade(String grade){ this._Grade = grade;  }

    // getting Subject's examination period
    public String getExaminationPeriod(){ return this._ExaminationPeriod;   }

    // setting Subject's examination period
    public void setExaminationPeriod(String exp){ this._ExaminationPeriod = exp;  }




}