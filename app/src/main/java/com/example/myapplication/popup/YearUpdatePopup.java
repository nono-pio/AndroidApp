package com.example.myapplication.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.YearAdapter;
import com.example.myapplication.dataclass.Year;

public class YearUpdatePopup {
    private AlertDialog dialog;

    private final Context context;
    private final Activity activity;

    private final YearAdapter yearAdapter;

    private final Year year;

    public YearUpdatePopup(Activity activity, YearAdapter yearAdapter, Year year){
        this.activity = activity;
        this.context = activity;
        this.yearAdapter = yearAdapter;
        this.year = year;
    }

    public void create(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View popupView = activity.getLayoutInflater().inflate(R.layout.popup_year_update, null);

        EditText yearTitle = popupView.findViewById(R.id.etPopupUpdateYearTitle);
        EditText yearDescription = popupView.findViewById(R.id.etPopupUpdateYearDesc);

        Button save = popupView.findViewById(R.id.btnPopupUpdateYearSave);
        Button cancel = popupView.findViewById(R.id.btnPopupUpdateYearCancel);
        ImageButton delete = popupView.findViewById(R.id.ibPopupUpdateYearDelete);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(view -> {
            if (!yearTitle.getText().toString().isEmpty())
            {
                Year newYear = new Year(yearTitle.getText().toString(), yearDescription.getText().toString(), 0d, null);

                yearAdapter.updateYear(year, newYear);
                Toast.makeText(context, "replace year", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else{
                Toast.makeText(context, "Title is empty", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(view -> dialog.dismiss());

        delete.setOnClickListener(view -> {
            yearAdapter.deleteYear(year.id);
            dialog.dismiss();
        });

    }
}
