package ch.adrart.zli.spingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String cat_TABLE_NAME = "tbl_category";
    private static final String cat_COL0 = "id";
    private static final String cat_COL1 = "category";

    private static final String ele_TABLE_NAME = "tbl_element";
    private static final String ele_COL0 = "id";
    private static final String ele_COL1 = "element";

    public DatabaseHelper(Context context) {
        super(context, cat_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table category
        String sCreateTable = "CREATE TABLE " + cat_TABLE_NAME + "("
                + cat_COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + cat_COL1 + " TEXT)";
        db.execSQL(sCreateTable);

        // Create table element
        sCreateTable = "CREATE TABLE " + ele_TABLE_NAME + "("
                + ele_COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ele_COL1 + " TEXT)";
        db.execSQL(sCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + cat_TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + ele_TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String sItem, String sTable) {
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(cat_COL1, sItem);

        switch (sTable) {
            case "category":
                Log.d(TAG, "addData: Adding " + sItem + " to " + cat_TABLE_NAME);
                result = db.insert(cat_TABLE_NAME, null, contentValues);

            case "element":
                Log.d(TAG, "addData: Adding " + sItem + " to " + ele_TABLE_NAME);
                result = db.insert(ele_TABLE_NAME, null, contentValues);
        }

        //if data as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Cursor getData(String sTable) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "";

        switch (sTable) {
            case "category":
                query = "SELECT * FROM " + cat_TABLE_NAME;

            case "element":
                query = "SELECT * FROM " + ele_TABLE_NAME;
        }

        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
