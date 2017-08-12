package com.abdul.wadud.costmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by anwar on 3/2/2017.
 */

public class Tab1 extends Activity {
    ListView listView;
    Button btnAdd;
    Button cancel;
    EditText inputLabel;
    List<Tab1_ListView> listViews;
    Tab1_Adapter tab1_adapter;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);

        listView = (ListView) findViewById(R.id.listAddCategory);
        btnAdd = (Button) findViewById(R.id.btnAddCategory);
        cancel = (Button) findViewById(R.id.btnCancelAdd);
        inputLabel = (EditText) findViewById(R.id.week_view_category);

        loadListView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String category_name = inputLabel.getText().toString();

                if (category_name.trim().length() > 0) {
                    DBHelper db = new DBHelper(getApplicationContext());
                    db.insertCategory(category_name);


                    inputLabel.setText("");

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);

                    Toast.makeText(getApplication(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                    loadListView();

                } else{
                    Toast.makeText(getApplication(), "Please enter category name",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
    }

    public void loadListView(){
        dbHelper = new DBHelper(getApplicationContext());
        listViews = dbHelper.getCategoryName();
        tab1_adapter = new Tab1_Adapter(getApplicationContext(), listViews);
        listView.setAdapter(tab1_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditBox(listViews.get(position).getName().toString(), position);
            }
        });
    }

    public void showEditBox(final String oldItem, final Integer id){
        final Dialog dialog = new Dialog(Tab1.this);
        dialog.setCancelable(true);
        dialog.setTitle("Update / Delete Category");
        dialog.setContentView(R.layout.edit_category);
        final EditText editText = (EditText)dialog.findViewById(R.id.editCategory);
        editText.setText(oldItem);
        Button btn = (Button)dialog.findViewById(R.id.btnDone);
        Button delete = (Button)dialog.findViewById(R.id.btnCancelEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper db = new DBHelper(getApplicationContext());
                //Update Category
                int ids = listViews.get(id).getId();
                tab1_adapter.notifyDataSetChanged();

                String Category_name = editText.getText().toString();
                editText.setText("");
                db.updateCategory(ids,Category_name);

                Toast.makeText(Tab1.this, "Category name edited to "+ Category_name, Toast.LENGTH_SHORT).show();
                loadListView();
                dialog.dismiss();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final AlertDialog.Builder dialog1 = new AlertDialog.Builder(Tab1.this)
                        .setTitle("Delete "+listViews.get(id).getName().toString()+"?")
                        .setMessage("Make sure this item is empty or transferred to other item. Otherwise all data related to this item will be deleted.")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                dialog1.dismiss();
                                final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Tab1.this)
                                        .setMessage("Are you sure you want to delete this item?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialogDel, int which) {
                                                String value  = listViews.get(id).getName().toString();
                                                String deleted= " is Deleted!";
                                                dbHelper.deleteCategory(value);
                                                dbHelper.deleteAddCategory(value);
                                                tab1_adapter.notifyDataSetChanged();
                                                loadListView();

                                                Toast.makeText(Tab1.this,"Category  " +value +deleted , Toast.LENGTH_SHORT).show();

                                                dialogDel.dismiss();

                                            }

                                        })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialogdel1, int which) {
                                                dialogdel1.dismiss();
                                            }
                                        });
                               dialogDelete.show();
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog2, int which) {

                            /*
                                String value = listViews.get(id).getName().toString();
                                if (value.trim().length() > 0) {
                                    dbHelper.insertArchive(value);
                                    editText.setText("");

                                    InputMethodManager i = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    i.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                                    Toast.makeText(Tab1.this, value + " Successfully moved to Archive", Toast.LENGTH_SHORT).show();

                                    loadListView();
                                    */

                                    dialog2.dismiss();
                                    dialog.dismiss();

                            }
                        });
                        dialog1.show();

            }

        });
        dialog.show();

    }

}


