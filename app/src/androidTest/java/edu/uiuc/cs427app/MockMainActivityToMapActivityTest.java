package edu.uiuc.cs427app;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.content.Intent;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.adapter.Page;
import edu.uiuc.cs427app.data.SelectedCity;
import edu.uiuc.cs427app.models.City;

public class MockMainActivityToMapActivityTest {

    @Mock
    private Context mockContext;

    private MainActivity mainActivity;
    private List<SelectedCity> selectedCities;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Initialize MainActivity with Mockito
        mainActivity = mock(MainActivity.class);
        when(mainActivity.getApplicationContext()).thenReturn(mockContext);

        // Set up the selectedCities
        selectedCities = new ArrayList<>();
        SelectedCity chicago = new SelectedCity();
        chicago.username = "user1";
        chicago.cityName = "Chicago";
        chicago.latitude = "-87.6298";
        chicago.longitude = "41.8781";
        selectedCities.add(chicago);
        SelectedCity champaign = new SelectedCity();
        champaign.username = "user1";
        champaign.cityName = "Champaign";
        champaign.latitude = "-88.2434";
        champaign.longitude = "40.1164";
        selectedCities.add(champaign);

        // Assume MainActivity can set selectedCities
        mainActivity.selectedCities = selectedCities;
        doCallRealMethod().when(mainActivity).onItemClick(anyInt(), any());
    }

    @Test
    public void testOnItemClick_ToMapActivity_Chicago() {
        int position = 0; // The position of the city in the list

        mainActivity.onItemClick(position, Page.Map);

        // Capturing the intent when startActivity is called
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(mainActivity).startActivity(intentCaptor.capture());

        Intent capturedIntent = intentCaptor.getValue();

        // Asserting the captured intent has correct data
        assertNotNull(capturedIntent);
        assertEquals(MapActivity.class.getName(), capturedIntent.getComponent().getClassName());
        assertEquals("Chicago", capturedIntent.getStringExtra("city"));
        assertEquals("-87.6298", capturedIntent.getStringExtra("latitude"));
        assertEquals("41.8781", capturedIntent.getStringExtra("longitude"));
    }

    @Test
    public void testOnItemClick_ToMapActivity_Champaign() {
        int position = 1; // The position of the city in the list

        mainActivity.onItemClick(position, Page.Map);

        // Capturing the intent when startActivity is called
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(mainActivity).startActivity(intentCaptor.capture());

        Intent capturedIntent = intentCaptor.getValue();

        // Asserting the captured intent has correct data
        assertNotNull(capturedIntent);
        assertEquals(MapActivity.class.getName(), capturedIntent.getComponent().getClassName());
        assertEquals("Champaign", capturedIntent.getStringExtra("city"));
        assertEquals("-88.2434", capturedIntent.getStringExtra("latitude"));
        assertEquals("40.1164", capturedIntent.getStringExtra("longitude"));
    }
}