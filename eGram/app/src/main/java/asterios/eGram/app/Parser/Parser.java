package asterios.eGram.app.Parser;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import asterios.eGram.app.Preferences;
import asterios.eGram.app.Subscriptions.GetNewSubscriptionSubjectsFragment;
import asterios.eGram.app.Subscriptions.NewSubscriptionFragment;
import asterios.eGram.app.eGramFunctions;
import asterios.eGram.app.Announcements.Announcement;
import asterios.eGram.app.Applications.UserApplications;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Grades.UserGrades;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.Subscriptions.UserSubscriptions;


/**
 * Created by asterios on 23/5/2015.
 */
public class Parser {

    //Constructor
    public Parser(){}
    private Handler mHandler = new Handler();
    private boolean finished  = false;

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private String title = null;


// -------------------------------------------------------- P R O F I L E ------------------------------------------------------------


    public void ParseProfile(WebView wb, final Activity curActivity, final String usrname, final String password, final UserProfile up, final userDataDB upDB) {

        final WebView web = wb;
        final JavaScriptParseFunctions userDetails = new JavaScriptParseFunctions(curActivity);

        web.addJavascriptInterface(userDetails, "HtmlViewer");


        web.loadUrl("javascript:HtmlViewer.getHTMLUserDetails" +
                "(document.getElementsByTagName('table')[14].innerHTML);");


        web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
            @Override
            //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


                boolean Refresh = false;
                //if (up.getDefaultProfile() == upDB.getDefaultProfilesID()) Refresh = true;
                if (upDB.getAllProfiles().size()>0) Refresh = true;

                ArrayList<String> details = new ArrayList<String>();
                details.clear();
                details = userDetails.getFinalUserDetails();

                up.UpdateStudentDetails(details);
                upDB.updateProfileToDB(up);
                Log.d("detUpdated", "Profile Updated");



                if (Refresh) {
                    curActivity.getFragmentManager().popBackStack();
                    eGramFunctions.displayView(1, curActivity);
                    if (eGramFunctions.isLangGreek())

                    {
                        eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                            curActivity.getString(R.string.informationTitle),
                            curActivity.getString(R.string.to) +" " +
                            curActivity.getString(R.string.DefaultProfile) + " " + curActivity.getString(R.string.refreshedSuccessfully2));
                    }
                    else {
                        eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                curActivity.getString(R.string.informationTitle),
                                curActivity.getString(R.string.DefaultProfile) + " " + curActivity.getString(R.string.refreshedSuccessfully));
                    }
                }

            }
        });

        //Loads another page in order to parse the previous one
        web.loadUrl("about:blank");
    }






    // -------------------------------------------------------- G R A D E S ------------------------------------------------------------

public void ParseGrades (WebView wb, final Activity curActivity,final int profileID, final UserGrades ug, final userDataDB upDB) {

    final WebView web = wb;


    final JavaScriptParseFunctions userGrades = new JavaScriptParseFunctions(curActivity);

    web.addJavascriptInterface(userGrades, "GradesViewer");

    web.setWebViewClient(new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            web.loadUrl("javascript:GradesViewer.getHTMLUserGrades" +
                    "(document.getElementsByTagName('table')[14].innerHTML);");


            web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                @Override
                //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);


                    boolean Refresh = false;
                    if (upDB.getAllGrades(profileID).size() != 0) Refresh = true;


                    ArrayList<String> details = new ArrayList<String>();
                    details.clear();
                    details = userGrades.getFinalUserGrades();

                    //Updates the database with the parsed grades
                    ug.UpdateStudentGrades(details, profileID, upDB);
                    Log.d("detUpdated", "Grades Updated");

                    //updates the corresponding profile with the sums:Passed Subjects , ECTS and AVG
                    upDB.updateProfileTotalsToDB(profileID, upDB.TotalSubjectsPassed(profileID), upDB.TotalECTS(profileID), upDB.MO(profileID));
                    Log.d("detUpdated", "Totals Updated");

                    if (Refresh) {
                        curActivity.getFragmentManager().popBackStack();
                        eGramFunctions.displayView(1, curActivity);

                        if (eGramFunctions.isLangGreek()) {
                            eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                    curActivity.getString(R.string.informationTitle),
                                    curActivity.getString(R.string.oi) +" " +
                                            curActivity.getString(R.string.Grades) + " " + curActivity.getString(R.string.refreshedSuccessfully));}


                        } else {

                            eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                    curActivity.getString(R.string.informationTitle),
                                    curActivity.getString(R.string.Grades) + " " + curActivity.getString(R.string.refreshedSuccessfully));

                    }

                }
            });

            //Loads another page in order to parse the previous one
            web.loadUrl("about:blank");


        }
    });

    web.loadUrl("http://gram-web.ionio.gr/unistudent/stud_CResults.asp?studPg=1&mnuid=mnu3&");
}//ParseGrades



    // -------------------------------------------------------- S U B S C R I P T I O N S ------------------------------------------------------------



    public void ParseSubscriptions (WebView wb, final Activity curActivity,final int profileID, final userDataDB upDB) {

        final WebView web = wb;


        final JavaScriptParseFunctions userSubscriptions = new JavaScriptParseFunctions(curActivity);

        web.addJavascriptInterface(userSubscriptions, "SubscriptionsViewer");

        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web.loadUrl("javascript:SubscriptionsViewer.getHTMLUserSubscriptions" +
                        "(document.getElementsByTagName('table')[14].innerHTML);");


                web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                                         @Override
                                         //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                                         public void onPageFinished(WebView view, String url) {
                                             super.onPageFinished(view, url);


                                             ArrayList<String> details = new ArrayList<String>();
                                             details.clear();
                                             details = userSubscriptions.getFinalUserSubscriptions();
                                             UserSubscriptions ug = new UserSubscriptions();


                                             //updates the DB with the parsed subscritpions
                                             ug.UpdateStudentSubscriptions(details, profileID, upDB);
                                             Log.d("detUpdated", "Subscriptions Updated");

/*
                                             if (Refresh) {
                                                 curActivity.getFragmentManager().popBackStack();
                                                 eGramFunctions.displayView(1, curActivity);
                                                 eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                                         curActivity.getString(R.string.informationTitle),
                                                         curActivity.getString(R.string.SemesterSubscriptions) + " " + curActivity.getString(R.string.refreshedSuccessfully));
                                             }*/

                                         }
                                     }

                );

                //Loads another page in order to parse the previous one


                web.loadUrl("http://gram-web.ionio.gr/unistudent/studentMain.asp?mnuID=student");


                }
            });

        web.loadUrl("http://gram-web.ionio.gr/unistudent/stud_NewClass.asp?studPg=1&mnuid=diloseis;newDil&");
    }

    public void CheckForSubscriptions (WebView wb, final Activity curActivity) {

        final WebView web = wb;
        final JavaScriptParseFunctions checkForSubscriptions = new JavaScriptParseFunctions(curActivity);
        Log.d("detURL", web.getUrl().toString());

        web.addJavascriptInterface(checkForSubscriptions, "HtmlViewer");

        web.loadUrl("javascript:HtmlViewer.getIfSubscriptionsTime" +
                "(document.getElementsByTagName('table')[14].innerHTML);");


        web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
            @Override
            //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.d("detUpdated", "Subscriptions Period Checked");
                //to epomeno log.d na MHN SBHSTEI xreiazetai giati allios den paizei!?!?!?!

                Log.d("detSubs", String.valueOf(checkForSubscriptions.getSubscriptionsTime()));
                userDataDB upDB = new userDataDB(curActivity);

                String whichFragment = curActivity.getFragmentManager().findFragmentByTag("Subscriptions").toString();
                boolean isItSubscriptionTime = checkForSubscriptions.getSubscriptionsTime();

                /*if (whichFragment.contains("Refresh"))  Log.d("detFrgName", "Comes from refresh Subscritpions");
                if (whichFragment.contains("Check"))    Log.d("detFrgName", "Comes from refresh Announcements");*/
                Log.d("detWhichFragment", whichFragment);


                //Comes from Announcements

                if (whichFragment.contains("Check")) {

                    boolean Refresh = false;
                    if (upDB.getAllAnnouncements().size() != 0) Refresh = true;


                    if (Refresh && !isItSubscriptionTime) {
                        curActivity.getFragmentManager().popBackStack();
                        eGramFunctions.displayView(1, curActivity);


                        if (eGramFunctions.isLangGreek()) {
                            eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                    curActivity.getString(R.string.informationTitle),
                                    curActivity.getString(R.string.oi) + " " +
                                            curActivity.getString(R.string.announcements) + " " + curActivity.getString(R.string.refreshedSuccessfully) + ".\n" +
                                            curActivity.getString(R.string.closedSubscriptionsPeriod));

                        } else {
                            eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                    curActivity.getString(R.string.informationTitle),
                                    curActivity.getString(R.string.announcements) + " " + curActivity.getString(R.string.refreshedSuccessfully) + ".\n" +
                                            curActivity.getString(R.string.closedSubscriptionsPeriod));
                        }
                    }

                    if (Refresh && isItSubscriptionTime) {


                        AlertDialog.Builder alert = new AlertDialog.Builder(curActivity);
                        alert.setTitle(R.string.informationTitle);

                        if (eGramFunctions.isLangGreek()) {
                            alert.setMessage(curActivity.getString(R.string.oi) + " " + curActivity.getString(R.string.announcements) + " " + curActivity.getString(R.string.refreshedSuccessfully) + "\n" +
                                    curActivity.getString(R.string.openSubscriptionsPeriod));

                        } else {
                            alert.setMessage(curActivity.getString(R.string.announcements) + " " + curActivity.getString(R.string.refreshedSuccessfully) + "\n" +
                                    curActivity.getString(R.string.openSubscriptionsPeriod));
                        }
                        alert.setIcon(R.drawable.ic_checked);
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                //GO TO SUBSCRIPTIONS
                                curActivity.getFragmentManager().popBackStack();
                                eGramFunctions.displayView(3, curActivity);


                            }
                        });

                        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d("det", "Cancel selected");
                                //CANCEL BUTTON AFTER NO NETWORK
                                curActivity.getFragmentManager().popBackStack();
                                eGramFunctions.displayView(1, curActivity);

                            }
                        });
                        alert.show();
                    }

                    if (!Refresh && !isItSubscriptionTime) {

                        curActivity.getFragmentManager().popBackStack();
                        eGramFunctions.ShowOKDialog(curActivity,
                                R.drawable.ic_checked,
                                curActivity.getString(R.string.informationTitle),
                                curActivity.getString(R.string.closedSubscriptionsPeriod));

                    }


                    if (!Refresh && isItSubscriptionTime) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(curActivity);
                        alert.setTitle(R.string.informationTitle);
                        alert.setMessage(curActivity.getString(R.string.openSubscriptionsPeriod));
                        alert.setIcon(R.drawable.ic_checked);
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                //GO TO SUBSCRIPTIONS

                                curActivity.getFragmentManager().popBackStack();
                                GetNewSubscriptionSubjectsFragment NewSub = new GetNewSubscriptionSubjectsFragment();
                                eGramFunctions.AddFragment(NewSub, curActivity);


                            }
                        });

                        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d("det", "Cancel selected");

                                curActivity.getFragmentManager().popBackStack();
                                eGramFunctions.displayView(1, curActivity);

                            }
                        });
                        alert.show();
                    }

                }

                if (whichFragment.contains("Refresh")) {

                    Log.d("detFrgName", "Comes from Subscriptions");

                    boolean RefreshS = false;
                    if (upDB.getAllSubscriptions(upDB.getDefaultProfilesID()).size() != 0)
                        RefreshS = true;


                    if (RefreshS && !isItSubscriptionTime) {
                        curActivity.getFragmentManager().popBackStack();
                        eGramFunctions.displayView(1, curActivity);

                        if (eGramFunctions.isLangGreek()) {
                            eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                    curActivity.getString(R.string.informationTitle),
                                    curActivity.getString(R.string.oi) + " " +
                                            curActivity.getString(R.string.SemesterSubscriptions) + " " + curActivity.getString(R.string.refreshedSuccessfully));

                        } else {
                            eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                    curActivity.getString(R.string.informationTitle),
                                    curActivity.getString(R.string.SemesterSubscriptions) + " " + curActivity.getString(R.string.refreshedSuccessfully));
                        }
                    }

                    if (RefreshS && isItSubscriptionTime) {


                        AlertDialog.Builder alertS = new AlertDialog.Builder(curActivity);
                        alertS.setTitle(R.string.informationTitle);


                        if (eGramFunctions.isLangGreek()) {
                            alertS.setMessage(curActivity.getString(R.string.oi) + " " +
                                    curActivity.getString(R.string.SemesterSubscriptions) + " " + curActivity.getString(R.string.refreshedSuccessfully) + "\n" +
                                    curActivity.getString(R.string.subscriptionChoices));
                        } else {
                            alertS.setMessage(curActivity.getString(R.string.SemesterSubscriptions) + " " + curActivity.getString(R.string.refreshedSuccessfully) + "\n" +
                                    curActivity.getString(R.string.subscriptionChoices));
                        }
                        alertS.setIcon(R.drawable.ic_checked);
                        alertS.setPositiveButton(R.string.newSubscriptionList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                                //GO TO NEW SUBSCRIPTION


                                curActivity.getFragmentManager().popBackStack();
                                GetNewSubscriptionSubjectsFragment NewSub = new GetNewSubscriptionSubjectsFragment();
                                eGramFunctions.AddFragment(NewSub, curActivity);


                            }
                        });

                        alertS.setNeutralButton(R.string.viewCurrentSubscriptionList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                                //GO TO Subscriptions
                                curActivity.getFragmentManager().popBackStack();
                                eGramFunctions.displayView(3, curActivity);

                                /*
                                curActivity.getFragmentManager().popBackStack();
                                GetNewSubscriptionSubjectsFragment NewSub = new GetNewSubscriptionSubjectsFragment();
                                eGramFunctions.AddFragment(NewSub, curActivity);
                                */


                            }
                        });

                        alertS.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d("det", "Cancel selected");
                                //CANCEL BUTTON AFTER NO
                                curActivity.getFragmentManager().popBackStack();
                                eGramFunctions.displayView(1, curActivity);

                            }
                        });
                        alertS.show();

                    }
                    Log.d("detREFRESH", String.valueOf(RefreshS));
                    Log.d("detSUB", String.valueOf(isItSubscriptionTime));

                    if (!RefreshS && isItSubscriptionTime) {


                        AlertDialog.Builder alertS = new AlertDialog.Builder(curActivity);
                        alertS.setTitle(R.string.informationTitle);
                        alertS.setMessage(curActivity.getString(R.string.openSubscriptionsPeriod));
                        alertS.setIcon(R.drawable.ic_checked);
                        alertS.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                //GO TO SUBSCRIPTIONS
                                //curActivity.getFragmentManager().popBackStack();
                                //  eGramFunctions.displayView(3, curActivity);

                                curActivity.getFragmentManager().popBackStack();
                                GetNewSubscriptionSubjectsFragment NewSub = new GetNewSubscriptionSubjectsFragment();
                                eGramFunctions.AddFragment(NewSub, curActivity);

                            }
                        });

                        alertS.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d("det", "Cancel selected");
                                //CANCEL BUTTON AFTER NO
                                curActivity.getFragmentManager().popBackStack();
                                eGramFunctions.displayView(1, curActivity);

                            }
                        });
                        alertS.show();

                    }
                }
            }
        });

        //Loads another page in order to parse the previous one
        web.loadUrl("about:blank");
    }




    public void ParseNewSubscriptions (WebView wb, final Activity curActivity,final int profileID, final userDataDB upDB) {

        final WebView web = wb;

        final JavaScriptParseFunctions userSubscriptions = new JavaScriptParseFunctions(curActivity);

        web.addJavascriptInterface(userSubscriptions, "SubscriptionsViewer");

        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web.loadUrl("javascript:(function(){document.getElementById('add').click();})();");


                web.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        //  web.loadUrl("javascript:(function(){document.getElementById('add').click();})();");

                        web.loadUrl("javascript:SubscriptionsViewer.getHTMLNewSubscriptionSubjects" +
                                "(document.getElementsByTagName('table')[14].innerHTML);");


                        web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                                                 @Override
                                                 //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                                                 public void onPageFinished(WebView view, String url) {
                                                     super.onPageFinished(view, url);


                                                     ArrayList<String> details = new ArrayList<String>();
                                                     details.clear();
                                                     details = userSubscriptions.getFinalNewSubscriptionSubjects();
                                                     UserSubscriptions ug = new UserSubscriptions();


                                                     //Updates the DB with the NEW parsed subscription
                                                     ug.UpdateNewSubscription(details, profileID, upDB);


                                                     Log.d("detNewSubs", "Got new Subscription subjects");

                                                     curActivity.getFragmentManager().popBackStack();
                                                     NewSubscriptionFragment NewSub = new NewSubscriptionFragment();
                                                     eGramFunctions.AddFragment(NewSub, curActivity);



/*
                                             if (Refresh) {
                                                 curActivity.getFragmentManager().popBackStack();
                                                 eGramFunctions.displayView(1, curActivity);
                                                 eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                                         curActivity.getString(R.string.informationTitle),
                                                         curActivity.getString(R.string.SemesterSubscriptions) + " " + curActivity.getString(R.string.refreshedSuccessfully));
                                             }*/

                                                 }
                                             }

                        );

                        //Loads another page in order to parse the previous one
                        // web.loadUrl("http://gram-web.ionio.gr/unistudent/stud_newclasssel.asp?studpg=1&mnuid=diloseis;newDil&" +
                        //  "(function(){document.getElementById('add').click();})();");
                        web.loadUrl("about:blank");


                    }
                });


            }
        });
       // web.loadUrl("http://gram-web.ionio.gr/unistudent/stud_newclasssel.asp?studpg=1&mnuid=diloseis;newDil&" +
         //               "(function(){document.getElementById('add').click();})();");

       // web.loadUrl("http://gram-web.ionio.gr/unistudent/stud_newclasssel.asp?studpg=1&mnuid=diloseis;newDil&");
        web.loadUrl("http://gram-web.ionio.gr/unistudent/stud_NewClass.asp?studPg=1&mnuid=diloseis;newDil&");
               // "javascript:(function(){document.getElementById('add').click();})();");


    }

    public void SendNewSubscription (WebView wb, final Activity curActivity,final int profileID, final userDataDB upDB, final ArrayList<String> SubscriptionData ) {

        final WebView web = wb;


        final JavaScriptParseFunctions NewSubscription = new JavaScriptParseFunctions(curActivity);

        web.addJavascriptInterface(NewSubscription, "NewSubscriptionViewer");
        final ProgressDialog pd = new ProgressDialog(curActivity);
        pd.setMessage(curActivity.getString(R.string.loading));
        pd.setCancelable(true);
        String url = null;

        Log.d("detAppData0", SubscriptionData.get(0));
        Log.d("detAppData1", SubscriptionData.get(1));
        Log.d("detAppData2", SubscriptionData.get(2));
        if (SubscriptionData.size()>3 )Log.d("detAppData3", SubscriptionData.get(3));


        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                    @Override
                    //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        if (pd.isShowing()) pd.dismiss();//L O A D I N G
/*

                        //web.setWebChromeClient(new WebChromeClient());
                        if (Integer.parseInt(SubscriptionData.get(0))==0){ //pistopoiitiko



                            url = "javascript:(function(){document.getElementById('P2').click();})();"
                                    +"(function(){document.getElementsByName('notes')[0].value='" + SubscriptionData.get(1).toString() + "';})();"
                                    +"(function(){document.getElementById('numcopies').value='"+ SubscriptionData.get(2).toString() +"';})();"
                                    +"(function(){document.getElementById('sbutton').click();})();";

                            title = "Πιστοποιητικό";
                            /* O K */ //url = "javascript:(function(){document.getElementById('sbutton').click();})();";

/*
                        }

                        if (Integer.parseInt(SubscriptionData.get(0))==1){//Pistopoiitiko Analytikis bathmologias


                            url = "javascript:(function(){document.getElementById('P1').click();})();"
                                    +"(function(){document.getElementsByName('notes')[0].value='" + SubscriptionData.get(1).toString() + "';})();"
                                    +"(function(){document.getElementById('numcopies').value='"+ SubscriptionData.get(2).toString() +"';})();"
                                    +"(function(){document.getElementById('sbutton').click();})();";

                            title = "Πιστοποιητικό Αναλυτικής Βαθμολογίας";

                        }
                        if (Integer.parseInt(SubscriptionData.get(0))==2){ //allo

                            url = "javascript:(function(){document.getElementById('other').click();})();"
                                    +"(function(){document.getElementsByName('notes')[0].value='" + SubscriptionData.get(1).toString() + "';})();"  //Simeioseis
                                    +"(function(){document.getElementById('otherForm').value='" + SubscriptionData.get(2).toString() + "';})();"    //Thema
                                    +"(function(){document.getElementById('numcopies').value='"+ SubscriptionData.get(3).toString() +"';})();"      //Antigrafa
                                    +"(function(){document.getElementById('sbutton').click();})();";

                            title = SubscriptionData.get(2).toString();
                        }

                        */

                      //  web.loadUrl(url);


/*
                        //enimerose tin basi apo edo

                        mHandler.postDelayed(new Runnable() {
                            public void run() {

                                UserApplications ua = new UserApplications();
                                ua.setProfileID(profileID);
                                ua.setDateTimeStamp(df.format(cal.getTime()));
                                ua.setState("Εκκρεμής");
                                ua.setTitle(title);
                                upDB.addApplication(ua);
                                curActivity.getFragmentManager().popBackStack();

                            }
                        }, 3000);


*/




                    }
                });

                //it works opossite in comparison to other same interfaces
                web.loadUrl("http://gram-web.ionio.gr/unistudent/sendforms.asp?studPg=1&mnuid=forms;newForm&");


            }
        });
        web.loadUrl("about:blank");
        //web.loadUrl("http://gram-web.ionio.gr/unistudent/sendforms.asp?studPg=1&mnuid=forms;newForm&");
    }


    // -------------------------------------------------------- A P P L I C A T I O N S ------------------------------------------------------


    public void ParseApplications (WebView wb, final Activity curActivity,final int profileID, final userDataDB upDB) {

        final WebView web = wb;


        final JavaScriptParseFunctions userApplications = new JavaScriptParseFunctions(curActivity);

        web.addJavascriptInterface(userApplications, "ApplicationsViewer");


        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web.loadUrl("javascript:ApplicationsViewer.getHTMLUserApplications" +
                        "(document.getElementsByTagName('table')[14].innerHTML);");


                web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                    @Override
                    //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        boolean Refresh = false;
                        if (upDB.getAllApplications(profileID).size() != 0) Refresh = true;

                        ArrayList<String> details = new ArrayList<String>();
                        details.clear();
                        details = userApplications.getFinalUserApplications();

                        UserApplications ua = new UserApplications();


                        //Updates the DB with the parsed applications
                        ua.UpdateStudentApplications(details, profileID, upDB);
                        Log.d("detUpdated", "Applications Updated");


                        curActivity.getFragmentManager().popBackStack();
                        eGramFunctions.displayView(1, curActivity);
                        if (Refresh) {

                            if (eGramFunctions.isLangGreek()) {


                                eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                        curActivity.getString(R.string.informationTitle),
                                        curActivity.getString(R.string.oi) + " " +
                                        curActivity.getString(R.string.Applications) + " " + curActivity.getString(R.string.refreshedSuccessfully));

                            } else {

                                eGramFunctions.ShowOKDialog(curActivity, R.drawable.ic_checked,
                                        curActivity.getString(R.string.informationTitle),
                                        curActivity.getString(R.string.Applications) + " " + curActivity.getString(R.string.refreshedSuccessfully));
                            }
                        }


                    }
                });

                //loads another page to parse the previous one
                web.loadUrl("about:blank");


            }
        });

        web.loadUrl("http://gram-web.ionio.gr/unistudent/stud_reqstatus.asp?studPg=1&mnuid=forms;sForm&");
    }






    public void SendNewApplication (WebView wb, final Activity curActivity,final int profileID, final userDataDB upDB, final ArrayList<String> ApplicationData ) {

        final WebView web = wb;


        final JavaScriptParseFunctions NewApplication = new JavaScriptParseFunctions(curActivity);

        web.addJavascriptInterface(NewApplication, "NewApplicationViewer");
        final ProgressDialog pd = new ProgressDialog(curActivity);
        pd.setMessage(curActivity.getString(R.string.loading));
        pd.setCancelable(true);
        String url = null;

        Log.d("detAppData0", ApplicationData.get(0));
        Log.d("detAppData1", ApplicationData.get(1));
        Log.d("detAppData2", ApplicationData.get(2));
        if (ApplicationData.size()>3 )Log.d("detAppData3", ApplicationData.get(3));


        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                    @Override
                    //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        if (pd.isShowing()) pd.dismiss();//L O A D I N G

                        if (Integer.parseInt(ApplicationData.get(0))==0){ //study certificate



                           url = "javascript:(function(){document.getElementById('P2').click();})();"
                                +"(function(){document.getElementsByName('notes')[0].value='" + ApplicationData.get(1).toString() + "';})();"
                                +"(function(){document.getElementById('numcopies').value='"+ ApplicationData.get(2).toString() +"';})();"
                                +"(function(){document.getElementById('sbutton').click();})();";

                            title = "Πιστοποιητικό";
                            /* O K */ //url = "javascript:(function(){document.getElementById('sbutton').click();})();";


                        }

                        if (Integer.parseInt(ApplicationData.get(0))==1){//Grade Certificate


                            url = "javascript:(function(){document.getElementById('P1').click();})();"
                                    +"(function(){document.getElementsByName('notes')[0].value='" + ApplicationData.get(1).toString() + "';})();"
                                    +"(function(){document.getElementById('numcopies').value='"+ ApplicationData.get(2).toString() +"';})();"
                                    +"(function(){document.getElementById('sbutton').click();})();";

                            title = "Πιστοποιητικό Αναλυτικής Βαθμολογίας";

                        }
                        if (Integer.parseInt(ApplicationData.get(0))==2){ //Other Certificate

                            url = "javascript:(function(){document.getElementById('other').click();})();"
                                    +"(function(){document.getElementsByName('notes')[0].value='" + ApplicationData.get(1).toString() + "';})();"  //Notes
                                    +"(function(){document.getElementById('otherForm').value='" + ApplicationData.get(2).toString() + "';})();"    //Subject
                                    +"(function(){document.getElementById('numcopies').value='"+ ApplicationData.get(3).toString() +"';})();"      //Copies
                                    +"(function(){document.getElementById('sbutton').click();})();";

                            title = ApplicationData.get(2).toString();
                        }

                        web.loadUrl(url);

                        //updates the DB

                        mHandler.postDelayed(new Runnable() {
                            public void run() {

                                UserApplications ua = new UserApplications();
                                ua.setProfileID(profileID);
                                ua.setDateTimeStamp(df.format(cal.getTime()));
                                ua.setState("Εκκρεμής");
                                ua.setTitle(title);
                                upDB.addApplication(ua);


                            }
                        }, 2300);




                    }
                });

                //It works opposite in comparisson to other interfaces
                web.loadUrl("http://gram-web.ionio.gr/unistudent/sendforms.asp?studPg=1&mnuid=forms;newForm&");


            }
        });
        web.loadUrl("about:blank");
        //web.loadUrl("http://gram-web.ionio.gr/unistudent/sendforms.asp?studPg=1&mnuid=forms;newForm&");
    }




    // -------------------------------------------------------- A N N O U N C E M E N T S  ------------------------------------------------------------

    public void ParseAnnouncements (WebView wb, final Activity curActivity, final userDataDB upDB, String url, final String AnType) {

        final WebView web = wb;


        final JavaScriptParseFunctions announcements = new JavaScriptParseFunctions(curActivity);

        web.addJavascriptInterface(announcements, "AnnouncementsViewer");

        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web.loadUrl("javascript:AnnouncementsViewer.getAnnouncements" +
                        "(document.getElementsByClassName('blog clearfix')[0].innerHTML);");


                web.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                    @Override
                    //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        ArrayList<String> details = new ArrayList<String>();
                        details.clear();
                        details = announcements.getFinalAnnouncements();

                        Announcement an = new Announcement();


                        //Updates the DB with the parsed announcements
                        if (AnType.contains("Γραμματείας"))
                            an.UpdateAnnouncements(details, upDB, "Γραμματείας");
                        if (AnType.contains("Γενικές"))
                            an.UpdateAnnouncements(details, upDB, "Γενικές");


                    }
                });

                //Loads another page to parse the previous one
              web.loadUrl("about:blank");



            }
        });

        web.loadUrl(url);
    }

}




