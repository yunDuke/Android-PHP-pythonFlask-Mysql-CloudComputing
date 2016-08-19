package app.map;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.user.Login;
import app.activities.parklot;
import app.parklot.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class first extends FragmentActivity implements 
  GooglePlayServicesClient.ConnectionCallbacks,
  GooglePlayServicesClient.OnConnectionFailedListener {
	
	
    private Button goButton;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	@SuppressWarnings("unused")
	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9002;
	GoogleMap mMap;
	private SharedPreferences settings;
	@SuppressWarnings("unused")
	  private static final double WIT_LAT = 52.246286,
                                   WIT_LNG = -7.139653;
	private static final float DEFAULTZOOM = 15;
	@SuppressWarnings("unused")
	private static final String LOGTAG = "Maps";
	
	LocationClient mLocationClient;
	Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = getSharedPreferences("loginPrefs", 0);
		if (servicesOK()) {
			setContentView(R.layout.first);

		  if (initMap()) {
			    mMap.setMyLocationEnabled(true);
				mLocationClient = new LocationClient(this, this, this);
				mLocationClient.connect();
				
			}
			else {
				Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT).show();
			}
		}
		else {
			setContentView(R.layout.first);
		}

	}
	
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
				getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		}
		else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}
		else {
			Toast.makeText(this, "Can't connect to Google Play services", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	private boolean initMap() {		
		if (mMap == null) {
			SupportMapFragment mapFrag =
					(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			mMap = mapFrag.getMap();		
			if (mMap != null) {
				mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {					
					@Override
					public View getInfoWindow(Marker arg0) {											
						return null;
					}					
					@Override
					public View getInfoContents(Marker marker) {
						View v = getLayoutInflater().inflate(R.layout.info, null);
						TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
						TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
						TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
						TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);						
						LatLng ll = marker.getPosition();						
						tvLocality.setText(marker.getTitle());
						tvLat.setText("Latitude: " + ll.latitude);
						tvLng.setText("Longitude: " + ll.longitude);
						tvSnippet.setText(marker.getSnippet());
						return v;											
					}							
				});	
			}
		}
		return (mMap != null);
	}
	
	private void gotoLocation(double lat, double lng,
			float zoom) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.moveCamera(update);
	}

	public void geoLocate(View v) throws IOException {
		  EditText et = (EditText) findViewById(R.id.editText1);
		  String location = et.getText().toString();
			
		  if (location.length() == 0) {
			Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
			return;	}
		  Geocoder gc = new Geocoder(this);
		  List<Address> list = gc.getFromLocationName(location, 1);
		  Address add = list.get(0);
		  String locality = add.getLocality();
		  Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
		  double lat = add.getLatitude();
		  double lng = add.getLongitude();
		  
		  gotoLocation(lat, lng, DEFAULTZOOM);	
		  setMarker(locality,add.getCountryName(),lat, lng);
		  SharedPreferences.Editor editor = settings.edit();	
		  editor.putString("jindu", lat+"");
		  editor.putString("weidu", lng+"");
          editor.commit();	
			
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
		case R.id.mapTypeNormal:
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapTypeSatellite:
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.mapTypeTerrain:
			mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		
		case R.id.gotoCurrentLocation:
			gotoCurrentLocation();
			break;
		default:
			break;
			
		}

		return super.onOptionsItemSelected(item);
	}
	
	public void logout(MenuItem item) {
		SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", 0).edit();
		editor.putBoolean("loggedin", false);
		editor.commit();

		startActivity(new Intent(first.this,Login.class)
		.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
		finish();
	}
	@Override
	protected void onStop() {
		super.onStop();
		MapStateManager mgr = new MapStateManager(this);
		mgr.saveMapState(mMap);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MapStateManager mgr = new MapStateManager(this);
		CameraPosition position = mgr.getSavedCameraPosition();
		if (position != null) {
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
			mMap.moveCamera(update);
			mMap.setMapType(mgr.getSavedMapType());
		}
		
	}

	protected void gotoCurrentLocation() {
		Location currentLocation = mLocationClient.getLastLocation();
		if (currentLocation == null) {
			Toast.makeText(this, "Current location isn't available", Toast.LENGTH_SHORT).show();
		}
		else {
			LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULTZOOM);
			mMap.animateCamera(update);
		}
		
		setMarker("Current location", "",
				currentLocation.getLatitude(), 
				currentLocation.getLongitude());
		Log.i("sha",currentLocation.getLatitude()+"");
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
	}

	@Override
	public void onConnected(Bundle arg0) {
		
	}

	@Override
	public void onDisconnected() {
	}

	private void setMarker(String locality, String country,double lat, double lng) {
		
		if (marker != null) {
			marker.remove();
		}

		MarkerOptions options = new MarkerOptions()
			.title(locality)
			.position(new LatLng(lat, lng))			
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mapmarker));
		   if(country.length()>0){
			   
			   options.snippet(country);
			   
		   }
		
		marker = mMap.addMarker(options);
	
	}
	    
	public void  search(View view) {
		 String location = settings.getString("location","" );
		 Log.i("locations",location);
		if(location ==null){
			Toast.makeText(this, "Please Input your location ", Toast.LENGTH_SHORT).show();
			
		}
		 
		else if(location!=null){ startActivity(new Intent(first.this, parklot.class));
			
		 }
		 
	}

	

}