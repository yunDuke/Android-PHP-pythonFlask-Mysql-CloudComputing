package app.activities;

import com.test.BTClient.BTClient;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import app.map.first;
import app.parklot.Newp;
import app.parklot.R;
import app.parklot.selfin;
import app.parklot.start;
import app.user.Registered;

public class tab extends TabActivity {
   
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        TabHost tabHost = getTabHost();
         
       
        TabSpec photospec = tabHost.newTabSpec("info");
      
        photospec.setIndicator("Info", getResources().getDrawable(R.drawable.icon_photos_tab));
        Intent photosIntent = new Intent(this, selfin.class);
        photospec.setContent(photosIntent);
         
       
        TabSpec songspec = tabHost.newTabSpec("lists");        
        songspec.setIndicator("mypoles", getResources().getDrawable(R.drawable.icon_songs_tab));
        Intent songsIntent = new Intent(this, ownparklot.class);
        songspec.setContent(songsIntent);
         
  
        TabSpec videospec = tabHost.newTabSpec("map");
        videospec.setIndicator("Map", getResources().getDrawable(R.drawable.icon_videos_tab));
        Intent videosIntent = new Intent(this, first.class);
        videospec.setContent(videosIntent);
         
        
        TabSpec orderspec = tabHost.newTabSpec("order");
        orderspec.setIndicator("order", getResources().getDrawable(R.drawable.icon_videos_tab));
        Intent orderIntent = new Intent(this, ordershow.class);
        orderspec.setContent(orderIntent);
         
        
        tabHost.addTab(photospec); 
        tabHost.addTab(songspec);
        tabHost.addTab(videospec); 
        tabHost.addTab(orderspec); 
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
		if (id == R.id.connect) {
			
			   startActivity(new Intent(this,BTClient.class));
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}