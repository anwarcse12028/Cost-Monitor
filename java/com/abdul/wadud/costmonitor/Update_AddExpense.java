package com.abdul.wadud.costmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Techsoft - 001 on 4/25/2017.
 */

public class Update_AddExpense extends Activity{
    ListView listView;
    List<Edit_expense_List> listExpense;
    Edit_expense_adapter adapter;
    DBHelper dbHelper;
    Button btnBack;
    Dialog transferDialog;
    Integer ids = 0;
    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_expense);

        listView = (ListView) findViewById(R.id.listView_edit_expense);
        btnBack = (Button) findViewById(R.id.btnBack);

        loadListView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loadListView(){
        DBHelper db = new DBHelper(getApplicationContext());
        listExpense = db.getAllExpenses();
        adapter = new Edit_expense_adapter(getApplicationContext(), listExpense);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = listExpense.get(position).getName().toString();
                String amount = listExpense.get(position).getAmount().toString();
                String date = listExpense.get(position).getDate().toString();
                String note = listExpense.get(position).getNote().toString();
                ids = listExpense.get(position).getId();

                ShowDialogBox(name, amount, date, note,  position);
                ShowTransferBox(name, amount, date, note, position);

            }
        });
    }

    public void ShowTransferBox(final String ItemName, final String ItemAmount, final String ItemDate, final String ItemNote, final Integer id){
        transferDialog = new Dialog(Update_AddExpense.this);
        transferDialog.setCancelable(true);
        transferDialog.setTitle("Transfer Item to:");
        transferDialog.setContentView(R.layout.transfer_item);
        final Spinner spinner = (Spinner)transferDialog.findViewById(R.id.transfer_spinner);

        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        List<String> labels = dbHelper.getAllCategory();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(dataAdapter);

        final TextView txtAmount = (TextView)transferDialog.findViewById(R.id.transfer_txtAmount);
        txtAmount.setText(ItemAmount);
        final TextView txtDate = (TextView)transferDialog.findViewById(R.id.transfer_txtDate);
        txtDate.setText(ItemDate);
        final TextView txtNote = (TextView)transferDialog.findViewById(R.id.transfer_txtNote);
        txtNote.setText(ItemNote);
        Button btnSave = (Button)transferDialog.findViewById(R.id.transfer_btnSave);
        Button btnCancel = (Button)transferDialog.findViewById(R.id.transfer_btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getApplicationContext());
                String name = spinner.getSelectedItem().toString();
                String amount = txtAmount.getText().toString();
                String date = txtDate.getText().toString();
                String note = txtNote.getText().toString();

                int idDelete = listExpense.get(id).getId();

                db.updateCategoryAdd(idDelete, name, amount, date, note);

                Toast.makeText(Update_AddExpense.this, "Items Successfully transferred to "+name, Toast.LENGTH_SHORT).show();
                transferDialog.dismiss();
                loadListView();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferDialog.dismiss();
            }
        });

    }

    public void ShowDialogBox(final String ItemName, final String ItemAmount, final String ItemDate, final String ItemNote,  final Integer id) {
        final Dialog dialog = new Dialog(Update_AddExpense.this);
        dialog.setCancelable(true);
        dialog.setTitle("Update / Delete Expense");
        dialog.setContentView(R.layout.edit_expense_in_history);
        final TextView del_id = (TextView) dialog.findViewById(R.id.txtID);
        del_id.setText(""+ids);
        final TextView editText = (TextView) dialog.findViewById(R.id.editCatName);
        editText.setText(ItemName);
        final EditText editAmount = (EditText) dialog.findViewById(R.id.editCatAmount);
        editAmount.setText(ItemAmount);
        final EditText editDate = (EditText)dialog.findViewById(R.id.editCatDate);
        editDate.setText(ItemDate);
        final EditText editNote = (EditText) dialog.findViewById(R.id.editCatNote);
        editNote.setText(ItemNote);
        Button btn = (Button) dialog.findViewById(R.id.btnCatEdit);
        final Button delete = (Button) dialog.findViewById(R.id.btnCatCancel);


        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Update_AddExpense.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editDate.setText((monthOfYear + 1)+ "/" + dayOfMonth + "/" + year);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getApplicationContext());

                int ids = listExpense.get(id).getId();
                adapter.notifyDataSetChanged();

                String Category_name = editText.getText().toString();
                String amount = editAmount.getText().toString();
                String date = editDate.getText().toString();
                String note = editNote.getText().toString();
                editText.setText("");
                editAmount.setText("");
                editDate.setText("");
                editNote.setText("");

                db.updateCategory(ids, Category_name);
                db.updateCategoryAdd(ids, Category_name, amount, date, note);
                Toast.makeText(Update_AddExpense.this, "Expense updated!", Toast.LENGTH_SHORT).show();

                loadListView();
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final AlertDialog.Builder dialog1 = new AlertDialog.Builder(Update_AddExpense.this)
                        .setTitle("Delete / Transfer Items")
                        .setMessage("Do you want delete this Items?" + "\n" + "Id: "+ ids + "\n" +"Name :  " + listExpense.get(id).getName().toString() + "\n" + "Amount :  " + listExpense.get(id).getAmount() + "\n" +
                                "Date :  " + listExpense.get(id).getDate().toString() + "\n" + "Note :  " + listExpense.get(id).getNote().toString() + "\n" + "" + "\n" +
                                "Or do you want to transfer this data to another Category?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog2, int which) {
                                transferDialog.dismiss();
                                final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Update_AddExpense.this)
                                        .setMessage("Are you sure you want to delete this items?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String name = listExpense.get(id).getName().toString();
                                                String deleted = "deleted!";

                                                dbHelper.deleteAddExpense(ids);
                                               // dbHelper.deleteAddCategory(name);
                                                adapter.notifyDataSetChanged();
                                                loadListView();
                                                Toast.makeText(Update_AddExpense.this, "Expense " + name + "is " + deleted, Toast.LENGTH_SHORT).show();

                                                dialog.dismiss();
                                            }
                                        })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        dialogDelete.show();
                            }
                        })

                        .setNegativeButton("Transfer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog3, int which) {
                                transferDialog.show();

                            }
                        });
                dialog1.show();
            }
        });

        dialog.show();
    }


}
