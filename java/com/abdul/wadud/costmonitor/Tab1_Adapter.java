package com.abdul.wadud.costmonitor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Techsoft - 001 on 4/12/2017.
 */

public class Tab1_Adapter extends BaseAdapter {

    Context context;
    List<Tab1_ListView> records;


    public Tab1_Adapter (Context context, List<Tab1_ListView> records){
        this.context = context;
        this.records = records;

    }


    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = View.inflate(context, R.layout.tab1_list, null);

        TextView txtCategoryName = (TextView)view.findViewById(R.id.txtCategoryName);
        txtCategoryName.setText(records.get(position).getName());

        return view;
    }

}
