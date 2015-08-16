package asterios.eGram.app.Announcements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import asterios.eGram.app.R;


public class AnnouncementAdapter extends BaseExpandableListAdapter {
    private final SparseArray<AnnouncementGroup> groups;
    public LayoutInflater inflater;
    public Activity activity;


    public AnnouncementAdapter(Activity act, SparseArray<AnnouncementGroup> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Titles.get(childPosition);
    }

    public Object getUrl(int groupPosition, int childPosition) {
        return groups.get(groupPosition).Urls.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        final String url = (String) getUrl(groupPosition, childPosition);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.explist_announcement_row_details, null);
        }
        TextView announcementTitle = null;
        announcementTitle = (TextView) convertView.findViewById(R.id.announcementTitle);
        announcementTitle.setText(children);




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("detUrl", url);


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                activity.startActivity(browserIntent);


/*
                eGramFunctions.ShowOKDialog(activity,R.drawable.ic_grades,children,
                        activity.getString(R.string.ExPeriodTitle)+Examinations+"\n"+
                        activity.getString(R.string.typeTitle)+types);*/
/*
                Toast.makeText(activity, "ΤΕΣΤ",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                /*Toast.makeText(activity, "ΤΕΣΤ",
                        Toast.LENGTH_SHORT).show();*/
                return false;
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).Titles.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
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
            convertView = inflater.inflate(R.layout.explist_announcement_group_row, null);
        }
        AnnouncementGroup group = (AnnouncementGroup) getGroup(groupPosition);

        ImageView announcementGroupIcon = (ImageView) convertView.findViewById(R.id.announcementGroupIcon);

        TextView groupSum = (TextView) convertView.findViewById(R.id.groupSum);
        groupSum.setText(group.GroupSum);

        TextView groupName = (TextView) convertView.findViewById(R.id.groupName);
        groupName.setText(group.GroupName);


       if (group.GroupName.contains(activity.getString(R.string.GeneralAnnouncements))) {announcementGroupIcon.setImageResource(R.drawable.ic_department);}
       if (group.GroupName.contains(activity.getString(R.string.SecreteriatAnnouncements))) {announcementGroupIcon.setImageResource(R.drawable.ic_launcher);}

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

