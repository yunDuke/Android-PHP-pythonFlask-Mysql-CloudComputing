package app.activities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.test.BTClient.BTClient;

import app.api.api;
import app.parklot.Newp;
import app.parklot.R;
import app.parklot.start;
import app.parklot.pay.Pay;
import app.parklot.pay.paylist;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import app.map.first;
import app.models.Parklot;
import app.models.order;




public class ordershow extends Base implements OnItemClickListener {
	public int d;
	private SharedPreferences settings;
	private ListView listView;
	public static List <order> show = new ArrayList<order>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paylist);
		 
		listView = (ListView) findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		settings = getSharedPreferences("loginPrefs", 0);
		 String plates  = settings.getString("plate", "");
		new GetAllTask(this).execute("/cost/"+plates); 
	
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                    int index, long arg3) {
            	 final int id =  v.getId();
            	 Log.i("shaid", id+"");
            	  Intent intent = getIntent();
    		      final String bd = intent.getStringExtra("id");  
            	final String itemid = String.valueOf(id);
            	final String url = "/listsss/"+bd;
            	Log.i("date",url);
            	AlertDialog.Builder builder = new AlertDialog.Builder(ordershow.this);
            	builder.setTitle("Are U sure?");
            	builder.setMessage("choose option");
                builder.setPositiveButton("delete",new DialogInterface.OnClickListener() {

            		  @Override
            		public void onClick(DialogInterface dialog, int which) {
            			  
            			  new DeleteTask(ordershow.this).execute("/moveor/"+id);            		
            			  String plates  = settings.getString("plate", "");
            			  new GetAllTask(ordershow.this).execute("/cost/"+plates);
            				String postid  = settings.getString("postid", "");
            			  new GetAllpostTask(ordershow.this).execute("/listsss/"+postid+""); 
            		  }
            		  });
                        			
                builder.setNegativeButton("No", null);	
                builder.setNeutralButton("connect",new DialogInterface.OnClickListener() {

          		  @Override
          		public void onClick(DialogInterface dialog, int which) {
          			  
          			//  new DeleteTask(ordershow.this).execute("/moveor/"+id);            		
          			  String plates  = settings.getString("plate", "");
          			//  new GetAllTask(ordershow.this).execute("/cost/"+plates);
          				String postid  = settings.getString("postid", "");
          			//  new GetAllpostTask(ordershow.this).execute("/listsss/"+postid+""); 
          			   	settings = getSharedPreferences("loginPrefs", 0);
				    	
					    SharedPreferences.Editor editor = settings.edit();
					    editor.putString("orderid", id+"");
					   // editor.putString("mo", cal.getTime().getMonth()+"");

					    editor.commit();
          				
          				
          				
          				
          			 startActivity(new Intent(ordershow.this,BTClient.class));
          		  }
          		  });
            	builder.show();
               return true;
             
            }
            
});
	           
	}
	
	 private class GetAllpostTask extends AsyncTask<String, Void, Parklot> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public GetAllpostTask(Context context)
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
    	
			  String sd = "up";
             l.polestate = sd;
              String id  = settings.getString("postid", "");
			 //   String number = settings.getString("singleurl","" );
             new parkTask(ordershow.this).execute("/chlists/"+id+"",l); 
		   
	  		   
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
	
	
	
	

	private class GetAllTask extends AsyncTask<String, Void, List<order>> {

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
		protected List<order> doInBackground(String... params) {

			try {
				return (List<order>) api.showorder((String) params[0]);
			}

			catch (Exception e) {
				Log.v("ASYNC", "ERROR : " + e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<order> result) {
			super.onPostExecute(result);

			show = result;
			DonationAdapter adapter = new DonationAdapter(context,show);
			listView.setAdapter(adapter);

			if (dialog.isShowing())
				dialog.dismiss();
		}
		
	
		}
		
	   
	
	
	
	 
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	class DonationAdapter extends ArrayAdapter<order> {
		private Context context;
		public List<order> show;

		public DonationAdapter(Context context, List<order> show) {
			super(context, R.layout.rowfororder, show);
			this.context = context;
			this.show = show;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View view = inflater.inflate(R.layout.rowfororder, parent, false);
			order der = show.get(position);
			
			TextView userTextView  = (TextView)view.findViewById(R.id.textView2);
			TextView amountView = (TextView) view.findViewById(R.id.textView4);
			TextView methodView = (TextView) view.findViewById(R.id.textView6);
			TextView polestate = (TextView) view.findViewById(R.id.polestate);
	       TextView timeTextView = (TextView)view.findViewById(R.id.textView7);
	       timeTextView.setText(""+der.aucode);
		
			amountView.setText("" + der.period);
			methodView.setText("" +der.price);
		    userTextView.setText(""+der.ordertime);
			polestate.setText(""+der.polestate);
		
		
			 
			
			

			

			view.setId(der.id); 
			
			return view;
		}
		
	}
}	