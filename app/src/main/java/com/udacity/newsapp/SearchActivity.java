package com.udacity.newsapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button selectedDate;
    TextView textViewDate;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        selectedDate = findViewById(R.id.btnDate);
        textViewDate = findViewById(R.id.tvSelectedDate);

        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(SearchActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            textViewDate.setText(year + "-" + month + "-" + day );
                        }
                    }, 2018, 1, 1);
                datePickerDialog.show();
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
