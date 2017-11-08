package aline.a2101036808.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aline.a2101036808.R;
import aline.a2101036808.activity.DashboardActivity;
import aline.a2101036808.dataModel.Test;
import aline.a2101036808.database.MedicalDatabaseHelper;

public class DisplayTestActivity extends Activity {
    List<Test> tests;
    TextView testNo, bpl, bph, temperature, glucose, cholesterol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_test);

        String PATIENTID = getIntent().getStringExtra("patientId");
        String PATIENTNAME = getIntent().getStringExtra("patientName");

        //Get references to all ui elements
        TextView patientName = (TextView) findViewById(R.id.txtPatientName);
        testNo = (TextView) findViewById(R.id.txtTestId);
        bpl = (TextView) findViewById(R.id.txtBpl);
        bph = (TextView)findViewById(R.id.txtBph);
        temperature = (TextView)findViewById(R.id.txtTemperature);
        glucose = (TextView)findViewById(R.id.txtGlucose);
        cholesterol = (TextView)findViewById(R.id.txtCholesterol);
        Spinner testsSpinner = (Spinner)findViewById(R.id.testsSpinner);

        patientName.setText("Patient: " + PATIENTNAME);

        //Get all tests for the selected patient
        MedicalDatabaseHelper db = new MedicalDatabaseHelper(getApplicationContext());
        tests = db.getTestsByPatientId(Integer.valueOf(PATIENTID));

        //Set testsSpinner with the tests for the selected patient
        final List<String> testNumbers = new ArrayList<String>();
        for(Test aT : tests){
            testNumbers.add("Test #: " + String.valueOf(aT.getTestId()));
        }

        final ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, testNumbers);

        testsSpinner.setAdapter(testAdapter);

        //Show information about test selected on the testsSpinner
        testsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Test selectedTest =  tests.get(i);
                //Set textviews to show the information about the selected test
                testNo.setText(String.valueOf(selectedTest.getTestId()));
                bpl.setText(String.valueOf(selectedTest.getBpl()));
                bph.setText(String.valueOf(selectedTest.getBph()));
                temperature.setText(String.valueOf(selectedTest.getTemperature()));
                glucose.setText(String.valueOf(selectedTest.getGlucose()));
                cholesterol.setText(String.valueOf(selectedTest.getCholesterol()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Adapter for the Back button
        AdapterView.OnClickListener back =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    }
                };
        //Add listener to Back button
        Button btnCancel = (Button) findViewById(R.id.btnDisplayTestBack);
        btnCancel.setOnClickListener(back);

    }
}
