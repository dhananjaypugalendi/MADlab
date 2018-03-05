package com.dhananjay.lab8;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "maps";
    private static final String KEY = "KEY";
    private GoogleMap googleMap;
    private String urlString;
    private Place currPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if(googleMap!=null){
                    currPlace = place;
                    CameraPosition target=CameraPosition
                            .builder()
                            .target(currPlace.getLatLng())
                            .tilt(45)
                            .zoom(15)
                            .build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target));
                }
            }
            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void showShoppingMalls(View view) {
        if(googleMap!=null && currPlace!=null)showPlaces("shopping_mall");
    }

    public void showRestaurants(View view) {
        if(googleMap!=null && currPlace!=null)showPlaces("restaurant");
    }

    public void showMovieTheatres(View view) {
        if(googleMap!=null && currPlace!=null)showPlaces("movie_theater");
    }

    public void showPlaces(String type){
        urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                +currPlace.getLatLng().latitude+","+currPlace.getLatLng().longitude
                +"&radius=5000&type="+type+"&key="+KEY;
        PlacesTask placesTask = new PlacesTask();
        try {
            List<Poi> poiList = placesTask.execute(urlString).get();
            googleMap.clear();
            for(int i = 0 ; poiList!=null && i < poiList.size(); i++){
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(poiList.get(i).lat, poiList.get(i).lng);
                markerOptions.position(latLng);
                markerOptions.title(poiList.get(i).name);
                Marker m = googleMap.addMarker(markerOptions);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
