package asterios.eGram.app.Informations;


import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.R;

public class GeneralInfos {

    //private variables
    int _infid;               //Database Id
    String _Name;             //Full information title
    String _Address;          //Living Address
    String _Phone;            //Phone Number
    String _Fax;              //Fax number
    String _Email;            //E-mail
    String _WebPage;          //Webpage
    String _Type;             //Type, DEP, ETEP, Stuff etc


    // Empty constructor
    public GeneralInfos(){}

    // constructor
    public GeneralInfos(int id, String Name, String Address, String Phone, String Fax, String Email, String WebPage, String Type){
        this._infid = id;
        this._Name = Name;
        this._Address = Address;
        this._Phone = Phone;
        this._Fax = Fax;
        this._Email = Email;
        this._WebPage = WebPage;
        this._Type = Type;
    }


    // To use in order to pass the user's subscriptions to the DB
    static public void CreateGeneralInfos(Context ctx)
    {
        userDataDB upDB = new userDataDB(ctx);

        //Department Details
        GeneralInfos gi = new GeneralInfos(1, "Γραμματεία Τμήματος Πληροφορικής Ιονίου Πανεπιστημίου"," Πλατεία Τσιριγώτη 7 (πρώην Πλατεία Παλιού Ψυχιατρείου) - Κτήριο Γραμματειών (Κτήριο 3) Κέρκυρα, 49100",
                "+30 26610 8 (7760), 8 (7761), 8 (7763)","+30 26610 87766","cs@ionio.gr","www.di.ionio.gr","Γραμματεία");
        upDB.addInformation(gi);

        //DEP - Information

        String Names[] = {"Βασίλειος Χρυσικόπουλος","Παναγιώτης Βλάμος", "Σπύρος Σιούτας", "Εμμανουήλ Μάγκος",
                          "Κωνσταντίνος Οικονόμου", "Χρήστος Αναγνωστόπουλος", "Παναγιώτης Κουρουθανάσης", "Φοίβος Μυλωνάς",
                          "Δημήτριος Τσουμάκος", "Θεόδωρος Ανδρόνικος", "Αδαμαντία Πατέλη", "Μάρκος Αυλωνίτης",
                          "Μιχαήλ Στεφανιδάκης", "Κωνσταντίνος Χωριανόπουλος", "Κάτια - Λήδα Κερμανίδου", "Αγγελική Τσώχου"};

        String Phones[] = {"+30 26610 87120","+30 26610 87710","+30 26610 87706","+30 26610 87704",
                           "+30 26610 87708","+30 26610 87756","+30 26610 87701","+30 26610 87756",
                           "+30 26610 87756","+30 26610 87712","+30 26610 87714","+30 26610 87702",
                           "+30 26610 87709", "+30 26610 87707","+30 26610 87703","+30 26610 87738"};
        String Faxes[] = {"+30 26610 87117","+30 26610 87766","+30 26610 87766","+30 26610 87766",
                           "+30 26610 87766","+30 26610 87766","+30 26610 87766","+30 26610 87766",
                            "+30 26610 87766","+30 26610 87766","+30 26610 87766","+30 26610 87766",
                            "+30 26610 87766","+30 26610 87766","+30 26610 87766", "+30 26610 87766"};
        String Emails[] = {"vchris@ionio.gr","vlamos@ionio.gr","sioutas@ionio.gr","emagos@ionio.gr",
                           "okon@ionio.gr","christos@ionio.gr","pkour@ionio.gr","fmylonas@ionio.gr",
                           "dtsouma@ionio.gr","andronikos@ionio.gr","pateli@ionio.gr","avlon@ionio.gr",
                           "mistral@ionio.gr","choko@ionio.gr","kerman@ionio.gr","atsohou@ionio.gr"};
        String WebPages[] = {"-","http://www.ionio.gr/~vlamos","http://www.ionio.gr/~sioutas","http://di.ionio.gr/~emagos/",
                            "http://www.ionio.gr/~okon","http://www.ionio.gr/~christos","http://www.ionio.gr/~pkour","http://image.ntua.gr/~fmylonas/",
                            "http://www.ionio.gr/~dtsouma","http://www.ionio.gr/~andronikos","http://www.ionio.gr/~pateli","http://di.ionio.gr/~avlon/",
                            "http://www.ionio.gr/~mistral","http://www.ionio.gr/~choko","http://www.ionio.gr/~kerman","-"};

        for (int i = 0; i<Names.length; i++) {
            GeneralInfos giDEP = new GeneralInfos(2,Names[i],"-",Phones[i], Faxes[i],Emails[i],WebPages[i],"ΔΕΠ");
            upDB.addInformation(giDEP);
        }


        //Etep Information
        gi._infid = 2;
        String EtepNames[] = {"Αλέξανδρος Πανάρετος", "Σπύρος Βούλγαρης" };
        String EtepPhones[] = {"+30 26610 87754", "+30 26610 87753"};
        String EtepFaxes[] = {"+30 26610 87766","+30 26610 87766" };
        String EtepEmails[] = {"alex@ionio.gr","svoul@ionio.gr"};
        String EtepWebPages[] = {"http://www.ionio.gr/~alex","-"};
        for (int i = 0; i<EtepNames.length; i++) {
            GeneralInfos giETEP = new GeneralInfos(3,EtepNames[i],"-",EtepPhones[i], EtepFaxes[i],EtepEmails[i],EtepWebPages[i],"ΕΤΕΠ");
            upDB.addInformation(giETEP);
        }

        //Teaching Stuff
        GeneralInfos giDID = new GeneralInfos(4, "Ιωάννης Καρύδης","-","-","-","karydis@ionio.gr","-","Διδακτικό");
        upDB.addInformation(giDID);


    }


    // getting Information ID
    public int getID(){  return this._infid;   }

    // setting Information id
    public void setID(int id){ this._infid = id;  }

    // getting Information name
    public String getName(){ return this._Name;  }

    // setting Information Semester
    public void setName(String Name){ this._Name = Name;  }

    // getting Information Address
    public String getAddress(){ return this._Address;  }

    // setting Information Address
    public void setAddress(String Address){ this._Address = Address;  }

    // getting Information Phone
    public String getPhone(){ return this._Phone; }

    // setting Information Phone
    public void setPhone(String Phone){ this._Phone = Phone; }

    // getting Information Fax
    public String getFax(){ return this._Fax;  }

    // setting Information Fax
    public void setFax(String Fax){ this._Fax = Fax;  }

    // getting Information email
    public String getEmail(){ return this._Email;  }

    // setting Information email
    public void setEmail(String Email){ this._Email = Email;  }

    // getting Information webpage
    public String getWebPage(){ return this._WebPage;  }

    // setting Information webpage
    public void setWebPage(String wp){ this._WebPage = wp;  }

    public String getType(){ return this._Type;  }

    // setting Information webpage
    public void setType(String tp){ this._Type = tp;  }

}