package app.options;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import app.api.api;
import app.models.Parklot;
import app.parklot.R;
import app.parklot.pay.add;
import app.parklot.pay.paylist;
import app.user.Register;

public class delete extends Activity {


	
	private SharedPreferences settings;

	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 Intent intent = getIntent();
	      String bd = intent.getStringExtra("id");       
	      String t = "/lots/"+bd+".json";
	      Log.i("date22222",t);
	      settings = getSharedPreferences("loginPrefs", 0);
	      SharedPreferences.Editor editor = settings.edit();
 			
		     editor.putString("dizhi", t);
		     editor.commit();
		
		setContentView(R.layout.delete);
		
		
		
	}

	public void delete(View v) {
		startActivity (new Intent(this, paylist.class));
		    String dizhi = settings.getString("dizhi","" );
		     Log.i("dizhiyoumuyou",dizhi);
		  new GetAllTask(this).execute(dizhi); 
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
//       	     String babab = l.title;
//       	     Log.i("baba",babab);
//			  int mm = settings.getInt("updatenum",0 );
//			  String mmmm=""+mm;
//               Log.i("mm",mmmm);
//              l.num =l.num + mm;
//              String mmmmmm=""+l.num;
//              Log.i("mmmm",mmmmmm);
//     	      
			  String number = settings.getString("singleurl","" );
         new parkTask(delete.this).execute(number,l); 
 		   
 	  		   
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
