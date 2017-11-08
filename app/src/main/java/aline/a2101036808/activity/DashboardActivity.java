package aline.a2101036808.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import aline.a2101036808.R;
import aline.a2101036808.dataModel.Patient;
import aline.a2101036808.database.MedicalDatabaseHelper;

public class DashboardActivity extends Activity {
    int selectedPatientId;
    String selectedPatientName;
    List<Patient> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button btnAddPatient = (Button) findViewById(R.id.btnAddPatient);
        Button btnDisplayPatient = (Button) findViewById(R.id.btnDisplayPatient);

        boolean isDoctor = false;

        try{
            isDoctor = getIntent().getExtras().getBoolean("isDoctor");
        }catch (Exception e){
            System.out.println(e);
        }

        //Check if the logged in user is a doctor or a nurse
        //Hide the Add Patient and Patient Information buttons for doctors
        if(isDoctor){
            btnAddPatient.setVisibility(View.INVISIBLE);
            btnDisplayPatient.setVisibility(View.INVISIBLE);
        }

        Spinner patientsSpinner = (Spinner) findViewById(R.id.patientsSpinner);

        MedicalDatabaseHelper db = new MedicalDatabaseHelper(getApplicationContext());
        patients = db.getAllPatients();
        final List<String> patientsNames = new ArrayList<String>();
        for(Patient aP : patients){
            patientsNames.add(aP.toString());
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientsNames);

        patientsSpinner.setAdapter(dataAdapter);

        patientsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Get the patient id of the selected patient
                selectedPatientId = patients.get(i).getPatientId();
                selectedPatientName = patients.get(i).getfName() + " " + patients.get(i).getlName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Adapter for the Enter Test button
        AdapterView.OnClickListener enterTest =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(getApplicationContext(), EnterTestActivity.class);
                        intent.putExtra("patientId", String.valueOf(selectedPatientId));
                        intent.putExtra("patientName", selectedPatientName);
                        startActivity(intent);
                    }
                };

        //Adapter for the Display Test button
        final AdapterView.OnClickListener displayTest =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){

                        Intent intent = new Intent(getApplicationContext(), DisplayTestActivity.class);
                        //Send the selected patient's patientId to the display test activity
                        intent.putExtra("patientId", String.valueOf(selectedPatientId));
                        intent.putExtra("patientName", selectedPatientName);
                        startActivity(intent);
                    }
                };

        //Adapter for the Add Patient button
        AdapterView.OnClickListener addPatient =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(getApplicationContext(), EnterPatientActivity.class);
                        startActivity(intent);
                    }
                };

        //Adapter for the Patient Information button
        AdapterView.OnClickListener displayPatient =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(getApplicationContext(), DisplayPatientActivity.class);
                        intent.putExtra("patientId", String.valueOf(selectedPatientId));
                        startActivity(intent);
                    }
                };

        //Adapter for the Logout button
        AdapterView.OnClickListener logoutAdapter =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                };


        //Add listener to Enter Test button
        Button btnEnterTest = (Button) findViewById(R.id.btnEnterTest);
        btnEnterTest.setOnClickListener(enterTest);

        //Add listener to Display Test button
        Button btnDisplayTest = (Button) findViewById(R.id.btnDisplayTest);
        btnDisplayTest.setOnClickListener(displayTest);

        //Add listener to Enter Patient button
        btnAddPatient.setOnClickListener(addPatient);

        //Add listener to Patient Information button
        btnDisplayPatient.setOnClickListener(displayPatient);

        //Add listener to Logout button
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(logoutAdapter);

    }

    @Override
    public void onBackPressed(){
        //Block the user from going to the login activity by pressing the back button
    }
}
