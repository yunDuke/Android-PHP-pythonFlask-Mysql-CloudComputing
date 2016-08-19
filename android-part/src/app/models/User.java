package app.models;

public class User {
int id;
public String name;
public String password;
public String email;
public String carplate;
public String cartype;
public String account;


public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCarplate() {
	return carplate;
}
public void setCarplate(String carplate) {
	this.carplate = carplate;
}
public String getCartype() {
	return cartype;
}
public void setCartype(String cartype) {
	this.cartype = cartype;
}
public String getAccount() {
	return account;
}
public void setAccount(String account) {
	this.account = account;
}
public User (int id, String name,String email,String password,String carplate,String cartype,String account)
{
	  this.id = id;
	  this.name = name;
	  this.email=email;
	  this.password = password;
	  this.carplate=carplate;
	  this.cartype=cartype;
      this.account = account;
}

public User ()
{
  this.name = "";
  this.id =0;
  this.email = "";
  this.account ="";
  this.password= "";
  this.carplate="";
  this.cartype="";  
}

}
