package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dataclass.Year;
import com.example.myapplication.db.Database;
import com.example.myapplication.popup.YearUpdatePopup;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    private Year[] years;
    private final Database db;
    private final Context context;
    private final Activity activity;

    public YearAdapter(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        db = new Database(context);
        years = db.year.getAllYear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView yearTitle, yearGrade;
        ImageButton update;

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
            YearUpdatePopup popup = new YearUpdatePopup(activity, this, position + 1);
            popup.create();
        });
    }

    public void addYear(String title){
        int[] id = {1,2};
        Year newYear = new Year(title, "jshjhdjdh", 6d, id);
        db.year.addYear(newYear);
        years = db.year.getAllYear();
        notifyItemInserted(years.length - 1);
    }

    public void updateYear(String title, int id){
        int[] ids = {1,2};
        Year newYear = new Year(title, "jshjhdjdh", 6d, ids);
        db.year.updateYear(newYear, id);
        years = db.year.getAllYear();
        //notifyItemInserted(id - 1);
        notifyItemChanged(id - 1);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return years.length;
    }
}