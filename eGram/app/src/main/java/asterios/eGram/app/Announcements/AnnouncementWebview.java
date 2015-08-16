package asterios.eGram.app.Announcements;

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
import asterios.eGram.app.Grades.UserGrades;
import asterios.eGram.app.Parser.JavaScriptParseFunctions;
import asterios.eGram.app.Parser.Parser;
import asterios.eGram.app.Preferences;
import asterios.eGram.app.Profile.UserProfile;
import asterios.eGram.app.R;
import asterios.eGram.app.Subscriptions.CheckForSubscriptionsPeriodFragment;
import asterios.eGram.app.eGramFunctions;



public class AnnouncementWebview extends Fragment {
    private Handler mHandler = new Handler();
    public userDataDB upDB;

   // public UserSubscriptions us;
    public Parser prs = new Parser();


    //private Handler handler = new Handler();

    public AnnouncementWebview(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        upDB = new userDataDB(getActivity());






        final View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        final WebView webview = (WebView) rootView.findViewById(R.id.webView);

        WebSettings webSettings = webview.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);

        if (Build.VERSION.SDK_INT <19) //API 19
            webSettings.setSavePassword(false);




        final JavaScriptParseFunctions userDetails = new JavaScriptParseFunctions(getActivity());

        webview.addJavascriptInterface(userDetails, "HtmlViewer");

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(getString(R.string.loadingAnnouncements));
        pd.setCancelable(false);



        webview.setWebViewClient(new WebViewClient() {
            boolean ServerError = false;
            boolean wrongCredentials = false;

            //Ionian University server Check

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("error")) {
                    ServerError = true;
                    Log.i("det", "Server Down, Stop");
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning,
                            getString(R.string.serverDownMsg), getString(R.string.serverDownTitle));

                    if (pd.isShowing()) pd.dismiss();//L O A D I N G
                } else// else of error
                {
                    upDB.DeleteAllAnnouncements();

                    // Load SECRETERIAT Announcements first from ionio.gr parse data, update DB
                    prs.ParseAnnouncements(webview, getActivity(), upDB, "http://di.ionio.gr/el/information/secretariat-announcements.html","Γραμματείας");
                    Log.d("detUpdated", "Γραμματείας Announcements Updated");

                    mHandler.postDelayed(new Runnable() {
                        public void run() {

                            //Load GENERAL Announcements first from ionio.gr parse data, update DB
                            prs.ParseAnnouncements(webview, getActivity(), upDB, "http://www.di.ionio.gr/el/information/news-announcements.html", "Γενικές");
                            Log.d("detUpdated", "Γενικές Announcements Updated");
                        }
                    }, 4000);
                    mHandler.postDelayed(new Runnable() {
                        public void run() {

                            if (pd.isShowing()) pd.dismiss();//L O A D I N G
                        }
                    }, 5000);

                    mHandler.postDelayed(new Runnable() {
                        public void run() {

                            getActivity().getFragmentManager().popBackStack();
                            CheckForSubscriptionsPeriodFragment Check = new CheckForSubscriptionsPeriodFragment();
                            eGramFunctions.AddFragment(Check,getActivity());
                        }
                    }, 7000);





                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                // Log.d("urldetOvveride",url);
                if (url.contains("error")) {
                    ServerError = true;
                    if (pd.isShowing()) pd.dismiss();//L O A D I N G
                } else ServerError = false;
                return false;

            }

        });
        webview.loadUrl("http://di.ionio.gr/el/information/secretariat-announcements.html");


        pd.show();

        return rootView;
    }
}