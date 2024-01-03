package edu.uiuc.cs427app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import edu.uiuc.cs427app.adapter.Page;
import edu.uiuc.cs427app.adapter.RecyclerViewInterface;
import edu.uiuc.cs427app.adapter.SelectedCityAdapter;
import edu.uiuc.cs427app.dao.SelectedCityDao;
import edu.uiuc.cs427app.data.SelectedCity;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * MainActivity: The primary activity that displays the list of selected cities for the logged-in user.
 * The user can add or remove cities, view weather information of the city and also log out from
 * this screen.
 */
public class MainActivity extends ThemeActivity implements View.OnClickListener, RecyclerViewInterface {

    // List of cities selected by the user
    List<SelectedCity> selectedCities = new ArrayList<>();

    // Executor for background threading
    private final Executor executor = Executors.newSingleThreadExecutor();

    // RecyclerView components
    RecyclerView recyclerView;
    SelectedCityAdapter adapter;

    // Database fields
    String accountUsername = "";
    AppDatabase db;
    SelectedCityDao selectedCityDao;

    // Shared Preferences for user data
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // UI
    TextView titleTextView;

    /**
     * This method initializes the UI, shared preferences and database.
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUI();
        setupSharedPreferences();
        loadDatabase();
    }

    /**
     * Initializes the user interface elements.
     */
    public void loadUI() {
        titleTextView = findViewById(R.id.textView3);

        Button buttonNew = findViewById(R.id.buttonAddLocation);
        Button logoutBtn = findViewById(R.id.logoutBtn);

        buttonNew.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        // Setup Recycle view
        recyclerView = findViewById(R.id.selectedCitiesRecycleView);
        adapter = new SelectedCityAdapter(this, selectedCities, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Set up shared preferences for storing user data.
     * Ensure that the loadUI is called first.
     */
    public void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username", "");
        accountUsername = username;

        titleTextView.setText("Team 11-" + username);
    }

    /**
     * Initialize database and DAO objects.
     */
    public void loadDatabase() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        selectedCityDao = db.selectedCityDao();
        retrieveSelectedCities();
    }

    /**
     * This callback handles swipe actions on the RecyclerView items.
     */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            int uid = selectedCities.get(position).uid;
            deleteSelectedCity(uid);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red_500))
                    .addActionIcon(R.drawable.baseline_delete_24)
                    .create()
                    .decorate();


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    /**
     * Handle button click events. For now, it handle logout and add location.
     * @param view the clicked view.
     */
    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.logoutBtn:
                intent = new Intent(this, LoginActivity.class);
                editor.putString("username", null);
                editor.apply();
                startActivity(intent);
                finish();
                break;
            case R.id.buttonAddLocation:
                // Implement this action to add a new location to the list of locations
                intent = new Intent(this, CityListActivities.class);
                getSelectedCityResult.launch(intent);
                break;
        }
    }

    /**
     * This callback fetches selected city result from another activity.
     */
    private final ActivityResultLauncher<Intent> getSelectedCityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if(data != null) {

                        String cityName = data.getStringExtra("cityName");
                        String longitude = data.getStringExtra("longitude");
                        String latitude = data.getStringExtra("latitude");

                        uploadSelectedCityToDB(cityName, longitude, latitude);

                        Log.i("city", cityName);
                    }
                }
            }
    );

    /**
     * Fetches selected cities from the database.
     */
    private void retrieveSelectedCities() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<SelectedCity> newSelectedCities = selectedCityDao.getAll(accountUsername);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("SelectedCityList", "Start loading");

                        selectedCities.clear();
                        selectedCities.addAll(newSelectedCities);

                        for (SelectedCity city : selectedCities) {
                            Log.i("SelectedCityList", "UID: " + city.uid + ", Username: " + city.username + ", City Name: " + city.cityName + ", Latitude: " + city.latitude + ", Longitude: " + city.longitude);
                        }
                        Log.i("Size New", "" + selectedCities.size());
                        // Any UI interactions should also be here

                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * Inserts a new selected city into the database.
     * @param cityName city's name.
     * @param longitude city's longitude.
     * @param latitude city's latitude.
     */
    private void uploadSelectedCityToDB(String cityName, String longitude, String latitude) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SelectedCity newSelectedCity = new SelectedCity();
                newSelectedCity.username = accountUsername;
                newSelectedCity.cityName = cityName;
                newSelectedCity.longitude = longitude;
                newSelectedCity.latitude = latitude;

                selectedCityDao.insert(newSelectedCity);
                selectedCities.add(newSelectedCity);

                // Move operations that update the UI to the main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemInserted(selectedCities.size() - 1);
                    }
                });
            }
        });
    }

    /**
     * Deletes a city from the database.
     * @param uid unique ID of the city.
     */
    public void deleteSelectedCity(int uid) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SelectedCity removedCity = selectCityByUid(uid);
                String removedCityName = removedCity.cityName;
                String removedCityLon = removedCity.longitude;
                String removedCityLat = removedCity.latitude;
                selectedCityDao.deleteByCityId(uid);
                int index = removeCityByUid(uid);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRemoved(index);
                        Snackbar snackbar = Snackbar.make(recyclerView, removedCityName + " deleted", Snackbar.LENGTH_SHORT);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploadSelectedCityToDB(removedCityName,removedCityLon,removedCityLat);
                            }
                        });
                        snackbar.setActionTextColor(Color.GREEN);
                        snackbar.show();
                    }
                });
            }
        });
    }

    /**
     * Removes a city from the local list using its unique ID.
     * @param uid unique ID of the city.
     * @return index of the removed city.
     */
    public int removeCityByUid(int uid) {
        Iterator<SelectedCity> iterator = selectedCities.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            SelectedCity city = iterator.next();
            if (city.uid == uid) {
                iterator.remove();
                return count; // Assuming uid is unique, you can break out of the loop once found
            }

            count += 1;
        }

        return -1;
    }
    /**
     * Gets a city from the local list using its unique ID. Returns null if no match.
     * @param uid unique ID of the city.
     * @return SelectedCity object.
     */
    public SelectedCity selectCityByUid(int uid) {
        Iterator<SelectedCity> iterator = selectedCities.iterator();
        while (iterator.hasNext()) {
            SelectedCity city = iterator.next();
            if (city.uid == uid) {
                return city;
            }
        }
        return null;
    }

    /**
     * Handles the click event of an item in the RecyclerView which represents a city.
     * Depending on the Page enum passed, it will open either the map view or the weather details view.
     *
     * @param position The position of the clicked item in the RecyclerView.
     * @param goTo The Page enum indicating whether to go to the Map view or the WeatherDetail view.
     */
    @Override
    public void onItemClick(int position, Page goTo) {
        SelectedCity city = selectedCities.get(position);
        Intent intent = new Intent(this, DetailsActivity.class);

        switch (goTo) {
            case Map:
                intent = new Intent(this, MapActivity.class);
                break; // Prevents fall-through
            case WeatherDetail:
                intent = new Intent(this, DetailsActivity.class);
                break; // Prevents fall-through
        }

        intent.putExtra("city", city.cityName);
        intent.putExtra("longitude", city.longitude);
        intent.putExtra("latitude", city.latitude);
        startActivity(intent);
    }
}
