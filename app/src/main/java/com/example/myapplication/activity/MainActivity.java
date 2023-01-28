package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.YearAdapter;
import com.example.myapplication.popup.YearCreatePopup;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    YearAdapter yearAdapter;
    Button btnChangeActivity;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        recyclerView = findViewById(R.id.rvYears);
        yearAdapter = new YearAdapter(this);
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
        YearCreatePopup popup = new YearCreatePopup(this, yearAdapter);
        popup.create();
    }
}