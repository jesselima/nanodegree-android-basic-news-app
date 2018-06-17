package com.udacity.newsapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private String q,fromDate, toDate, fullFromDateString, fullToDateString;
    private String orderBy = "";

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final RadioGroup radioGroup = findViewById(R.id.search_radio_group);
        final EditText searchTerms = findViewById(R.id.search_q);

        /* Date picker implementation */
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        textViewFromDateSelected = findViewById(R.id.from_date_selected);
        textViewFromDateSelected.setText(DateUtils.datePickerFormat(DateUtils.buildMyDate(currentYear, (currentMonth), currentDayOfMonth)));// ONE MONTH AGO FROM TODAY.

        textViewToDateSelected = findViewById(R.id.to_date_selected);
        textViewToDateSelected.setText(DateUtils.datePickerFormat(DateUtils.buildMyDate(currentYear, (currentMonth + 1), currentDayOfMonth)));// TODAY!!!

        fromDate = DateUtils.buildMyDate(currentYear, currentMonth, currentMonth);
        LinearLayout linearLayoutPickFromDate = findViewById(R.id.from_date);
        linearLayoutPickFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(SearchActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            fullFromDateString = DateUtils.buildMyDate(year, month, day); // DateUtils.buildMyDate returns a date as String with this pattern: "yyyy-MM-dd"
                            textViewFromDateSelected.setText(DateUtils.datePickerFormat(fullFromDateString)); // DateUtils.datePickerFormat receives a date as String as "yyyy-MM-dd" and returns with the pattern:  LLL dd, yyyy
                        }
                    }, currentYear, currentMonth, currentDayOfMonth); // Today pre selected date on date picker dialog.

                datePickerDialog.show();
            }
        });

        toDate = DateUtils.buildMyDate(currentYear, (currentMonth + 1), currentMonth);
        LinearLayout linearLayoutPickToDate = findViewById(R.id.to_date);
        linearLayoutPickToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(SearchActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            fullToDateString = DateUtils.buildMyDate(year, month, day);
                            textViewToDateSelected.setText(DateUtils.datePickerFormat(fullToDateString));
                        }
                    }, currentYear, currentMonth, currentDayOfMonth); // Today pre selected date on date picker dialog.
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
                if (searchInput.isEmpty()){
                    doToast("Please, type one or more terms.");
                    return;
                }
                q = searchInput;

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

    private void doToast(String string){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }

}
