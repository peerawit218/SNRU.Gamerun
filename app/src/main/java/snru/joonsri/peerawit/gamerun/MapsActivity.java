package snru.joonsri.peerawit.gamerun;

import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.sharing.LocalContent;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double snruLatDouble = 17.190512,
            snruLanADouble = 104.090424;
    private LocationManager locationManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_design);//เปิดไปหน้าที่ต้องการ
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } //Main Method



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup for สกล
        LatLng snruLatlng = new LatLng(snruLatDouble, snruLanADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(snruLatlng, 16));

    }
} //Main Class

