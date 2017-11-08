package aline.a2101036808.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import aline.a2101036808.R;
import aline.a2101036808.activity.DashboardActivity;
import aline.a2101036808.dataModel.Patient;
import aline.a2101036808.database.MedicalDatabaseHelper;

public class DisplayPatientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient);

        String PATIENTID = getIntent().getStringExtra("patientId");

        //Get Patient object by the patientId
        MedicalDatabaseHelper db = new MedicalDatabaseHelper(getApplicationContext());
        Patient selectedPatient = db.getPatientById(Integer.valueOf(PATIENTID));

        //Get references to all UI elements and set their values
        ((TextView)findViewById(R.id.txtPatientName)).setText(selectedPatient.getfName() + " " + selectedPatient.getlName());
        ((TextView)findViewById(R.id.txtPatientDept)).setText(selectedPatient.getDept());
        ((TextView)findViewById(R.id.txtPatientDoctor)).setText(db.getDoctorNameById(selectedPatient.getDoctorId()));
        ((TextView)findViewById(R.id.txtPatientRoom)).setText(selectedPatient.getRoom());

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
        Button btnCancel = (Button) findViewById(R.id.btnDisplayPatientBack);
        btnCancel.setOnClickListener(back);
    }
}
