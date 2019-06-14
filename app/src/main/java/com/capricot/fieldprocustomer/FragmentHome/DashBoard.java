package com.capricot.fieldprocustomer.FragmentHome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capricot.fieldprocustomer.R;

/**
 * Created by BalajiPrabhu on 9/13/2017.
 */

public class DashBoard  extends Fragment {
    private View view;
    Context cm2;
    public DashBoard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        PieChart pieChart = (PieChart) view.findViewById(R.id.piechart);
//        pieChart.setUsePercentValues(true);
//
//        // IMPORTANT: In a PieChart, no values (Entry) should have the same
//        // xIndex (even if from different DataSets), since no values can be
//        // drawn above each other.
//        ArrayList<Entry> yvalues = new ArrayList<Entry>();
//        yvalues.add(new Entry(10f, 0));
//        yvalues.add(new Entry(15f, 1));
//        yvalues.add(new Entry(12f, 2));
//        yvalues.add(new Entry(25f, 3));
//
//
//        PieDataSet dataSet = new PieDataSet(yvalues, "Result");
//
//        ArrayList<String> xVals = new ArrayList<String>();
//
//        xVals.add("Upcoming");
//        xVals.add("Scheduled");
//        xVals.add("Esclated");
//        xVals.add("Completed");
//
//
//        PieData data = new PieData(xVals, dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        pieChart.setData(data);
//        pieChart.setDescription("");
//
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setTransparentCircleRadius(25f);
//        pieChart.setHoleRadius(25f);
//
//        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        data.setValueTextSize(13f);
//        data.setValueTextColor(Color.DKGRAY);
//        pieChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) cm2);
//
//        pieChart.animateXY(1400, 1400);
//

        return view;
    }


}
