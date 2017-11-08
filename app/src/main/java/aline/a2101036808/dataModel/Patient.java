package aline.a2101036808.dataModel;

/**
 * Created by aline on 2017-11-06.
 */

public class Patient {
    private int patientId, doctorId;
    private String fName, lName, dept, room;

    public Patient() {
    }

    public Patient(String fName, String lName, String dept, int doctorId, String room) {
        this.fName = fName;
        this.lName = lName;
        this.dept = dept;
        this.doctorId = doctorId;
        this.room = room;
    }

    public Patient(int patientId, String fName, String lName, String dept, int doctorId, String room) {
        this.patientId = patientId;
        this.fName = fName;
        this.lName = lName;
        this.dept = dept;
        this.doctorId = doctorId;
        this.room = room;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
    
    @Override
    public String toString() {
        return fName + " " + lName;
    }
}
