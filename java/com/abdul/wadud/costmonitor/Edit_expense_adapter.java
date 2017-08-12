package com.abdul.wadud.costmonitor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Techsoft - 001 on 4/22/2017.
 */

public class Edit_expense_adapter extends BaseAdapter {
    Context context;
    List<Edit_expense_List> lists;

    public Edit_expense_adapter(Context context, List<Edit_expense_List> lists){
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.edit_expense_list, null);

        TextView name = (TextView)view.findViewById(R.id.txtedit_expense_name);
        TextView amount = (TextView)view.findViewById(R.id.txtedit_expense_amount);
        TextView date = (TextView)view.findViewById(R.id.txtedit_expense_date);
        TextView note = (TextView)view.findViewById(R.id.txtedit_expense_note);


        name.setText(lists.get(position).getName());
        amount.setText(lists.get(position).getAmount());
        date.setText(lists.get(position).getDate());
        note.setText(lists.get(position).getNote());

        return view;
    }
}
