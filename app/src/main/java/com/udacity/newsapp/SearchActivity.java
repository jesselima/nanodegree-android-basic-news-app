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

    private static final String CONST_ORDER_BY_KEY = "order-by";
    private static final String CONST_SEARCH_TYPE_KEY = "searchType";
    private static final String CONST_SEARCH_TYPE_VALUE = "advanced";
    private static final String CONST_FROM_DATE_KEY = "from-date";
    private static final String CONST_TO_DATE_KEY = "to-date";
    private static final String CONST_Q_KEY = "q";


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
    private String q,fromDate, toDate, fullFromDate, fullToDate;
    private String orderBy = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final RadioGroup radioGroup = findViewById(R.id.search_radio_group);
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
                                date.append(getString(R.string.dash));
                                date.append(month + 1);
                                date.append(getString(R.string.dash));
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
                                    date.append(getString(R.string.dash));
                                    date.append(month + 1);
                                    date.append(getString(R.string.dash));
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
                q = searchTerms.getText().toString();

                Intent intent = new Intent(getApplicationContext(), NewsListActivity.class);
                    intent.putExtra(CONST_FROM_DATE_KEY, fromDate);
                    intent.putExtra(CONST_TO_DATE_KEY, toDate);
                    intent.putExtra(CONST_ORDER_BY_KEY, orderBy);
                    intent.putExtra(CONST_Q_KEY, q);
                    intent.putExtra(CONST_SEARCH_TYPE_KEY, CONST_SEARCH_TYPE_VALUE);
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
