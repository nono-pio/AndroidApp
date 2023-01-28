package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dataclass.Year;
import com.example.myapplication.db.Database;
import com.example.myapplication.popup.YearUpdatePopup;

import java.util.ArrayList;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    private Year[] years;
    private final ArrayList<Integer> yearId;

    private final Database db;
    private final Activity activity;

    public YearAdapter(Activity activity) {
        Context context = activity.getApplicationContext();
        this.activity = activity;

        db = new Database(context);

        years = db.year.getAllYear();
        yearId = new ArrayList<>();
        for ( Year year : years ) yearId.add(year.id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView yearTitle;
        final TextView yearGrade;
        final ImageButton update;

        public ViewHolder(View view) {
            super(view);
            yearTitle = view.findViewById(R.id.tvYearTitle);
            yearGrade = view.findViewById(R.id.tvYearGrade);
            update = view.findViewById(R.id.btnYearSettings);
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
        viewHolder.update.setOnClickListener(view -> {
            YearUpdatePopup popup = new YearUpdatePopup(activity, this, curYear);
            popup.create();
        });
    }

    public void addYear(Year newYear){
        db.year.addYear(newYear);
        years = db.year.getAllYear();
        yearId.add(years[years.length - 1].id);

        notifyItemInserted(years.length - 1);
    }

    public void updateYear(Year oldYear, Year newYear){
        newYear.id = oldYear.id;
        db.year.updateYear(newYear);
        years = db.year.getAllYear();

        notifyItemChanged(yearId.indexOf(newYear.id));
    }

    public void deleteYear(int id){
        db.year.deleteYear(id);
        years = db.year.getAllYear();

        notifyItemRemoved(yearId.indexOf(id));
        yearId.remove(Integer.valueOf(id));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return years.length;
    }
}