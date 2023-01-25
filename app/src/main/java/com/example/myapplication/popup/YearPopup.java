package com.example.myapplication.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.YearAdapter;

public class YearPopup {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText yearTitle, yearDescription;
    private Button save, cancel;

    private Context context;
    private Activity activity;

    private YearAdapter yearAdapter;

    public YearPopup(Activity activity, YearAdapter yearAdapter){
        this.activity = activity;
        this.context = activity;
        this.yearAdapter = yearAdapter;
    }

    public void create(){
        dialogBuilder = new AlertDialog.Builder(context);
        final View popupView = activity.getLayoutInflater().inflate(R.layout.popup_year, null);

        yearTitle = popupView.findViewById(R.id.etPopupYearTitle);
        yearDescription = popupView.findViewById(R.id.etPopupYearDesc);
        save = popupView.findViewById(R.id.btnPopupYearSave);
        cancel = popupView.findViewById(R.id.btnPopupYearCancel);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(view -> {
            if (!yearTitle.getText().toString().isEmpty())
            {
                yearAdapter.addYear(yearTitle.getText().toString());
                Toast.makeText(context, "Add year", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else{
                Toast.makeText(context, "Title is empty", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(view -> dialog.dismiss());

    }

}
