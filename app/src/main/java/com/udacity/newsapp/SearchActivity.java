package com.udacity.newsapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {

    LinearLayout linearLayoutPickFromDate;
    LinearLayout linearLayoutPickToDate;

    TextView fromDateSelected;
    TextView toDateSelected;
    DatePickerDialog datePickerDialog;

    int currentYear;
    int currentMonth;
    int currentDayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        linearLayoutPickFromDate = findViewById(R.id.from_date);
        fromDateSelected = findViewById(R.id.from_date_selected);

        linearLayoutPickToDate = findViewById(R.id.to_date);
        toDateSelected = findViewById(R.id.to_date_selected);

        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        linearLayoutPickFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(SearchActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            StringBuilder date = new StringBuilder();
                                date.append(year);
                                date.append("-");
                                date.append(month + 1);
                                date.append("-");
                                date.append(day);
                            String fullDate = String.valueOf(date);
                            fromDateSelected.setText(fullDate);
                        }
                    }, currentYear, currentMonth, currentDayOfMonth);
                datePickerDialog.show();
            }
        });
        fromDateSelected.setText(currentYear + "-" + (currentMonth +1) + "-" + currentDayOfMonth);


        linearLayoutPickToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                StringBuilder date = new StringBuilder();
                                    date.append(year);
                                    date.append("-");
                                    date.append(month + 1);
                                    date.append("-");
                                    date.append(day);
                                String fullDate = String.valueOf(date);
                                toDateSelected.setText(fullDate);
                            }
                        }, currentYear, currentMonth, currentDayOfMonth);
                datePickerDialog.show();
            }
        });
        toDateSelected.setText(currentYear + "-" + (currentMonth +1) + "-" + currentDayOfMonth);

    }

}
