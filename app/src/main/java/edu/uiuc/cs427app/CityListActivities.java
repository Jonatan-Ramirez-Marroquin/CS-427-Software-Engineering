package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import edu.uiuc.cs427app.adapter.CityAdapter;
import edu.uiuc.cs427app.adapter.Page;
import edu.uiuc.cs427app.adapter.RecyclerViewInterface;
import edu.uiuc.cs427app.models.City;

/**
 * CityListActivities: An activity that displays a list of cities in a RecyclerView.
 * Allows users to select a city and then returns the selected city information.
 * This activity extends ThemeActivity to inherit its theme setting behavior.
 */
public class CityListActivities extends ThemeActivity implements RecyclerViewInterface {

    private final ArrayList<City> cities = new ArrayList<>();

    /**
     * Initializes the activity, sets the content view, and populates the RecyclerView with cities.
     *
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list_activities);

        setupCityRecyclerView();
        loadAllCities();
    }

    /**
     * Sets up the RecyclerView components, including adapter and layout manager.
     */
    private void setupCityRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.RecycleViewCity);
        CityAdapter adapter = new CityAdapter(this, cities, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void loadAllCities() {
        cities.add(new City("Champaign", "-88.2434", "40.1164"));
        cities.add(new City("Chicago", "-87.6298", "41.8781"));
        cities.add(new City("New York", "-74.0060", "40.7128"));
        cities.add(new City("Los Angeles", "-118.2437", "34.0522"));
        cities.add(new City("San Francisco", "-122.4194", "37.7749"));
        cities.add(new City("Abu Dhabi", "54.3666", "24.4715"));
        cities.add(new City("Accra", "-0.2057", "5.5600"));
        cities.add(new City("Addis Ababa", "38.7578", "8.9806"));
        cities.add(new City("Algiers", "2.3419", "36.7518"));
        cities.add(new City("Amsterdam", "4.9041", "52.3676"));
        cities.add(new City("Ankara", "32.8541", "39.9334"));
        cities.add(new City("Athens", "23.7275", "37.9838"));
        cities.add(new City("Auckland", "174.7633", "-36.8485"));
        cities.add(new City("Baghdad", "44.3661", "33.3152"));
        cities.add(new City("Baku", "49.8671", "40.4093"));
        cities.add(new City("Bangkok", "100.4931", "13.7563"));
        cities.add(new City("Barcelona", "2.1734", "41.3851"));
        cities.add(new City("Beijing", "116.4074", "39.9042"));
        cities.add(new City("Beirut", "35.5125", "33.8959"));
        cities.add(new City("Belgrade", "20.4651", "44.7866"));
        cities.add(new City("Berlin", "13.4050", "52.5200"));
        cities.add(new City("Bogota", "-74.0721", "4.7110"));
        cities.add(new City("Brasilia", "-47.8818", "-15.7942"));
        cities.add(new City("Bratislava", "17.1077", "48.1486"));
        cities.add(new City("Brussels", "4.3517", "50.8503"));
        cities.add(new City("Bucharest", "26.1025", "44.4268"));
        cities.add(new City("Budapest", "19.0402", "47.4979"));
        cities.add(new City("Buenos Aires", "-58.3816", "-34.6037"));
        cities.add(new City("Cairo", "31.2357", "30.0444"));
        cities.add(new City("Cape Town", "18.4232", "-33.9249"));
        cities.add(new City("Caracas", "-66.9036", "10.4806"));
        cities.add(new City("Casablanca", "-7.5898", "33.5731"));
        cities.add(new City("Chennai", "80.2707", "13.0827"));
        cities.add(new City("Copenhagen", "12.5683", "55.6761"));
        cities.add(new City("Dakar", "-17.4381", "14.6928"));
        cities.add(new City("Dallas", "-96.7970", "32.7767"));
        cities.add(new City("Damascus", "36.7128", "33.5158"));
        cities.add(new City("Dar es Salaam", "39.2833", "-6.7924"));
        cities.add(new City("Delhi", "77.2090", "28.6139"));
        cities.add(new City("Dhaka", "90.4125", "23.8103"));
        cities.add(new City("Doha", "51.5201", "25.2916"));
        cities.add(new City("Dubai", "55.2708", "25.2048"));
        cities.add(new City("Dublin", "-6.2603", "53.3498"));
        cities.add(new City("Durban", "31.0218", "-29.8587"));
        cities.add(new City("Edinburgh", "-3.1883", "55.9533"));
        cities.add(new City("Frankfurt", "8.6821", "50.1109"));
        cities.add(new City("Geneva", "6.1432", "46.2044"));
        cities.add(new City("Glasgow", "-4.2518", "55.8642"));
        cities.add(new City("Hanoi", "105.8544", "21.0285"));
        cities.add(new City("Harare", "31.0524", "-17.8292"));
        cities.add(new City("Havana", "-82.3666", "23.1136"));
        cities.add(new City("Helsinki", "24.9384", "60.1695"));
        cities.add(new City("Ho Chi Minh City", "106.6297", "10.8231"));
        cities.add(new City("Hong Kong", "114.1694", "22.3193"));
        cities.add(new City("Houston", "-95.3698", "29.7604"));
        cities.add(new City("Istanbul", "28.9784", "41.0082"));
        cities.add(new City("Jakarta", "106.8456", "-6.2088"));
        cities.add(new City("Johannesburg", "28.0473", "-26.2041"));
        cities.add(new City("Kabul", "69.1776", "34.5200"));
        cities.add(new City("Karachi", "67.0099", "24.8607"));
        cities.add(new City("Kathmandu", "85.3240", "27.7172"));
        cities.add(new City("Kiev", "30.5234", "50.4501"));
        cities.add(new City("Kingston", "-76.7936", "17.9970"));
        cities.add(new City("Kinshasa", "15.2663", "-4.4419"));
        cities.add(new City("Kuala Lumpur", "101.6869", "3.1390"));
        cities.add(new City("Kuwait City", "47.9783", "29.3759"));
        cities.add(new City("Lagos", "3.3792", "6.5244"));
        cities.add(new City("Lahore", "74.3587", "31.5497"));
        cities.add(new City("Lima", "-77.0428", "-12.0464"));
        cities.add(new City("Lisbon", "-9.1427", "38.7267"));
        cities.add(new City("Ljubljana", "14.5058", "46.0569"));
        cities.add(new City("London", "-0.1278", "51.5074"));
        cities.add(new City("Los Angeles", "-118.2437", "34.0522"));
        cities.add(new City("Luanda", "13.2343", "-8.8368"));
        cities.add(new City("Lusaka", "28.2833", "-15.4067"));
        cities.add(new City("Luxembourg", "6.1319", "49.6116"));
        cities.add(new City("Lyon", "4.8357", "45.7640"));
        cities.add(new City("Madrid", "-3.7038", "40.4168"));
        cities.add(new City("Manila", "120.9822", "14.5995"));
        cities.add(new City("Maputo", "32.5727", "-25.9626"));
        cities.add(new City("Marseille", "5.3698", "43.2965"));
        cities.add(new City("Melbourne", "144.9631", "-37.8136"));
        cities.add(new City("Mexico City", "-99.1332", "19.4326"));
        cities.add(new City("Miami", "-80.1918", "25.7617"));
        cities.add(new City("Milan", "9.1900", "45.4642"));
        cities.add(new City("Minsk", "27.5619", "53.9023"));
        cities.add(new City("Monaco", "7.4246", "43.7384"));
        cities.add(new City("Montevideo", "-56.1882", "-34.9033"));
        cities.add(new City("Montreal", "-73.5673", "45.5017"));
        cities.add(new City("Moscow", "37.6173", "55.7558"));
        cities.add(new City("Mumbai", "72.8777", "19.0760"));
        cities.add(new City("Munich", "11.5810", "48.1351"));
        cities.add(new City("Nairobi", "36.8219", "-1.2921"));
        cities.add(new City("Naples", "14.2681", "40.8522"));
        cities.add(new City("New Delhi", "77.2090", "28.6139"));
        cities.add(new City("Nice", "7.2620", "43.7102"));
        cities.add(new City("Nicosia", "33.3823", "35.1856"));
        cities.add(new City("Oslo", "10.7522", "59.9139"));
        cities.add(new City("Ottawa", "-75.6972", "45.4215"));
        cities.add(new City("Paris", "2.3522", "48.8566"));
        cities.add(new City("Perth", "115.8575", "-31.9505"));
        cities.add(new City("Phnom Penh", "104.9160", "11.5625"));
        cities.add(new City("Prague", "14.4378", "50.0755"));
        cities.add(new City("Quito", "-78.4678", "-0.1807"));
        cities.add(new City("Reykjavik", "-21.8278", "64.1265"));
        cities.add(new City("Rio de Janeiro", "-43.1729", "-22.9068"));
        cities.add(new City("Riyadh", "46.7219", "24.6877"));
        cities.add(new City("Rome", "12.4964", "41.9028"));
        cities.add(new City("Santiago", "-70.6483", "-33.4489"));
        cities.add(new City("Sao Paulo", "-46.6333", "-23.5505"));
        cities.add(new City("Seoul", "126.9780", "37.5665"));
        cities.add(new City("Shanghai", "121.4737", "31.2304"));
        cities.add(new City("Singapore", "103.8198", "1.3521"));
        cities.add(new City("Sofia", "23.3219", "42.6977"));
        cities.add(new City("Stockholm", "18.0686", "59.3293"));
        cities.add(new City("Sydney", "151.2093", "-33.8688"));
        cities.add(new City("Taipei", "121.5654", "25.0330"));
        cities.add(new City("Tallinn", "24.7536", "59.4370"));
        cities.add(new City("Tashkent", "69.2406", "41.2995"));
        cities.add(new City("Tbilisi", "44.7872", "41.7151"));
        cities.add(new City("Tehran", "51.3890", "35.6892"));
        cities.add(new City("Tel Aviv", "34.7818", "32.0853"));
        cities.add(new City("Thimphu", "89.6335", "27.4722"));
        cities.add(new City("Tokyo", "139.6917", "35.6895"));
        cities.add(new City("Toronto", "-79.3832", "43.6532"));
        cities.add(new City("Tripoli", "13.1875", "32.8872"));
        cities.add(new City("Tunis", "10.1815", "36.8065"));
        cities.add(new City("Ulaanbaatar", "106.9057", "47.8864"));
        cities.add(new City("Vancouver", "-123.1216", "49.2827"));
        cities.add(new City("Vienna", "16.3738", "48.2082"));
        cities.add(new City("Vientiane", "102.6331", "17.9757"));
        cities.add(new City("Vilnius", "25.2797", "54.6872"));
        cities.add(new City("Warsaw", "21.0122", "52.2297"));
        cities.add(new City("Washington", "-77.0369", "38.8951"));
        cities.add(new City("Wellington", "174.7762", "-41.2865"));
        cities.add(new City("Windhoek", "17.0835", "-22.5597"));
        cities.add(new City("Yaounde", "11.5021", "3.8480"));
        cities.add(new City("Yerevan", "44.4991", "40.1792"));
        cities.add(new City("Zagreb", "15.9819", "45.8150"));
        cities.add(new City("Zurich", "8.5417", "47.3769"));
    }

    /**
     * Handles the back button press.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed(); // This will close the activity
    }

    /**
     * Handles the item click event from the RecyclerView and sets the result for the parent activity
     * based on the selected item's details. The method prepares an intent with the selected city's
     * information and navigates the user back to the parent activity.
     *
     * @param position the position of the clicked item in the list.
     * @param goTo     the page enum indicating what action should be taken after the item click.
     *                 Depending on the enum value, additional actions can be defined here. Currently,
     *                 the method is set to always return to the parent activity with the selected city's data.
     */
    @Override
    public void onItemClick(int position, Page goTo) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("cityName", cities.get(position).getCityName());
        intent.putExtra("longitude", cities.get(position).getLongitude());
        intent.putExtra("latitude", cities.get(position).getLatitude());
        setResult(RESULT_OK, intent);
        onBackPressed();
    }
}
