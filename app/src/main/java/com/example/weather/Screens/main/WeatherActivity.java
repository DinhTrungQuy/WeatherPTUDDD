package com.example.weather.Screens.main;

import static com.example.weather.R.string.location_error_notice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Adapters.WeatherForecastAdapter;
import com.example.weather.Models.Weather5days;
import com.example.weather.R;
import com.example.weather.Screens.options.OptionsActivity;
import com.example.weather.Utils.Converters;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity implements WeatherView {
    private static final String CHANNEL_ID = "channel_weather";
    private Context ctx;
    private static double lat;
    private static double lon;
    private static String cityName;
    private int firstColor;
    private int secondColor;
    private static String celsiusOrFahrenheit;
    int iconSet;

    public static double getLat() {
        return lat;
    }

    public static double getLon() {
        return lon;
    }

    public static String getCityName() {
        return cityName;
    }

    private RecyclerView recyclerViewWeather;
    private WeatherForecastAdapter weatherAdapter;
    private TextView textViewCityName;
    private ImageView imageViewWeatherNow;
    private TextView textViewTemperatureNow;
    private TextView textViewNowPlus6;
    private TextView textViewNowPlus12;
    private TextView textViewNowPlus18;
    private ImageView imageViewWeatherPlus6;
    private ImageView imageViewWeatherPlus12;
    private ImageView imageViewWeatherPlus18;
    private TextView textViewTemperature6;
    private TextView textViewTemperature12;
    private TextView textViewTemperature18;
    private TextView textViewCorF3;
    private SearchView searchViewLocation;
    private WeatherPresenter presenter;
    SharedPreferences preferences;
    private View viewLine1;
    private View viewLine2;
    private ConstraintLayout constraintLayoutMain;
    private TextView textViewWeatherForecastLabel;
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setLocate();
        setContentView(R.layout.activity_main);
        ctx = this;
        textViewCityName = findViewById(R.id.textViewCityName);
        searchViewLocation = findViewById(R.id.searchViewLocation);
        viewLine1 = findViewById(R.id.viewLine1);
        viewLine2 = findViewById(R.id.viewLine2);
        constraintLayoutMain = findViewById(R.id.constraintLayoutMain);
        imageViewWeatherNow = findViewById(R.id.imageViewWeatherNow);
        textViewTemperatureNow = findViewById(R.id.textViewTemperatureNow);
        textViewCorF3 = findViewById(R.id.textViewCorF3);
        textViewNowPlus6 = findViewById(R.id.textViewNowPlus6);
        textViewNowPlus12 = findViewById(R.id.textViewNowPlus12);
        textViewNowPlus18 = findViewById(R.id.textViewNowPlus18);
        imageViewWeatherPlus6 = findViewById(R.id.imageViewWeatherPlus6);
        imageViewWeatherPlus12 = findViewById(R.id.imageViewWeatherPlus12);
        imageViewWeatherPlus18 = findViewById(R.id.imageViewWeatherPlus18);
        textViewTemperature6 = findViewById(R.id.textViewTemperature6);
        textViewTemperature12 = findViewById(R.id.textViewTemperature12);
        textViewTemperature18 = findViewById(R.id.textViewTemperature18);
        recyclerViewWeather = findViewById(R.id.recyclerViewWeather);
        textViewWeatherForecastLabel = findViewById(R.id.textViewWeatherForecastLabel);
        cityName = preferences.getString("cityName", "Hanoi");
        String lang = preferences.getString("language", "en");
        iconSet = preferences.getInt("iconSet", 2);
        celsiusOrFahrenheit = preferences.getString("celsiusOrFahrenheit", "C");
        firstColor = preferences.getInt("firstColor", ContextCompat.getColor(this, R.color.blue1));
        secondColor = preferences.getInt("secondColor", ContextCompat.getColor(this, R.color.blue2));
        lat = Double.parseDouble(preferences.getString("lat", "0"));
        lon = Double.parseDouble(preferences.getString("lon", "0"));
        presenter = new WeatherPresenter(this, lang);
        constraintLayoutMain.setBackgroundColor(firstColor);
        textViewWeatherForecastLabel.setBackgroundColor(secondColor);
        viewLine1.setBackgroundColor(secondColor);
        viewLine2.setBackgroundColor(secondColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        Log.d(TAG, "onLocationResult: " + lat + " " + lon);
                    }
                }
            }
        };



        weatherAdapter = new WeatherForecastAdapter(this, new Weather5days());
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewWeather.setAdapter(weatherAdapter);


        createNotificationChannel();

        presenter.getWeatherCity();




        searchViewLocation.setOnSearchClickListener(v -> {
            textViewCityName.setVisibility(View.GONE);
            recyclerViewWeather.setVisibility(View.GONE);
        });

        searchViewLocation.setOnCloseListener(() -> {
            cityName = preferences.getString("cityName", "Hanoi");
            textViewCityName.setVisibility(View.VISIBLE);
            recyclerViewWeather.setVisibility(View.VISIBLE);
            return false;
        });

        searchViewLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cityName = query.trim();
                searchViewLocation.clearFocus();
                presenter.getWeatherCity();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, location -> {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        preferences.edit().putString("lat", String.valueOf(lat)).apply();
                        preferences.edit().putString("lon", String.valueOf(lon)).apply();
                        Log.d(TAG, "getCurrentLocation: " + lat + " " + lon);
                        presenter.getWeather();

                    } else {
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                        Log.d(TAG, "getCurrentLocation: location is null");
                        showSnackbar(getString(R.string.no_location_detected), ctx);
                    }

                });
    }



    private void setLocate() {
        Locale myLocale = new Locale(preferences.getString("language", "en"));
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
    }

    public void showWeatherNow() {
        celsiusOrFahrenheit = preferences.getString("celsiusOrFahrenheit", "C");
        firstColor = preferences.getInt("firstColor", ContextCompat.getColor(this, R.color.blue1));
        secondColor = preferences.getInt("secondColor", ContextCompat.getColor(this, R.color.blue2));
        iconSet = preferences.getInt("iconSet", 4);
        constraintLayoutMain.setBackgroundColor(firstColor);
        textViewWeatherForecastLabel.setBackgroundColor(secondColor);
        viewLine1.setBackgroundColor(secondColor);
        viewLine2.setBackgroundColor(secondColor);
        int iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(0).getWeather().get(0).getIcon(), iconSet);
        imageViewWeatherNow.setImageResource(iconId);
        double temperatureC = weatherAdapter.getWeatherLists().get(0).getMain().getTemp();
        double temperatureC6 = weatherAdapter.getWeatherLists().get(2).getMain().getTemp();
        double temperatureC12 = weatherAdapter.getWeatherLists().get(4).getMain().getTemp();
        double temperatureC18 = weatherAdapter.getWeatherLists().get(6).getMain().getTemp();
        double temperatureF = Converters.celsiusToFahrenheit(temperatureC);
        double temperatureF6 = Converters.celsiusToFahrenheit(temperatureC6);
        double temperatureF12 = Converters.celsiusToFahrenheit(temperatureC12);
        double temperatureF18 = Converters.celsiusToFahrenheit(temperatureC18);
        if (celsiusOrFahrenheit.equals("C")) {
            textViewCorF3.setText("C");
            textViewTemperatureNow.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC)));
            textViewTemperature6.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC6)));
            textViewTemperature12.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC12)));
            textViewTemperature18.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC18)));
        } else if (celsiusOrFahrenheit.equals("F")) {
            textViewCorF3.setText("F");
            textViewTemperatureNow.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF)));
            textViewTemperature6.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF6)));
            textViewTemperature12.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF12)));
            textViewTemperature18.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF18)));
        }
        iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(2).getWeather().get(0).getIcon(), iconSet);
        imageViewWeatherPlus6.setImageResource(iconId);
        iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(4).getWeather().get(0).getIcon(), iconSet);
        imageViewWeatherPlus12.setImageResource(iconId);
        iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(6).getWeather().get(0).getIcon(), iconSet);
        imageViewWeatherPlus18.setImageResource(iconId);
        int now = LocalDateTime.now().getHour();
        if (now >= 0 && now < 6) {
            textViewNowPlus6.setText(R.string.morning);
            textViewNowPlus12.setText(R.string.day);
            textViewNowPlus18.setText(R.string.evening);
        } else if (now >= 6 && now < 12) {
            textViewNowPlus6.setText(R.string.day);
            textViewNowPlus12.setText(R.string.evening);
            textViewNowPlus18.setText(R.string.night);
        } else if (now >= 12 && now < 18) {
            textViewNowPlus6.setText(R.string.evening);
            textViewNowPlus12.setText(R.string.night);
            textViewNowPlus18.setText(R.string.morning);
        } else if (now >= 18 && now < 24) {
            textViewNowPlus6.setText(R.string.night);
            textViewNowPlus12.setText(R.string.morning);
            textViewNowPlus18.setText(R.string.day);
        }

    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void pushNotification(Weather5days weather5days) {
        String description = weather5days.getWeatherList().get(0).getWeather().get(0).getDescription();
        Double temperature = weather5days.getWeatherList().get(0).getMain().getTemp();
        String cityName = weather5days.getCity().getName();
        Intent resultIntent = new Intent(this, WeatherActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.s01d)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setContentText(getString(R.string.notification_text_1)+ " " + cityName + " " + getString(R.string.notification_text_2)  + " " + description + " " + (celsiusOrFahrenheit.equals("C") ? temperature.toString() : Converters.celsiusToFahrenheit(temperature)) + "°"+ celsiusOrFahrenheit)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .build();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, notification);

    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getWeatherCity();
    }

    @Override
    public void showData(Weather5days weather5days) {
        weatherAdapter.setWeather5days(weather5days);
        weatherAdapter.setWeatherLists(weather5days.getWeatherList());
        cityName = weatherAdapter.getWeather5days().getCity().getName();
        preferences.edit().putString("cityName", cityName).apply();
        showWeatherNow();
        searchViewLocation.setQuery("", false);
        searchViewLocation.onActionViewCollapsed();
        searchViewLocation.clearFocus();
        textViewCityName.setText(String.format(Locale.ROOT, "%s (%s)", cityName, weatherAdapter.getWeather5days().getCity().getCountry()));
        textViewCityName.setVisibility(View.VISIBLE);
        recyclerViewWeather.setVisibility(View.VISIBLE);
        pushNotification(weather5days);
    }

    @Override
    public void showError() {
        Toast.makeText(this, location_error_notice, Toast.LENGTH_LONG).show();
    }

    public void onClickImageViewLocation(View view) {
        if (!checkPermissions()) {
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
            requestPermissions();
        } else {
            getLastLocation();
            presenter.getWeather();
            searchViewLocation.clearFocus();
        }
    }

    public void onClickTextViewWeatherForecastLabel(View view) {
        Intent intentWeather = new Intent(ctx, OptionsActivity.class);
        startActivity(intentWeather);
    }





    private void showSnackbar(final String text, Context context) {
        if (context != null) {
            Snackbar.make(findViewById(android.R.id.content),
                    text,
                    Snackbar.LENGTH_LONG).show();

        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                        getString(mainTextStringId),
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(WeatherActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }
    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION,false);
                    }
            );

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    view -> startLocationPermissionRequest());

        } else {
            Log.i(TAG, "Requesting permission");
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length == 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLastLocation();

            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        view -> {
                            Intent intent = new Intent();
                            intent.setAction(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    "com.example.weather", null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });
            }
        }
    }
}