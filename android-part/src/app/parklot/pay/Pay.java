package app.parklot.pay;

public class Pay {
	  public int id;
	  public String carplate;
	  public String time;
	  public int total;
	  public String type;
      public String num;
	  public String park;
      
      
	  public Pay (int total, String carplate,String time,String type,String num,String park)
	  {
	    this.carplate = carplate;
	    this.time = time;
	    this.type=type;
	    this.total=total;
	    this.park = park;
this.num=num;
	    
	   
	    
	  }
	  
	  public Pay(){
		  this.carplate = "";
		    this.time = "";
		    
		    this.type="";
		    this.total=0;
             this.num="";
		  this.park="";
		  
		  
	  }
	  public String toString()
	  {
	    return id + ", " + carplate + ", " + time + ","+type+","+total+","+park;
	  }
}
