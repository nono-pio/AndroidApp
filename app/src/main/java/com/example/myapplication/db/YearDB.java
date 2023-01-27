package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.dataclass.Year;

public class YearDB extends SQLiteOpenHelper {

    private Context context;
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

    public Year[] getAllYear(){
        String query = "SELECT * FROM year;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        Year[] years;

        if(cursor == null || cursor.getCount() == 0){
            years = new Year[0];
        } else {
            years = new Year[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()){
                years[i] = new Year(
                        cursor.getString(1),
                        cursor.getString(2),
                        0d,
                        null
                );
                i++;
            }
        }
        return years;
    }
}