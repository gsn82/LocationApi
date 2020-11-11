package com.example.locationapi;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button startLocationUpdatesButton;
    private Button stopLocationUpdatesButton;
    private TextView locationTextView;
    private TextView locationUpdateTimeTextView;


    private FusedLocationProviderClient fusedLocationClient;
    // доступ к пользовательским настройкам
    private SettingsClient settingsClient;
    // для сохранения данных запроса
    private LocationRequest locationRequest;
    // для определения настройки девайся
    private LocationSettingsRequest locationSettingsRequest;
    // используется клас для событий определения место положения
    private LocationCallback locationCallback;
    // класс где хранятся широта и долгота
    private Location currentLocation;

    // активноли обновление место положения
    private boolean isLocationUpdateActive;
    //за какое время  обновились
    private String locationUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLocationUpdatesButton = findViewById(R.id.startLocationUpdatesButton);
        stopLocationUpdatesButton = findViewById(R.id.stopLocationUpdatesButton);
        locationTextView = findViewById(R.id.locationTextView);
        locationUpdateTimeTextView = findViewById(R.id.locationUpdateTimeTextView);
        //Create location services client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        settingsClient = LocationServices.getSettingsClient(this);

        // обработчик нажатия на кнопку
        startLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates();
            }
        });

        BuildLocationRequest();
        BuildLocationCallback();
        BuildLocationSettingsRequest();
    }

    private void startLocationUpdates() { 
    }

    private void BuildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    private void BuildLocationCallback() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                currentLocation = locationResult.getLastLocation();
                // устнавливаем координаты : широта и долгота
                locationTextView.setText(""+currentLocation.getLatitude() +"/"+ currentLocation.getLongitude());
                // выводим время
                locationUpdateTimeTextView.setText(DateFormat.getTimeInstance().format(new Date()));
            }
        };
    }

    private void BuildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        // высокая точность
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
}