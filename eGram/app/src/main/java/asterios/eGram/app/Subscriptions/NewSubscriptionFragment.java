package asterios.eGram.app.Subscriptions;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.melnykov.fab.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class NewSubscriptionFragment extends ListFragment {

    private userDataDB upDB;
    ArrayList<String> SubscriptionStateList = new ArrayList<String>();
    ArrayList<String> SubscriptionSubjectList = new ArrayList<String>();
    ArrayList<String> SubscriptionCodeList = new ArrayList<String>();
    ArrayList<String> SubscriptionCheckBoxIdList = new ArrayList<String>();

    public ArrayList<String> SubscriptionBundle = new ArrayList<String>();
    View footer = null;


    public NewSubscriptionFragment(){}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        upDB = new userDataDB(getActivity());
        refreshSubscriptionsList();

        // use your custom layout
        NewSubscriptionAdapter Subscriptions = new NewSubscriptionAdapter(getActivity(), SubscriptionCodeList, SubscriptionSubjectList,SubscriptionStateList,SubscriptionCheckBoxIdList);
        setListAdapter(Subscriptions);
    }

	@Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        container.removeAllViews();

        final View rootView = inflater.inflate(R.layout.add_subscription, container, false);
        SubscriptionBundle.clear();


        final FloatingActionButton SendButton = (FloatingActionButton) rootView.findViewById(R.id.sendSubscritpion);
        final ImageView BasicIcon = (ImageView) rootView.findViewById(R.id.basic_icon);
        eGramFunctions.HelpOverLay(getActivity(), BasicIcon, getString(R.string.informationTitle), getString(R.string.subscriptionInstructions));

        SendButton.setEnabled(true);
        SendButton.setColorNormal(Color.parseColor("#83d0c9"));

        footer = inflater.inflate(R.layout.subscription_instructions, null);


        SendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (eGramFunctions.isNetAvailable(getActivity())) {
                    if (AllSubjectsChecked(NewSubscriptionAdapter.UserSelections)) {

                        eGramFunctions.ShowOKDialog(getActivity(),R.drawable.ic_warning, getString(R.string.informationTitle), getString(R.string.subscriptionServiceNotAvailable));

                    } else {
                        getFragmentManager().popBackStack();
                        eGramFunctions.SendingSubscription(getActivity(), SubscriptionBundle);
                    }

                }
                //NO NETWORK
                else
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_no_internet, getString(R.string.noNetworkTitle), getString(R.string.noNetworkMsg));


            }
        });




        return rootView;
    }
    //------------------------------ SINGLE CLICK Actions ---------------------//
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {


        if (position<SubscriptionStateList.size()) {
            final String item = (String) getListAdapter().getItem(position);

            if (SubscriptionStateList.get(position).equals("0"))
                eGramFunctions.ShowToast(getActivity().getLayoutInflater(), getActivity(), R.drawable.ic_subscriptions, "'" + item + "' " + getString(R.string.alreadyInSubscriptionList), "Short");
            if (SubscriptionStateList.get(position).equals("1"))
                eGramFunctions.ShowToast(getActivity().getLayoutInflater(), getActivity(), R.drawable.ic_subscriptions, "'" + item + "' " + getString(R.string.tapSwitchToAdd), "Short");
        }

    }

    public void refreshSubscriptionsList() //To refresh the List View

    {

        SubscriptionCodeList.clear();
        SubscriptionSubjectList.clear();
        SubscriptionStateList.clear();
        SubscriptionCheckBoxIdList.clear();

        List<UserSubscriptions> NewSubscription = upDB.getNewSubscription(upDB.getDefaultProfilesID());

        for (UserSubscriptions us : NewSubscription) {
           
            if (!SubscriptionCodeList.contains(us.getCode())) {
                SubscriptionCodeList.add(us.getCode());
                SubscriptionSubjectList.add(us.getTitle());
                SubscriptionStateList.add(us.getState());
                SubscriptionCheckBoxIdList.add(us.getCheckBoxId());
            }


        }
        //Only checked buttons' ids will pass to the webview SubscriptionBundle


        ListView list = getListView();
        list.addFooterView(footer);

        SubscriptionBundle = SubscriptionCheckBoxIdList;

    }

    public boolean AllSubjectsChecked(ArrayList<String> States)
    {

        boolean AllChecked = false;

        int i = 0;
        int count = 0;
        while (i<States.size())
        {
            Log.d("detState", States.get(i));
            if(States.get(i).equals("0")) {
                count++;
                SubscriptionBundle.set(i, "0");
            }

            i++;
        }

        if (count == States.size()) return true;
        else return false;
    }
}
