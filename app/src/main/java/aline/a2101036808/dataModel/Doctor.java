package aline.a2101036808.dataModel;

/**
 * Created by aline on 2017-11-06.
 */

public class Doctor {
    private int doctorId;
    private String fName, lName, dept;

    public Doctor() {
    }

    public Doctor(int doctorId, String fName, String lName, String dept) {
        this.doctorId = doctorId;
        this.fName = fName;
        this.lName = lName;
        this.dept = dept;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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
