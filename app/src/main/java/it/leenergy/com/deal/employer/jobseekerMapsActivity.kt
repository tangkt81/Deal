package it.leenergy.com.deal.employer

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.multidex.MultiDex
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import it.leenergy.com.deal.R
import it.leenergy.com.deal.`object`.LatLngJobSeeker
import java.util.*

class jobseekerMapsActivity : AppCompatActivity(),
        OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private val TAG = "jobseekerMapActivity"
    private lateinit var latlngJobSeekerList : ArrayList<LatLngJobSeeker>
    private lateinit var latlngJobSeeker : LatLngJobSeeker
    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null

    private lateinit  var locationManager: LocationManager
    private var latLng: LatLng? = null
    private var showMap: Boolean = true


    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobseeker_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        MultiDex.install(this)
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mapFragment.getMapAsync(this)

        val myTimer = Timer()
        myTimer.schedule(object : TimerTask() {
            override fun run() {

                getJobSeekerLocation();
            }
        }, 10000)
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        })
                        .create()
                        .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }
    }

    override fun onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    override fun onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    override fun onLocationChanged(location: Location) {


        var msg = "Updated Location: Latitude " + location.latitude.toString() + location.longitude;
        //txt_latitude.setText(""+location.latitude);
        //txt_longitude.setText(""+location.longitude);
        latLng = LatLng(location.latitude, location.longitude)
        if (showMap && latLng != null) {
            Toast.makeText(this, "$msg , $showMap", Toast.LENGTH_SHORT).show();


            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            //mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
            showMap = false;
        }
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();


    }

    override fun onConnected(p0: Bundle?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        startLocationUpdates();

        var fusedLocationProviderClient :
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient .getLastLocation()
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location
                        //txt_latitude.setText("" + mLocation.latitude)
                        //txt_longitude.setText("" + mLocation.longitude)
                        latLng = LatLng(location.longitude, location.longitude)
                        Log.i(TAG, "onConnected ${mLocation.latitude} , ${mLocation.longitude}")
                    }
                })
    }

    private fun checkLocation(): Boolean {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    protected fun startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    private fun getJobSeekerLocation() {
        if (latlngJobSeekerList.size != 0)
            latlngJobSeekerList.clear()

        latlngJobSeeker.latitude = 4.888
        latlngJobSeeker.longitude = 114.489
        latlngJobSeeker.jobseeker_id = "1"
        latlngJobSeeker.nickname = "Tai"

        latlngJobSeekerList.add(latlngJobSeeker)

        latlngJobSeeker.latitude = 7.888
        latlngJobSeeker.longitude = 113.489
        latlngJobSeeker.jobseeker_id = "2"
        latlngJobSeeker.nickname = "john"

        latlngJobSeekerList.add(latlngJobSeeker)

        latlngJobSeeker.latitude = 5.888
        latlngJobSeeker.longitude = 114.889
        latlngJobSeeker.jobseeker_id = "3"
        latlngJobSeeker.nickname = "kiu"

        latlngJobSeekerList.add(latlngJobSeeker)

        for (item in latlngJobSeekerList){
            mMap.addMarker(MarkerOptions().position(LatLng(item.latitude!!, item.longitude!!)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_light_green_900_24dp)))
        }
    }
}
