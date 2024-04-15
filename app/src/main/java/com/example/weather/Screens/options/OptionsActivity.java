package com.example.weather.Screens.options;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.example.weather.R;
import com.example.weather.Screens.main.WeatherActivity;

import java.util.Locale;
import java.util.Objects;

public class OptionsActivity extends AppCompatActivity {

    private RadioButton radioButtonCelsius;
    private RadioButton radioButtonFahrenheit;
    private RadioButton radioButtonMeterPerSec;
    private RadioButton radioButtonMilePerHour;
    private RadioButton radioButtonMmHg;
    private RadioButton radioButtonMBar;
    private RadioButton radioButtonIconSet01;
    private RadioButton radioButtonIconSet02;
    private RadioButton radioButtonIconSet03;
    private RadioButton radioButtonIconSet04;
    private RadioButton radioButtonViLanguage;
    private RadioButton radioButtonEnLanguage;
    private RadioButton radioButtonEsLanguage;
    SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ConstraintLayout constraintLayoutOptions = findViewById(R.id.constraintLayoutOptions);

        TextView textViewOptionsLabel = findViewById(R.id.textViewOptionsLabel);
        TextView textViewUnitSettings = findViewById(R.id.textViewUnitSettings);
        TextView textViewColors = findViewById(R.id.textViewColors);
        TextView textViewTemperatureUnitLabel = findViewById(R.id.textViewTemperatureUnitLabel);
        TextView textViewWindUnitLabel = findViewById(R.id.textViewWindUnitLabel);
        TextView textViewPressureUnitLabel = findViewById(R.id.textViewPressureUnitLabel);
        TextView textViewLanguageLabel = findViewById(R.id.textViewLanguageLabel);
        radioButtonCelsius = findViewById(R.id.radioButtonCelsius);
        radioButtonFahrenheit = findViewById(R.id.radioButtonFahrenheit);
        radioButtonMeterPerSec = findViewById(R.id.radioButtonMeterPerSec);
        radioButtonMilePerHour = findViewById(R.id.radioButtonMilePerHour);
        radioButtonMmHg = findViewById(R.id.radioButtonMmHg);
        radioButtonMBar = findViewById(R.id.radioButtonMBar);
        radioButtonIconSet01 = findViewById(R.id.radioButtonIconSet01);
        radioButtonIconSet02 = findViewById(R.id.radioButtonIconSet02);
        radioButtonIconSet03 = findViewById(R.id.radioButtonIconSet03);
        radioButtonIconSet04 = findViewById(R.id.radioButtonIconSet04);
        radioButtonViLanguage = findViewById(R.id.radioButtonViLanguage);
        radioButtonEnLanguage = findViewById(R.id.radioButtonEnLanguage);
        radioButtonEsLanguage = findViewById(R.id.radioButtonEsLanguage);


        TextView textViewIconSetLabel = findViewById(R.id.textViewIconSetLabel);
        ConstraintLayout constraintLayoutIconSets = findViewById(R.id.constraintLayoutIconSets);
        ConstraintLayout constraintLayoutLanguageSets = findViewById(R.id.constraintLayoutLanguageSets);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int firstColor = preferences.getInt("firstColor", ContextCompat.getColor(getApplicationContext(), R.color.blue1));
        int secondColor = preferences.getInt("secondColor", ContextCompat.getColor(getApplicationContext(), R.color.blue2));
        if (Objects.equals(preferences.getString("celsiusOrFahrenheit", "C"), "C")) {
            radioButtonCelsius.setChecked(true);
            radioButtonFahrenheit.setChecked(false);
        } else {
            radioButtonCelsius.setChecked(false);
            radioButtonFahrenheit.setChecked(true);
        }
        if (Objects.equals(preferences.getString("windSpeedUnit", "m/s"), "m/s")) {
            radioButtonMeterPerSec.setChecked(true);
            radioButtonMilePerHour.setChecked(false);
        } else {
            radioButtonMeterPerSec.setChecked(false);
            radioButtonMilePerHour.setChecked(true);
        }
        if (Objects.equals(preferences.getString("pressureUnit", "mm Hg"), "mm Hg")) {
            radioButtonMmHg.setChecked(true);
            radioButtonMBar.setChecked(false);
        } else {
            radioButtonMmHg.setChecked(false);
            radioButtonMBar.setChecked(true);
        }
        if (Objects.equals(preferences.getInt("iconSet", 4), 1)) {
            radioButtonIconSet01.setChecked(true);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (Objects.equals(preferences.getInt("iconSet", 4), 2)) {
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(true);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (Objects.equals(preferences.getInt("iconSet", 4), 3)) {
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(true);
            radioButtonIconSet04.setChecked(false);
        } else if (Objects.equals(preferences.getInt("iconSet", 4), 4)) {
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(true);
        }
        if (Objects.equals(preferences.getString("language", "en"), "vi")) {
            radioButtonViLanguage.setChecked(true);
            radioButtonEnLanguage.setChecked(false);
            radioButtonEsLanguage.setChecked(false);
        } else if (Objects.equals(preferences.getString("language", "en"), "en")) {
            radioButtonViLanguage.setChecked(false);
            radioButtonEnLanguage.setChecked(true);
            radioButtonEsLanguage.setChecked(false);
        } else if (Objects.equals(preferences.getString("language", "en"), "es")) {
            radioButtonViLanguage.setChecked(false);
            radioButtonEnLanguage.setChecked(false);
            radioButtonEsLanguage.setChecked(true);
        }


        constraintLayoutOptions.setBackgroundColor(firstColor);
        textViewOptionsLabel.setBackgroundColor(secondColor);
        textViewUnitSettings.setBackgroundColor(secondColor);
        textViewColors.setBackgroundColor(secondColor);
        textViewTemperatureUnitLabel.setBackgroundColor(secondColor);
        textViewWindUnitLabel.setBackgroundColor(secondColor);
        textViewPressureUnitLabel.setBackgroundColor(secondColor);
        textViewLanguageLabel.setBackgroundColor(secondColor);
        radioButtonCelsius.setBackgroundColor(secondColor);
        radioButtonFahrenheit.setBackgroundColor(secondColor);
        radioButtonMeterPerSec.setBackgroundColor(secondColor);
        radioButtonMilePerHour.setBackgroundColor(secondColor);
        radioButtonMBar.setBackgroundColor(secondColor);
        radioButtonMmHg.setBackgroundColor(secondColor);
        textViewIconSetLabel.setBackgroundColor(secondColor);
        constraintLayoutIconSets.setBackgroundColor(secondColor);
        constraintLayoutLanguageSets.setBackgroundColor(secondColor);
        radioButtonIconSet01.setBackgroundColor(secondColor);
        radioButtonIconSet02.setBackgroundColor(secondColor);
        radioButtonIconSet03.setBackgroundColor(secondColor);
        radioButtonIconSet04.setBackgroundColor(secondColor);
        radioButtonViLanguage.setBackgroundColor(secondColor);
        radioButtonEnLanguage.setBackgroundColor(secondColor);
        radioButtonEsLanguage.setBackgroundColor(secondColor);
    }

    public void onClickTextViewWeatherActivityLabel(View view) {
        finish();
    }



    public void onClickColors(View view) {
        Intent intentColors = new Intent(this, ChooseBackgroundActivity.class);
        finish();
        startActivity(intentColors);
    }

    public void onClickTempUnitChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonCelsius) {
            preferences.edit().putString("celsiusOrFahrenheit", "C").apply();
            radioButtonCelsius.setChecked(true);
            radioButtonFahrenheit.setChecked(false);
        } else if (id == R.id.radioButtonFahrenheit) {
            preferences.edit().putString("celsiusOrFahrenheit", "F").apply();
            radioButtonCelsius.setChecked(false);
            radioButtonFahrenheit.setChecked(true);
        }
    }

    public void onClickWindUnitChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonMeterPerSec) {
            preferences.edit().putString("windSpeedUnit", "m/s").apply();
            radioButtonMeterPerSec.setChecked(true);
            radioButtonMilePerHour.setChecked(false);
        } else if (id == R.id.radioButtonMilePerHour) {
            preferences.edit().putString("windSpeedUnit", "mph").apply();
            radioButtonMeterPerSec.setChecked(false);
            radioButtonMilePerHour.setChecked(true);
        }
    }

    public void onClickPressureUnitChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonMmHg) {
            preferences.edit().putString("pressureUnit", "mm Hg").apply();
            radioButtonMmHg.setChecked(true);
            radioButtonMBar.setChecked(false);
        } else if (id == R.id.radioButtonMBar) {
            preferences.edit().putString("pressureUnit", "mBar").apply();
            radioButtonMmHg.setChecked(false);
            radioButtonMBar.setChecked(true);
        }
    }

    public void onClickIconSetChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonIconSet01) {
            preferences.edit().putInt("iconSet", 1).apply();
            radioButtonIconSet01.setChecked(true);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (id == R.id.radioButtonIconSet02) {
            preferences.edit().putInt("iconSet", 2).apply();
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(true);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (id == R.id.radioButtonIconSet03) {
            preferences.edit().putInt("iconSet", 3).apply();
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(true);
            radioButtonIconSet04.setChecked(false);
        } else if (id == R.id.radioButtonIconSet04) {
            preferences.edit().putInt("iconSet", 4).apply();
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(true);
        }
    }
    public void onClickLanguageSetChange(View view){
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonViLanguage) {
            preferences.edit().putString("language", "vi").apply();
            radioButtonViLanguage.setChecked(true);
            radioButtonEnLanguage.setChecked(false);
            radioButtonEsLanguage.setChecked(false);
            setLocale("vi");
        } else if (id == R.id.radioButtonEnLanguage) {
            preferences.edit().putString("language", "en").apply();
            radioButtonViLanguage.setChecked(false);
            radioButtonEnLanguage.setChecked(true);
            radioButtonEsLanguage.setChecked(false);
            setLocale("en");
        } else if (id == R.id.radioButtonEsLanguage) {
            preferences.edit().putString("language", "es").apply();
            radioButtonViLanguage.setChecked(false);
            radioButtonEnLanguage.setChecked(false);
            radioButtonEsLanguage.setChecked(true);
            setLocale("es");
        }
    }
    public void setLocale(String langCode) {
        Locale myLocale = new Locale(langCode);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        Intent refresh = new Intent(this, WeatherActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
        finish();
        startActivity(refresh);
    }
}