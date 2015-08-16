package asterios.eGram.app.Grades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asterios on 11/6/2015.
 */
public class Group {

    public String string;
    public String semesterSubjectsPassed;
    public String semesterDM;
    public String semesterECTS;
    public String semesterMO;

    public final List<String> subjects = new ArrayList<String>();
    public final List<String> grades = new ArrayList<String>();
    public final List<String> types = new ArrayList<String>();
    public final List<String> DMs = new ArrayList<String>();
    public final List<String> ECTS = new ArrayList<String>();
    public final List<String> Examinations = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

    public Group(String grp, String subjectsPassed, String DM, String ECTS, String MO) {
        this.string = grp;
        this.semesterSubjectsPassed = subjectsPassed;
        this.semesterDM = DM;
        this.semesterECTS = ECTS;
        this.semesterMO = MO;
    }

}
