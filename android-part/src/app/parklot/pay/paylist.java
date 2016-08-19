package app.parklot.pay;


import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.activities.AppConstants;
import app.models.Parklot;
import app.options.delete;
import app.parklot.R;



public class paylist extends add {
	
	 
	  private ListView listView;
	  private RadioGroup   radioGroup1;
	  public add app;
	 private EditText Time;
	 private EditText hour;
	 private SharedPreferences settings;
	 EditText searchView;
	 
	 private Button searchButton;
	 public int d;
	  @Override
	  public void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.paylist);
	  //  searchButton = (Button) findViewById(R.id.button);
	    listView = (ListView) findViewById(R.id.list_view);

	    settings = getSharedPreferences("loginPrefs", 0);
		itemAdapter adapter = new itemAdapter(this, dbmanager.getAll());
	    listView.setAdapter(adapter);
	    addListener1();
	    
	    
	  
//
//	    listView.setOnItemClickListener(new OnItemClickListener() {
//	         @Override
//	         public void onItemClick(AdapterView<?> parent, View view, int position,
//	                 long id) {
//	            d = view.getId();
//	            String u = ""+d;
//	            Log.i("date",u);
//	            Intent intent = new Intent(paylist.this, delete.class);
//	          
//	    		intent.putExtra("id",u);
//	    		
//	    		
//	    		startActivityForResult(intent, AppConstants.TOUR_DETAIL_ACTIVITY);
//	             
//	         }
//	     });
	    
	      
	    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                    int index, long arg3) {
            	 final int id =  v.getId();
            	   
            	final String itemid = String.valueOf(id);
            	 Toast.makeText(paylist.this, ""+itemid,
 						Toast.LENGTH_SHORT).show();
            	AlertDialog.Builder builder = new AlertDialog.Builder(paylist.this);
            	builder.setTitle("Are U sure?");
            	builder.setMessage("two options");
                builder.setPositiveButton("delete",new DialogInterface.OnClickListener() {

            		  @Override
            		public void onClick(DialogInterface dialog, int which) {
           		    
                     dbmanager.delete(itemid);
                  
                     itemAdapter adapter = new itemAdapter(paylist.this, dbmanager.getAll());
                	    listView.setAdapter(adapter);
                	    String number = settings.getString("singleurl","" );
   	   			      Log.i("meiyoudizhihahah",number);
   	   			     
                	  
            		  }
            		  

            		  });
               
          			
             
            	builder.show();
               return true;
             
            }
            
});
	    
	    
	    
	    
	    
	        }    
	   
	  
	  

	  
	  

	 
	  private void addListener1() {
		  searchButton.setOnClickListener(new OnClickListener()
	        {
	            @Override
	            public void onClick(View v)
	            {   
	            	String searchinfo = searchView.getText().toString();
	            	
	                AlertDialog.Builder builder = new AlertDialog.Builder(paylist.this);
	               
	                Pay p = dbmanager.search(searchinfo);
	                if (p!=null){
	                builder.setMessage("Your Car Plate Is :"+p.carplate
	                		         + ", Total Fee is :€"+p.num);
	               
	                builder.setNegativeButton( "Ok", null);
	        
	             
	             
	               }
	                else {
	                	builder.setMessage("No product exist");
	                	builder.setNegativeButton( "Ok", null);        	
	                }
	                builder.show();
	            }
	        });
		
	}








	@Override
	  public boolean onCreateOptionsMenu(Menu menu)
	  {
	    getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	  }	
	  
	  public void reset(MenuItem item) {
		  dbmanager.reset();
	  }
	  
	  
}
class itemAdapter extends ArrayAdapter<Pay>
{
  private Context        context;
  public  List<Pay>     list;

  public itemAdapter(Context context, List<Pay> list)
  {
    super(context, R.layout.item, list);
    this.context   = context;
    this.list = list;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
    View     view       = inflater.inflate(R.layout.item, parent, false);
     Pay pay = list.get(position);
    TextView textView1 = (TextView) view.findViewById(R.id.plateView);
    TextView textView2 = (TextView) view.findViewById(R.id.timeView);
    TextView textView3 = (TextView) view.findViewById(R.id.typeView);
    TextView textView4 = (TextView) view.findViewById(R.id.numView);
    TextView textView5 = (TextView) view.findViewById(R.id.totalView);
    TextView textView6 = (TextView) view.findViewById(R.id.titleView);
    textView1.setText("" + pay.carplate);
    textView2.setText("" + pay.time);
    textView3.setText("" + pay.type);
    textView5.setText("" + pay.num);
    textView4.setText(Integer.toString(pay.total));
    textView6.setText("" + pay.park);
    view.setId(pay.id);
    return view;
  }

  @Override
  public int getCount()
  {
    return list.size();
  }
}
	    
	  
