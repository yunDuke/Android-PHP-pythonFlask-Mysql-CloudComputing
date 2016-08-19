package app.models;

import java.net.URLEncoder;

import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class Parklot
{
	  public int 	id;
	  public String allowingperiod;
	  public String content;
	  public String polepassword;
	  public String polestate;
	  public String price;
	  public String img1;
	  public String img2;
	  public String img3;
	  public int user_id;
	  public double lat;
	  public double lon;
	  public String location;
	  public String time ;
	  public String title;
	  public String ph;
	  
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getPolepassword() {
		return polepassword;
	}
	public void setPolepassword(String polepassword) {
		this.polepassword = polepassword;
	}
	public String getPolestate() {
		return polestate;
	}
	public void setPolestate(String polestate) {
		this.polestate = polestate;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAllowingperiod() {
		return allowingperiod;
	}
	public void setAllowingperiod(String allowingperiod) {
		this.allowingperiod = allowingperiod;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	} 
	 public Parklot (int id, String allowingperiod,String content,int user_id,String polepassword,String polestate,String price,String img1,String img2, String img3,String location,double lat, double lon,String time,String title,String ph)
	  {
		  this.id = id;
	    this.title = title;
	    this.user_id = user_id;
	    this.polepassword = polepassword;
	    this.polestate = polestate;
	    this.allowingperiod =allowingperiod;
	    this.content = content;
	    this.price  = price;
	    this.lat = lat;
	    this.lon = lon;
	    this.img1 = img1;
	    this.img2 = img2;
	    this.img3 = img3;
	    this.location = location;
	    this.time = time;
	    this.ph = ph;
	  }
	  
	  public Parklot ()
	  {
	    this.title = "";
	    this.id =0;
	    this.user_id = 0;
	    this.polepassword = "";
	    this.polestate = "";
	    this.allowingperiod = "";
	    this.lat = 36.778261;
	    this.lon = -119.417932;
	    this.content= "";
	    this.price="";
	    this.img1="";
	    this.img2="";
	    this.img3="";
	    this.location="";
	    this.time="";
	    this.ph = "";
	    
	  }
	public LatLng getLatLng() {
		LatLng latLng = new LatLng(lat, lon);
		return latLng;
	}
	


	
}




  
//  public String toString()
//  {
//    return "[" + id + "] " + title + ", " + num ;
//  }

