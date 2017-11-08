package aline.a2101036808;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import aline.a2101036808.dataModel.Patient;
import aline.a2101036808.dataModel.Test;
import aline.a2101036808.helper.ValidateHelper;

public class EnterTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_test);

        //Set header with the name of the patient whose test is being added
        String PATIENTNAME = getIntent().getStringExtra("patientName");
        ((TextView) findViewById(R.id.txtTestPatientName)).setText(PATIENTNAME);

        //Adapter for the Cancel button
        AdapterView.OnClickListener cancel =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    }
                };
        //Add listener to Cancel button
        Button btnCancel = (Button) findViewById(R.id.btnCancelTest);
        btnCancel.setOnClickListener(cancel);

        //Listener to get the information from the form and redirect the nurse to the home page
        AdapterView.OnClickListener addTestClickListener =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Test newTest = new Test();

                        //Get all the entries from the form
                        EditText bplTxt = (EditText) findViewById(R.id.txtBxBpl);
                        EditText bphTxt = (EditText) findViewById(R.id.txtBxBph);
                        EditText tempTxt = (EditText) findViewById(R.id.txtBxTemp);
                        EditText glucoseTxt = (EditText) findViewById(R.id.txtBxGlucose);
                        EditText cholesterolTxt = (EditText) findViewById(R.id.txtBxCholesterol);

                        //Get the ID of the patient
                        String PATIENTID = getIntent().getStringExtra("patientId");

                        boolean validEntries = true;
                        //Validate all fields
                        if(TextUtils.isEmpty((bplTxt.getText().toString()).trim()) || !ValidateHelper.isValidInteger((bplTxt.getText().toString()))){
                            bplTxt.setError("Please enter an numeric valid BPL.");
                            validEntries = false;
                        }
                        if(TextUtils.isEmpty((bphTxt.getText().toString()).trim())|| !ValidateHelper.isValidInteger((bphTxt.getText().toString()))){
                            bphTxt.setError("Please enter an numeric valid BPH.");
                            validEntries = false;
                        }
                        if(TextUtils.isEmpty((tempTxt.getText().toString()).trim())|| !ValidateHelper.isValidDouble((tempTxt.getText().toString()))){
                            tempTxt.setError("Please enter an numeric valid temperature.");
                            validEntries = false;
                        }
                        if(TextUtils.isEmpty((glucoseTxt.getText().toString()).trim()) || !ValidateHelper.isValidDouble((glucoseTxt.getText().toString()))){
                            glucoseTxt.setError("Please enter an numeric valid glucose level.");
                            validEntries = false;
                        }
                        if(TextUtils.isEmpty((cholesterolTxt.getText().toString()).trim()) || !ValidateHelper.isValidDouble((cholesterolTxt.getText().toString()))){
                            cholesterolTxt.setError("Please enter an numeric valid cholesterol level.");
                            validEntries = false;
                        }

                        // If all entries are valid, add patient to the database
                        if(validEntries){
                            //set attributes of the Test object
                            newTest.setBpl(Integer.valueOf(bplTxt.getText().toString()));
                            newTest.setBph(Integer.valueOf(bphTxt.getText().toString()));
                            newTest.setTemperature(Double.valueOf(tempTxt.getText().toString()));
                            newTest.setGlucose(Double.valueOf(glucoseTxt.getText().toString()));
                            newTest.setCholesterol(Double.valueOf(cholesterolTxt.getText().toString()));
                            newTest.setPatientId(Integer.valueOf(PATIENTID));

                            //Add patient to the database
                            MedicalDatabaseHelper db = new MedicalDatabaseHelper(getApplicationContext());
                            db.insertTest(newTest);
                            Toast.makeText(getApplicationContext(), "Test was added!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),
                                    DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                };

        // Add listener to the button
        Button btnAddTest = (Button) findViewById(R.id.btnAddTest);
        btnAddTest.setOnClickListener(addTestClickListener);

    }
}
