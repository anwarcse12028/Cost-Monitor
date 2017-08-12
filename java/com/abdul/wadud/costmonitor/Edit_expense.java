package com.abdul.wadud.costmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Techsoft - 001 on 4/22/2017.
 */

public class Edit_expense extends Activity {
    ListView listView;
    List<Edit_expense_List> listExpense;
    Edit_expense_adapter adapter;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_expense);

        listView = (ListView)findViewById(R.id.listView_edit_expense);
        loadListView();
    }

    public void loadListView(){
        DBHelper db = new DBHelper(getApplicationContext());
        listExpense = db.getAllExpenses();
        adapter = new Edit_expense_adapter(getApplicationContext(), listExpense);
        listView.setAdapter(adapter);

    }
}
