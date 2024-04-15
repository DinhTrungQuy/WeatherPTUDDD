package com.example.weather.Api;

import com.example.weather.Models.Weather5days;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET ("forecast?")
    Observable<Weather5days> getWeather5days(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid);

    @GET ("forecast?")
    Observable<Weather5days> getWeather5daysCity(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid);
}
