package com.abdul.wadud.costmonitor;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Techsoft-003 on 3/9/2017.
 */

public class Graph_all extends Fragment {

    public Graph_all(){

    }

    PieChart mChart;
    ListView listView;
    List<Graph_all_List> listOverview;
    Graph_all_Adapter adapterOverview;
    ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pieggraph, container, false);

        mChart = (PieChart) view.findViewById(R.id.pieChartGraph);
        listView = (ListView)view.findViewById(R.id.pieGraphListView);

        loadListView();
        addData();

        Legend l=mChart.getLegend();
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        return view;
    }

    public ArrayList<String> queryXData(){
        DBHelper db = new DBHelper(getActivity());
        SQLiteDatabase sqlite = db.getReadableDatabase();
        ArrayList<String> xNewData = new ArrayList<String>();
        String query="SELECT category_add FROM Add_Expense GROUP BY category_add ";
        Cursor cursor = sqlite.rawQuery(query,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            xNewData.add(cursor.getString(0));
        }
        cursor.close();
        return xNewData;
    }

    public ArrayList<Integer> queryYData(){
        DBHelper db = new DBHelper(getActivity());
        SQLiteDatabase sqliteY = db.getReadableDatabase();
        ArrayList<Integer> yNewData= new ArrayList<Integer>();
        String query="SELECT category_add, SUM(amount) AS total FROM Add_Expense GROUP BY category_add";
        Cursor cursor=sqliteY.rawQuery(query,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            yNewData.add(cursor.getInt(cursor.getColumnIndexOrThrow("total")));
        }
        cursor.close();
        return yNewData;
    }

    private void addData() {
        final ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();

        for (int i = 0; i < queryYData().size(); i++)
            yVals.add(new PieEntry(queryYData().get(i), i));

        final List<String> xVals = new ArrayList<String>();

        for (int i = 0; i < queryXData().size(); i++)
            xVals.add(queryXData().get(i).toString());

        //create pie data set
        PieDataSet dataSet=new PieDataSet(yVals,"");
        dataSet.setSliceSpace(3); // space between each arc slice
        dataSet.setSelectionShift(5);

        //add many colors
        ArrayList<Integer> colors=new ArrayList<Integer>();

        for(int c: ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for(int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for(int c: ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for(int c: ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for(int c: ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data=new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
        mChart.animateXY(2000, 2000);
        mChart.setHoleRadius(70f);
        mChart.setCenterText("Expense\nBudget");
        mChart.setCenterTextSize(30);
        mChart.setCenterTextColor(Color.CYAN);

    }

    public void loadListView() {
        DBHelper db = new DBHelper(getContext());
        listOverview = db.getPieGrapgListView();
        adapterOverview = new Graph_all_Adapter(getContext(), listOverview);
        listView.setAdapter(adapterOverview);
    }

}
