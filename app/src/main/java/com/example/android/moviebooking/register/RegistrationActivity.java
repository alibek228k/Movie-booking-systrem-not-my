package com.example.android.moviebooking.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.moviebooking.R;
import com.example.android.moviebooking.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextFirstName,
            textInputEditTextLastName,
            textInputEditTextEmail,
            textInputEditTextPassword;
    private Button registrationButton;
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
                        PutData putData = new PutData("http://192.168.1.65/LoginRegister/signup.php", "POST", field, data);
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
}