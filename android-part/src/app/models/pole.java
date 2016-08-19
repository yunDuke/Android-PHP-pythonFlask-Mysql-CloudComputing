
package app.models;

public class pole
{
  public int 	id;
  public String allowingperiod;
  public String content;
  public String price;
  public String img1;
  public String img2;
  public String img3;
  public String location;
  public String time ;
  public String title; 
  
	
  
  
  
  
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


	

  public pole (int id, String allowingperiod,String content,String price,String img1,String img2, String img3,String location,String time,String title)
  {
	  this.id = id;
    this.title = title;
    this.allowingperiod =allowingperiod;
    this.content = content;
    this.price  = price;
    this.img1 = img1;
    this.img2 = img2;
    this.img3 = img3;
    this.location = location;
    this.time = time;
  }
  
  public pole ()
  {
    this.title = "";
    this.id =0;
    this.allowingperiod = "";
    
    this.content= "";
    this.price="";
    this.img1="";
    this.img2="";
    this.img3="";
    this.location="";
    this.time="";
    
  }

	
}




  
//  public String toString()
//  {
//    return "[" + id + "] " + title + ", " + num ;
//  }

