package app.models;

public class reply {
	int id;
	int user_id;
	int post_id;
	String content;
	String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public reply (int id, int user_id,int post_id,String content,String time)
	{
		  this.id = id;
		  this.user_id  = user_id;
		  this.post_id=post_id;
		  this.content = content;
		  this.time=time;
		
	 
	}

	public reply ()
	{
	 
	  this.id =0;
	  this.user_id = 0;
	  
	  this.post_id= 0;
	  this.content="";
	  this.time="";  
	}
}
