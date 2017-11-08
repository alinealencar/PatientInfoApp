package aline.a2101036808;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hide login error message
        ((TextView) findViewById(R.id.txtLoginError)).setVisibility(View.INVISIBLE);

        //Adapter for the Login button
        AdapterView.OnClickListener loginAdapter =
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        //Get the username and password entered in the login form
                        String username = ((TextView) findViewById(R.id.txtBxUsername)).getText().toString();
                        String password = ((TextView) findViewById(R.id.txtBxPassword)).getText().toString();

                        //Check the login credentials (0 = failed, 1 = doctor, 2 = nurse)
                        MedicalDatabaseHelper db = new MedicalDatabaseHelper(getApplicationContext());
                        int login = db.authenticateUser(username, password);

                        if(login == 0){
                            //Show error message
                            ((TextView) findViewById(R.id.txtLoginError)).setVisibility(View.VISIBLE);
                        }
                        else if(login == 1) {
                            //Redirect user to dashboard
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            intent.putExtra("isDoctor", true); //User is a doctor
                            startActivity(intent);
                        }
                        else if(login == 2){
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            intent.putExtra("isDoctor", false); //User is a nurse
                            startActivity(intent);
                        }

                    }
                };
        //Add listener to Login button
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(loginAdapter);
    }
}
