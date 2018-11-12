package com.solution.internet.shopping.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelMarker.ModelMarker;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, HandleRetrofitResp
{

    //region fields

    private GoogleMap mMap;
    List<Marker> markerList;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        markerList = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        HandleCalls.getInstance(this).setonRespnseSucess(this);
    }

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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        callMap();
        // Add a marker in Sydney and move the camera
      /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    //region calls

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
}
