package asterios.eGram.app.Informations;

import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import asterios.eGram.app.Database.userDataDB;
import asterios.eGram.app.R;

public class InformationFragment extends Fragment {

    SparseArray<InfoGroup> groups = new SparseArray<InfoGroup>();
    InformationAdapter adapter;
    ExpandableListView listView;
    public userDataDB upDB;
    public List<GeneralInfos> gI;
    public InformationFragment() { }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setAdapter(adapter);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new InformationAdapter(getActivity(), groups);

        upDB = new userDataDB(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        listView = (ExpandableListView) rootView.findViewById(R.id.listView);

        createData();
        listView.setAdapter(adapter);

        return rootView;
    }


    public void createData() {

        gI = upDB.getAllInformation();
        InfoGroup group = null;

        int i = 1;  //It starts on 1 in order to not add secreteriat again
        int j = 0;

       group = new InfoGroup(getString(R.string.depStaff),"(16)");

        while (i<gI.size()){

            if (i>2 && gI.get(i).getType().equals("ΕΤΕΠ")&& !group.GroupName.contains(getString(R.string.etepStaff))) {
                group = new InfoGroup(getString(R.string.etepStaff), "(2)");

                j++;
            }
            if (i>2 && gI.get(i).getType().equals("Διδακτικό")&& !group.GroupName.contains(getString(R.string.teachingStaff))) {
                group = new InfoGroup(getString(R.string.teachingStaff), "(1)");

                j++;
            }

            group.Names.add(gI.get(i).getName());
            group.Addresses.add(gI.get(i).getAddress());
            group.Phones.add(gI.get(i).getPhone());
            group.Faxes.add(gI.get(i).getFax());
            group.Mails.add(gI.get(i).getEmail());
            group.WebPages.add(gI.get(i).getWebPage());
            group.Types.add(gI.get(i).getType());

            groups.append(j, group);
            i++;
        }
    }

}