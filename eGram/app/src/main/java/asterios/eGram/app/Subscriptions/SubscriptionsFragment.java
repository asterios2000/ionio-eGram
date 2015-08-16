package asterios.eGram.app.Subscriptions;


import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.melnykov.fab.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.Parser.JavaScriptParseFunctions;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;

public class SubscriptionsFragment extends ListFragment {

    private userDataDB upDB;
    ArrayList<String> SubscriptionCodesList = new ArrayList<String>();
    ArrayList<String> SubscriptionTitleList = new ArrayList<String>();
    ArrayList<String> SubscriptionTypeList = new ArrayList<String>();
    ArrayList<String> SubscriptionSemesterList = new ArrayList<String>();
    ArrayList<String> SubscriptionDMList = new ArrayList<String>();
    ArrayList<String> SubscriptionHourList = new ArrayList<String>();
    ArrayList<String> SubscriptionECTSList = new ArrayList<String>();

    public SubscriptionsFragment(){}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        upDB = new userDataDB(getActivity());
        refreshSubscriptionList();

        // use your custom layout
        SubscriptionsAdapter Applications = new SubscriptionsAdapter(getActivity(), SubscriptionCodesList, SubscriptionTitleList, SubscriptionTypeList,SubscriptionSemesterList,SubscriptionDMList,SubscriptionHourList,SubscriptionECTSList);
        setListAdapter(Applications);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        final FloatingActionButton AddSubscriptionBtn = (FloatingActionButton) rootView.findViewById(R.id.addsubscription);


        AddSubscriptionBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                if (eGramFunctions.isNetAvailable(getActivity())) {


                    //CREATE NEW SEMESTER SUBSCRIPTION

                    JavaScriptParseFunctions checkSub = new JavaScriptParseFunctions(getActivity());

                    if (checkSub.getSubscriptionsTime()) {

                        getActivity().getFragmentManager().popBackStack();
                        GetNewSubscriptionSubjectsFragment NewSub = new GetNewSubscriptionSubjectsFragment();
                        eGramFunctions.AddFragment(NewSub, getActivity());


                    } else {

                        getActivity().getFragmentManager().popBackStack();
                        CheckForSubscriptionsPeriodFragment Check = new CheckForSubscriptionsPeriodFragment();
                        eGramFunctions.AddFragment(Check, getActivity());
                    }
                    Log.d("DetWhich", String.valueOf(checkSub.getSubscriptionsTime()));

                }
                //NO NETWORK
                else
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_no_internet, getString(R.string.noNetworkTitle), getString(R.string.noNetworkMsg));


            }
        });

        return rootView;
    }

    //------------------------ SUBSCRIPTION SINGLE CLICK Actions ------------------//
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        //eGramFunctions.ShowToast(getActivity().getLayoutInflater(), getActivity(), R.drawable.ic_subscriptions, item,"Short");

        eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_subscriptions,item,
                SubscriptionCodesList.get(position) + "\n" +
                SubscriptionSemesterList.get(position)+"\n"+
                SubscriptionTypeList.get(position)+"\n"+
                SubscriptionDMList.get(position)+"\n"+
                SubscriptionHourList.get(position)+"\n"+
                SubscriptionECTSList.get(position)
        );

    }

    public void refreshSubscriptionList() //To refresh the list View

    {

        SubscriptionCodesList.clear();
        SubscriptionTitleList.clear();
        SubscriptionTypeList.clear();
        SubscriptionSemesterList.clear();
        SubscriptionDMList.clear();
        SubscriptionHourList.clear();
        SubscriptionECTSList.clear();

        List<UserSubscriptions> subscriptions = upDB.getAllSubscriptions(upDB.getDefaultProfilesID());
        for (UserSubscriptions us : subscriptions) {
            if (!SubscriptionTitleList.contains(us.getTitle())) {
                SubscriptionTitleList.add(us.getTitle());
                SubscriptionCodesList.add(getString(R.string.code)+us.getCode());
                SubscriptionTypeList.add(getString(R.string.type)+us.getType());
                SubscriptionSemesterList.add(getString(R.string.semester)+us.getSemester());
                SubscriptionDMList.add(getString(R.string.DMTitle)+us.getDM());
                SubscriptionHourList.add(getString(R.string.hours)+us.getHours());
                SubscriptionECTSList.add(getString(R.string.ECTSTitle)+us.getECTS());

            }
        }

    }
}
