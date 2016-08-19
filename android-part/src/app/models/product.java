package app.models;

public class product {
	int id;
	String password;
	String polestate;
	String uniquecode;
	String image;
	String image2;
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	String image3;
	String photo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPolestate() {
		return polestate;
	}
	public void setPolestate(String polestate) {
		this.polestate = polestate;
	}
	public String getUniquecode() {
		return uniquecode;
	}
	public void setUniquecode(String uniquecode) {
		this.uniquecode = uniquecode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	
	 public product (int id, String polestate,String password,String uniquecode,String image,String image2, String image3,String photo)
	  {
		  this.id = id;
	    this.polestate = polestate;
	    this.password =password;
	    this.uniquecode = uniquecode;
	    this.image  = image;
	    this.image2 = image2;
	    this.image3 = image3;
	    this.photo = photo;
	 
	  }
	public product() {
		
		this.id = 0;
		this.polestate = "";
		this.password = "";
		this.uniquecode="";
		this.image = "";
		this.image2 = "";
		this.image3 ="";
		this.photo ="";
		
		// TODO Auto-generated constructor stub
	}
	
	
}
