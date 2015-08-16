package asterios.eGram.app.Applications;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.melnykov.fab.FloatingActionButton;
import java.util.ArrayList;

import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class NewApplicationFragment extends Fragment {

    private userDataDB upDB;

    public ArrayList<String> ApplicationBundle = new ArrayList<String>();


    public NewApplicationFragment(){}

	@Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        container.removeAllViews();
        upDB = new userDataDB(getActivity());
        final View rootView = inflater.inflate(R.layout.add_application, container, false);
        ApplicationBundle.clear();

        final EditText allo = (EditText) rootView.findViewById(R.id.somethingElse);
        final TextView notes = (TextView) rootView.findViewById(R.id.notes);
        final FloatingActionButton SendButton = (FloatingActionButton) rootView.findViewById(R.id.SendApplicationBtn);



        allo.setVisibility(View.INVISIBLE);
        SendButton.setEnabled(true);
        SendButton.setColorNormal(Color.parseColor("#83d0c9"));

        final  Spinner subject = (Spinner) rootView.findViewById(R.id.spinnerSubject);
        ArrayAdapter<CharSequence> subjectsAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.subjects, android.R.layout.simple_spinner_item);
        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(subjectsAdapter);
        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                String subj = subject.getSelectedItem().toString();
                SendButton.setEnabled(true);
                SendButton.setColorNormal(Color.parseColor("#83d0c9"));

                if (subj.contains(getString(R.string.otherCertificate))){

                    allo.setVisibility(View.VISIBLE);
                }
                else
                {
                    allo.setVisibility(View.INVISIBLE);

                }

                if (subj.contains(getString(R.string.studyCertificate)))
                    if (upDB.PendingStudyCertificate(String.valueOf(upDB.getDefaultProfilesID())))
                    {
                        eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning,
                                                         getString(R.string.informationTitle),
                                                         getString(R.string.pendingStudyCertificate));

                        SendButton.setEnabled(false);
                        SendButton.setColorNormal(Color.parseColor("#c5c4c4"));

                    }

                if (subj.contains(getString(R.string.gradeCertificate)))
                    if (upDB.PendingGradeCertificate(String.valueOf(upDB.getDefaultProfilesID())))
                    {
                        eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning,
                                getString(R.string.informationTitle),
                                getString(R.string.pendingGradeCertificate));

                        SendButton.setEnabled(false);
                        SendButton.setColorNormal(Color.parseColor("#c5c4c4"));

                    }




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // Do nothing

            }
        });


        final Spinner copies = (Spinner) rootView.findViewById(R.id.spinnerCopies);
        ArrayAdapter<CharSequence> copiesAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.copies, android.R.layout.simple_spinner_item);
        copiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        copies.setAdapter(copiesAdapter);
        copies.setSelection(0);

        SendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (eGramFunctions.isNetAvailable(getActivity())) {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (subject.getSelectedItemPosition() == 0) {   //Study Certificate
                        ApplicationBundle.clear();
                        ApplicationBundle.add("0");    //Study Certificate               //0
                        if (notes.getText().toString().length() > 0)
                            ApplicationBundle.add(notes.getText().toString().trim());    //1
                        else ApplicationBundle.add(" ");
                        ApplicationBundle.add(copies.getSelectedItem().toString());      //2


                        eGramFunctions.SendingApplication(getActivity(), ApplicationBundle);
                    }

                    if (subject.getSelectedItemPosition() == 1) { //Grade Certificate
                        ApplicationBundle.clear();
                        ApplicationBundle.add("1");   //Grade Certificate                       //0
                        if (notes.getText().toString().length() > 0)
                            ApplicationBundle.add(notes.getText().toString().trim());           //1
                        else ApplicationBundle.add(" ");
                        ApplicationBundle.add(copies.getSelectedItem().toString());             //2


                        eGramFunctions.SendingApplication(getActivity(), ApplicationBundle);
                    }

                    if (subject.getSelectedItemPosition() == 2) { //Other Certificate
                        if (allo.getText().toString().length() == 0 || notes.getText().toString().length() == 0) {
                            eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_warning, getString(R.string.emptyFieldsTitle), getString(R.string.emptyFieldsMsg));


                        } else {
                            ApplicationBundle.clear();
                            ApplicationBundle.add("2");  ////Other Certificate                        //0
                            ApplicationBundle.add(notes.getText().toString().trim());                 //1
                            ApplicationBundle.add(allo.getText().toString().trim());                  //2
                            ApplicationBundle.add(copies.getSelectedItem().toString());               //3


                            eGramFunctions.SendingApplication(getActivity(), ApplicationBundle);
                        }
                    }
                }
                //NO NETWORK
                else
                    eGramFunctions.ShowOKDialog(getActivity(), R.drawable.ic_no_internet, getString(R.string.noNetworkTitle), getString(R.string.noNetworkMsg));


            }
        });




        return rootView;
    }

}
