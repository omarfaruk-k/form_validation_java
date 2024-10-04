package com.ofk.form_validation_java;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText et_name, et_mbl, et_email, et_pass, con_pass;
    private Spinner spinner;
    private String name, phone, email, pass, conf_pass, cont;
    private Button signup;

    Pattern namePattern = Pattern.compile("[a-z A-Z._]+");
    Pattern numpattern = Pattern.compile("^01(3|4|5|6|7|8|9)\\d{8}");
    Pattern emailpattern = Pattern.compile("[a-z\\d_.-]+@(gmail\\.com|yahoo\\.com|hotmail\\.com|lus.ac.bd)");
    Pattern uppercase = Pattern.compile("(?=.*[A-Z])");
    Pattern lowercase = Pattern.compile("(?=.*[a-z])");
    Pattern digit = Pattern.compile("(?=.*\\d)");
    Pattern specialChar = Pattern.compile("(?=.*[@$!%*?&])");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_name = findViewById(R.id.et_name);
        et_mbl = findViewById(R.id.et_mbl);
        et_email = findViewById(R.id.et_signupEmail);
        et_pass = findViewById(R.id.et_signupPass);
        con_pass = findViewById(R.id.et_signupConfirmPass);
        spinner = findViewById(R.id.spinner);
        signup = findViewById(R.id.btn_signupSignup);


        String[] items = new String[]{"Select affiliation", "Student", "Teacher"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cont = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        signup.setOnClickListener(v -> {
            name = et_name.getText().toString().trim();
            phone = et_mbl.getText().toString().trim();
            email = et_email.getText().toString().trim();
            pass = et_pass.getText().toString().trim();
            conf_pass = con_pass.getText().toString().trim();


            if (validateField(et_name, name, namePattern, "Name can only be Alphabet") &&
                    validateField(et_mbl, phone, numpattern, "Invalid Number") &&
                    validateField(et_email, email, emailpattern, "Please Enter a Valid Email Address") &&
                    validatePassword(pass, conf_pass)) {

                if (Objects.equals(cont, "Select affiliation")) {
                    Toast.makeText(getApplicationContext(), "Please Select affiliation", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private boolean validateField(EditText field, String value, Pattern pattern, String errorMessage) {
        if (value.isEmpty()) {
            field.setError("Empty!");
            field.requestFocus();
            return false;
        } else if (!pattern.matcher(value).matches()) {
            field.setError(errorMessage);
            field.requestFocus();
            return false;
        }
        return true;
    }


    private boolean validatePassword(String pass, String confPass) {
        if (pass.isEmpty()) {
            et_pass.setError("Empty!!");
            et_pass.requestFocus();
            return false;
        } else if (pass.length() < 6) {
            et_pass.setError("Password must contain at least 6 characters!");
            et_pass.requestFocus();
            return false;
        } else if (!uppercase.matcher(pass).find()) {
            et_pass.setError("Password must contain at least one uppercase letter!");
            et_pass.requestFocus();
            return false;
        } else if (!lowercase.matcher(pass).find()) {
            et_pass.setError("Password must contain at least one lowercase letter!");
            et_pass.requestFocus();
            return false;
        } else if (!digit.matcher(pass).find()) {
            et_pass.setError("Password must contain at least one digit!");
            et_pass.requestFocus();
            return false;
        } else if (!specialChar.matcher(pass).find()) {
            et_pass.setError("Password must contain at least one special character!");
            et_pass.requestFocus();
            return false;
        } else if (!pass.equals(confPass)) {
            con_pass.setError("Passwords do not match");
            con_pass.requestFocus();
            return false;
        }
        return true;
    }
}
