package app.activities;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.api.api;
import app.models.Parklot;
import app.models.reply;
import app.parklot.Newp;
import app.parklot.R;
import app.parklot.start;
import app.parklot.pay.add;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;




public class lotsdetail extends parklot {
    double no;
	Parklot mParklot;
	boolean mShowMap;
	GoogleMap mMap;
	 public double lat;
	public double log;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	@SuppressWarnings("unused")
	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9002;
	private SharedPreferences settings;
	private ListView reply;
	public static List <reply> rep = new ArrayList<reply>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_parklots_detail);
		 
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mShowMap = GooglePlayServiceUtility.isPlayServiceAvailable(this) && initMap();
		settings = getSharedPreferences("loginPrefs", 0);
		  Intent intent = getIntent();
	      String bd = intent.getStringExtra("id");       
	      String t = "/listsss/"+bd;
	      Log.i("date",t);
	      SharedPreferences.Editor editor = settings.edit();	
		     editor.putString("singleurl", t);
		     editor.putString("postid", bd+"");
		  editor.commit();
		  Log.i("sdadsasdurl",settings.getString("singleurl",""));
		  
		  reply = (ListView) findViewById(R.id.listView1);
		  
	      new GetAllTask(this).execute(t); 
	      String uString = "/repliess/"+bd;
	      Log.i("sds", uString);
	      new getreply(this).execute(uString);
//	      GetAllTask result = new GetAllTask(this).execute(t);
	      
	      reply.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	            public boolean onItemLongClick(AdapterView<?> arg0, View v,
	                    int index, long arg3) {
	            	 final int id =  v.getId();
	            	 Log.i("shaid", id+"");
	            	  Intent intent = getIntent();
        		      final String bd = intent.getStringExtra("id");  
	            	final String itemid = String.valueOf(id);
	            	final String url = "/lists/"+bd;
	            	Log.i("date",url);
	            	AlertDialog.Builder builder = new AlertDialog.Builder(lotsdetail.this);
	            	builder.setTitle("Are U sure?");
	            	builder.setMessage("Delete?");
	                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

	            		  @Override
	            		public void onClick(DialogInterface dialog, int which) {
	            			  new DeletesTask(lotsdetail.this).execute("/movere/"+id); 
	            			 
	            			
	            			  
	            			  String uString = "/repliess/"+bd;
	            			      Log.i("sds", uString);
	            			     new getreply(lotsdetail.this).execute(uString);
	            		  }
	            		  });
	                        			
	                builder.setNegativeButton("No", null);	                
	            	builder.show();
	               return true;
	             
	            }
	            
	});
	      
	    
	}
	
	private class DeletesTask extends AsyncTask<String, Void, Void> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public DeletesTask(Context context)
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
		protected Void doInBackground(String... params) {

			try {
				api.delete((String) params[0]);
			}

			catch (Exception e) {
				Log.v("ASYNC", "ERROR : " + e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (dialog.isShowing())
				dialog.dismiss();
		}
		
	
		}
	
	
	private boolean initMap() {
		if (mMap == null) {
			MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
			mMap = mapFrag.getMap();
			
		}
		return (mMap != null);
	}

    @SuppressLint("SimpleDateFormat")
	public void reply(View v){
//    	  Intent intents = getIntent();
//    	  finish();
//    	  startActivity(intents);
	     Intent intent = getIntent();
	      String bd = intent.getStringExtra("id"); 
	      String id  = settings.getString("ownid", "");
	     EditText content = (EditText) findViewById(R.id.tell);
	     String con = content.getText().toString();
	   	     Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
	  Date currentLocalTime = cal.getTime();
	  DateFormat date = new SimpleDateFormat("HH:mm a"); 
	
	  date.setTimeZone(TimeZone.getTimeZone("GMT+1:00")); 
         
		 String localTime = date.format(currentLocalTime);

		
		
		
		  new chaTask(this).execute("/addreply",new reply(0,Integer.parseInt(id),Integer.parseInt(bd),con,localTime));
		  content.setText("");
	      String uString = "/repliess/"+bd;
	      Log.i("sds", uString);
	      new getreply(this).execute(uString);
	
	  
   }

   private class chaTask extends AsyncTask<Object, Void, String> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public chaTask(Context context)
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
			   res = api.jinqu((String)params[0], (reply) params[1]);
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
                 Log.i("sd",l.lon+"");
                 TextView tv = (TextView) findViewById(R.id.titleText);
       		     tv.setText(""+l.title);
       		     tv = (TextView) findViewById(R.id.price);
      		     tv.setText("€" + l.price);
                 String latitude = ""+l.lat;
                 String longitude = ""+l.lon;
                 String markerText =l.location;
                 String parking = l.allowingperiod;
//     		     tv = (TextView) findViewById(R.id.descText);
//      		     tv.setText(""+l.allowingperiod);
      		     SharedPreferences.Editor editor = settings.edit();
   			
   			     editor.putString("latitude", latitude);
   			     editor.putString("longitude", longitude);
   			     editor.putString("markerText",markerText);
   			     editor.putString("jiage",l.price);
   			     editor.putString("title",parking);
   			     editor.commit();

          	
  	    		     
                  
      		      if (mShowMap) {
     				
      			
      				CameraUpdate update = CameraUpdateFactory.newLatLngZoom(l.getLatLng(), 15);
      				mMap.moveCamera(update);
      				
      				String markerTitle = l.getLocation().equals("") ?
      						l.getTitle() :
      						l.getLocation();
      						
      				mMap.addMarker(new MarkerOptions()
      					.position(l.getLatLng())
      					.title(markerTitle)
      					.anchor(.5f, .5f)
      					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_starmarker))
      				);
      			}
      	  		   
				if (dialog.isShowing())
					dialog.dismiss();
			}

		  
			
			
		}
	  
	 
	  private class getreply extends AsyncTask<String, Void, List<reply>> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public getreply(Context context)
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
			protected List<reply> doInBackground(String... params) {

				try {
					return (List<reply>) api.showreply((String) params[0]);
				}

				catch (Exception e) {
					Log.v("ASYNC", "ERROR : " + e);
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<reply> result) {
				super.onPostExecute(result);

				rep = result;
				DonationAdapterqw adapter = new DonationAdapterqw(context,rep);
				reply.setAdapter(adapter);

				if (dialog.isShowing())
					dialog.dismiss();
			}
			
		
			}
			
	  
	  
	  
	  public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.menu_goto_action :

				sendToActionIntent();
				break;
			case android.R.id.home:

				finish();
				break;
			}
			return false;
		}
		
	  
	  
	  
		  @SuppressWarnings("deprecation")
		  public void sendToActionIntent() {
			 String latitude  = settings.getString("latitude", "");
			 String longitude = settings.getString("longitude", "");
			 String markerText  = settings.getString("markerText","");
		  	StringBuilder uri = new StringBuilder("geo:");
		  	double d=Double.parseDouble(latitude);
		  	double s=Double.parseDouble(longitude);
		  	Log.i("longitude",longitude);
		  	Log.i("latitude",latitude);
		  	Log.i("markerText",markerText);
		  	uri.append(d);
		  	uri.append(",");
		  	uri.append(s);
		  	uri.append("?z=15");		  	
		  	if (!markerText.equals("")) {
		  		uri.append("&q=" + URLEncoder.encode(markerText));
		  	}
		  	
		  	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));
		  	startActivity(intent);
		  }

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.direction, menu);
			return true;
		}

	
		 
		public void  reserval(View view) {
			 
	           
	            
		    
	    	
		    
			   startActivity(new Intent(lotsdetail.this,start.class));
			 
		}
		
	
	    public void build(DialogInterface dialog, int which) {
	  	  Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT).show();
	    

	  	  };
		
	  	  
	  	  
	  	class DonationAdapterqw extends ArrayAdapter<reply> {
			private Context context;
			public List<reply> rep;

			public DonationAdapterqw(Context context, List<reply> rep) {
				super(context, R.layout.rowforreply, rep);
				this.context = context;
				this.rep = rep;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				View view = inflater.inflate(R.layout.rowforreply, parent, false);
				reply repliyes = rep.get(position);
				
				TextView userTextView  = (TextView)view.findViewById(R.id.content);
				TextView amountView = (TextView) view.findViewById(R.id.time);
				
		       
				
				amountView.setText("" + repliyes.getTime());
				userTextView.setText("" +repliyes.getContent());
			
				 
				
				
		
				view.setId(repliyes.getId()); 
				
				return view;
			}
	  
	}
}

		
		




