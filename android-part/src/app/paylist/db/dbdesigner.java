package app.paylist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import app.paylist.db.dbdesigner;

public class dbdesigner extends SQLiteOpenHelper {
	public static final String 	TABLE_LIST3 = "table_list3";
	public static final String 	COLUMN_ID = "id";
	public static final String 	COLUMN_CARPLATE = "carplate";
	public static final String 	COLUMN_TIME = "time";
	public static final String 	COLUMN_TYPE = "type";
	public static final String 	COLUMN_NUM = "num";
	public static final String  COLUMN_TOTAL = "total";
	public static final String  COLUMN_PARK = "park";
	private static final String DATABASE_NAME = "list3.db";
	private static final int 	DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE_TABLE_LIST = "create table "
			+ TABLE_LIST3 + "( " + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_CARPLATE + " text not null,"
			+ COLUMN_TIME + " text not null,"+ 
			COLUMN_TYPE+"  text not null,"
			+ 
			COLUMN_NUM+"  text not null,"
			+COLUMN_TOTAL+" integer not null);"
			+ 
			COLUMN_PARK+"  text not null,"
		;
		
	public dbdesigner(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_TABLE_LIST);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(dbdesigner.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST3);
		onCreate(db);
	}
}