package com.example.weather.Utils;

import android.content.SharedPreferences;

import com.example.weather.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {

    public static String dateTime(String dateTxt, String format,SharedPreferences sharedPreferences) {
        String dateStr;
        DateFormat dfStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag(sharedPreferences.getString("language", "en")));
        try {
            Date date = dfStr.parse(dateTxt);
            DateFormat df = new SimpleDateFormat(format, Locale.forLanguageTag(sharedPreferences.getString("language", "en")));
            assert date != null;
            dateStr = df.format(date);
        } catch (ParseException e) {
            dateStr = "error";
        }
        return dateStr;
    }


    public static double celsiusToFahrenheit(double temperatureC) {
        return 1.8 * temperatureC + 32;
    }

    public static int getIconId(String icon, int iconSet) {
        if (iconSet == 1) {
            switch (icon) {
                case "01d":
                    return R.drawable.f01d;
                case "01n":
                    return R.drawable.f01n;
                case "02d":
                    return R.drawable.f02d;
                case "02n":
                    return R.drawable.f02n;
                case "03d":
                    return R.drawable.f03d;
                case "03n":
                    return R.drawable.f03n;
                case "04d":
                    return R.drawable.f04d;
                case "04n":
                    return R.drawable.f04n;
                case "09d":
                    return R.drawable.f09d;
                case "09n":
                    return R.drawable.f09n;
                case "10d":
                    return R.drawable.f10d;
                case "10n":
                    return R.drawable.f10n;
                case "11d":
                    return R.drawable.f11d;
                case "11n":
                    return R.drawable.f11n;
                case "13d":
                    return R.drawable.f13d;
                case "13n":
                    return R.drawable.f13n;
                case "50d":
                    return R.drawable.f50d;
                case "50n":
                    return R.drawable.f50n;
            }
            return R.drawable.f01d;
        }
        if (iconSet == 2) {
            switch (icon) {
                case "01d":
                    return R.drawable.s01d;
                case "01n":
                    return R.drawable.s01d;
                case "02d":
                    return R.drawable.s02d;
                case "02n":
                    return R.drawable.s02d;
                case "03d":
                    return R.drawable.s03d;
                case "03n":
                    return R.drawable.s03d;
                case "04d":
                    return R.drawable.s04d;
                case "04n":
                    return R.drawable.s04d;
                case "09d":
                    return R.drawable.s09d;
                case "09n":
                    return R.drawable.s09d;
                case "10d":
                    return R.drawable.s10d;
                case "10n":
                    return R.drawable.s10d;
                case "11d":
                    return R.drawable.s11d;
                case "11n":
                    return R.drawable.s11d;
                case "13d":
                    return R.drawable.s13d;
                case "13n":
                    return R.drawable.s13d;
                case "50d":
                    return R.drawable.s50d;
                case "50n":
                    return R.drawable.s50d;
            }
            return R.drawable.s01d;
        }
        if (iconSet == 3) {
            switch (icon) {
                case "01d":
                    return R.drawable.r01d;
                case "01n":
                    return R.drawable.r01d;
                case "02d":
                    return R.drawable.r02d;
                case "02n":
                    return R.drawable.r02d;
                case "03d":
                    return R.drawable.r03d;
                case "03n":
                    return R.drawable.r03d;
                case "04d":
                    return R.drawable.r04d;
                case "04n":
                    return R.drawable.r04d;
                case "09d":
                    return R.drawable.r09d;
                case "09n":
                    return R.drawable.r09d;
                case "10d":
                    return R.drawable.r10d;
                case "10n":
                    return R.drawable.r10d;
                case "11d":
                    return R.drawable.r11d;
                case "11n":
                    return R.drawable.r11d;
                case "13d":
                    return R.drawable.r13d;
                case "13n":
                    return R.drawable.r13d;
                case "50d":
                    return R.drawable.r50d;
                case "50n":
                    return R.drawable.r50d;
            }
        return R.drawable.r01d;
        }
        if (iconSet == 4) {
            switch (icon) {
                case "01d":
                    return R.drawable.g01d;
                case "01n":
                    return R.drawable.g01d;
                case "02d":
                    return R.drawable.g02d;
                case "02n":
                    return R.drawable.g02d;
                case "03d":
                    return R.drawable.g03d;
                case "03n":
                    return R.drawable.g03d;
                case "04d":
                    return R.drawable.g04d;
                case "04n":
                    return R.drawable.g04d;
                case "09d":
                    return R.drawable.g09d;
                case "09n":
                    return R.drawable.g09d;
                case "10d":
                    return R.drawable.g10d;
                case "10n":
                    return R.drawable.g10d;
                case "11d":
                    return R.drawable.g11d;
                case "11n":
                    return R.drawable.g11d;
                case "13d":
                    return R.drawable.g13d;
                case "13n":
                    return R.drawable.g13d;
                case "50d":
                    return R.drawable.g50d;
                case "50n":
                    return R.drawable.g50d;
            }
        }
        return R.drawable.g01d;
    }
}
