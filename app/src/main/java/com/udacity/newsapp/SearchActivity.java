package com.udacity.newsapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout linearLayoutPickFromDate;
    private LinearLayout linearLayoutPickToDate;

    private TextView fromDateSelected;
    private TextView toDateSelected;
    private DatePickerDialog datePickerDialog;

    private int currentYear;
    private int currentMonth;
    private int currentDayOfMonth;
    private Calendar calendar;

    /* Strings to be used in Advanced search */
    private String pageSize, q,fromDate, toDate, fullFromDate, fullToDate;
    private String orderBy = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final RadioGroup radioGroup = findViewById(R.id.search_radio_group);
        final EditText newsPerPage = findViewById(R.id.search_page_size);
        final EditText searchTerms = findViewById(R.id.search_q);

        /* Date picker implementation */

        linearLayoutPickFromDate = findViewById(R.id.from_date);
        fromDateSelected = findViewById(R.id.from_date_selected);

        linearLayoutPickToDate = findViewById(R.id.to_date);
        toDateSelected = findViewById(R.id.to_date_selected);

        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        fromDateSelected.setText(R.string.no_date_selected);
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
                            fullFromDate = String.valueOf(date);
                            fromDateSelected.setText(fullFromDate);
                            fromDate = String.valueOf(fullFromDate);
                        }
                    }, currentYear, currentMonth, currentDayOfMonth);
                datePickerDialog.show();
            }
        });

        toDateSelected.setText(R.string.no_date_selected);
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
                                fullToDate = String.valueOf(date);
                                toDateSelected.setText(fullToDate);
                                toDate = String.valueOf(fullToDate);
                            }
                        }, currentYear, currentMonth, currentDayOfMonth);
                datePickerDialog.show();
            }
        });

        /* Get input values and update variables to be sent by intent do NewsListActivity */


        Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderBy = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                pageSize = newsPerPage.getText().toString();
                q = searchTerms.getText().toString();

                Intent intent = new Intent(getApplicationContext(), NewsListActivity.class);
                intent.putExtra("from-date", fromDate);
                intent.putExtra("to-date", toDate);
                intent.putExtra("order-by", orderBy);
                intent.putExtra("page-size", pageSize);
                intent.putExtra("q", q);
                String searchType = "advanced";
                intent.putExtra("searchType", searchType);

                Log.v("SEARCH ACTIVITY: ", "TESTING MY INPUTS");
                Log.v("Search fromDate value: ", fromDate);
                Log.v("Search toDate value: ", toDate);
                Log.v("Search orderBy value: ", orderBy);
                Log.v("Search pageSize value: ", pageSize);
                Log.v("Search q value: ", q);
                Log.v("searchType value: ", searchType);

                startActivity(intent);
            }
        });

    } // Close onCreate method

    /**
     * This method receives the date (data type Date) as input parameter and
     * @param dateObject is the date to be formatted.
     * @return a string with the date formatted according to the SimpleDateFormat method pattern.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

}
