package app.user.api;

/**
 * Created by Raj Amal on 5/30/13.
 */




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cloud_contacts";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CARPLATE = "fname";
    private static final String KEY_CARTYPE = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "uname";
    private static final String KEY_CREATED_AT = "created_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CARPLATE + " TEXT,"
                + KEY_CARTYPE + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_NAME + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

     
        onCreate(db);
    }


    public void addUser(String fname, String lname, String email, String uname,  String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CARPLATE, fname); 
        values.put(KEY_CARTYPE, lname); 
        values.put(KEY_EMAIL, email); 
        values.put(KEY_NAME, uname);
     
        values.put(KEY_CREATED_AT, created_at); 

       
        db.insert(TABLE_LOGIN, null, values);
        db.close(); 
    }



    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("fname", cursor.getString(1));
            user.put("lname", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("uname", cursor.getString(4));
            user.put("uid", cursor.getString(5));
            user.put("created_at", cursor.getString(6));
        }
        cursor.close();
        db.close();
   
        return user;
    }







    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        return rowCount;
    }



//    public void resetTables(){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.delete(TABLE_LOGIN, null, null);
//        db.close();
//    }

}
