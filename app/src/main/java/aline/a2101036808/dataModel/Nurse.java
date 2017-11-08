package aline.a2101036808.dataModel;

/**
 * Created by aline on 2017-11-06.
 */

public class Nurse {
    private int nurseId;
    private String fName, lName, dept;

    public Nurse() {
    }

    public Nurse(int nurseId, String fName, String lName, String dept) {
        this.nurseId = nurseId;
        this.fName = fName;
        this.lName = lName;
        this.dept = dept;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
