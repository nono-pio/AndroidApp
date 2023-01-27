package com.example.myapplication.db;

import android.content.Context;

public class Database {
    public final YearDB year;


    public Database(Context context) {
        year = new YearDB(context);
    }
}
