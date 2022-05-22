package com.example.cinemaapp.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cinemaapp.R;
import com.example.cinemaapp.login.LoginActivity;
import com.example.cinemaapp.validator.EditTextValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextFirstName,
            textInputEditTextLastName,
            textInputEditTextEmail,
            textInputEditTextPassword;
    private MaterialButton registrationButton;
    private TextView loginTextView;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textInputEditTextFirstName = findViewById(R.id.firstName);
        textInputEditTextLastName = findViewById(R.id.lastName);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        registrationButton = findViewById(R.id.buttonSignUp);
        loginTextView = findViewById(R.id.loginText);
        progress = findViewById(R.id.progress);
        setupPasswordValidator();
        setupEmailValidator();

        registrationButton.setOnClickListener(view -> {

            String firstName, lastName, email, password;

            firstName = String.valueOf(textInputEditTextFirstName.getText());
            lastName = String.valueOf(textInputEditTextLastName.getText());
            email = String.valueOf(textInputEditTextEmail.getText());
            password = String.valueOf(textInputEditTextPassword.getText());

            if (!firstName.equals("") && !lastName.equals("") && !email.equals("") && !password.equals("")){
                progress.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[4];
                        field[0] = "first_name";
                        field[1] = "last_name";
                        field[2] = "email";
                        field[3] = "password";
                        //Creating array for data
                        String[] data = new String[4];
                        data[0] = firstName;
                        data[1] = lastName;
                        data[2] = email;
                        data[3] = password;
                        PutData putData = new PutData("https://devscinema.herokuapp.com/signupAndroid.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progress.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if (result.equals("Sign Up Success")){
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                                Log.i("PutData", result);
                            }
                        }
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_LONG).show();
            }
        });

        loginTextView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupPasswordValidator() {
        textInputEditTextPassword.addTextChangedListener(new EditTextValidator(textInputEditTextPassword) {
            @Override
            public void validate(TextInputEditText editText, String text) {
                String regex = "^(?=.{8,}\\$)(?=.*[a-z])(?=.*\\\\W).*\\$";
                String email = textInputEditTextPassword.getText().toString();

                if (email.trim().isEmpty()) {
                    textInputEditTextPassword.setError("Password can not be blanc!");
                } else if (email.matches(regex)){
                    textInputEditTextPassword.setError(null);
                }else{
                    textInputEditTextPassword.setError("Password should contain: at least 8 symbols, 1 special symbol");
                }
            }
        });
    }

    private void setupEmailValidator() {
        textInputEditTextEmail.addTextChangedListener(new EditTextValidator(textInputEditTextEmail) {
            @Override
            public void validate(TextInputEditText editText, String text) {
                String regex = "^(.+)@(.+)$";
                String email = textInputEditTextEmail.getText().toString();

                if (email.trim().isEmpty()) {
                    textInputEditTextEmail.setError("Email can not be blanc!");
                } else if (email.matches(regex)){
                    textInputEditTextEmail.setError(null);
                }else{
                    textInputEditTextEmail.setError("Incorrect format");
                }
            }
        });
    }




}