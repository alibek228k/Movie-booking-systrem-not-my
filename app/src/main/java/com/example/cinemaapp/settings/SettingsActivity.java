package com.example.cinemaapp.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.RadioButton;

import com.example.cinemaapp.R;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton russianRadioButton;
    private RadioButton englishRadioButton;
    private RadioButton kazakhRadioButton;
    private RadioButton lightRadioButton;
    private RadioButton nightRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        russianRadioButton = findViewById(R.id.russianRadioButton);
        englishRadioButton = findViewById(R.id.englishRadioButton);
        kazakhRadioButton = findViewById(R.id.kazakhRadioButton);
        lightRadioButton = findViewById(R.id.lightTheme);
        nightRadioButton = findViewById(R.id.nightTheme);
        setupLanguage();
        setupTheme();
    }

    private void setupLanguage(){
        russianRadioButton.setOnClickListener(view -> {
            String languageCode = "ru";
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Resources resources = getApplicationContext().getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        });
        englishRadioButton.setOnClickListener(view -> {
            String languageCode = "en";
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Resources resources = getApplicationContext().getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        });
        kazakhRadioButton.setOnClickListener(view -> {
            String languageCode = "kk";
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Resources resources = getApplicationContext().getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        });
    }

    private void setupTheme(){
        lightRadioButton.setOnClickListener(view -> {
            String languageCode = "ru";
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Resources resources = getApplicationContext().getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        });
        nightRadioButton.setOnClickListener(view -> {
            setTheme(android.R.style.Theme);
        });
    }

}