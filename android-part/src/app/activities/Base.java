package app.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;

import app.api.Rest;
import app.parklot.R;

import app.models.Parklot;
import app.user.Login;

public class Base extends Activity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Rest.setup();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Rest.shutDown();
	}
	
	
	
//	@Override
//	  public boolean onPrepareOptionsMenu (Menu menu){
//	      super.onPrepareOptionsMenu(menu);
//	      MenuItem report = menu.findItem(R.id.menuReport);
//	      MenuItem donate = menu.findItem(R.id.menuDonate);
//	      MenuItem reset = menu.findItem(R.id.menuReset);
//	      
//	      if(donations.isEmpty())
//	      {
//	           report.setEnabled(false);
//	           reset.setEnabled(false);
//	      }
//	      else {
//	    	  report.setEnabled(true); 
//	    	  reset.setEnabled(true);
//	      }
//	      if(this instanceof Donate){
//	    	  	donate.setVisible(false);
//	    	  	if(!donations.isEmpty())
//	    	  	{
//	    	          report.setVisible(true);
//	    	          reset.setEnabled(true);
//	    	  	}
//	      	}
//	      else {
//	    	  report.setVisible(false);
//	    	  donate.setVisible(true);
//	    	  reset.setVisible(false);
//	      	}
//	      
//	      return true;  
//	  }
	    
	  public void report(MenuItem item)
	  {
		  startActivity (new Intent(this, parklot.class));
	  }
	  
	 

	  public void reset(MenuItem item) {}
	  
	  public void logout(MenuItem item) {
			SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", 0).edit();
			editor.putBoolean("loggedin", false);
			editor.commit();

			startActivity(new Intent(Base.this,Login.class)
			.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
			finish();
		}


}
