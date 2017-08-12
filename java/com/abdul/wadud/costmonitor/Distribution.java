package com.abdul.wadud.costmonitor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import static com.abdul.wadud.costmonitor.R.layout.fragment_fragment_distribution;


public class Distribution extends Fragment{

    public Distribution() {
        // Required empty public constructor
    }

    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(fragment_fragment_distribution, container, false);

        barChart = (BarChart)view.findViewById(R.id.barChartDist);
        createbarChart();

        return view;

    }

    public ArrayList<String> queryXData() {
        DBHelper db = new DBHelper(getActivity());
        SQLiteDatabase sqlite = db.getReadableDatabase();
        ArrayList<String> xNewData = new ArrayList<String>();
        String query = "SELECT category_add FROM Add_Expense GROUP BY category_add ";
        Cursor cursor = sqlite.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            xNewData.add(cursor.getString(0));
        }
        cursor.close();
        return xNewData;
    }

    public ArrayList<Integer> queryYData(){
        DBHelper db = new DBHelper(getActivity());
        SQLiteDatabase sqliteY = db.getReadableDatabase();
        ArrayList<Integer> yNewData= new ArrayList<>();
        String query="SELECT category_add, SUM(amount) AS total FROM Add_Expense GROUP BY category_add";
        Cursor cursor=sqliteY.rawQuery(query,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            yNewData.add(cursor.getInt(cursor.getColumnIndexOrThrow("total")));
        }
        cursor.close();
        return yNewData;
    }

    public void createbarChart(){
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i < queryYData().size(); i++)
            yVals.add(new BarEntry(queryYData().get(i), i));

        List<String> xVals = new ArrayList<String>();

        for(int i = 0; i < queryXData().size(); i++)
            xVals.add(queryXData().get(i).toString());

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

        BarDataSet dataSet = new BarDataSet(yVals, "Expense");
        dataSet.setColors(colors);

        BarData data = new BarData(dataSet);
        data.setDrawValues(true);
        data.setBarWidth(100f);

        barChart.setData(data);
        barChart.setEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDrawGridBackground(false);

        barChart.setFitBars(true);
        barChart.animateXY(2000, 2000);

    }
}


