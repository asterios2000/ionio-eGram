package asterios.eGram.app.Announcements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asterios on 11/6/2015.
 */
public class AnnouncementGroup {

    public String GroupName;
    public String GroupSum;

    public final List<String> Titles = new ArrayList<String>();
    public final List<String> Urls = new ArrayList<String>();

    public AnnouncementGroup(String GroupName) { this.GroupName = GroupName; }

    public AnnouncementGroup(String grp, String sum) {
        this.GroupName = grp;
        this.GroupSum = sum;
    }

}
