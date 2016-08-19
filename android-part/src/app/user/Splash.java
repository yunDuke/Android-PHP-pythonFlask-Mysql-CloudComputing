package app.user;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import app.parklot.R;

public class Splash extends Activity {
    // used to know if the back button was pressed in the splash screen activity 
	// and avoid opening the next activity
    private boolean 			mIsBackButtonPressed;
    private static final int 	SPLASH_DURATION = 2000; // 2 seconds
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler handler = new Handler();
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // make sure we close the splash screen so the user 
            	// won't come back when it presses back key
                finish();
                 
                if (!mIsBackButtonPressed) {
                    // start the home screen if the back button wasn't pressed already 
                    Splash.this.startActivity(new Intent(Splash.this, Login.class));
               }       
            }
        }, SPLASH_DURATION); // time in milliseconds to delay call to run()
    }
 
    @Override
   public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }
}
