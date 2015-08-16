package asterios.eGram.app.Informations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asterios on 11/6/2015.
 */
public class InfoGroup {

    public String GroupName;
    public String GroupSum;

    public final List<String> Names = new ArrayList<String>();
    public final List<String> Addresses = new ArrayList<String>();
    public final List<String> Phones = new ArrayList<String>();
    public final List<String> Types = new ArrayList<String>();
    public final List<String> Faxes = new ArrayList<String>();
    public final List<String> Mails = new ArrayList<String>();
    public final List<String> WebPages = new ArrayList<String>();

    public InfoGroup(String GroupName) { this.GroupName = GroupName; }

    public InfoGroup(String grp, String sum) {
        this.GroupName = grp;
        this.GroupSum = sum;
    }

}
