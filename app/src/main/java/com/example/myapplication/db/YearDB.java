package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.dataclass.Year;

public class YearDB extends SQLiteOpenHelper {

    private final Context context;
    private static final String YearDB_NAME = "year.db";
    private static final int YearDB_VERSION = 1;

    private static final String TABLE_NAME = "year";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";

    public YearDB(@Nullable Context context) {
        super(context, YearDB_NAME, null, YearDB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addYear(Year year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, year.title);
        cv.put(COLUMN_DESCRIPTION, year.description);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed add year to database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added to the database", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateYear(Year newYear){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, newYear.title);
        cv.put(COLUMN_DESCRIPTION, newYear.description);
        Log.i("abc", "id = " + newYear.id);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {String.valueOf(newYear.id)});
        if (result == -1 ){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteYear(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {String.valueOf(id)});
        if (result == -1){
            Toast.makeText(context, "Error deleting", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public Year[] getAllYear(){
        String query = "SELECT * FROM year;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        Year[] years = new Year[0];
        if (cursor != null) years = new Year[cursor.getCount()];

        assert cursor != null;
        if(cursor.getCount() != 0){
            int i = 0;
            while (cursor.moveToNext()){
                Year newYear = new Year(
                                cursor.getString(1),
                                cursor.getString(2),
                                0d,
                                null
                                );
                newYear.id = cursor.getInt(0);
                years[i] = newYear;
                i++;
            }
        }
        cursor.close();
        return years;
    }
}
