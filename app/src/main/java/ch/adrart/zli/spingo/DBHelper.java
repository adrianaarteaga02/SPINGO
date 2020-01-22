package ch.adrart.zli.spingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {

    // Database Info
    private static final int iDB_Version = 1;
    private static final String sDB_Name = "postsDatabase";

    // Table Names
    private static final String tbl_category = "tbl_category";
    private static final String tbl_element = "tbl_element";

    // Category Table Columns
    private static final String cat_id = "id";
    private static final String cat_name = "category";

    // Element Table Columns
    private static final String ele_id = "id";
    private static final String ele_category_fk = "category_fk";
    private static final String ele_name = "element";

    public DBHelper(Context context) {
        super(context, sDB_Name, null, iDB_Version);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + tbl_category + "(" +
                cat_id + " INTEGER PRIMARY KEY," +
                cat_name + " TEXT)";

        String CREATE_ELEMENT_TABLE = "CREATE TABLE " + tbl_element + "(" +
                ele_id + " INTEGER PRIMARY KEY," +
                ele_category_fk + " INTEGER REFERENCES " + tbl_category + ", " +
                ele_name + " TEXT)";

        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_ELEMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + tbl_category);
            db.execSQL("DROP TABLE IF EXISTS " + tbl_element);
            onCreate(db);
        }
    }

    //get ID of selected category
    public int getIDFromSelectedCategory(String sName) {

        SQLiteDatabase db = this.getReadableDatabase();
        int iIDEntry = 0;
        String query = String.format("SELECT " + cat_id + ", " + cat_name + " FROM " + tbl_category +
                " WHERE " + cat_name + " = '%s'", sName);

        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    int iID = cursor.getInt(cursor.getColumnIndex(cat_id));
                    String sCatName = cursor.getString(cursor.getColumnIndex(cat_name));
                    iIDEntry = iID;
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "getAllElements: Error while trying to get id from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return iIDEntry;
    }

    //select all entries from category/element
    public List<String> getAllEntries(int iTable, int iCategoryFK) {

        List<String> entries = new ArrayList<>();
        String sQuery = "";

        boolean bResult = true;

        if (iTable == 1) {
            //category
            sQuery = String.format("SELECT %s FROM %s",
                    cat_name, tbl_category);

        } else if (iTable == 2) {
            //elements
            if (iCategoryFK > 0) {
                sQuery = String.format("SELECT %s FROM %s WHERE %s = %s",
                        ele_name, tbl_element,
                        ele_category_fk, iCategoryFK);

            } else {
                Log.d(TAG, "getAllElements: Error no category selected");
                bResult = false;

            }
        } else {
            bResult = false;
        }

        if (bResult) {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sQuery, null);

            try {
                if (cursor.moveToFirst()) {
                    do {
                        if (iTable == 1) {
                            String newElement = cursor.getString(cursor.getColumnIndex(cat_name));
                            entries.add(newElement);

                        } else if (iTable == 2) {
                            String newElement = cursor.getString(cursor.getColumnIndex(ele_name));
                            entries.add(newElement);

                        }
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "getAllElements: Error while trying to get entries from database");

            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }

        return entries;
    }

    //insert into category/element
    public boolean insertEntry(int iTable, String sName, int iCategoryFK) {
        long bDataInserted = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        if (!(sName.equals(""))) {
            if (iTable == 1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(cat_name, sName);

                bDataInserted = db.insert(tbl_category, null, contentValues);

            } else if (iTable == 2) {
                if (iCategoryFK < 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ele_name, sName);
                    contentValues.put(ele_category_fk, iCategoryFK);

                    bDataInserted = db.insert(tbl_category, null, contentValues);

                }
            }
        }

        //check if data was inserted
        return bDataInserted != -1;
    }

}
