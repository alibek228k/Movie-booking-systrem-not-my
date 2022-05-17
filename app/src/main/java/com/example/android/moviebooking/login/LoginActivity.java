package com.example.android.moviebooking.login;

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
import com.example.android.moviebooking.home.MainActivity;
import com.example.android.moviebooking.register.RegistrationActivity;
import com.example.cinemaapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextEmail,
            textInputEditTextPassword;
    private MaterialButton loginButton;
    private TextView signUpTextView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.singUpText);
        progress = findViewById(R.id.progress);

        loginButton.setOnClickListener(view -> {

            String email, password;

            email = String.valueOf(textInputEditTextEmail.getText());
            password = String.valueOf(textInputEditTextPassword.getText());

            if ( !email.equals("") && !password.equals("")){
                progress.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[2];
                    field[0] = "email";
                    field[1] = "password";
                    //Creating array for data
                    String[] data = new String[4];
                    data[0] = email;
                    data[1] = password;
                    PutData putData = new PutData("https://devscinema.herokuapp.com/loginAndroid.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) { 
                            progress.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Login Success")){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
                            Log.i("PutData", result);
                        }
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_LONG).show();
            }
        });

        signUpTextView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}