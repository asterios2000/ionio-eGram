package asterios.eGram.app.Subscriptions;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Parser.JavaScriptParseFunctions;
import asterios.eGram.app.Parser.Parser;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class SendSubscriptionFragment extends Fragment {
    private Handler mHandler = new Handler();
    public userDataDB upDB;
    public UserProfile up;
    public Parser prs = new Parser();


    public SendSubscriptionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        upDB = new userDataDB(getActivity());
        up = new UserProfile();

        // Get subscription details from the bundle
        final ArrayList<String> SubscriptionData = getArguments().getStringArrayList("SubscriptionData");

        final int ProfileID = upDB.getDefaultProfilesID();

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


        final JavaScriptParseFunctions SubscriptionDetails = new JavaScriptParseFunctions(getActivity());

        webview.addJavascriptInterface(SubscriptionDetails, "HtmlViewer");

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(getString(R.string.sendingSubscription));
        pd.setCancelable(false);

        webview.setWebViewClient(new WebViewClient() {
            boolean ServerError = false;
            boolean wrongCredentials = false;

            //Ionian server Check

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("error")) {
                    ServerError = true;
                    Log.i("det", "Server Down, Stop");
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning,
                            getString(R.string.serverDownMsg), getString(R.string.serverDownTitle));

                    if (pd.isShowing()) pd.dismiss();//L O A D I N G
                } else {

                    ServerError = false;
                    Log.i("det", "Server ok, Continue");


//Check for wrong USERNAME  & PASSWORD

                    webview.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);

                            if (url.contains("login.asp")) {
                                wrongCredentials = true;
                                if (pd.isShowing()) pd.dismiss();//L O A D I N G
                                Log.i("det", "Wrong Credentials");

                                getFragmentManager().popBackStack();
                                eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning,
                                        getString(R.string.wrongCredentialsTitle), getString(R.string.wrongCredentialsMsg));


                            } else {
                                wrongCredentials = false;
                                Log.i("det", "Correct Credentials");


                                //If Server and Credentials are correct then send the subscription

                                webview.setWebViewClient(new WebViewClient() {                    //DHLOSH

                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        super.onPageFinished(view, url);

                                        webview.setWebViewClient(new WebViewClient() {           //PROSTHIKI

                                            @Override
                                            public void onPageFinished(WebView view, String url) {
                                                super.onPageFinished(view, url);


                                                String checkBoxUrl = "javascript:";
                                                for (String sd : SubscriptionData) {
                                                    if (sd.contains("#")) {

                                                        checkBoxUrl = checkBoxUrl.concat("(function(){document.getElementById('" + sd + "').checked=true;})();");


                                                    }
                                                }


                                                checkBoxUrl = checkBoxUrl.concat("(function(){countCourses()})();"
                                                        + "(function(){document.getElementById('confDhl').click();})();");

                                                Log.d("detCheckboxURL", checkBoxUrl);

                                                webview.setWebViewClient(new WebViewClient() {      //EISAGOGI STH DHLOSH

                                                    @Override
                                                    public void onPageFinished(WebView view, String url) {
                                                        super.onPageFinished(view, url);


                                                        webview.setWebViewClient(new WebViewClient() {    //APOSTOLH

                                                            @Override
                                                            public void onPageFinished(WebView view, String url) {
                                                                super.onPageFinished(view, url);


                                                                // Successfull confirmation and refresh subscriptions
                                                                if (pd.isShowing())
                                                                    pd.dismiss();//L O A D I N G

                                                                if (url.contains("stud_confirmnewclass")) {


                                                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                                                    alert.setTitle(R.string.informationTitle);
                                                                    alert.setMessage(R.string.successfullSubmission);
                                                                    alert.setIcon(R.drawable.ic_checked);
                                                                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {

                                                                            //ON OK SUBSCRIPTIONS ARE REFRESHED


                                                                            getActivity().getFragmentManager().popBackStack();
                                                                            RefreshSubscriptionsFragment RefreshSubscriptions = new RefreshSubscriptionsFragment();
                                                                            eGramFunctions.AddFragment(RefreshSubscriptions, getActivity());


                                                                        }
                                                                    });

                                                                    alert.show();

                                                                } else {
                                                                    eGramFunctions.ShowOKDialog(getActivity(),
                                                                            R.drawable.ic_warning,
                                                                            getString(R.string.informationTitle),
                                                                            getString(R.string.unSuccessfullSubmission));

                                                                }


                                                                Log.d("detLastURL", url);
                                                            }
                                                        });
                                                        webview.loadUrl("javascript:(function(){document.getElementById('send').click();})();"); //Subscription Send


                                                        Log.d("detLoaded", url);
                                                    }
                                                });
                                                webview.loadUrl(checkBoxUrl);   //Check user subjects and press of Eisagogi stin Dilosi button


                                            }
                                        });
                                        webview.loadUrl("javascript:(function(){document.getElementById('add').click();})();"); //Add button (Prostiki)


                                    }
                                });

                                webview.loadUrl("http://gram-web.ionio.gr/unistudent/stud_NewClass.asp?studPg=1&mnuid=diloseis;newDil&"); //CURRENT SUBSCRIPTION


                            }// end of wrong credentials

                        }
                    });
                    webview.loadUrl("javascript:(function(){document.getElementById('userName').value='" + usrname + "';})();" +
                            "(function(){document.getElementById('pwd').value='" + password + "';})();" +
                            "(function(){document.getElementById('submit1').click();})();");
                    pd.show();

                }// end of error
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