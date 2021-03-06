package com.solution.internet.shopping.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.fragments.BaseFragment;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelMarker.ModelMarker;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MapsActivity extends BaseFragment implements OnMapReadyCallback, HandleRetrofitResp {

    //region fields

    private GoogleMap mMap;
    List<Marker> markerList;
    PlaceAutocompleteFragment placeAutoComplete;

    //endregion

    //region life cycle


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        callMap();


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                Intent intent = new Intent(getBaseActivity(), DelegateActivity.class);
                intent.putExtra(DataEnum.intentDeligateId.name(), (int) arg0.getTag());
                startActivity(intent);
            }
        });
    }


    //endregion


    //region fields

    //endregion

    //region views

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_maps, container, false);

        unbinder = ButterKnife.bind(this, view);

        markerList = new ArrayList<>();

        placeAutoComplete = (PlaceAutocompleteFragment) getBaseActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Log.d("Maps", "Place selected: " + place.getName());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader() {
        return false;
    }

    @Override
    protected boolean canShowBottomBar() {
        return false;
    }

    @Override
    protected boolean canShowBackArrow() {
        return false;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    public int getSelectedMenuId() {
        return 0;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {

    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion

    //region clicks


    //endregion

    //region calls

    //endregion

    //region functions

    public static MapsActivity init() {
        return new MapsActivity();
    }
    //endregion


    //////////////////////////////////////////////////////////////////////////////////////////////////
   /* //region calls

    private void callMap()
    {
        Call call = HandleCalls.restShopping.getClientService().callMap();
        HandleCalls.getInstance(this).callRetrofit(call, DataEnum.callMap.name(), true);
    }

    //endregion

    //region call response
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

    //region functions

    private void setMarkers(List<ModelMarker> modelList)
    {
        if (mMap != null)
        {
            mMap.clear();
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            for (ModelMarker model : modelList)
            {
                LatLng latLng = new LatLng(model.getLat(), model.getLng());
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
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
            mMap.animateCamera(cu);

        }
    }

    //endregion

    //region clicks

    @OnClick(R.id.tvNavBarMore)
    public void onClicktvNavBarMore()
    {
        startActivity(new Intent(MapsActivity.this, DelegateDetailsActivity.class));
    }

    @OnClick(R.id.tvNavBarProducts)
    public void onClicktvNavBarProducts()
    {
        startActivity(new Intent(MapsActivity.this, SearchActivity.class));
    }
    //endregion*/
}
