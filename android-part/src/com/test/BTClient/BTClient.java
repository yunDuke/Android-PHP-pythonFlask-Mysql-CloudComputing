package com.test.BTClient;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.test.BTClient.DeviceListActivity;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
//import android.view.Menu;            //��ʹ�ò˵����������
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import app.parklot.R;

@SuppressLint("NewApi")
public class BTClient extends Activity {
	
	private final static int REQUEST_CONNECT_DEVICE = 1;  
	
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";  
	
	private InputStream is;   

    
    int d = 0;
    EditText incode;
    private SharedPreferences settings;
    public String filename=""; 
    BluetoothDevice _device = null;    
    BluetoothSocket _socket = null;   
    boolean _discoveryFinished = false;    
    boolean bRun = true;
    boolean bThread = false;
	
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();   
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        incode = (EditText)findViewById(R.id.incode);
        settings = getSharedPreferences("loginPrefs", 0);
        String postid  = settings.getString("postid", "");
        String orderid  = settings.getString("orderid", "");
        Toast.makeText(getApplicationContext(), orderid+postid+"",1).show();
        if (_bluetooth == null){
        	Toast.makeText(this, "no bluetooth", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
  
      new Thread(){
    	   public void run(){
    		   if(_bluetooth.isEnabled()==false){
        		_bluetooth.enable();
    		   }
    	   }   	   
       }.start();      
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode){
    	case REQUEST_CONNECT_DEVICE:    
    		
            if (resultCode == Activity.RESULT_OK) {  
               
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);                  
                _device = _bluetooth.getRemoteDevice(address);          
                try{
                	_socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                }catch(IOException e){
                	Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
                }
            
            	Button btn = (Button) findViewById(R.id.Button03);
                try{
                	_socket.connect();
                	 d = 1;
                	Toast.makeText(this, "connect to"+_device.getName(), Toast.LENGTH_SHORT).show();
                	btn.setText("disconnect");
                }catch(IOException e){
                	try{
                		Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                		_socket.close();
                		_socket = null;
                	}catch(IOException ee){
                		Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                	}
                	
                	return;
                }
               
                try{
            		is = _socket.getInputStream(); 
            		}catch(IOException e){
            			Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
            			return;
            		}
            		if(bThread==false){
//            			ReadThread.start();
            			bThread=true;
            		}else{
            			bRun = true;
            		}
            }
    		break;
    	default:break;
    	}
    }
 
    Handler handler= new Handler(){
    	public void handleMessage(Message msg){
    		super.handleMessage(msg);
    	}
    };
    

    public void onDestroy(){
    	super.onDestroy();
    	if(_socket!=null)  
    	try{
    		_socket.close();
    	}catch(IOException e){}

    }
    
 
    public void onConnectButtonClicked(View v){ 
    	if(_bluetooth.isEnabled()==false){  
    		Toast.makeText(this, " open bluetooth", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
    	
    
    	Button btn = (Button) findViewById(R.id.Button03);
    	if(_socket==null){
    		Intent serverIntent = new Intent(this, DeviceListActivity.class); 
    		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE); 
    	}
    	else{
    	    try{	
    	    	is.close();
    	    	_socket.close();
    	    	_socket = null;
    	    	bRun = false;
    	    	btn.setText("connect");
    	    	d=0;
    	    }catch(IOException e){}   
    	}
    	return;
    }
    

   
    public void onQuitButtonClicked(View v){
    	finish();
    }
    
    public void set(View v){
    	if(d==0){
    		Toast.makeText(getApplicationContext(), "connect first", 1).show();}
    	else
    		{
    			Toast.makeText(getApplicationContext(), "caonidaye", 1).show();
    			//change vaue
    		}
    	}
    	
    	
    }
    
    
    
  