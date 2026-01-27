package com.example.locationtracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    private Button btnStartService, btnStopService, btnFetchApi, btnViewLocations;
    private TextView tvApiData, tvLocationCount;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;

    private AppDatabase database;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        initializeViews();
        setupRecyclerView();
        setupButtons();
        checkAndRequestPermissions();
        updateLocationCount();
    }

    private void initializeViews() {
        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);
        btnFetchApi = findViewById(R.id.btnFetchApi);
        btnViewLocations = findViewById(R.id.btnViewLocations);
        tvApiData = findViewById(R.id.tvApiData);
        tvLocationCount = findViewById(R.id.tvLocationCount);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        locationAdapter = new LocationAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locationAdapter);
    }

    private void setupButtons() {
        btnStartService.setOnClickListener(v -> startLocationService());
        btnStopService.setOnClickListener(v -> stopLocationService());
        btnFetchApi.setOnClickListener(v -> fetchApiData());
        btnViewLocations.setOnClickListener(v -> loadLocationsFromDatabase());
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
            };
        }

        boolean allGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (!allGranted) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }

        // Request background location permission separately (Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        PERMISSION_REQUEST_CODE + 1);
            }
        }
    }

    private void startLocationService() {
        if (checkLocationPermissions()) {
            Intent serviceIntent = new Intent(this, LocationService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
            Toast.makeText(this, "Location service started", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Location permissions required", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationService() {
        Intent serviceIntent = new Intent(this, LocationService.class);
        stopService(serviceIntent);
        Toast.makeText(this, "Location service stopped", Toast.LENGTH_SHORT).show();
    }

    private void fetchApiData() {
        tvApiData.setText("Fetching data...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Post>> call = apiService.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> posts = response.body();
                    if (!posts.isEmpty()) {
                        Post firstPost = posts.get(0);
                        String data = "Title: " + firstPost.getTitle() + "\n\n" +
                                "Body: " + firstPost.getBody();
                        tvApiData.setText(data);
                        Toast.makeText(MainActivity.this,
                                "Fetched " + posts.size() + " posts",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tvApiData.setText("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                tvApiData.setText("Failed: " + t.getMessage());
                Toast.makeText(MainActivity.this,
                        "API Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLocationsFromDatabase() {
        executorService.execute(() -> {
            List<LocationEntity> locations = database.locationDao().getAllLocations();

            runOnUiThread(() -> {
                locationAdapter.setLocations(locations);
                updateLocationCount();
                Toast.makeText(this,
                        "Loaded " + locations.size() + " locations",
                        Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void updateLocationCount() {
        executorService.execute(() -> {
            int count = database.locationDao().getLocationCount();

            runOnUiThread(() -> {
                tvLocationCount.setText("Total Locations: " + count);
            });
        });
    }

    private boolean checkLocationPermissions() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}