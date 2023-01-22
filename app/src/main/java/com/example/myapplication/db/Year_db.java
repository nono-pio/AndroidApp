package com.example.myapplication.db;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.dataclass.Year;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Year_db {
    private final String fileName;

    private final Context context;

    private FileOutputStream fosAppend;
    private FileOutputStream fos;

    private BufferedReader reader;

    private char mode;

    Year_db(String file, Context context) {
        fileName = file;
        this.context = context;
        //Log.i("database_test", "year db good");
    }

    public void openFile(char m) throws IOException {
        mode = m;
        if (mode == 'R')
            reader = new BufferedReader(new InputStreamReader( context.openFileInput(fileName)));
        else if (mode == 'W')
            fosAppend = context.openFileOutput(fileName, Context.MODE_APPEND);
        else if (mode == 'C')
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
    }

    public void clear() throws IOException {
        fos.write("".getBytes());
    }

    public void write(Year year) throws IOException {
        StringBuilder partYearIdString = new StringBuilder();
        for (int id : year.partYearId) partYearIdString.append(id).append(';');

        String text = year.title + ';' + year.description + ';' + year.grade + '/' + partYearIdString.substring(0, partYearIdString.length() - 1) + "\n";

        fosAppend.write(text.getBytes());
    }

    public void writeAll(Year[] years) throws IOException {
        StringBuilder partYearIdString;
        for (Year year : years) {
            partYearIdString = new StringBuilder();
            for (int id : year.partYearId) partYearIdString.append(id).append(';');

            String text = year.title + ';' + year.description + ';' + year.grade + '/' + partYearIdString.substring(0, partYearIdString.length() - 1) + "\n";

            fosAppend.write(text.getBytes());
        }
    }

    public Year[] read() throws IOException {
        ArrayList<Year> years = new ArrayList<>();
        Year year;

        String yearString;
        String[] yearAndPartYearId;
        String[] yearInfo;
        String[] partYearIdString;
        int[] partYearId;

        do {
            yearString = reader.readLine();
            if (yearString != null) {

                yearAndPartYearId = yearString.split("/");

                yearInfo = yearAndPartYearId[0].split(";");
                partYearIdString = yearAndPartYearId[1].split(";");

                partYearId = new int[partYearIdString.length];
                for (int i = 0; i < partYearIdString.length; i++)
                    partYearId[i] = Integer.parseInt(partYearIdString[i]);

                year = new Year(yearInfo[0], yearInfo[1], Double.parseDouble(yearInfo[2]), partYearId);
                years.add(year);
            }
        } while (yearString != null);

        Year[] years2 = new Year[years.size()];
        return years.toArray(years2);
    }

    public void close() throws IOException {
        if (mode == 'R')
            reader.close();
        else if (mode == 'W')
            fosAppend.close();
        else if (mode == 'C')
            fos.close();
    }
}

