package aline.a2101036808;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import aline.a2101036808.dataModel.Patient;

public class EnterPatientActivity extends Activity {
    Spinner deptSpinner, doctorSpinner, roomSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_patient);

        deptSpinner = (Spinner) findViewById(R.id.deptSpinner);
        doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);
        roomSpinner = (Spinner) findViewById(R.id.roomSpinner);

        //Populate the departments spinner with pre-defined departments
        List<String> departments = new ArrayList<String>();
        departments.add("Oncology");
        departments.add("Cardiology");
        departments.add("Neurology");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departments);

        deptSpinner.setAdapter(dataAdapter);

        //Populate the departments spinner with room numbers
        List<String> roomNumbers = new ArrayList<String>();
        for(int i = 317; i < 325; i++)
            roomNumbers.add(String.valueOf(i));

        for(int j = 401; j < 415; j++)
            roomNumbers.add(String.valueOf(j));

        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roomNumbers);

        roomSpinner.setAdapter(roomAdapter);

        //Change the values in the doctors spinner according to the department that was selected before
        //Every doctor belongs to one department
        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDept = deptSpinner.getSelectedItem().toString();
                MedicalDatabaseHelper db = new MedicalDatabaseHelper(getApplicationContext());
                List<String> doctors = db.getDoctorByDept(selectedDept);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EnterPatientActivity.this,
                        android.R.layout.simple_dropdown_item_1line, doctors);

                doctorSpinner.setAdapter(adapter);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Adapter for the Cancel button
        AdapterView.OnClickListener cancel =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(EnterPatientActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                };
        //Add listener to Cancel button
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(cancel);

        //Listener to get the information from the form and redirect the nurse to the home page
        AdapterView.OnClickListener addPatientClickListener =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Patient newPatient = new Patient();

                        //Get all the entries from the form
                        EditText fNameTxt = (EditText) findViewById(R.id.txtBxFName);
                        newPatient.setfName(fNameTxt.getText().toString());
                        EditText lNameTxt = (EditText) findViewById(R.id.txtBxLName);
                        newPatient.setlName(lNameTxt.getText().toString());
                        Spinner deptSpinner = (Spinner) findViewById(R.id.deptSpinner);
                        newPatient.setDept(deptSpinner.getSelectedItem().toString());
                        Spinner doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);
                        //Get the ID of the selected doctor
                        MedicalDatabaseHelper db = new MedicalDatabaseHelper(getApplicationContext());
                        newPatient.setDoctorId(db.getDoctorIdByName((doctorSpinner.getSelectedItem().toString()).substring(4)));
                        Spinner roomSpinner = (Spinner) findViewById(R.id.roomSpinner);
                        newPatient.setRoom(roomSpinner.getSelectedItem().toString());

                        boolean validEntries = true;
                        //Validate all fields
                        if(TextUtils.isEmpty(fNameTxt.getText().toString().trim())){
                            fNameTxt.setError("Required");
                            validEntries = false;
                        }
                        if(TextUtils.isEmpty(lNameTxt.getText().toString().trim())){
                            lNameTxt.setError("Required");
                            validEntries = false;
                        }

                        // If all entries are valid, add patient to the database
                        if(validEntries){
                            //Add patient to the database
                            db.insertPatient(newPatient);
                            Toast.makeText(EnterPatientActivity.this, "Patient was added!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EnterPatientActivity.this,
                                    DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                };

        // Add listener to the button
        Button btnAddPatient = (Button) findViewById(R.id.btnAdd);
        btnAddPatient.setOnClickListener(addPatientClickListener);

    }
}
