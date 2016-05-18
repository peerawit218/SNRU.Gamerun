package snru.joonsri.peerawit.gamerun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
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


    @Override
    protected void onResume() {
        super.onResume();

        locationManage.removeUpdates( locationListener);
        myLatADouble = snruLanADouble;
        myLatADouble = snruLatDouble;

        Location networdLocation = myFindLocation(LocationManager.NETWORK_PROVIDER, "ไม่ได้ต่อเน็ต");
        if (networdLocation != null) {
            myLatADouble = networdLocation.getLatitude();
            myLatADouble = networdLocation.getLongitude();
        }

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER, "ไม่มี GPS");
        if (gpsLocation != null) {
            myLatADouble = gpsLocation.getLatitude();
            myLatADouble = gpsLocation.getLongitude();
        }

    }

    @Override
    protected void onStop() {
        super.onStop(); //ปิดเซอร์วิส

        locationManage.removeUpdates(locationListener);

    }

    public Location myFindLocation(String strProvider, String strError) {

        Location location = null;
        if (locationManage.isProviderEnabled(strProvider)) {

            locationManage.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManage.getLastKnownLocation(strProvider);



        } else {

            Log.d("test", "my Error == " + strError);

        }
        return null;
    }


    //Create Inner Class //คลาสซ้อนคลาส
    public android.location.LocationListener locationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myLatADouble = location.getLatitude();
            myLatADouble = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup for สกล
        LatLng snruLatlng = new LatLng(snruLatDouble, snruLanADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(snruLatlng, 15));

        //My loop
        myLoop();

    } //onMapReady

    private void myLoop() {

        Log.d("18May16", "MyLat = " + myLatADouble);
        Log.d("18May16", "MyLon = " + myLngADouble);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



            }
        }, 3000//วนทุก 3 วินาที
                );

    } //My Loop
} //Main Class

