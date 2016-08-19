package app.api;

import app.models.Parklot;
import app.models.User;
import app.models.order;
import app.models.product;
import app.models.reply;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class api {
		
	public static List<Parklot> getAll(String call) {
		String json = Rest.get(call);
		Type collectionType = new TypeToken<List<Parklot>>() {}.getType();
		
		return new Gson().fromJson(json, collectionType);
	}
	
	public static Parklot get(String call) {
		String json = Rest.get(call);
		Type objType = new TypeToken<Parklot>(){}.getType();

		return new Gson().fromJson(json, objType);
	}
	
	public static String delete(String call) {
		return Rest.delete(call);
	}
	
	public static String insert(String call,Parklot d) {
		Type objType = new TypeToken<Parklot>(){}.getType();
		String json = new Gson().toJson(d, objType);
  
		return Rest.post(call,json);
	}

	public static String jinqu(String call,reply d) {
		Type objType = new TypeToken<reply>(){}.getType();
		String json = new Gson().toJson(d, objType);
  
		return Rest.post(call,json);
	}
	
	public static String start(String call,order d) {
		Type objType = new TypeToken<order>(){}.getType();
		String json = new Gson().toJson(d, objType);
  
		return Rest.post(call,json);
	}
	
	
	
	
	public static String addnew(String call,Parklot d) {
		Type objType = new TypeToken<Parklot>(){}.getType();
		String json = new Gson().toJson(d, objType);
  
		return Rest.put(call,json);
	}
	public static String  newuser(String call,User d) {
		Type objType = new TypeToken<User>(){}.getType();
		String json = new Gson().toJson(d, objType);
  
		return Rest.put(call,json);
	}
	
	
	public static List<User> show(String call) {
		String json = Rest.get(call);
		Type collectionType = new TypeToken<List<User>>() {}.getType();
		return new Gson().fromJson(json, collectionType);
	}
	public static User getone(String call) {
		String json = Rest.get(call);
		Type objType = new TypeToken<User>(){}.getType();

		return new Gson().fromJson(json, objType);
	}
	
	
	
	public static product getsingle(String call) {
		String json = Rest.get(call);
		Type objType = new TypeToken<product>(){}.getType();

		return new Gson().fromJson(json, objType);
	}
	
	
	public static List<reply> showreply(String call) {
		String json = Rest.get(call);
		Type collectionType = new TypeToken<List<reply>>() {}.getType();
		return new Gson().fromJson(json, collectionType);
	}
	
	public static List<order> showorder(String call) {
		String json = Rest.get(call);
		Type collectionType = new TypeToken<List<order>>() {}.getType();
		return new Gson().fromJson(json, collectionType);
	}
	
	
}
