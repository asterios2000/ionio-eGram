package asterios.eGram.app.Grades;

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

import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Parser.JavaScriptParseFunctions;
import asterios.eGram.app.Parser.Parser;
import asterios.eGram.app.Preferences;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class RefreshGradesFragment extends Fragment {
    private Handler mHandler = new Handler();
    public userDataDB upDB;
    public UserProfile up;
    public UserGrades ug;
    public Parser prs = new Parser();

    public RefreshGradesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        upDB = new userDataDB(getActivity());
        up = new UserProfile();
        ug = new UserGrades();



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

        final JavaScriptParseFunctions userDetails = new JavaScriptParseFunctions(getActivity());

        webview.addJavascriptInterface(userDetails, "HtmlViewer");

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(getString(R.string.loadingMessage));
        pd.setCancelable(false);



        webview.setWebViewClient(new WebViewClient() {
            boolean ServerError = false;
            boolean wrongCredentials = false;

            //Check Ionian Server Error

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



                  //Check username & password

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


                                //SERVER OK Parse GRADES


                                prs.ParseGrades(webview, getActivity(), ProfileID, ug, upDB);

                               mHandler.postDelayed(new Runnable() {
                                   public void run() {

                                       if (pd.isShowing()) pd.dismiss();//L O A D I N G
                                   }
                               }, 2500);



                            }// end of wrong credentials

                        }
                    });
                    webview.loadUrl("javascript:(function(){document.getElementById('userName').value='" + usrname + "';})();" +
                            "(function(){document.getElementById('pwd').value='" + password + "';})();" +
                            "(function(){document.getElementById('submit1').click();})();");

                }// else of error
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