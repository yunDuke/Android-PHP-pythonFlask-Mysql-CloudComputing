package app.activities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.api.api;
import app.parklot.Newp;
import app.parklot.R;
import app.parklot.pay.Pay;
import app.parklot.pay.paylist;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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




public class ownparklot extends Base implements OnItemClickListener {
	public int d;
	private ListView listView;
	public static List <Parklot> lots = new ArrayList<Parklot>();
	private SharedPreferences settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ownlot);

		listView = (ListView) findViewById(R.id.reportList);
		listView.setOnItemClickListener(this);
		settings = getSharedPreferences("loginPrefs", 0);
		 String id  = settings.getString("ownid", "");
		new GetAllTask(this).execute("/single/"+id); 
	
		 listView.setOnItemClickListener(new OnItemClickListener() {
	         @Override
	         public void onItemClick(AdapterView<?> parent, View view, int position,
	                 long id) {
	            d = view.getId();
	            String u = ""+d;
	            Log.i("date",u);
	            Intent intent = new Intent(ownparklot.this, lotsdetail.class);
	          
	    		intent.putExtra("id",u);
	    		
	    		
	    		startActivityForResult(intent, AppConstants.TOUR_DETAIL_ACTIVITY);
	             
	         }
	     });
		  listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	            public boolean onItemLongClick(AdapterView<?> arg0, View v,
	                    int index, long arg3) {
	            	 final int id =  v.getId();
	            	
	            	final String itemid = String.valueOf(id);
	            	final String url = "/movepo/"+itemid;
	            	Log.i("date",url);
	            	AlertDialog.Builder builder = new AlertDialog.Builder(ownparklot.this);
	            	builder.setTitle("Are U sure?");
	            	builder.setMessage("Delete?");
	                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

	            		  @Override
	            		public void onClick(DialogInterface dialog, int which) {
	            			  new DeleteTask(ownparklot.this).execute(url); 
	            			  settings = getSharedPreferences("loginPrefs", 0);
	            				 String id  = settings.getString("ownid", "");
	            				 new GetAllTask(ownparklot.this).execute("/single/"+id); 
	            		  }
	            		  });
	                        			
	                builder.setNegativeButton("No", null);	                
	            	builder.show();
	               return true;
	             
	            }
	            
	});
	}
	private class DeleteTask extends AsyncTask<String, Void, Void> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public DeleteTask(Context context)
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
			DonationsAdapter adapter = new DonationsAdapter(context,lots);
			listView.setAdapter(adapter);

			if (dialog.isShowing())
				dialog.dismiss();
		}
		
	
		}
		
	   
		public void  add(View view) {
			
			 startActivity(new Intent(ownparklot.this, Newp.class));
			 
		}

		
	


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		  d = view.getId();
          String u = ""+d;
          Log.i("baba",u);
          Intent intent = new Intent(ownparklot.this, lotsdetail.class);

  		intent.putExtra("id",u);
  		startActivityForResult(intent, AppConstants.TOUR_DETAIL_ACTIVITY);
           
		
	}


}





class DonationsAdapter extends ArrayAdapter<Parklot> {
	private Context context;
	public List<Parklot> lots;

	public DonationsAdapter(Context context, List<Parklot> lots) {
		super(context, R.layout.listforown, lots);
		this.context = context;
		this.lots = lots;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.listforown, parent, false);
		Parklot parking = lots.get(position);
		
		TextView userTextView  = (TextView)view.findViewById(R.id.textView2);
		TextView amountView = (TextView) view.findViewById(R.id.titleText);
		TextView methodView = (TextView) view.findViewById(R.id.numText);
		ImageView image = (ImageView) view.findViewById(R.id.imageView1);
       
//		
		amountView.setText("" + parking.location);
		methodView.setText("" +parking.allowingperiod);
	    userTextView.setText(""+parking.title);
		
	
		image.setImageResource(R.drawable.map_various);
		 
		
		
//		
		try{
	    String url = "http://178.62.86.129/web/static/img/imgweb/"+parking.ph.toString();
	    Log.i("data",url);
		new DownloadImageTask(image).execute(url);}
		catch (Exception e) {
			Log.v("ASYNC", "ERROR : " + e);
			e.printStackTrace();
		}
		

		view.setId(parking.id); 
		
		return view;
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

	    private ProgressDialog dialog;
	    private ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected void onPreExecute() {

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
	        //set image of your imageview
	        bmImage.setImageBitmap(result);
	        //close
	        dialog.dismiss();
	    }


	}
		
	
	}
	






	






