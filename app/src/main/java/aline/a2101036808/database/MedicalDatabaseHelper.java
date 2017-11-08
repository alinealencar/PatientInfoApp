package aline.a2101036808.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import aline.a2101036808.dataModel.Patient;
import aline.a2101036808.dataModel.Test;

/**
 * Created by aline on 2017-11-06.
 */

public class MedicalDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "medicalDB";
    private static final int DB_VERSION = 1;

    //Tables
    private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE tbl_doctor (" +
            "doctorId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "firstName TEXT," +
            "lastName TEXT," +
            "department TEXT," +
            "username TEXT," +
            "password TEXT);";
    private static final String CREATE_NURSE_TABLE = "CREATE TABLE tbl_nurse(" +
            "nurseId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "firstName TEXT," +
            "lastName TEXT," +
            "department TEXT," +
            "username TEXT," +
            "password TEXT);";
    private static final String CREATE_PATIENT_TABLE = "CREATE TABLE tbl_patient(" +
            "patientId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "firstName TEXT," +
            "lastName TEXT," +
            "department TEXT," +
            "doctorId INTEGER," +
            "room TEXT);";
    private static final String CREATE_TEST_TABLE = "CREATE TABLE tbl_test(" +
            "testId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "patientId INTEGER," +
            "BPL INTEGER," +
            "BPH INTEGER," +
            "temperature REAL," +
            "glucose REAL," +
            "cholesterol REAL);";

    public MedicalDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DOCTOR_TABLE);
        db.execSQL(CREATE_NURSE_TABLE);
        db.execSQL(CREATE_PATIENT_TABLE);
        db.execSQL(CREATE_TEST_TABLE);

        /* Insert values */
        //Add a few doctors for each department (Oncology, Neurology and Cardiology)
        insertDoctor(db, "John", "Doe", "Oncology", "jdoe","123456");
        insertDoctor(db, "William", "Brown", "Oncology", "wbrown", "123456");
        insertDoctor(db, "Eleanor", "Shellstrop", "Oncology", "eshellstrop", "123456");
        insertDoctor(db, "Tahani", "Al-Jamil", "Neurology", "tjamil", "123456");
        insertDoctor(db, "Chidi", "Anagonye", "Neurology", "canagonye", "123456");
        insertDoctor(db, "Jason", "Mendoza", "Neurology", "jmendoza", "123456");
        insertDoctor(db, "Jane", "Villanueva", "Cardiology", "jvillanueva", "123456");
        insertDoctor(db, "Ilana", "Wexler", "Cardiology", "iwexler", "123456");
        insertDoctor(db, "Abbi", "Abrams", "Cardiology", "aabrams", "123456");

        insertNurse(db, "Michael", "Jones", "Oncology", "mjones", "123456");
        insertNurse(db, "Jack", "Smith", "Cardiology", "jsmith","123456");
        insertPatient(db, new Patient ("Lucas", "Byers", "Oncology", 1, "222"));
        insertTest(db, new Test(1, 92, 62, 36.5, 100, 150));
        insertPatient(db, new Patient("Joyce", "Phillips", "Oncology", 1, "223"));
        insertTest(db, new Test(2, 83, 52, 37.5, 104, 180));
        insertPatient(db, new Patient("Bob", "McDonald", "Oncology", 1, "224"));
        insertTest(db, new Test (3, 89, 70, 38, 115, 200));
        insertPatient(db, new Patient("Jim", "Hopper", "Neurology", 2, "310"));
        insertTest(db, new Test(4, 111, 80, 36, 102, 178));
        insertPatient(db, new Patient("Michael", "Bluth", "Neurology", 2, "311"));
        insertTest(db, new Test(5, 122, 82, 36.8, 113, 124));
        insertPatient(db, new Patient("Lucille", "Johnson", "Neurology", 2, "312"));
        insertTest(db, new Test(6, 97, 75, 37.3, 131, 215));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion ,int newVersion){

    }

    public static void insertDoctor(SQLiteDatabase db, String fName, String lName, String department, String username, String password){
        ContentValues doctorValues = new ContentValues();
        doctorValues.put("firstName", fName);
        doctorValues.put("lastName", lName);
        doctorValues.put("department", department);
        doctorValues.put("username", username);
        doctorValues.put("password", password);
        db.insert("tbl_doctor", null, doctorValues);
    }

    public static void insertNurse(SQLiteDatabase db, String fName, String lName, String department, String username, String password){
        ContentValues nurseValues = new ContentValues();
        nurseValues.put("firstName", fName);
        nurseValues.put("lastName", lName);
        nurseValues.put("department", department);
        nurseValues.put("username", username);
        nurseValues.put("password", password);
        db.insert("tbl_nurse", null, nurseValues);
    }

    //Overload insertPatient method to accept a parameter of Patient type (this method will be called from other classes)
    public void insertPatient(Patient newPatient){
        insertPatient(this.getWritableDatabase(), newPatient);
    }

    public void insertPatient(SQLiteDatabase db, Patient newPatient){
        ContentValues patientValues = new ContentValues();
        patientValues.put("firstName", newPatient.getfName());
        patientValues.put("lastName", newPatient.getlName());
        patientValues.put("department", newPatient.getDept());
        patientValues.put("doctorId", newPatient.getDoctorId());
        patientValues.put("room", newPatient.getRoom());
        db.insert("tbl_patient", null, patientValues);
    }

    //Overload insertTest method to accept a parameter of Test type (this method will be called from other classes)
    public void insertTest(Test newTest){
        insertTest(this.getWritableDatabase(), newTest);
    }

    public void insertTest(SQLiteDatabase db, Test newTest){
        ContentValues testValues = new ContentValues();
        testValues.put("patientId", newTest.getPatientId());
        testValues.put("BPL", newTest.getBpl());
        testValues.put("BPH", newTest.getBph());
        testValues.put("temperature", newTest.getTemperature());
        testValues.put("glucose", newTest.getGlucose());
        testValues.put("cholesterol", newTest.getCholesterol());
        db.insert("tbl_test", null, testValues);
    }

    public List<Patient> getAllPatients(){
        List<Patient> patients = new ArrayList<Patient>();

        //Select query
        String selectPatients = "SELECT * FROM tbl_patient";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectPatients, null);

        if(cursor.moveToFirst()){
            do {
                Patient patient = new Patient(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getInt(4), cursor.getString(5));
               patients.add(patient);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return patients;
    }

    public List<String> getDoctorByDept(String department){
        List<String> doctors = new ArrayList<String>();

        //Select query
        String selectDoctor = "SELECT lastName FROM tbl_doctor WHERE department IS '" + department + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectDoctor, null);

        if(cursor.moveToFirst()){
            do {
                doctors.add("Dr. " + cursor.getString(0));
            }
            while (cursor.moveToNext());

            cursor.close();
            db.close();
        }
        return doctors;
    }

    public String getDoctorNameById(int doctorId){
        String doctorName = null;

        //Select query
        String selectDoctor = "SELECT firstName, lastName FROM tbl_doctor WHERE doctorId IS '" + doctorId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectDoctor, null);

        if(cursor.moveToFirst())
            doctorName = cursor.getString(0) + " " +cursor.getString(1);

        return doctorName;
    }

    public int getDoctorIdByName(String lName){
        int doctorId = 0;

        //Select query
        String selectDoctor = "SELECT doctorId FROM tbl_doctor WHERE lastName IS '" + lName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectDoctor, null);

        if(cursor.moveToFirst())
            doctorId = cursor.getInt(0);

        return doctorId;
    }

    public List<Test> getTestsByPatientId(int patientId){
        List<Test> tests = new ArrayList<Test>();

        //Select query
        String selectTest = "SELECT * FROM tbl_test WHERE patientId IS'" + patientId +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectTest, null);
        if(cursor.moveToFirst()){
            do {
                //Create Test object
                Test aTest = new Test(cursor.getInt(0), cursor.getInt(1),
                        cursor.getInt(2), cursor.getInt(3),
                        cursor.getDouble(4), cursor.getDouble(5),
                        cursor.getDouble(6));
                //Add Test object to tests array list
                tests.add(aTest);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return tests;
    }

    public Patient getPatientById(int patientId){
        Patient patient = null;

        //Select query
        String selectPatient = "SELECT firstName, lastName, department, doctorId, room " +
                "FROM tbl_patient " +
                "WHERE patientId IS'" + patientId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectPatient, null);
        if(cursor.moveToFirst()){
            patient = new Patient(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3),
                    cursor.getString(4));
        }
        System.out.println(patient.getfName() + "' doctor is " + patient.getDoctorId());
        return patient;
    }

    public int authenticateUser(String username, String password){
        //Select query for doctor
        String loginDoctor = "SELECT * " +
                "FROM tbl_doctor " +
                "WHERE username IS '" + username + "' AND " +
                "password IS '" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorD = db.rawQuery(loginDoctor, null);
        if(cursorD.moveToFirst())
            return 1; //Login is valid and user is a doctor

        //Select query for nurse
        String loginNurse = "SELECT * " +
                "FROM tbl_nurse " +
                "WHERE username IS '" + username + "' AND " +
                "password IS '" + password + "'";
        Cursor cursorN = db.rawQuery(loginNurse, null);
        if(cursorN.moveToFirst())
            return 2; //Login is valid and user is a nurse

        return 0; //Login is not valid
    }
}
