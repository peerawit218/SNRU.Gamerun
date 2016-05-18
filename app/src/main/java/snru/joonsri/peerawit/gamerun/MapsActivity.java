package snru.joonsri.peerawit.gamerun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
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
    private Criteria criteria;
    private double myLatADouble, myLngADouble;
    private boolean gpsABoolean, networkABoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_design);//เปิดไปหน้าที่ต้องการ
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Setup Location
        locationManage = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);



    } //Main Method

    public Location myFindLocation(String strProvider, String strError) {

        Location location = null;
        if (locationManage.isProviderEnabled(strProvider)) {

            locationManage.requestLocationUpdates(strProvider, 1000, 10, (android.location.LocationListener) locationListener);
            location = locationManage.getLastKnownLocation(strProvider);



        } else {

            Log.d("test", "my Error == " + strError);

        }
        return null;
    }



    //Create Inner Class //คลาสซ้อนคลาส
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            //การค้นหา เมื่อมีการเปลี่ยนค่า
            myLatADouble = location.getLatitude();
            myLatADouble = location.getLongitude();

        }
    }; //Location



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup for สกล
        LatLng snruLatlng = new LatLng(snruLatDouble, snruLanADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(snruLatlng, 16));

    }
} //Main Class

