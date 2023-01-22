package com.example.myapplication.db;

import android.content.Context;
import android.util.Log;

public class Database {
    public YearManager year;

    private final String fileNameYear = "Year.txt";

    public Database(Context context)
    {
        year = new YearManager(context, fileNameYear);
        //Log.i("database_test", "Year Manager = " + year);
    }
}
