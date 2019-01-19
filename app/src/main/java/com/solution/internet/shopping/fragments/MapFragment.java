package com.solution.internet.shopping.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.DelegateActivity;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelMarker.ModelMarker;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class MapFragment extends BaseFragment implements HandleRetrofitResp, OnMapReadyCallback
{

    //region fields
    private GoogleMap googleMap;
    List<Marker> markerList;
    PlaceAutocompleteFragment placeAutoComplete;

    //endregion

    //region views

/*
    @BindView(R.id.mapFragment)
    MapView mapFragment;
*/

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.map_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        markerList = new ArrayList<>();

        markerList = new ArrayList<>();
/*
        placeAutoComplete = (PlaceAutocompleteFragment) getBaseActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setHint("");
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(Place place)
            {

                Log.d("Maps", "Place selected: " + place.getName());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), (float) 15));
                placeAutoComplete.setText("");
            }

            @Override
            public void onError(Status status)
            {
                Log.d("Maps", "An error occurred: " + status);
            }
        });
*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        if(googleMap==null)
        {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
//        placeAutoComplete.onDestroyView();

//        placeAutoComplete.onStop();
/*
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            getChildFragmentManager().beginTransaction()
                    .remove(mapFragment).commitAllowingStateLoss();*/
        //        appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader()
    {
        return false;
    }

    @Override
    protected boolean canShowBottomBar()
    {
        return true;
    }

    @Override
    protected boolean canShowBackArrow()
    {
        return false;
    }

    @Override
    protected String getTitle()
    {
        return null;
    }

    @Override
    public int getSelectedMenuId()
    {
        return R.id.tvNavBarProducts;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o)
    {
        Gson gson = new Gson();

        if (flag.equals(DataEnum.callMap.name()))
        {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            List<ModelMarker> modelMarkerList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++)
            {
                ModelMarker modelMarker = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelMarker.class);
                modelMarkerList.add(modelMarker);
            }
            setMarkers(modelMarkerList);

        }

    }

    @Override
    public void onNoContent(String flag, int code)
    {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position)
    {

    }

    //endregion

    //region clicks


    //endregion

    //region calls
    private void callMap()
    {
        Call call = HandleCalls.restShopping.getClientService().callMap();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callMap.name(), true);
    }
    //endregion

    //region functions

    public static MapFragment init()
    {
        return new MapFragment();
    }

    private void setMarkers(List<ModelMarker> modelList)
    {
        if (googleMap != null)
        {
            googleMap.clear();
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            for (ModelMarker model : modelList)
            {
                LatLng latLng = new LatLng(model.getLat(), model.getLng());
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo))
                        .title(model.getFullname()));

                marker.setTag(model.getUserid());
                markerList.add(marker);
            }

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markerList)
            {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();

            int padding = 150; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.animateCamera(cu);

        }
    }
    //endregion

    //region map
   /* protected void initMap(Bundle savedInstanceState) {
        mapFragment.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(this.getBaseActivity());
        } catch (Exception e) {
            Log.e("mapError", e.getMessage());
        }

        mapFragment.getMapAsync(this);
    }*/

    @Override
    public void onMapReady(GoogleMap map)
    {
        this.googleMap = map;

        callMap();

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {

            @Override
            public void onInfoWindowClick(Marker arg0)
            {
                addFragment(DeliveryPageFragment.init((int) arg0.getTag()), true);
            }
        });
//        LatLng latLng = new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongitude()));
//        adjustMapLatLng(latLng);
    }


    private void adjustMapLatLng(@android.support.annotation.NonNull LatLng latLng)
    {
        if (googleMap != null)
        {
            googleMap.clear();
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.addMarker(new MarkerOptions().position(latLng));
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_avaliable_selected)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) 11));

        }
    }

    //endregion
}
