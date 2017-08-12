package com.abdul.wadud.costmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


/**
 * Created by anwar on 3/2/2017.
 */

public class Tab2 extends Activity implements AdapterView.OnItemSelectedListener {

    EditText amount_add;
    EditText date_add;
    EditText note;
    Button button;
    Button buttonCancel;
    Calendar calendar;
    Spinner spinner;
    private int year, month, day;
    final Context context = this;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab4);

        amount_add = (EditText) findViewById(R.id.editTextAmount);
        date_add = (EditText) findViewById(R.id.date);
        note = (EditText) findViewById(R.id.editNote);
        button = (Button) findViewById(R.id.btnAddExpense);
        buttonCancel = (Button) findViewById(R.id.btnCancelExpense);
        spinner = (Spinner) findViewById(R.id.list_spinner);
        spinner.setOnItemSelectedListener(this);
        loadListView();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptsView);
            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialog);

        alertDialogBuilder
                .setCancelable(false)
                .

            setPositiveButton("OK",
                                      new DialogInterface.OnClickListener() {
                public void onClick (DialogInterface dialog,int id){
                    amount_add.setText(userInput.getText());
                }
            }) .
                    setNegativeButton("Cancel",
                                      new DialogInterface.OnClickListener() {
                public void onClick (DialogInterface dialog,int id){
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        amount_add.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialog);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        amount_add.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
            });
        buttonCancel.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                finish();
            }
            });

        button.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View arg0){
                String category_add = spinner.getSelectedItem().toString();
                String amount = amount_add.getText().toString();
                String date = date_add.getText().toString();
                String notes = note.getText().toString();

                if (category_add.trim().length() > 0) {
                    DBHelper db = new DBHelper(getApplicationContext());
                    db.insertAdd_Expense(category_add, amount, date, notes);

                    amount_add.setText("");
                    date_add.setText("");
                    note.setText("");


                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(spinner.getWindowToken(), 0);

                    InputMethodManager imm_amount = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm_amount.hideSoftInputFromWindow(amount_add.getWindowToken(), 0);

                    InputMethodManager imm_date = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm_date.hideSoftInputFromWindow(date_add.getWindowToken(), 0);

                    InputMethodManager imm_note = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm_note.hideSoftInputFromWindow(note.getWindowToken(), 0);
                    Toast.makeText(Tab2.this, "New Expense Added!", Toast.LENGTH_LONG).show();

                 for(int i = 0; i < category_add.length(); i++);
                    if(category_add == null) {
                        Toast.makeText(getApplicationContext(), "Please Add Category first!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter label name",
                            Toast.LENGTH_SHORT).show();
                }

            }
            });
    }

    private void loadListView() {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        List<String> labels = dbHelper.getAllCategory();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(dataAdapter);

    }

    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "Choose date", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        date_add.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();
        spinner.getSelectedItem().toString();
        //Toast.makeText(parent.getContext(), "You Selected: " + label, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Please select category", Toast.LENGTH_LONG).show();
    }
}

