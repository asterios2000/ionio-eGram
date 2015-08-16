package asterios.eGram.app.Student;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

import asterios.eGram.app.Announcements.AnnouncementWebview;
import asterios.eGram.app.Applications.UserApplications;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Grades.UserGrades;
import asterios.eGram.app.Parser.JavaScriptParseFunctions;
import asterios.eGram.app.Parser.Parser;
import asterios.eGram.app.Preferences;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.Subscriptions.UserSubscriptions;
import asterios.eGram.app.eGramFunctions;



public class RefreshAllFragment extends Fragment {
    public userDataDB upDB;
    public UserProfile up;
    public UserGrades ug;
    public Parser prs = new Parser();
    public int ProfileID;



    public RefreshAllFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        upDB = new userDataDB(getActivity());
        up = new UserProfile();
        ug = new UserGrades();



        if (getArguments()!=null) {
            ProfileID = getArguments().getInt("ProfileID");
        }
        else {

            ProfileID = upDB.getDefaultProfilesID();
        }

        // Get The profile from the DB
        up = upDB.getProfileByID(ProfileID);


        final View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        final WebView webview = (WebView) rootView.findViewById(R.id.webView);

        WebSettings webSettings = webview.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);

        if (Build.VERSION.SDK_INT <19) //API 19
            webSettings.setSavePassword(false);


        final String usrname, password;
        usrname = up.getUserName();
        password = up.getCode();



        final JavaScriptParseFunctions userDetails = new JavaScriptParseFunctions(getActivity());

        webview.addJavascriptInterface(userDetails, "HtmlViewer");

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(getString(R.string.loadingMessage));
        pd.setCancelable(false);


        webview.setWebViewClient(new WebViewClient() {
            boolean ServerError = false;
            boolean wrongCredentials = false;

            //Ionian Server Check

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("error")) {
                    ServerError = true;
                    Log.i("det", "Server Down, Stop");
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning,
                            getString(R.string.serverDownMsg), getString(R.string.serverDownTitle));

                    if (pd.isShowing()) pd.dismiss();//L O A D I N G
                }
                else{

                    ServerError = false;
                    Log.i("det", "Server ok, Continue");



             //Check for USERNAME & PASSWORD

                    webview.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);

                            if (url.contains("login.asp")) {
                                wrongCredentials = true;
                                if (pd.isShowing()) pd.dismiss();//L O A D I N G
                                Log.i("det", "Wrong Credentials");

                                upDB.DeleteProfile(ProfileID);

                                //Set FIRSTTIME condition only if there aren't any profiles left
                                if (upDB.getAllProfiles().size()==0) Preferences.setPreferences(0, true, getActivity());


                                getFragmentManager().popBackStack();
                                eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning,
                                        getString(R.string.wrongCredentialsTitle), getString(R.string.wrongCredentialsMsg));


                            } else {
                                wrongCredentials = false;
                                Log.i("det", "Correct Credentials");


                                final JavaScriptParseFunctions userDetails = new JavaScriptParseFunctions(getActivity());

                                webview.addJavascriptInterface(userDetails, "HtmlViewer");

                                webview.loadUrl("javascript:HtmlViewer.getHTMLUserDetails" +
                                        "(document.getElementsByTagName('table')[14].innerHTML);");


                //   P R O F I L E

                                webview.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        super.onPageFinished(view, url);




                                        ArrayList<String> details = new ArrayList<String>();
                                        details.clear();
                                        details = userDetails.getFinalUserDetails();

                                        up.UpdateStudentDetails(details);
                                        upDB.updateProfileToDB(up);
                                        Log.d("detUpdated", "Profile Updated");





                          //   G R A D E S


                                        final JavaScriptParseFunctions userGrades = new JavaScriptParseFunctions(getActivity());

                                        webview.addJavascriptInterface(userGrades, "GradesViewer");

                                        webview.setWebViewClient(new WebViewClient() {

                                            @Override
                                            public void onPageFinished(WebView view, String url) {
                                                super.onPageFinished(view, url);
                                                webview.loadUrl("javascript:GradesViewer.getHTMLUserGrades" +
                                                        "(document.getElementsByTagName('table')[14].innerHTML);");


                                                webview.setWebViewClient(new WebViewClient() {   //AUTH TH TELEUTAIA THN THELEI GIA NA PROLABEI
                                                    @Override
                                                    //NA FORTOSEI KAI NA KANEI PARSE TA STOIXEIΑ APO THN ISTOSELIDA BATHMOUS
                                                    public void onPageFinished(WebView view, String url) {
                                                        super.onPageFinished(view, url);



                                                        ArrayList<String> details = new ArrayList<String>();
                                                        details.clear();
                                                        details = userGrades.getFinalUserGrades();

                                                        //Updates DB with parsed grades
                                                        ug.UpdateStudentGrades(details, ProfileID, upDB);
                                                        Log.d("detUpdated", "Grades Updated");

                                                        //Updates DB with totals : Subjects Passed, ECTS and AVG
                                                        upDB.updateProfileTotalsToDB(ProfileID, upDB.TotalSubjectsPassed(ProfileID), upDB.TotalECTS(ProfileID), upDB.MO(ProfileID));
                                                        Log.d("detUpdated", "Totals Updated");





                                //   S U B S C R I P T I O N S

                                                        final JavaScriptParseFunctions userSubscriptions = new JavaScriptParseFunctions(getActivity());

                                                        webview.addJavascriptInterface(userSubscriptions, "SubscriptionsViewer");

                                                        webview.setWebViewClient(new WebViewClient() {
                                                            @Override
                                                            public void onPageFinished(WebView view, String url) {


                                                                webview.loadUrl("javascript:SubscriptionsViewer.getHTMLUserSubscriptions" +
                                                                        "(document.getElementsByTagName('table')[14].innerHTML);");


                                                                webview.setWebViewClient(new WebViewClient() {
                                                                                             @Override
                                                                                             public void onPageFinished(WebView view, String url) {
                                                                                                 super.onPageFinished(view, url);


                                                                                                 ArrayList<String> details = new ArrayList<String>();
                                                                                                 details.clear();
                                                                                                 details = userSubscriptions.getFinalUserSubscriptions();
                                                                                                 UserSubscriptions ug = new UserSubscriptions();


                                                                                                 //UPDATES the DB with parsed Subscriptions
                                                                                                 ug.UpdateStudentSubscriptions(details, ProfileID, upDB);
                                                                                                 Log.d("detUpdated", "Subscriptions Updated");


                                          //   A P P L I C A T I O N S


                                                                                                 final JavaScriptParseFunctions userApplications = new JavaScriptParseFunctions(getActivity());
                                                                                                 webview.addJavascriptInterface(userApplications, "ApplicationsViewer");


                                                                                                 webview.setWebViewClient(new WebViewClient() {

                                                                                                     @Override
                                                                                                     public void onPageFinished(WebView view, String url) {
                                                                                                         super.onPageFinished(view, url);
                                                                                                         webview.loadUrl("javascript:ApplicationsViewer.getHTMLUserApplications" +
                                                                                                                 "(document.getElementsByTagName('table')[14].innerHTML);");


                                                                                                         webview.setWebViewClient(new WebViewClient() {
                                                                                                             @Override

                                                                                                             public void onPageFinished(WebView view, String url) {
                                                                                                                 super.onPageFinished(view, url);
                                                                                                                 ArrayList<String> details = new ArrayList<String>();
                                                                                                                 details.clear();
                                                                                                                 details = userApplications.getFinalUserApplications();

                                                                                                                 UserApplications ua = new UserApplications();


                                                                                                                 //UPDATES the DB with parsed Applications
                                                                                                                 ua.UpdateStudentApplications(details, ProfileID, upDB);
                                                                                                                 Log.d("detUpdated", "Applications Updated");

                                                                                                                 if (pd.isShowing()) pd.dismiss();//L O A D I N G
                                                                                                                 getFragmentManager().popBackStack();
                                                                                                                 eGramFunctions.displayView(1, getActivity());
                                                                                                                 eGramFunctions.ShowToast(getActivity().getLayoutInflater(),getActivity(),R.drawable.ic_checked,up.getScreenName()+":"+getString(R.string.profileCreated),"Short");


                                                                                                             }
                                                                                                         });

                                                                                                         //Loads another page to parse the previous one
                                                                                                         webview.loadUrl("about:blank");


                                                                                                     }
                                                                                                 });

                                                                                                 webview.loadUrl("http://gram-web.ionio.gr/unistudent/stud_reqstatus.asp?studPg=1&mnuid=forms;sForm&");//   A P P L I C A T I O N S





                                                                                             }
                                                                                         }

                                                                );

                                                                //Loads another page to parse the previous one
                                                                webview.loadUrl("about:blank");




                                                            }
                                                        });
                                                        webview.loadUrl("http://gram-web.ionio.gr/unistudent/stud_NewClass.asp?studPg=1&mnuid=diloseis;newDil&"); //   S U B S C R I P T I O N S




                                                    }
                                                });

                                                //Loads another page to parse the previous one
                                                webview.loadUrl("about:blank");




                                            }
                                        });
                                        webview.loadUrl("http://gram-web.ionio.gr/unistudent/stud_CResults.asp?studPg=1&mnuid=mnu3&"); //   G R A D E S




                                    }
                                });
                                webview.loadUrl("about:blank"); //   P R O F I L E



                            }// end of wrong credentials

                        }
                    });
                    webview.loadUrl("javascript:(function(){document.getElementById('userName').value='" + usrname + "';})();" +
                            "(function(){document.getElementById('pwd').value='" + password + "';})();" +
                            "(function(){document.getElementById('submit1').click();})();");

                }// end of  error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if (url.contains("error")) {
                    ServerError = true;
                    if (pd.isShowing()) pd.dismiss();//L O A D I N G
                } else ServerError = false;
                return false;

            }

        });
        webview.loadUrl("http://gram-web.ionio.gr/unistudent/");


        pd.show();

        return rootView;
    }
}