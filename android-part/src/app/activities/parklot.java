package app.activities;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSException;

import com.google.android.gms.maps.model.LatLng;

import app.api.api;
import app.parklot.Newp;
import app.parklot.R;
import app.parklot.pay.Pay;
import app.parklot.pay.paylist;
import app.user.Login;
import android.R.integer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.map.first;
import app.models.Parklot;




public class parklot extends Base implements OnItemClickListener {
	public int d;
	private ListView listView;
	public SharedPreferences settings;
	Parklot pole;
	int i;
	public static List <Parklot> lots = new ArrayList<Parklot>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lot);

		listView = (ListView) findViewById(R.id.reportList);
		listView.setOnItemClickListener(this);
		

		new GetAllTask(this).execute("/lists/"+"up"); 
	

		
		
		 listView.setOnItemClickListener(new OnItemClickListener() {
	         @Override
	         public void onItemClick(AdapterView<?> parent, View view, int position,
	                 long id) {
	            d = view.getId();
	            String u = ""+d;
	            Log.i("date",u);
	            Intent intent = new Intent(parklot.this, lotsdetail.class);
	          
	    		intent.putExtra("id",u);
	    		
	    		
	    		startActivityForResult(intent, AppConstants.TOUR_DETAIL_ACTIVITY);
	             
	         }
	     });
		    
	}


	
	
	private class GetAllTask extends AsyncTask<String, Void, List<Parklot>> {

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
			this.dialog.setMessage("Retrieving Parking Lots List");
			this.dialog.show();
		}

		@Override
		protected List<Parklot> doInBackground(String... params) {

			try {
				return (List<Parklot>) api.getAll((String) params[0]);
			}

			catch (Exception e) {
				Log.v("ASYNC", "ERROR : " + e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Parklot> result) {
			super.onPostExecute(result);

			lots = result;
		
			DonationttAdapter adapter = new DonationttAdapter(context,lots);
		
			listView.setAdapter(adapter);

			if (dialog.isShowing())
				dialog.dismiss();
		}
		
	
		}
		
	   
		public void  add(View view) {
			
			 startActivity(new Intent(parklot.this, Newp.class));
			 
		}

		
	


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		  d = view.getId();
          String u = ""+d;
          Log.i("baba",u);
          Intent intent = new Intent(parklot.this, lotsdetail.class);

  		intent.putExtra("id",u);
  		startActivityForResult(intent, AppConstants.TOUR_DETAIL_ACTIVITY);
           
		
	}


}





class DonationttAdapter extends ArrayAdapter<Parklot> {
	private Context context;
	public List<Parklot> lots;

	public DonationttAdapter(Context context, List<Parklot> lots) {
		super(context, R.layout.list, lots);
		this.context = context;
		this.lots = lots;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.list, parent, false);
		Parklot parking = lots.get(position);	
		TextView amountView = (TextView) view.findViewById(R.id.titleText);
		TextView methodView = (TextView) view.findViewById(R.id.numText);
		ImageView image = (ImageView) view.findViewById(R.id.imageView1);
	    TextView disTextView = (TextView) view.findViewById(R.id.distance);
		amountView.setText("" + parking.location);
		methodView.setText("" +parking.allowingperiod);
		SharedPreferences settings = context.getSharedPreferences("loginPrefs", 0);
		String jinduya = settings.getString("jindu", "");
		String weiduya = settings.getString("weidu", "");
		try{
	    String url = "http://178.62.86.129/web/static/img/imgweb/"+parking.ph.toString();

		new DownloadImageTask(image).execute(url);}
		catch (Exception e) {
			Log.v("ASYNC", "ERROR : " + e);
			e.printStackTrace();
		}
				  double theta = Double.parseDouble(weiduya) - parking.lon;
				    double dist = Math.sin(deg2rad(Double.parseDouble(jinduya))) * Math.sin(deg2rad(parking.lat))
				            + Math.cos(deg2rad(Double.parseDouble(jinduya))) * Math.cos(deg2rad(parking.lat))
				            * Math.cos(deg2rad(theta));
				    Log.i("distacne", dist+"");
				    dist = Math.acos(dist);
				    dist = rad2deg(dist);
				    dist = dist * 60 * 1.8;
				       dist=((int)(dist*100))/100;			       
				       NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
				       ddf1.setMaximumFractionDigits(2); 
				       String s= ddf1.format(dist) ; 		
				        disTextView.setText(s+"km");
				      
		view.setId(parking.id); 
		
		return view;
	}
	
	private double deg2rad(double deg) {
	    return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
	    return (rad * 180.0 / Math.PI);
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

	    private ProgressDialog dialog;
	    private ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected void onPreExecute() {
//
	    	this.dialog = new ProgressDialog(context, 1);	
			this.dialog.setMessage("Retrieving List");
			this.dialog.show();
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", "image download error");
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        
	        bmImage.setImageBitmap(result);
	        
	        dialog.dismiss();
	    }


	}
		
	
	}
	






	






