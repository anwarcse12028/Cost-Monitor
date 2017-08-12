package com.abdul.wadud.costmonitor;

import android.app.Activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class TabHistory_week extends Activity{
    ListView list;
    List<TabHistory_Week_List> listWeek;
    TabHistory_Week_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhistory_week);

        list = (ListView) findViewById(R.id.listview_week);
        loadListView();

    }

    private void loadListView(){
        DBHelper db = new DBHelper(getApplicationContext());
        listWeek = db.getHistoryList();
        adapter = new TabHistory_Week_Adapter(getApplicationContext(), listWeek);
        list.setAdapter(adapter);

    }

}
