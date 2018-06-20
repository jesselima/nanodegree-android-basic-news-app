package com.udacity.newsapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.newsapp.utils.DateUtils;

import java.util.Calendar;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private static final String CONST_ORDER_BY_KEY = "order-by";
    private static final String CONST_SEARCH_TYPE_KEY = "searchType";
    private static final String CONST_SEARCH_TYPE_VALUE = "advanced";
    private static final String CONST_FROM_DATE_KEY = "from-date";
    private static final String CONST_TO_DATE_KEY = "to-date";
    private static final String CONST_Q_KEY = "q";

    private TextView textViewFromDateSelected;
    private TextView textViewToDateSelected;
    private DatePickerDialog datePickerDialog;

    private int currentYear;
    private int currentMonth;
    private int currentDayOfMonth;

    /* Strings to be used in Advanced search */
    private String q, fromDate, toDate, fullFromDateString, fullToDateString;
    private String orderBy = "";

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final RadioGroup radioGroup = findViewById(R.id.search_radio_group);
        final EditText searchTerms = findViewById(R.id.search_q);

        /*
         *  DATE PICKER IMPLEMENTATION AUTHORING WARNING
         *  The basic guideline for this implementation was from YouTube:
         *  Title: Android Datepicker – Using Datepickerdialog With Button (Explained)
         *  Publisher: Coding Demos
         *  Published date: Feb 23, 2018
         *  Url: https://www.youtube.com/watch?v=CNGLsYPZd_o
         * */

        /* Creates variables with the current year, month and day of the month */
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        /* Creates a reference to the TextView where the from-date is show in the UI */
        textViewFromDateSelected = findViewById(R.id.from_date_selected);
        /* Set the from date TextView with the date 30 days backward from the current date  */
        textViewFromDateSelected.setText(DateUtils.datePickerFormat(DateUtils.buildMyDate(currentYear, (currentMonth), currentDayOfMonth)));// ONE MONTH AGO FROM TODAY.

        /* Creates a reference to the TextView where the to-date is show in the UI */
        textViewToDateSelected = findViewById(R.id.to_date_selected);
        /* Set the from date TextView with the current date  */
        textViewToDateSelected.setText(DateUtils.datePickerFormat(DateUtils.buildMyDate(currentYear, (currentMonth + 1), currentDayOfMonth)));// TODAY!!!

        /* Update the pre selected date to be used as search parameter if the user do not select one specific date */
        fromDate = DateUtils.buildMyDate(currentYear, currentMonth, currentDayOfMonth);
        /* Implementation of date picker. If the user clicks in the vertical LinearLayout ViewGroup that holds from data info the DatePicker dialog shows up. */
        LinearLayout linearLayoutPickFromDate = findViewById(R.id.from_date);
        linearLayoutPickFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // DateUtils.buildMyDate returns a date as String with this pattern: "yyyy-MM-dd"
                                fullFromDateString = DateUtils.buildMyDate(year, month + 1, day);
                                // DateUtils.datePickerFormat receives a date as String as "yyyy-MM-dd" and returns with the pattern:  LLL dd, yyyy
                                textViewFromDateSelected.setText(DateUtils.datePickerFormat(fullFromDateString));
                                // Update the variable "fromDate" to send it to NewsListActivity when the GO (Search Action) button is clicked
                                fromDate = fullFromDateString;
                            }
                        }, currentYear, (currentMonth - 1), currentDayOfMonth); // Today pre selected date on date picker DIALOG.
                datePickerDialog.show();
            }
        });

        /* Update the pre selected date to be used as search parameter if the user do not select one specific date. By default the will be the current date. */
        toDate = DateUtils.buildMyDate(currentYear, (currentMonth + 1), currentDayOfMonth);
        /* Implementation of date picker. If the user clicks in the vertical LinearLayout ViewGroup that holds from data info the DatePicker dialog shows up. */
        LinearLayout linearLayoutPickToDate = findViewById(R.id.to_date);
        linearLayoutPickToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // DateUtils.buildMyDate returns a date as String with this pattern: "yyyy-MM-dd"
                                fullToDateString = DateUtils.buildMyDate(year, month + 1, day); // set "to-date" with TODAY date.
                                // DateUtils.datePickerFormat receives a date as String as "yyyy-MM-dd" and returns with the pattern:  LLL dd, yyyy
                                textViewToDateSelected.setText(DateUtils.datePickerFormat(fullToDateString));
                                // Update the variable "toDate" to send it to NewsListActivity when the GO (Search Action) button is clicked
                                toDate = fullToDateString;
                            }
                        }, currentYear, currentMonth, currentDayOfMonth); // Today pre selected date on date picker DIALOG.
                datePickerDialog.show();
            }
        });

        /* Get input values and update variables to be sent by intent do NewsListActivity */
        Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderBy = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                String searchInput = searchTerms.getText().toString();

                if (!checkInternetConnection()) {
                    doToast(getString(R.string.check_your_connection));
                    return;
                }
                if (searchInput.isEmpty()) {
                    doToast(getString(R.string.type_a_term));
                    return;
                }
                q = searchInput;

                Intent intent = new Intent(getApplicationContext(), NewsListActivity.class);
                intent.putExtra(CONST_FROM_DATE_KEY, fromDate);
                Log.v("fromDate out Search", ">>>>>>>" + fromDate);
                intent.putExtra(CONST_TO_DATE_KEY, toDate);
                Log.v("toDate out Search", ">>>>>>>" + toDate);
                intent.putExtra(CONST_ORDER_BY_KEY, orderBy);
                intent.putExtra(CONST_Q_KEY, q);
                intent.putExtra(CONST_SEARCH_TYPE_KEY, CONST_SEARCH_TYPE_VALUE);
                startActivity(intent);
            }
        });

    } // Close onCreate method

    /**
     * Thia method makes the reuse of toast object to avoid toasts queue
     *
     * @param string is the text you want to show in the toast
     */
    private void doToast(String string) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }


    public boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
