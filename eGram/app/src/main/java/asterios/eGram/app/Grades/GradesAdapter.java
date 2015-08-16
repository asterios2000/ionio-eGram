package asterios.eGram.app.Grades;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import asterios.eGram.app.R;
import asterios.eGram.app.eGramFunctions;


public class GradesAdapter extends BaseExpandableListAdapter {
    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public GradesAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).subjects.get(childPosition);
    }

    public Object getGrade(int groupPosition, int childPosition) {
        return groups.get(groupPosition).grades.get(childPosition);
    }
    public Object getType(int groupPosition, int childPosition) {
        return groups.get(groupPosition).types.get(childPosition);
    }
    public Object getDM(int groupPosition, int childPosition) {
        return groups.get(groupPosition).DMs.get(childPosition);
    }
    public Object getECTS(int groupPosition, int childPosition) {
        return groups.get(groupPosition).ECTS.get(childPosition);
    }
    public Object getExaminationPeriod(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Examinations.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        final String grades = (String) getGrade(groupPosition, childPosition);
        final String types = (String) getType(groupPosition, childPosition);
        final String DMs = (String) getDM(groupPosition, childPosition);
        final String ECTS = (String) getECTS(groupPosition, childPosition);
        final String Examinations = (String) getExaminationPeriod(groupPosition, childPosition);


        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.explist_row_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.subject);
        text.setText(children);

        TextView grade = null;
        grade = (TextView) convertView.findViewById(R.id.grade);
        grade.setText(grades);

        TextView DM = null;
        DM = (TextView) convertView.findViewById(R.id.DM);
        DM.setText(DMs);

        TextView Ects = null;
        Ects = (TextView) convertView.findViewById(R.id.ECTS);
        Ects.setText(ECTS);


//------------------------------- Short Click Actions --------------------------------//
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eGramFunctions.ShowOKDialog(activity,R.drawable.ic_grades,children,
                        activity.getString(R.string.ExPeriodTitle)+Examinations+"\n"+
                        activity.getString(R.string.typeTitle)+types);

            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).subjects.size();
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
            convertView = inflater.inflate(R.layout.explist_group_row, null);
        }
        Group group = (Group) getGroup(groupPosition);

        CheckedTextView sem = (CheckedTextView) convertView.findViewById(R.id.semester);
        sem.setText(group.string);

        TextView TotalSubjectsPassed = (TextView) convertView.findViewById(R.id.totalSubjectsPassed);
        TotalSubjectsPassed.setText(group.semesterSubjectsPassed);

        TextView TotalDM = (TextView) convertView.findViewById(R.id.totalDM);
        TotalDM.setText(group.semesterDM);

        TextView TotalECTS = (TextView) convertView.findViewById(R.id.totalECTS);
        TotalECTS.setText(group.semesterECTS);

        TextView mo = (TextView) convertView.findViewById(R.id.mo);
        mo.setText(group.semesterMO);


        /*((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);*/
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

