package com.example.weather.Screens.options;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.weather.R;
import com.example.weather.Screens.main.WeatherActivity;

public class ChooseBackgroundActivity extends AppCompatActivity {
    private static int firstColor;
    private static int secondColor;
    private TextView textViewChooseBackgroundLabel;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        firstColor = preferences.getInt("firstColor", ContextCompat.getColor(getApplicationContext(), R.color.blue1));
        secondColor = preferences.getInt("secondColor", ContextCompat.getColor(getApplicationContext(), R.color.blue2));
        textViewChooseBackgroundLabel = findViewById(R.id.textViewChooseBackgroundLabel);
        textViewChooseBackgroundLabel.setBackgroundColor(secondColor);
    }


    @SuppressLint("NonConstantResourceId")
    public void onClickCardView(View view) {
        CardView cardView = (CardView)view;
        int id = cardView.getId();
        switch (id){
            case R.id.cardView1:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.blue1);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.blue2);
                break;
            case R.id.cardView2:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.blue4);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.blue5);
                break;
            case R.id.cardView3:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.green1);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.green2);
                break;
            case R.id.cardView4:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.green4);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.green5);
                break;
            case R.id.cardView5:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.orange1);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.orange2);
                break;
            case R.id.cardView6:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.orange4);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.orange5);
                break;
            case R.id.cardView7:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.red1);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.red2);
                break;
            case R.id.cardView8:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.red4);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.red5);
                break;
            case R.id.cardView9:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.grey1);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.grey2);
                break;
            case R.id.cardView10:
                firstColor = ContextCompat.getColor(getApplicationContext(), R.color.grey4);
                secondColor = ContextCompat.getColor(getApplicationContext(), R.color.grey5);
                break;
        }
        textViewChooseBackgroundLabel.setBackgroundColor(secondColor);
        preferences.edit().putInt("firstColor", firstColor).apply();
        preferences.edit().putInt("secondColor", secondColor).apply();
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
