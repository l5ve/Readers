package com.capstone.readers;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/** 2번째 메뉴인 추천페이지 화면을 나타내는 프래그먼트
 *
 */
public class Menu2Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private String name;
    private TextView profile_name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu2, container, false);

        // Get Username
        name = ((MyApp) getActivity().getApplication()).getUser_name();
        profile_name = (TextView) fv.findViewById(R.id.profile_name);
        profile_name.setText(name);


        /************************* 아래 부터 선호 장르 통계 그래프 표시 */

        /** Retrive chart on Fragment*/
        HorizontalBarChart chart = (HorizontalBarChart) fv.findViewById(R.id.chart);

        /** settings */
        // -1. Axis
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        // -2. grid property
        chart.setDrawGridBackground(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        chart.setDrawValueAboveBar(true);

        // -3.
        chart.getDescription().setEnabled(false); // 효력x
        chart.setNoDataText("데이터가 없습니다");  // 효력x
        chart.setNoDataTextColor(Color.parseColor("#281e42"));
        chart.setHorizontalFadingEdgeEnabled(true);


        /** set data */
        // -1. Labels
        ArrayList xAxisLabel = new ArrayList();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));


        // -2. Data (Temporary)
        ArrayList values = new ArrayList();
        for (int i = 0; i < 13; i++) {
            values.add(new BarEntry(i, (float) i+1));
        }


        BarDataSet set1;
        set1 = new BarDataSet(values, "선호 장르 통계");
        // Setting bar colors
        set1.setColors(new int[] {  Color.parseColor("#281e42"),
                                    Color.parseColor("#493e5f"),
                                    Color.parseColor("#6a617c"),
                                    Color.parseColor("#8e869c"),
                                    Color.parseColor("#b2adbc"),
                                    Color.parseColor("#d8d5dd")
                                });
        // set1.setColors(ColorTemplate.MATERIAL_COLORS);
        //             or ColorTemplate.VORDIPLOM_COLORS    for 알록달록~~

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new StackedValueFormatter(false, "", 1));
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        chart.setTouchEnabled(false);

        // need to implement click event listener
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // fire up event
                e.getData();
                Log.d("VAL SELECTED",
                        "Value: " + e.getY() + ", xIndex: " + e.getX()
                                + ", DataSet index: " + h.getDataSetIndex());
            }

            @Override
            public void onNothingSelected() {
                Log.d("BAR_CHART_SAMPLE", "nothing selected X is ");
            }
        });


        chart.setData(data);
        chart.setFitBars(true);
        chart.animateY(1000);
        chart.invalidate();

        return fv;
    }

    // 설정값을 불러오는 함수
    private void load() {
        name = ((MyApp) getActivity().getApplication()).getUser_name();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}