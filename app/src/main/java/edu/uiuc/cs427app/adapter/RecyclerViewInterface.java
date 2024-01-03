package edu.uiuc.cs427app.adapter;


/**
 * An interface for handling item click events in a RecyclerView.
 * This allows for custom actions to be performed when a specific item
 * within the RecyclerView is clicked.
 */
public interface RecyclerViewInterface {

    /**
     * Callback method to be invoked when an item in the RecyclerView has been clicked.
     * Implementations can use this method to define custom actions upon an item click, such as navigating to
     * different pages based on the context of the application.
     *
     * @param position The position of the item in the RecyclerView that was clicked.
     * @param goTo     The page to navigate to as indicated by the {@link Page} enum, which determines the
     *                 action to be taken after the item click, such as showing a map, weather details, or no action.
     */
    void onItemClick(int position, Page goTo);
}

