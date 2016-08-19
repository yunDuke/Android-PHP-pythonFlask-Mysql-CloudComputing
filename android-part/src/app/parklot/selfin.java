package app.parklot;

import java.util.List;

import com.google.android.gms.internal.a;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.activities.Base;
import app.activities.parklot;
import app.api.api;
import app.map.first;
import app.models.Image;
import app.models.Parklot;
import app.models.User;
import app.user.Login;

public class selfin extends Base {
	private SharedPreferences settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registered);
		settings = getSharedPreferences("loginPrefs", 0);
	 String emails  = settings.getString("email", "");
	  String t = "/users/"+emails;
	  new GetAllTask(this).execute(t); 
	 
	  Button login = (Button) findViewById(R.id.login);
      login.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
              Intent myIntent = new Intent(view.getContext(), Login.class);
              startActivityForResult(myIntent, 0);
              finish();
          }

      });
   
     
    
	}
	  private class GetAllTask extends AsyncTask<String, Void, User> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public GetAllTask(Context context)
			{
				this.context = context;
			}
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();	
//				this.dialog = new ProgressDialog(context, 1);	
//				this.dialog.setMessage("Retrieving information");
//				this.dialog.show();
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
               User l = new User();
               l =result;
             
     		   
           
                TextView fname = (TextView)findViewById(R.id.fname);
    		   
    		    TextView lname = (TextView)findViewById(R.id.lname);
    		    TextView uname = (TextView)findViewById(R.id.uname);
    	        TextView email = (TextView)findViewById(R.id.email);
    	        TextView account = (TextView) findViewById(R.id.account);
    		   
    		    SharedPreferences.Editor editor = settings.edit();
                editor.putString("ownid", l.getId()+"");
                editor.putString("plate", l.getCartype()+"");
                editor.putString("username", l.getName());
                editor.putString("ac", l.account);
                editor.commit();
                
    		     fname.setText(l.getCarplate());
    		     lname.setText(l.getCartype());
    		     uname.setText(l.getName());
    		     email.setText(l.getEmail());
    		     account.setText("€"+ l.account);
    		 	
    		
    		 //    dialog.dismiss();
			}
		  
			
			
		}
	  
	 
		

	
}
