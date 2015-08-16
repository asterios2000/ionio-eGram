package asterios.eGram.app.Profile;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import asterios.eGram.app.R;

import asterios.eGram.app.eGramFunctions;

public class NoProfilesFragment extends Fragment {

	public NoProfilesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.no_profile, container, false);


        final FloatingActionButton AddProfileBtn = (FloatingActionButton) rootView.findViewById(R.id.gotoAddProfile);


        AddProfileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (eGramFunctions.isNetAvailable(getActivity())) {
                    //create profile and add it to the DB

                    Fragment AddProfile = new AddProfileFragment();
                    eGramFunctions.AddFragment(AddProfile, getActivity());


                }
                //NO NETWORK, NO PROFILE
                else
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_no_internet, getString(R.string.noNetworkTitle), getString(R.string.noNetworkMsg));



            }
        });
         
        return rootView;
    }
}

