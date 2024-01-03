package edu.uiuc.cs427app.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.uiuc.cs427app.MainActivity;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.models.City;

/**
 * CityAdapter is a custom RecyclerView adapter designed to display a list of cities.
 * It interacts with the RecyclerViewInterface to provide callback functionality when
 * a city item is clicked in the RecyclerView.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;
    private final ArrayList<City> localDataSet;

    /**
     * Constructor to initialize the CityAdapter.
     *
     * @param context The current context, used to inflate the layout.
     * @param dataSet The list of cities to display in the RecyclerView.
     * @param recyclerViewInterface The interface providing the callback mechanism on item click.
     */
    public CityAdapter(Context context,
                       ArrayList<City> dataSet,
                       RecyclerViewInterface recyclerViewInterface
                       ) {
        this.context = context;
        this.localDataSet = dataSet;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     * Invoked when the RecyclerView needs a new ViewHolder of the given type to represent an item.
     * This method inflates the view from XML layout and initializes the ViewHolder.
     *
     * @param viewGroup The ViewGroup into which the new View will be added after it is bound to
     *                  an adapter position.
     * @param viewType  The view type of the new View.
     * @return A new CityViewHolder that holds a View of the given view type.
     */
    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recycle_view_city, viewGroup, false);

        return new CityViewHolder(view, recyclerViewInterface);
    }

    /**
     * Invoked by the RecyclerView to display the data at the specified position.
     * This method updates the contents of the CityViewHolder to reflect the city item at the given position.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item
     *                   at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CityViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getCityName());
    }

    /**
     * Returns the total number of items in the dataset held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     * It contains the city name TextView and provides the mechanism for item click functionality.
     */
    public static class CityViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public CityViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            // Define click listener for the ViewHolder's View
            CardView cityCard = view.findViewById(R.id.CityCard);
            cityCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos, Page.None);
                        }
                    }
                }
            });

            textView = view.findViewById(R.id.TextViewListCity);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
