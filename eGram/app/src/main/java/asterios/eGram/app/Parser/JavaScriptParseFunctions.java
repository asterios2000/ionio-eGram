package asterios.eGram.app.Parser;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collections;

import asterios.eGram.app.eGramFunctions;

public class JavaScriptParseFunctions {

    //Variables
    public static ArrayList<String> finalUserDetails;
    public static ArrayList<String> finalUserGrades;
    public static ArrayList<String> finalUserSubscriptions;
    public static ArrayList<String> finalUserApplications;
    public static ArrayList<String> finalAnnouncements;
    public static ArrayList<String> finalNewSubscriptionSubjects;
    public static boolean errorOnPage = false;
    public static boolean subscritpionsTime = false;


    // Setters & Getters
    public void setFinalUserDetails(ArrayList<String> finalUserDetails) {

        this.finalUserDetails = finalUserDetails;
    }
    public void setFinalUserGrades(ArrayList<String> finalUserGrades) {

        this.finalUserGrades = finalUserGrades;
    }

    public void setFinalUserSubscriptions(ArrayList<String> finalUserSubscriptions) {

        this.finalUserSubscriptions = finalUserSubscriptions;
    }
    public void setFinalUserApplications(ArrayList<String> finalUserApplications) {

        this.finalUserApplications = finalUserApplications;
    }

    public void setFinalAnnouncements(ArrayList<String> announcements) {

        this.finalAnnouncements = announcements;
    }

    public void setSubscriptionsTime(boolean time) {

        this.subscritpionsTime = time;
    }
    public void setFinalNewSubscriptionSubjects(ArrayList<String> subjects) {

        this.finalNewSubscriptionSubjects = subjects;
    }

    public ArrayList<String> getFinalUserDetails() { return finalUserDetails; }
    public ArrayList<String> getFinalUserGrades() { return finalUserGrades; }
    public ArrayList<String> getFinalUserSubscriptions() { return finalUserSubscriptions; }
    public ArrayList<String> getFinalUserApplications() { return finalUserApplications; }
    public ArrayList<String> getFinalAnnouncements() { return finalAnnouncements; }
    public ArrayList<String> getFinalNewSubscriptionSubjects() { return finalNewSubscriptionSubjects; }
    public boolean getSubscriptionsTime() { return subscritpionsTime; }


    public boolean pageHasError() {return errorOnPage;}


    private Context ctx;

    //Constructor
    public JavaScriptParseFunctions(Context ctx) {
        this.ctx = ctx;
    }


    public ArrayList<String> personalDetailsCorrection(String str)
    {

        int onomaXristi = str.indexOf("Όνομα χρήστη");
        int stoixeiaEggrafis = str.indexOf("Στοιχεία εγγραφής");
        int akadEtos = str.indexOf("Ακαδ.έτος");
        int periodos = str.indexOf("Περίοδος");
        int tropos = str.indexOf("Τρόπος εγγραφής");
        int eksamino = str.indexOf("Εξάμηνο");
        int eksaminob = str.lastIndexOf("Εξάμηνο");
        int eponimo = str.indexOf("Επώνυμο");
        int onoma = str.indexOf("Όνομα:");
        int aem = str.indexOf("ΑEΜ");
        int tmima = str.indexOf("Τμήμα");
        int ps = str.indexOf("Πρόγραμμα Σπουδών");
        int kat = str.indexOf("Κατεύθυνση");
        int monimi = str.indexOf("Μόνιμη διεύθυνση");

        ArrayList<String> userDetails = new ArrayList<String>();
        userDetails.clear();
        userDetails.add(elliminateStartSpace(str.substring(onomaXristi,stoixeiaEggrafis))); //user name          //0 p11kome
        userDetails.add(elliminateStartSpace(str.substring(akadEtos, periodos))); //academic year                //1 2011-2012 (aka registration Year)
        userDetails.add(elliminateStartSpace(str.substring(periodos, eksamino))); //registration year            //2 XEIM
        userDetails.add(elliminateStartSpace(str.substring(tropos, eponimo))); //way of registration             //3 KATATAKTHRIES Eksetaseis (aka Registration Way)
        userDetails.add(elliminateStartSpace(str.substring(eponimo, onoma))); //lastname                         //4 Komertzoglou
        userDetails.add(elliminateStartSpace(str.substring(onoma, aem))); //name                                 //5 Asterios
        userDetails.add(elliminateStartSpace(str.substring(aem, tmima))); //AEM                                  //6 P2011059
        userDetails.add(elliminateStartSpace(str.substring(tmima, eksaminob))); //Department                     //7 Tmima Pliroforikis (aka Department)
        userDetails.add(elliminateStartSpace(str.substring(ps, kat))); //Program of studies                      //8 Programma Spoudon 2004-14
        userDetails.add(elliminateStartSpace(str.substring(kat, monimi))); //Specialty                           //9 Pliroforiki Anthropistikes Epistimes (aka Field)
        userDetails.add(elliminateStartSpace(str.substring(eksaminob, ps))); //current semester                  //10 8
        return userDetails;
    }

    public ArrayList<String> GradesCorrection(String str)
    {

        ArrayList<String> GradesDetails = new ArrayList<String>();
        ArrayList<Integer> SubjectPlaces = new ArrayList<Integer>();
        ArrayList<Integer> SemesterPlaces = new ArrayList<Integer>();
        ArrayList<Integer> Places = new ArrayList<Integer>();
        GradesDetails.clear();
        SubjectPlaces.clear();
        SemesterPlaces.clear();
        Places.clear();

        int i = 0;
        while((i=(str.indexOf("Εξάμηνο",i)+1))>0)SemesterPlaces.add(i);

        i = 0;
        while((i=(str.indexOf("topBorderLight",i)+1))>0)SubjectPlaces.add(i);

        Places.addAll(SemesterPlaces);
        Places.addAll(SubjectPlaces);
        Collections.sort(Places);


        for (int temp : Places) {

            if (SemesterPlaces.contains(temp)) {
                int j = 0;
                j = str.substring(temp + 7).indexOf("<");
                GradesDetails.add("Εξάμηνο " +str.substring(temp + 7, temp + 7 + j).trim());

            }
            else
            {
                int j = 0;
                j = str.substring(temp + 15).indexOf("<");
                GradesDetails.add(str.substring(temp + 15, temp + 15 + j).trim());
            }

        }


        return GradesDetails;
    }

    public ArrayList<String> SubscriptionsCorrection(String str)
    {

        ArrayList<String> SubScriptionsDetails = new ArrayList<String>();
        ArrayList<Integer> CodePlaces = new ArrayList<Integer>();
        ArrayList<Integer> TitlePlaces = new ArrayList<Integer>();
        ArrayList<Integer> AllOtherPlaces = new ArrayList<Integer>();
        ArrayList<String> Temp = new ArrayList<String>();

        SubScriptionsDetails.clear();
        CodePlaces.clear();
        TitlePlaces.clear();
        AllOtherPlaces.clear();
        Temp.clear();


        int i = 0;
        int j = 0;

         //CODES
        while((i=(str.indexOf("<img",i)+1))>0)CodePlaces.add(i);

        for (int temp : CodePlaces) {


            j = 0;
            j = str.substring(temp + 3).indexOf("<");
            SubScriptionsDetails.add(str.substring(temp + 3, temp + 3 + j).trim());

        }

        //TITLES
        i = 0;
        while((i=(str.indexOf("_self')\">",i)+1))>0)TitlePlaces.add(i);

        for (int temp : TitlePlaces) {


            j = 0;
            j = str.substring(temp + 9).indexOf("<");
            SubScriptionsDetails.add(str.substring(temp + 9, temp + 9 + j).trim());

        }

        //SEMESTERS - TYPES -DM - HOURS - ECTS
        i = 0;
        while((i=(str.indexOf("<td width=\"5%\" valign=\"top\">",i)+1))>0)AllOtherPlaces.add(i);


        for (int m = 0; m<=4 ;m++ ){


        for (int r = m; r <=AllOtherPlaces.size()-1; r += 5) {

            j = 0;
            j = str.substring(AllOtherPlaces.get(r) + 27).indexOf("<");
            SubScriptionsDetails.add(str.substring(AllOtherPlaces.get(r) + 27, AllOtherPlaces.get(r) + 27 + j).trim());

        }
    }


        return SubScriptionsDetails;
    }

    public ArrayList<String> NewSubscriptionCorrection(String str)
    {

        ArrayList<Integer> CheckBoxes = new ArrayList<Integer>();
        ArrayList<String> CheckBoxStates = new ArrayList<String>();
        ArrayList<Integer> CheckBoxidPlaces = new ArrayList<Integer>();
        ArrayList<Integer> TempPlaces = new ArrayList<Integer>();
        ArrayList<String> CheckBoxIds = new ArrayList<String>();
        ArrayList<String> Codes = new ArrayList<String>();
        ArrayList<String> Subjects = new ArrayList<String>();
        ArrayList<String> Finals = new ArrayList<String>();

        CheckBoxes.clear();
        CheckBoxStates.clear();
        CheckBoxIds.clear();
        CheckBoxidPlaces.clear();
        Codes.clear();
        Subjects.clear();
        TempPlaces.clear();

        int i = 0;


        //CheckBoxStates

        while((i=(str.indexOf("checkbox",i)+1))>0)CheckBoxes.add(i);


        for (int temp : CheckBoxes) {


            Log.d("detState", str.substring(temp + 9, temp + 17));
            if (str.substring(temp + 9,temp +17).contains("disabled"))
            {
                CheckBoxStates.add("0"); //Disabled
                CheckBoxIds.add(str.substring(temp + 27, temp + 42));
             }
            else CheckBoxStates.add("1"); //Enabled

        }


        //CheckBoxIds

        i = 0;
        while((i=(str.indexOf("countCourses()\" name=", i)+1))>0)CheckBoxidPlaces.add(i);

        for (int k=0; k< CheckBoxidPlaces.size(); k++) {


        Log.d("detID", str.substring(CheckBoxidPlaces.get(k) + 21, CheckBoxidPlaces.get(k) + 36));
        if (str.substring(CheckBoxidPlaces.get(k) + 21,CheckBoxidPlaces.get(k) +36).contains("check")) {
            //if (CheckBoxStates.get(k).equals("1"))
                CheckBoxIds.add(str.substring(CheckBoxidPlaces.get(k) + 21, CheckBoxidPlaces.get(k) + 36) + "#0"); //Enabled
            //else
                //CheckBoxIds.add(str.substring(CheckBoxidPlaces.get(k) + 21, CheckBoxidPlaces.get(k) + 36)); //Disabled

        }

       }


        //Subject - Codes  in case of DISABLED

        i = 0;

        while((i=(str.indexOf("<td class=\"bottomBorderLight\">", i)+1))>0)TempPlaces.add(i);

        //Subject Names

       // for (m=0; m< TempPlaces.size(); m+=5) {
       // Log.d("detTempBorderLight",String.valueOf(TempPlaces.size()));

            int m = 0;
            while (m< TempPlaces.size() && TempPlaces.size()!=0){



            int j = 0;
            int r = 0;
            j = str.substring(TempPlaces.get(m) + 29).indexOf("<");
            r = str.substring(TempPlaces.get(m+1) + 29).indexOf("<");

            Codes.add(str.substring(TempPlaces.get(m) + 29, TempPlaces.get(m) + 29 + j).trim());
            Subjects.add(str.substring(TempPlaces.get(m+1) + 29, TempPlaces.get(m+1) + 29 + r).trim());

                m+=5;
        }



        //Subjects - Codes in case of ENABLED

        i = 0;
        TempPlaces.clear();
        while((i=(str.indexOf("_self')\">", i)+1))>0)TempPlaces.add(i);


        //Subject Names

        //for ( m=0; m< TempPlaces.size(); m++) {
        m=0;
       while (m< TempPlaces.size() && TempPlaces.size()!=0){



            int j = 0;
            j = str.substring(TempPlaces.get(m) + 8).indexOf("<");

            Subjects.add(str.substring(TempPlaces.get(m) + 8, TempPlaces.get(m) + 8 + j).trim());

        m++;
        }


        i = 0;
        TempPlaces.clear();
        while((i=(str.indexOf("\"top\"></td><td>", i)+1))>0)TempPlaces.add(i);


        //Subject Codes

        //for ( m=0; m< TempPlaces.size(); m++) {
        m = 0;
            while (m< TempPlaces.size() && TempPlaces.size()!=0){



            int j = 0;
            j = str.substring(TempPlaces.get(m) + 14).indexOf("<");

            Codes.add(str.substring(TempPlaces.get(m) + 14, TempPlaces.get(m) + 14 + j).trim());
            m++;
        }


        for (String u : CheckBoxStates) {

        Log.d("detailsCheckBoxState", String.valueOf( u));
         }
        for (String u : CheckBoxIds) {

            Log.d("detailsids", u);
        }

        for (String u : Codes) {

            Log.d("detCodes", u);
        }
        for (String u : Subjects) {

            Log.d("detSubjectTitle", u);
        }


        Finals.clear();
        Finals.addAll(CheckBoxStates);
        Finals.addAll(CheckBoxIds);
        Finals.addAll(Codes);
        Finals.addAll(Subjects);

        return Finals;
    }

    public ArrayList<String> ApplicationsCorrection(String str)
    {


        ArrayList<String> ApplicationDetails = new ArrayList<String>();
        ArrayList<Integer> HrefPlaces = new ArrayList<Integer>();
        ArrayList<String> MiddleStrings = new ArrayList<String>();


        ApplicationDetails.clear();
        HrefPlaces.clear();
        MiddleStrings.clear();


        int i = 0;
        int j = 0;
        int g =0;
        String test = null;

        //CODES
        while((i=(str.indexOf("><a class=\"underline\" target=\"req\" href=\"csunifiles/reqreport.axd?sessionID=",i)+1))>0)HrefPlaces.add(i);


        for (int temp : HrefPlaces) {


            j = 0;
            j = str.substring(temp).indexOf(">");
            MiddleStrings.add(str.substring(temp, temp + j+1));

        }

        for (String repStr : MiddleStrings) {

            str = str.replace(repStr, "");

        }


        int ekremotita = str.indexOf("εκκρεμότητα");
        int olokliromenes = str.indexOf("Ολοκληρωμένες");

        ArrayList<Integer> EkkremeisDateTimePlaces = new ArrayList<Integer>();
        ArrayList<Integer> EkkremeisTitlePlaces = new ArrayList<Integer>();
        ArrayList<Integer> OlokliromenesDateTimePlaces = new ArrayList<Integer>();
        ArrayList<Integer> OlokliromenesTitlePlaces = new ArrayList<Integer>();

        EkkremeisDateTimePlaces.clear();
        EkkremeisTitlePlaces.clear();
        OlokliromenesDateTimePlaces.clear();
        OlokliromenesTitlePlaces.clear();




        if (ekremotita!=-1) { //only if it has pending
            String Holding = str.substring(ekremotita, olokliromenes);

            //PENDING
            i = 0;
            while ((i = (Holding.indexOf("\"true\">", i) + 1)) > 0) EkkremeisDateTimePlaces.add(i);
            i = 0;
            while ((i = (Holding.indexOf("width=\"85%\">", i) + 1)) > 0)
                EkkremeisTitlePlaces.add(i);


            ApplicationDetails.add("Εκκρεμείς");
            for (int temp : EkkremeisDateTimePlaces) {


                j = 0;
                j = Holding.substring(temp + 6).indexOf("<");
                ApplicationDetails.add(Holding.substring(temp + 6, temp + 6 + j).trim());

            }

            for (int temp : EkkremeisTitlePlaces) {
                j = 0;
                j = Holding.substring(temp + 11).indexOf("<");
                ApplicationDetails.add(Holding.substring(temp + 11, temp + 11 + j).trim());
            }

        }// Only if there are pending

        //Fullfilled
        if (olokliromenes!=-1) { //Only for fullfilled
            String Finished = str.substring(olokliromenes, str.length() - 1);
            i = 0;
            while ((i = (Finished.indexOf("\"true\">", i) + 1)) > 0)
                OlokliromenesDateTimePlaces.add(i);
            i = 0;
            while ((i = (Finished.indexOf("width=\"85%\">", i) + 1)) > 0)
                OlokliromenesTitlePlaces.add(i);


            ApplicationDetails.add("Ολοκληρωμένες");
            for (int temp : OlokliromenesDateTimePlaces) {


                j = 0;
                j = Finished.substring(temp + 6).indexOf("<");
                ApplicationDetails.add(Finished.substring(temp + 6, temp + 6 + j).trim());

            }

            for (int temp : OlokliromenesTitlePlaces) {
                j = 0;
                j = Finished.substring(temp + 11).indexOf("<");
                ApplicationDetails.add(Finished.substring(temp + 11, temp + 11 + j).trim());

            }
        }

        if (ekremotita==-1 && olokliromenes==-1) ApplicationDetails.add("Δεν υπάρχουν αιτήσεις προς την γραμματεία");


        return ApplicationDetails;
    }



    public ArrayList<String> AnnouncementsCorrection(String str)
    {

        ArrayList<String> AnnouncementDetails = new ArrayList<String>();
        ArrayList<String> hrefs = new ArrayList<String>();
        ArrayList<Integer> AnnouncementPlaces = new ArrayList<Integer>();

        AnnouncementDetails.clear();
        hrefs.clear();
        AnnouncementPlaces.clear();


        int i = 0;
        int j = 0;
        while((i=(str.indexOf("<h2 class=\"contentheading\">",i)+1))>0)AnnouncementPlaces.add(i);

       /* i = 0;
        while((i=(str.indexOf("<a href=\"",i)+1))>0)hrefPlaces.add(i);

        Places.addAll(SemesterPlaces);
        Places.addAll(SubjectPlaces);
        Collections.sort(Places);
        */

        for (int temp : AnnouncementPlaces) {

                j = 0;
                int k = 0;

                j = str.substring(temp + 35).indexOf("\">"); //hrefs
                k = str.substring(temp + 35 + j).indexOf("</a>");      //announcement

                AnnouncementDetails.add("www.di.ionio.gr"+str.substring(temp + 35, temp + 35 + j).trim()); //href
                AnnouncementDetails.add(str.substring(temp + 37 + j, temp + 37 + j + k -2 ).trim());           //announcement

        }


        return AnnouncementDetails;
    }



    //To replace the extra spaces in student page details
    public String elliminateStartSpace(String str)
    {
        if(str.contains(": ")) {   return str.replace(": ",":");   }
        else {     return str;   }

    }

    @JavascriptInterface
    public void checkForError(String html) { //To check if the loaded page has error


        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");


        if (html.indexOf("σφάλμα")>-1) errorOnPage = true;
        else errorOnPage = false;

    }

    //To check for wrong credentials

    @JavascriptInterface
    public void getHTMLUserDetails(String html) { //gets student's personal details



        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");

        String clearedDetails = Html.fromHtml(html).toString();

        setFinalUserDetails(personalDetailsCorrection(clearedDetails));


    }//getHTMLUserDetails

    @JavascriptInterface
    public void getHTMLUserGrades(String html) { //Get Student's Grades

        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");
        html = html.replaceAll("<td valign=\"top\"> <img align=\"absbottom\" src=\"images/course1.gif\" width=\"16\"></td>"," ");
        html = html.replaceAll("<span class=\"redfonts\">&nbsp;"," ");
        html = html.replaceAll("<span class=\"redFonts\">"," ");
        html = html.replaceAll("<span class=\"tablecell\"><i>"," ");
        html = html.replaceAll("<i>"," ");
        html = html.replaceAll("&nbsp; "," "); //Me keno
        html = html.replaceAll("&nbsp;"," ");//Xoris keno
        html = html.replaceAll("  ","");
        html = html.replaceAll("&amp;","&");

        setFinalUserGrades(GradesCorrection(html));


    }//getHTMLUserGrades


    @JavascriptInterface
    public void getHTMLUserSubscriptions(String html) { //Get student's Subscriptions.

        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");
        html = html.replaceAll("src=\"images/course1.gif\" align=\"absmiddle\">&nbsp;"," ");
        html = html.replaceAll("<i>"," ");
        html = html.replaceAll("&nbsp;"," ");//no spaces
        html = html.replaceAll("  ","");
        html = html.replaceAll("&amp;","&");


      setFinalUserSubscriptions(SubscriptionsCorrection(html));


    }//getHTMLUserSubscriptions


    @JavascriptInterface
    public void getIfSubscriptionsTime(String html) { //To check if it's subscriptions period



        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");

        String clearedDetails = Html.fromHtml(html).toString();

        if(clearedDetails.contains("περίοδος δηλώσεων"))
            setSubscriptionsTime(true);


    }//getIfSubscriptionsTime


    @JavascriptInterface
    public void getHTMLNewSubscriptionSubjects(String html) { //Gets The new subscription subject titles

        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");
        html = html.replaceAll("src=\"images/course1.gif\" align=\"absmiddle\">&nbsp;"," ");
        html = html.replaceAll("<i>"," ");
        html = html.replaceAll("&nbsp;"," ");//no spaces
        html = html.replaceAll("  ","");
        html = html.replaceAll("&amp;","&");

        setFinalNewSubscriptionSubjects(NewSubscriptionCorrection(html));


    }//getHTMLUserSubscriptions



    @JavascriptInterface
    public void getHTMLUserApplications(String html) { //Gets student's applications

        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");
        html = html.replaceAll("&nbsp;"," ");//no spaces
        html = html.replaceAll("&amp;","&");

        setFinalUserApplications(ApplicationsCorrection(html));


    }//getHTMLUserGrades


    @JavascriptInterface
    public void getAnnouncements(String html) { //Get student's announcements

        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");
        html = html.replaceAll("&nbsp;", " ");//No spaces
        html = html.replaceAll("&amp;","&");

        setFinalAnnouncements(AnnouncementsCorrection(html));


    }//getHTMLUserGrades

    @JavascriptInterface
    public void showHtml(String html) { //It prints a message to the screen

        html = html.replaceAll("\\n","");
        html = html.replaceAll("\\t","");
        html = html.replaceAll("  ","");
        html = html.replaceAll("&nbsp;"," ");//No space
        html = html.replaceAll("&amp;","&");


 new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();

    }//showHTML

}//My javasciptParseFunctions Interface
