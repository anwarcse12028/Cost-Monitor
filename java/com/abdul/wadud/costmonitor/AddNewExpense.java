package com.abdul.wadud.costmonitor;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewExpense extends Fragment implements AdapterView.OnItemSelectedListener{
    ListView listView;
    Button btnSave, btnCancel;
    EditText editAmount, editDate, editNote;
    Calendar calendar;
    private int year, month, day;


    public AddNewExpense() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab2, container, false);

        listView = (ListView)view.findViewById(R.id.listCategory);
        DBHelper dbHelper = new DBHelper(getContext());
        List<String> labels = dbHelper.getAllCategory();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, labels);
        listView.setAdapter(dataAdapter);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        editAmount = (EditText)view.findViewById(R.id.editAmount);
        editDate = (EditText)view.findViewById(R.id.editDate);
        editNote = (EditText)view.findViewById(R.id.editNote);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);
        btnSave = (Button)view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            String category_add = listView.getSelectedItem().toString();
            String amount = editAmount.getText().toString();
            String date = editDate.getText().toString();
            String note = editNote.getText().toString();


            if (category_add.trim().length() > 0) {
                DBHelper db = new DBHelper(getContext());
                db.insertAdd_Expense(category_add,amount,date,note);

                editAmount.setText("");
                editDate.setText("");
               // editNote.setText("");

                InputMethodManager imm = (InputMethodManager)
                       getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(listView.getWindowToken(), 0);

                InputMethodManager imm_amount = (InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm_amount.hideSoftInputFromWindow(editAmount.getWindowToken(), 0);

                InputMethodManager imm_date = (InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm_date.hideSoftInputFromWindow(editDate.getWindowToken(), 0);

                InputMethodManager imm_note = (InputMethodManager)
                      getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm_note.hideSoftInputFromWindow(editNote.getWindowToken(), 0);

            } else {
                Toast.makeText(getContext(), "Please enter label name",
                        Toast.LENGTH_SHORT).show();
            }

        }
        });

        return view;
    }

    public void setDate(View view) {
        getActivity().showDialog(999);
        Toast.makeText(getContext(), "Choose date", Toast.LENGTH_SHORT).show();
        return;
    }


    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(getContext(), myDateListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener  = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day){
        editDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();
        listView.getSelectedItem().toString();
        Toast.makeText(parent.getContext().getApplicationContext(), "You Selected: " + label, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
