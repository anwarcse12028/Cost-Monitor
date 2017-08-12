package com.abdul.wadud.costmonitor;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Techsoft-003 on 3/29/2017.
 */

public class PieGraph extends Fragment{

    public PieGraph(){
    }
    PieChart mChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pieggraph, container, false);

        mChart = (PieChart) view.findViewById(R.id.pieChartGraph);
        addData();

        Legend l=mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
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
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();

        for (int i = 0; i < queryYData().size(); i++)
            yVals.add(new PieEntry(queryYData().get(i), i));

        List<String> xVals = new ArrayList<String>();

        for (int i = 0; i < queryXData().size(); i++)
            xVals.add(queryXData().get(i));

        //create pie data set
        PieDataSet dataSet=new PieDataSet(yVals,"Expenses");
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

    }
}
