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
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import app.activities.Base;
import app.activities.ordershow;
import app.activities.ownparklot;
import app.activities.parklot;
import app.activities.tab;
import app.api.api;
import app.map.first;
import app.models.Image;
import app.models.Parklot;
import app.models.User;
import app.models.order;
import app.models.product;
import app.parklot.pay.add;


public class start  extends Base {

	Button set;
	Button saveButton;
	TextView ordertime;
	EditText period;
	TextView price;
	EditText plate;
	 TimePicker pickerTime;
	 DatePicker pickerDate;
	TextView pl;
	private NumberPicker amountpicker;
	private SharedPreferences settings;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 
				setContentView(R.layout.add);
				ordertime = (TextView) findViewById(R.id.showtime);
				price = (TextView) findViewById(R.id.price);
			
			
			   amountpicker  = (NumberPicker) findViewById(R.id.amountpicker);
			    amountpicker.setMinValue(0);
				 amountpicker.setMaxValue(5);
	}


	
	public void check(View v){
		  settings = getSharedPreferences("loginPrefs", 0);
		   String plates  = settings.getString("plate", "");
		   String timee  = settings.getString("m", "");
	     
	
			String id  = settings.getString("postid", "");
			//String ownid  = settings.getString("ownid", "");
			  // Log.i("youmeiyou a ",plates + id);
			String danjia  = settings.getString("jiage", "");
			String yue  = settings.getString("ac", "");
			int per = amountpicker.getValue();
		    Double q =Double.parseDouble(danjia);
		    Double qian = Double.parseDouble(yue);
		   
		    ordertime.setText(timee);
		    Double sd = per*q;
		    Log.i("jiage", sd+"");
		    order p = new order();
		    price.setText(sd+"");
		    p.ordertime = timee;
		    p.period = per+"";
		    p.price = sd+"";
			if (qian>=sd){	
				Toast.makeText(getApplicationContext(),	plates.substring(plates.length()-4,plates.length()), 1).show();
				String ors  = settings.getString("d", "");
				 String m  = settings.getString("mo", "");
			
				
				 String h  = settings.getString("h", "");
				 
				new InsertTask(this).execute("/addorder",new order(0,"",plates,timee,Integer.parseInt(id),per+"",sd+"","",plates.substring(plates.length()-4,plates.length())+m+ors+h,"reserved"));				
			    new GetAllTask(this).execute("/listsss/"+id+""); 
			    String ownid  = settings.getString("ownid", "");
			    Double haiyou = qian-sd;
			    SharedPreferences.Editor editor = settings.edit();
	   			
  			    editor.putString("meiqian", haiyou+"");
  			    editor.commit();
  			    
  			  String emails  = settings.getString("email", "");
  			  String t = "/users/"+emails;
			     new GetAlluserTask(this).execute(t);
				 startActivity(new Intent(start.this, tab.class));				
			}
			else{
				Toast.makeText(this, "not enough money", 1).show();
				
			}
			
		}

	
	 private class GetAllTask extends AsyncTask<String, Void, Parklot> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public GetAllTask(Context context)
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
			protected Parklot doInBackground(String... params) {

				try {
					return (Parklot) api.get((String) params[0]);
				}

				catch (Exception e) {
					Log.v("ASYNC", "ERROR : " + e);
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Parklot result) {
				super.onPostExecute(result);
               Parklot l = new Parklot();
                l =result;
       	
			  String sd = "reserved";
                l.polestate = sd;
                 String id  = settings.getString("postid", "");
			 //   String number = settings.getString("singleurl","" );
                new parkTask(start.this).execute("/chlists/"+id+"",l); 
 		   
 	  		   
				if (dialog.isShowing())
					dialog.dismiss();
			}

			
			
		}
	
	 
	 
	 
	 
	 
	
	 private class parkTask extends AsyncTask<Object, Void, String> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public parkTask(Context context)
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
				   res = api.addnew((String)params[0], (Parklot) params[1]);
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
	  
	 private class GetAlluserTask extends AsyncTask<String, Void, User> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public GetAlluserTask(Context context)
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
			protected User doInBackground(String... params) {

				try {
					return (User) api.getone((String) params[0]);
				}

				catch (Exception e) {
					Log.v("ASYNC", "ERROR : " + e);
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(User result) {
				super.onPostExecute(result);
                 User t = new User();
                 t =result;
             String id  = settings.getString("ownid", "");
             String number = settings.getString("meiqian","" );
             Log.i("youqianma", number);
             Log.i("youidma", id+"");
			  //  Double.parseDouble(number);
            t.account = number;
  	
             new Update(start.this).execute("/yonghu/"+id+"",t);   
				if (dialog.isShowing())
					dialog.dismiss();
			}

			
			
		}
	 private class Update extends AsyncTask<Object, Void, String> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public Update(Context context)
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
				   res = api.newuser((String)params[0], (User) params[1]);
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
			   res = api.start((String)params[0], (order) params[1]);
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.up) {
			final Dialog dialog3 = new Dialog(this);
			dialog3.setContentView(R.layout.timeset);
			dialog3.setTitle("set the time");
			pickerDate = (DatePicker)dialog3.findViewById(R.id.datePicker1);
			pickerTime = (TimePicker)dialog3.findViewById(R.id.timePicker1);
			
			Calendar now = Calendar.getInstance();

			pickerDate.init(
					now.get(Calendar.YEAR), 
					now.get(Calendar.MONTH), 
					now.get(Calendar.DAY_OF_MONTH), 
					null);
			
			pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
			pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));
			final EditText etEditText =(EditText)dialog3.findViewById(R.id.editText1);
			
			Button dialogButton = (Button) dialog3.findViewById(R.id.save);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Calendar current = Calendar.getInstance();
				
					Calendar cal = Calendar.getInstance();
					cal.set(pickerDate.getYear(), 
							pickerDate.getMonth(), 
							pickerDate.getDayOfMonth(), 
							pickerTime.getCurrentHour(), 
							pickerTime.getCurrentMinute(), 
							00);
					
					String word = etEditText.getText().toString();
					if(cal.compareTo(current) <= 0){
					
					    Toast.makeText(getApplicationContext(), 
					    		"you can not do that!", 
					    		Toast.LENGTH_LONG).show();
					}else{
//						 SharedPreferences.Editor editor = settings.edit();
//						 SharedPreferences.Editor editor = settings.edit();
//						  Log.i("i",cal.getTime().getHours()+"");
//						  editor.putString("m", cal.getTime().getMinutes()+"");
//						  editor.putString("mo", cal.getTime().getMonth()+"");
//						  editor.putString("d", cal.getTime().getDate()+"");
//						  editor.putString("w", word);
//						  editor.putString("s", "woshigetiancans");
//						  editor.commit();
						//setAlarm(cal);
					    	settings = getSharedPreferences("loginPrefs", 0);
					    	
						    SharedPreferences.Editor editor = settings.edit();
						    editor.putString("m", cal.getTime()+"");
						    editor.putString("mo", cal.getTime().getMonth()+"");
						    editor.putString("h", cal.getTime().getHours()+"");
						    editor.putString("d", cal.getTime().getDate()+"");
						    Toast.makeText(getApplicationContext(), cal.getTime().getHours()+"", 1).show();
						    editor.commit();
						    
						    String timee  = settings.getString("m", "");
						    ordertime.setText(timee);
					}
					dialog3.dismiss();
				}
			});
			dialog3.show();
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	 

}
	


