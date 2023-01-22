package com.example.myapplication.db;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.dataclass.Year;

import java.io.IOException;
import java.util.Arrays;

public class YearManager {

    private Year_db yearDb;
    private String fileName;
    private Context context;

    YearManager(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
        yearDb = new Year_db(fileName, context);
        //Log.i("database_test", "year db = "+ yearDb);
    }

    public Year[] getAllStart(){
        Year[] years;
        if(Arrays.asList(context.fileList()).contains(fileName))
        {
            years = getAll();
        } else
        {
            try {
                yearDb.openFile('W');
                yearDb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            years = new Year[0];
        }
        return years;
    }

    public void add(Year year) {
        try {
            yearDb.openFile('W');
            yearDb.write(year);
            yearDb.close();
        } catch (IOException e) {
            e.printStackTrace();
            //Log.i("database_test", "Error "+e.toString());
        }
    }

    public void addAll(Year[] years) {
        try {
            yearDb.openFile('W');
            yearDb.writeAll(years);
            yearDb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Year[] getAll() {
        Year[] years;
        try {
            yearDb.openFile('R');
            years = yearDb.read();
            yearDb.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("database_test", "Error" + e.toString());
            years = null;
        }
        return years;
    }

    public void replace(Year newYear, int idCurYear) {
        Year[] years = getAll();
        years[idCurYear] = newYear;
        clearAll();
        addAll(years);
    }

    public void clearAll() {
        try {
            yearDb.openFile('C');
            yearDb.clear();
            yearDb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(int idToClear){
        Year[] years = getAll();
        clearAll();

        Year[] newYears = new Year[years.length - 1];
        System.arraycopy(years, 0, newYears, 0, idToClear);
        if (years.length != idToClear) {
            System.arraycopy(years, idToClear + 1, newYears, idToClear, years.length - idToClear - 1);
        }

        addAll(newYears);
    }
}
