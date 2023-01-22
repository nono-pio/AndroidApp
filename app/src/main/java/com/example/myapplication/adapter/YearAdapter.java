package com.example.myapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dataclass.Year;
import com.example.myapplication.db.Database;

import java.util.ArrayList;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    private Year[] years;
    private final Database db;

    public YearAdapter(Database database) {
        db = database;
        years = db.year.getAllStart();
        //Log.i("database_test", "years = " + years);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView yearTitle, yearGrade;

        public ViewHolder(View view) {
            super(view);
            yearTitle = view.findViewById(R.id.tvYearTitle);
            yearGrade = view.findViewById(R.id.tvYearGrade);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_year, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Year curYear = years[position];

        viewHolder.yearTitle.setText(curYear.title);
        viewHolder.yearGrade.setText(curYear.grade + "/6");
    }

    public void addYear(String title){
        int[] id = {1,2};
        Year newYear = new Year(title, "jshjhdjdh", 6d, id);
        db.year.add(newYear);
        years = db.year.getAll();
        notifyItemInserted(years.length - 1);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return years.length;
    }
}