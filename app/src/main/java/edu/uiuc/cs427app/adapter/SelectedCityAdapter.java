package edu.uiuc.cs427app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.data.SelectedCity;
import edu.uiuc.cs427app.models.City;

/**
 * Adapter for displaying the list of selected cities in a RecyclerView.
 */
public class SelectedCityAdapter extends RecyclerView.Adapter<SelectedCityAdapter.SelectedCityViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;
    private final List<SelectedCity> selectedCities;

    /**
     * Constructor to initialize the adapter with the required parameters.
     *
     * @param context The context in which the adapter operates.
     * @param dataSet The list of SelectedCity items to be displayed.
     * @param recyclerViewInterface The interface for handling item click events.
     */
    public SelectedCityAdapter(Context context,
                               List<SelectedCity> dataSet,
                               RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.selectedCities = dataSet;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     * Called when the RecyclerView needs a new SelectedCityViewHolder of the given type to represent an item.
     * This method creates a new view by inflating the layout resource specified in
     * R.layout.recycle_view_selected_city and returns a new ViewHolder that holds this view.
     *
     * @param viewGroup The ViewGroup into which the new view will be added after it is bound to an adapter position.
     * @param viewType The view type of the new view.
     * @return A new ViewHolder that holds the view.
     */
    @NonNull
    @Override
    public SelectedCityViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recycle_view_selected_city, viewGroup, false);

        return new SelectedCityAdapter.SelectedCityViewHolder(view, recyclerViewInterface);
    }

    /**
     * Called by the RecyclerView to display the data at the specified position.
     * This method updates the contents of the SelectedCityViewHolder textView to reflect the
     * city name of the item at the given position.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item
     *                   at the given position in the data set.
     * @param position The position of the item within the data set.
     */
    @Override
    public void onBindViewHolder(SelectedCityViewHolder viewHolder, int position) {
        viewHolder.textView.setText(selectedCities.get(position).cityName);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set.
     */
    @Override
    public int getItemCount() {
        return selectedCities.size();
    }


    /**
     * ViewHolder for representing an individual selected city item in the RecyclerView.
     */
    public static class SelectedCityViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final Button showDetailBtn;
        final Button showMapBtn;

        /**
         * Constructor for the ViewHolder.
         *
         * @param view The root view of the selected city item layout.
         * @param recyclerViewInterface The interface for handling item click events.
         */
        public SelectedCityViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            textView = view.findViewById(R.id.selectedTextView);
            showDetailBtn = view.findViewById(R.id.showBtn);
            showMapBtn = view.findViewById(R.id.showMap);

            showMapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos, Page.Map);
                        }
                    }
                }
            });

            showDetailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos, Page.WeatherDetail);
                        }
                    }
                }
            });
        }
    }
}

