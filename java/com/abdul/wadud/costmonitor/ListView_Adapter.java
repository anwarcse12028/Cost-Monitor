package com.abdul.wadud.costmonitor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Techsoft-003 on 3/28/2017.
 */

public class ListView_Adapter extends BaseAdapter {
    Context context;
    List<ListView_List>listView_lists;

    public ListView_Adapter(Context context, List<ListView_List> listView_lists){
        this.context = context;
        this.listView_lists = listView_lists;
    }
    @Override
    public int getCount() {
        return listView_lists.size();
    }

    @Override
    public Object getItem(int position) {
        return listView_lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listView_lists.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.listview_name, null);
        TextView txtName = (TextView)view.findViewById(R.id.txtNameList);
        //RadioButton radioButton = (RadioButton)view.findViewById(R.id.btnRadio);

        txtName.setText(listView_lists.get(position).getName());
        //radioButton.setClickable(true);
        return view;
    }
}
