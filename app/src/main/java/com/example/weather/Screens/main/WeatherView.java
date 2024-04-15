package com.example.weather.Screens.main;

import com.example.weather.Models.Weather5days;

public interface WeatherView {
    void showData(Weather5days weather5days);
    void showError();

}
