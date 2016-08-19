package app.models;

import com.google.android.gms.internal.t;

public class order {
	  public int id;
	  public String currenttime;
	  public String ordername;
	  public String ordertime;
	  public int post_id;
	  public String period;
      public String price;
      public String incode;
      public String aucode;
     public String polestate;
        
	  public order (int id, String currenttime,String ordername,String ordertime,int post_id,String period,String price,String incode, String aucode,String polestate)
	  {
	
	  this.id = id;
	  this.currenttime = currenttime;
	  this.ordername = ordername;
	  this.ordertime = ordertime;
	  this.post_id = post_id;
	  this.period = period;
	  this.price = price;
	  this.incode = incode;
	  this.aucode = aucode;
	  this.polestate = polestate;
	    
	  }
	  
	  public order(){
		  this.id = 0;
		    this.currenttime = "";
		    this.ordertime = "";
		    this.ordername="";
		    this.post_id=0;
             this.period="";
		  this.price="";
		  this.aucode="";
		  this.incode="";
		  this.polestate="";
		  
	  }

}

