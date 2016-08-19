package app.parklot;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.activities.Base;
import app.activities.ownparklot;
import app.activities.parklot;
import app.activities.tab;
import app.api.api;
import app.map.first;
import app.models.Image;
import app.models.Parklot;
import app.models.product;

public class Newp  extends FragmentActivity implements 
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	public EditText time;
	public EditText price;
	public EditText location;
	public EditText code;
	Button set;
	Button saveButton;
	private SharedPreferences settings;
	LocationClient mLocationClient;
	Marker marker;
	private static final float DEFAULTZOOM = 15;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	@SuppressWarnings("unused")
	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9002;
	GoogleMap mMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		 if (servicesOK()) {
				setContentView(R.layout.activity_newp);

			  if (initMap()) {
				    mMap.setMyLocationEnabled(true);
					mLocationClient = new LocationClient(this, this, this);
					mLocationClient.connect();

					time = (EditText) findViewById(R.id.editText1);
					price = (EditText) findViewById(R.id.editText2);
					location =(EditText) findViewById(R.id.editText3);

					 code = (EditText) findViewById(R.id.code);
					 Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
					 Date currentLocalTime = cal.getTime();
					 DateFormat date = new SimpleDateFormat("HH:mm a"); 
					 // you can get seconds by adding  "...:ss" to it
					 date.setTimeZone(TimeZone.getTimeZone("GMT+1:00")); 

					 String localTime = date.format(currentLocalTime);
			    	 Toast.makeText(getApplicationContext(), localTime+"", 1).show();
				}
				else {
					Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT).show();
				}
			}
			else {
				setContentView(R.layout.activity_newp);
			}

		 
		 
		 
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
	
	
	
	public void set(View v) {
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

	    SharedPreferences.Editor editor = settings.edit();
        editor.putString("lat",currentLocation.getLatitude()+"");
        editor.putString("lon",currentLocation.getLongitude()+"");
      
        
        editor.commit();
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
	    
	
	

	
	public void search(View v){
		String unique = code.getText().toString();
		Log.i("code", "/products/"+unique);
		new GetproductTask(this).execute("/products/"+unique); 
		

	}
	
	public void save(View v){
	    String timee = time.getText().toString();
		String pricee = price.getText().toString();
		String locationn = location.getText().toString();
	   
		
		if(!timee.equals("")&&!pricee.equals("")&&!locationn.equals("")){
			
			Parklot p = new Parklot();
			p.allowingperiod = timee;
			p.price = pricee;
			p.location = locationn;
		
			settings = getSharedPreferences("loginPrefs", 0);
			String id  = settings.getString("ownid", "");
			String ps  = settings.getString("password", "");
		
	    	int userid = 	Integer.parseInt(id);
	    	 String plates  = settings.getString("plate", "");
	    	 String i1  = settings.getString("img1", "");
	    	 String i2  = settings.getString("img2", "");
	    	 String i3  = settings.getString("img3", "");
	    	 String un  = settings.getString("username", "");
	    	 String lat  = settings.getString("lat", "");
	    	 Log.i("shenmejiba", lat+"");
	    	 String lng  = settings.getString("lon", "");
	    	 String zhao  = settings.getString("photo", "");
	    	 Double latiDouble = Double.parseDouble(lat);
	    	  Double longi =  Double.parseDouble(lng);
	    	 Log.i("latityde", lat+"");
	    	 Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
			 Date currentLocalTime = cal.getTime();
			 DateFormat date = new SimpleDateFormat("HH:mm:ss a"); 
		
			 date.setTimeZone(TimeZone.getTimeZone("GMT+1:00")); 

			 String localTime = date.format(currentLocalTime);
			new InsertTask(this).execute("/listsadd",new Parklot(0,timee,plates,userid,ps,"",pricee,i1,i2,i3,locationn,latiDouble,longi,localTime,un,zhao));
		
			 startActivity(new Intent(Newp.this, tab.class));
		}
	}
	
	
	private class InsertTask extends AsyncTask<Object, Void, String> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public InsertTask(Context context)
		{
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			this.dialog = new ProgressDialog(context, 1);	
			this.dialog.setMessage("Retrieving Parking Lots List");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {

			String res = null;
			try {
			   res = api.insert((String)params[0], (Parklot) params[1]);
				}
			
		  	catch(Exception e)
		      {
		    	Log.v("lot","ERROR : " + e);
		    	e.printStackTrace();
		      }
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (dialog.isShowing())
				dialog.dismiss();
		}
		
	
		}


	  private class GetproductTask extends AsyncTask<String, Void, product> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public GetproductTask(Context context)
			{
				this.context = context;
			}
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();	
				this.dialog = new ProgressDialog(context, 1);	
				this.dialog.setMessage("Retrieving Parkings List");
				this.dialog.show();
			}

			@Override
			protected product doInBackground(String... params) {

				try {
					return (product) api.getsingle((String) params[0]);
					
				}

				catch (Exception e) {
					Log.v("ASYNC", "ERROR : " + e);
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(product result) {
				 super.onPostExecute(result);
               product l = new product();
               l =result;
               
               if (l.getUniquecode()==null){
              
               Toast.makeText(context, "inpuut a right code", 1).show();
               }
               else{
            	   Log.i("sd",l.getUniquecode());
                   TextView tv = (TextView)findViewById(R.id.textView4);
                   tv.setText(l.getUniquecode());
                   TextView pa = (TextView)findViewById(R.id.textView5);
                   pa.setText(l.getPassword());
                   
                   TextView plate = (TextView)findViewById(R.id.textView7);
                   settings = getSharedPreferences("loginPrefs", 0);
                   String plates  = settings.getString("plate", "");
                   SharedPreferences.Editor editor = settings.edit();
                   editor.putString("password", l.getPassword());
                   editor.putString("img1", l.getImage());
                   editor.putString("img2", l.getImage2());
                   editor.putString("img3", l.getImage3());
                   editor.putString("photo", l.getPhoto());
                  
                 
                   editor.commit();
                   plate.setText(plates);
            	  
            	   
               }
   			if (dialog.isShowing())
   				dialog.dismiss();
		}
	  
	 
	  }


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	

}
	


