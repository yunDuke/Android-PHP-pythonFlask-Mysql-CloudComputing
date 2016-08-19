package app.parklot.pay;

import java.util.ArrayList;
import java.util.List;











import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.activities.Base;
import app.api.api;
import app.models.Parklot;
import app.parklot.R;
import app.paylist.db.dbmanager;
import app.user.Login;






public class add extends Base{
	
	
	
	
	private int target = 10000;
	private int total = 0;	
	private TextView     plateview;
	private TextView     timeview;	
	private NumberPicker amountpicker;
	private NumberPicker numpicker;
	private TextView     totaledit;
	private RadioGroup   type;
   private TextView numview;
   private SharedPreferences settings;
	 
	
	 
	public dbmanager dbmanager = new dbmanager(this);
	 
	 public static List <Pay> list    = new ArrayList<Pay>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		settings = getSharedPreferences("loginPrefs", 0);
	
	
	//	 timeview      = (TextView)     findViewById(R.id.timeedite);
		 numview      = (TextView)     findViewById(R.id.editText1);
		 amountpicker  = (NumberPicker) findViewById(R.id.amountpicker);
		
		 totaledit     = (TextView)     findViewById(R.id.price);
		 
	
		 dbmanager.open();
		 amountpicker.setMinValue(0);
		 amountpicker.setMaxValue(5);
	
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbmanager.close();
	}
	
	
	  public void reset(MenuItem item)
	  {
		  	dbmanager.reset();
			total = 0;
			
	  } 
	 public boolean newPay(Pay pay)
	 
	   
	    {
	      boolean targetAchieved = total > target;
	      if (!targetAchieved)
	      {
	    	dbmanager.add(pay);
	        list.add(pay);
	        
	      }
	      else
	      {
	        Toast toast = Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT);
	        toast.show();
	      }
	      return targetAchieved;
	    }
	
	public void check (View view) 
	  {		 
		 String plateview1 = plateview.getText().toString();	 
		 String timeview1 = timeview.getText().toString();		   
		    int amountprice =  amountpicker.getValue();

	        SharedPreferences.Editor editor = settings.edit();	
		       
		  //   editor.putInt("updatenum", bb);
	     
		     editor.commit();
		    
		    
		    
		   
		    	if ( plateview1==null||timeview1==null||amountprice == 0)
		    	 {
		    	 Toast toast = Toast.makeText(this, "Please fill in the blank", Toast.LENGTH_SHORT);
		    	 toast.show();
		    	
		    	 }
		    	 else
		    	 {
		    			
	   			     String title = settings.getString("title","" );
	   			     Log.i("title",title);
	   			//     String asd = ""+bb;
		    		//newPay(new Pay(total,plateview1,timeview1,method,asd,title));
		    		
		    		
		    			
	   			     String number = settings.getString("singleurl","" );
	   			     Log.i("url",number);
	   			    new GetAllTask(this).execute(number); 
		    		
		    	    }
		    
		    
		    
		    
		    	 
		    	 }
	
	
	
	 
	

//	public void onRadioButtonClicked(View view) {
//	 
//	    boolean checked = ((RadioButton) view).isChecked();
//	    int amountprice =  amountpicker.getValue();
////	    String nber = numview.getText().toString();
//        int  ber = numpicker.getValue();
//       if (ber <= 0){
//    	    Toast toast = Toast.makeText(this, "select number", Toast.LENGTH_SHORT);
//	    	 toast.show();
//       }
//	    switch(view.getId()) {
//	        case R.id.radio0:
//	            if (checked)
//	            	total = ber*amountprice * 3/2;
//	            break;
//	        case R.id.radio1:
//	            if (checked)
//	            	total = ber*amountprice * 2;
//	            break;
//	    }
//	    String totalDonatedStr = "€" + total;
//	    totaledit.setText(totalDonatedStr);
//	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu)
	  {
	    getMenuInflater().inflate(R.menu.list, menu);
	    return true;
	  }	
	public void logout(MenuItem item) {
		SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", 0).edit();
		editor.putBoolean("loggedin", false);
		editor.commit();

		startActivity(new Intent(add.this,Login.class)
		.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
		finish();
	}
	

	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item)
	  {
	    switch (item.getItemId())
	    {
	      case R.id.list: startActivity (new Intent(this, paylist.class));
	                                 break;
	   
	   
	    }
	    return true;
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
          	
			    int mm = settings.getInt("updatenum",0 );
             
//                l.num =l.num -mm;
        	
			   String number = settings.getString("singleurl","" );
               new parkTask(add.this).execute(number,l); 
    		   
    	  		   
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
	  
		
	  
	
	  
}
