package app.paylist.db;

import java.util.ArrayList;
import java.util.List;











import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import app.parklot.pay.Pay;
import app.paylist.db.dbdesigner;

public class dbmanager {

	private SQLiteDatabase database;
	private dbdesigner dbHelper;

	public dbmanager(Context context) {
		dbHelper = new dbdesigner(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void add(Pay d) {
		ContentValues values = new ContentValues();
		values.put(dbdesigner.COLUMN_CARPLATE, d.carplate);
		values.put(dbdesigner.COLUMN_TIME, d.time);	
		values.put(dbdesigner.COLUMN_TYPE, d.type);
		values.put(dbdesigner.COLUMN_NUM, d.num);
		values.put(dbdesigner.COLUMN_TOTAL, d.total);
		values.put(dbdesigner.COLUMN_PARK, d.park);

		database.insert(dbdesigner.TABLE_LIST3, null, values);
	}
	 public void delete(String id){
		   database.delete(dbdesigner.TABLE_LIST3,dbdesigner.COLUMN_ID+"=?", new String[] {id});
	   }

	public List<Pay> getAll() {
		List<Pay> list = new ArrayList<Pay>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ dbdesigner.TABLE_LIST3, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Pay d = toPay(cursor);
			list.add(d);
			cursor.moveToNext();
		}
		cursor.close();
		return list;
	}

	public Pay get(int id) {
		Pay d = null;
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ dbdesigner.TABLE_LIST3 + " WHERE "
				+ dbdesigner.COLUMN_ID + " = " + id , null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Pay temp = toPay(cursor);
			d = temp;
			cursor.moveToNext();
		}
		cursor.close();
		return d;
	}
	private Pay toPay(Cursor cursor) {
		Pay pojo = new Pay();
		pojo.id = cursor.getInt(0);
		pojo.total = cursor.getInt(4);
		pojo.carplate = cursor.getString(1);
		pojo.type = cursor.getString(3);
		pojo.time = cursor.getString(2);
		pojo.num = cursor.getString(5);
		pojo.park = cursor.getString(6);
		return pojo;
	}
	 public void edittime(String time,String id){
			
		   ContentValues updatedValues = new ContentValues();
		    
		  
		   updatedValues.put(dbdesigner.COLUMN_TIME, time);
		  

		   
		   database.update(dbdesigner.TABLE_LIST3, updatedValues, dbdesigner.COLUMN_ID + " = " + id, null);
		  
	   }
	 public void edittotal(int total,String id){
			
		   ContentValues updatedValues = new ContentValues();
		    
		  
		   updatedValues.put(dbdesigner.COLUMN_TOTAL, total);
		  

		   
		   database.update(dbdesigner.TABLE_LIST3, updatedValues, dbdesigner.COLUMN_ID + " = " + id, null);
		  
	   }
	 public void editmethod(String method,String id){
			
		   ContentValues updatedValues = new ContentValues();
		    
		  
		   updatedValues.put(dbdesigner.COLUMN_TYPE, method);
		  

		   
		   database.update(dbdesigner.TABLE_LIST3, updatedValues, dbdesigner.COLUMN_ID + " = " + id, null);
		  
	   }


	
		

	public void reset() {
		database.delete(dbdesigner.TABLE_LIST3, null, null);
	}

	public Pay search(String carplate) {
		// TODO Auto-generated method stub
		Pay p = null;
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ dbdesigner.TABLE_LIST3 + " WHERE "
				+ dbdesigner.COLUMN_CARPLATE + "=?",new String[] {carplate});
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Pay temp = toPay(cursor);
			
			p = temp;
			cursor.moveToNext();
		}
		cursor.close();
		return p;
		
	}
}
