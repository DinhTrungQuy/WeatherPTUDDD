package com.example.weather.Screens.main;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.weather.Api.ApiFactory;
import com.example.weather.Api.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter {
    SharedPreferences sharedPreferences;
    private final String units = "metric";
    private final String lang;
    private final String appid = "7ee56f5dd8b2f7a248bc24820840f27b";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFactory apiFactory = ApiFactory.getInstance();
    ApiService apiService = apiFactory.getApiService();
    private final WeatherView weatherView;

    public WeatherPresenter(WeatherView weatherView,String lang) {
        this.weatherView = weatherView;
        this.lang = lang;

    }

    public void getWeather() {
        Disposable disposable = apiService.getWeather5days(WeatherActivity.getLat(), WeatherActivity.getLon(), units, lang, appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weather5days -> {
                    weatherView.showData(weather5days);
                    Log.d("WeatherPresenter", "getWeather: " + weather5days);
                }, throwable -> {
                    weatherView.showError();
                    Log.d("WeatherPresenter", "getWeather: " + throwable);
                });
        compositeDisposable.add(disposable);
    }

    public void getWeatherCity() {
        Disposable disposable = apiService.getWeather5daysCity(WeatherActivity.getCityName(), units, lang, appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weather5days -> {
                    weatherView.showData(weather5days);
                    Log.d("WeatherPresenter", "getWeatherCity: " + weather5days);
                }, throwable -> {
                    weatherView.showError();
                    Log.d("WeatherPresenter", "getWeatherCity: " + throwable);
                });
        compositeDisposable.add(disposable);
    }


    public void disposeDisposable() {
        compositeDisposable.dispose();
    }
}
