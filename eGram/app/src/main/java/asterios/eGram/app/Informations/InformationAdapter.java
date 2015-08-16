package asterios.eGram.app.Informations;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import asterios.eGram.app.Grades.Group;
import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class InformationAdapter extends BaseExpandableListAdapter {
    private final SparseArray<InfoGroup> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public InformationAdapter(Activity act, SparseArray<InfoGroup> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Names.get(childPosition);
    }

    /*public Object getName(int groupPosition, int childPosition) {
        return groups.get(groupPosition).grades.get(childPosition);
    }*/
    public Object getType(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Types.get(childPosition);
    }
    public Object getAddress(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Addresses.get(childPosition);
    }
    public Object getPhone(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Phones.get(childPosition);
    }
    public Object getFax(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Faxes.get(childPosition);
    }
    public Object getMail(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Mails.get(childPosition);
    }
    public Object getWebPage(int groupPosition, int childPosition) {
        return groups.get(groupPosition).WebPages.get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        final String webpage = (String) getWebPage(groupPosition, childPosition);
        final String types = (String) getType(groupPosition, childPosition);
        final String address = (String) getAddress(groupPosition, childPosition);
        final String phone = (String) getPhone(groupPosition, childPosition);
        final String fax = (String) getFax(groupPosition, childPosition);
        final String mail = (String) getMail(groupPosition, childPosition);



        if (convertView == null) {
            convertView = inflater.inflate(R.layout.explist_inforow_details, null);
        }
        TextView infoName = null;
        infoName = (TextView) convertView.findViewById(R.id.infoName);
        infoName.setText(children);

        TextView infoAddress = null;
        infoAddress = (TextView) convertView.findViewById(R.id.infoAddress);
        infoAddress.setText(address);

        TextView infoPhone = null;
        infoPhone = (TextView) convertView.findViewById(R.id.infoPhone);
        infoPhone.setText(phone);

        TextView infoFax = null;
        infoFax = (TextView) convertView.findViewById(R.id.infoFax);
        infoFax.setText(fax);

        TextView infoMail = null;
        infoMail = (TextView) convertView.findViewById(R.id.infoMail);
        infoMail.setText(mail);

        TextView infoWebPage = null;
        infoWebPage = (TextView) convertView.findViewById(R.id.infoWebPage);
        infoWebPage.setText(webpage);




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                eGramFunctions.ShowOKDialog(activity,R.drawable.ic_grades,children,
                        activity.getString(R.string.ExPeriodTitle)+Examinations+"\n"+
                        activity.getString(R.string.typeTitle)+types);
                /*Toast.makeText(activity, types+"\n"+children+"\n"+Examinations,
                        Toast.LENGTH_SHORT).show();*/
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).Names.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }
    public Object getGroupTotalSubjectsPassed(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.explist_infogroup_row, null);
        }
        InfoGroup group = (InfoGroup) getGroup(groupPosition);

        ImageView infoGroupIcon = (ImageView) convertView.findViewById(R.id.infoGroupIcon);

        CheckedTextView groupName = (CheckedTextView) convertView.findViewById(R.id.groupName);
        groupName.setText(group.GroupName);

        TextView groupSum = (TextView) convertView.findViewById(R.id.groupSum);
        groupSum.setText(group.GroupSum);

        if (group.GroupName.contains(activity.getString(R.string.depStaff))) {infoGroupIcon.setImageResource(R.drawable.ic_dep);}
        if (group.GroupName.contains(activity.getString(R.string.etepStaff))) {infoGroupIcon.setImageResource(R.drawable.ic_etep);}
        if (group.GroupName.contains(activity.getString(R.string.teachingStaff))) {infoGroupIcon.setImageResource(R.drawable.ic_stuff);}
        //if (group.GroupName.contains("Γραμματεία")) {infoGroupIcon.setImageResource(R.drawable.ic_department);}

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

