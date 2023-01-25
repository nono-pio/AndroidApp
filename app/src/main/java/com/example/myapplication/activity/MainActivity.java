package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.YearAdapter;
import com.example.myapplication.db.Database;
import com.example.myapplication.popup.YearPopup;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    YearAdapter yearAdapter;
    Button btnChangeActivity;

    Context context;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        db = new Database(context);

        recyclerView = findViewById(R.id.rvYears);
        yearAdapter = new YearAdapter(db);
        //Log.i("database_test", "files = " + Arrays.toString(context.fileList()));
        recyclerView.setAdapter(yearAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnChangeActivity = findViewById( R.id.btnChangeActivity );
        btnChangeActivity.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    YearPartActivity.class
            );
            startActivity(intent);

        });
    }

    public void btnAddYear(View v){
        YearPopup popup = new YearPopup(this, yearAdapter);
        popup.create();
    }
}